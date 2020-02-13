### Zookeeper服务端处理命令源码流程
- 问题一：服务端启动流程，即执行完zkServer.sh之后的执行逻辑是什么？
- 问题二：Zookeeper事务日志和快照的区别？
- 问题三：当Zookeeper Server收到了命令的执行逻辑
- 问题四：NIOServerCnxnFactory线程做了哪些事？
- 问题五：PreRequestProcessor的作用
- 问题六：Zookeeper持久化事务日志逻辑

#### 问题一：服务端启动流程，即执行完zkServer.sh之后的执行逻辑是什么？
- 执行zkServer.sh
    + 调用org.apache.zookeeper.server.quorum.QuorumPeerMain.main()方法
        * 执行main.initializeAndRun()
            - 配置初始化工作：就是将zoo.cfg中的内容加载到QuronumPeerConfig -- config中
            - 整个配置完成工作包括半数验证在 config.parse()中完成
        * 如果是集群模式（server.size()>0）:执行runFromConfig(config)
        * 如果是单机模式：执行ZookeeperServerMain.main();
            - 执行 zookeeperServerMain.initializeAndRun(args);
                + 执行ManagedUtil.registerLog4jMBeans(); 注册Log4j
                + 生成一个单机模式的config-->ServerConfig config = new ServerConfig(); 为了和集群模式的配置QuronumPeerConfig 区别开来
                + runFromConfig(config); **此处和集群模式调用了同样的方法，只是参数不一样，单机模式使用和集群模式不一样的配置类**
                    * new一个AOF事务日志工具类FileTxnSnapLog--txnLog
                    * ServerCnxnFactory.createFactory() 建立socket工厂类，工厂设计模式默认使用：NIOServerCnxnFactory
                        - 启动NIOServerCnxnFactory是一个线程
                        - public class NIOServerCnxnFactory extends ServerCnxnFactory implements Runnable
                    * 配置 cnxnFactory: cnxnFactory.configure();
                        - serverSocket.open()+ss.socket().bind(addr)+ss.register(selector, SelectionKey.OP_ACCEPT)
                    * 启动 cnxnFactory
                        - cnxnFactory.startup(zkServer);
                            + start(); //启动NIOServerCnxnFactory中的run方法
                                * start()调用的是：thread = new ZooKeeperThread(this, "NIOServerCxn.Factory:" + addr);
                                * 其中的this就是NIOServerCnxnFactory，因此运行的就是NIOServerCnxnFactory的run()方法
                            + setZookeeperServer(zks);
                            + zks.startdata(); restore sessions and data 加载数据
                                * 如果ZKDatabase是空的则 new ZKDatabase(this.tnxLogFactory)
                                * 如果ZKDatabase没有初始化，则loadData()
                                    - loadData()
                            + zks.startup();
                                * 如果sessionTracker为空，则创建一个sessionTracker()--session跟踪器
                                * startSessionTracker();
                                * 最重要的一步：setupRequestProcessors();
                                    - 这里比较重要，这里设置请求处理器，包括请求前置处理器和请求后置处理器
                                    - PreRequestProcessor.next = SyncRequestProcessor.next = FinalRequestProcessor
                                    - 三个processor都是线程
                                    - 数据流向
                                        + client: request-packet-outgoingqueue->socket
                                        + server: socket->packet->request->queue(submittedRequests)
                                    - firstProcessor.run()从queue中取出request,请求处理链来处理这个请求




#### 问题三：当Zookeeper Server收到了命令的执行逻辑
服务端接收请求：

- 1、创建事务日志（Aof）
- 2、定时生成DataBase的快照（DataBase-->DataTree-->DataNode）
- 3、更新内存，操作DataTree
- 4、只有上述几步都完成之后才会返回错误或正确信息

服务器启动的时候：
1、从事务日志文件里面读取数据加载到内存（生成DataBase）



#### 问题五：PreRequestProcessor的作用
- PreRequestProcessor
- processRequest()
- run(): 从队列中获取命令进行处理
    + 从submittedRequests中取出request ：Request request = submittedRequests.take();
    + pRequest(request); 处理命令的核心方法 this method inside the ProcessRequestThread
        * CreateRequest createRequest = new CreateRequest();
        * pRequest2Txn(request.type, zks.getNextZxid(), request, createRequest, true); 其中zks.getNextZxid()生成zxid
            - 创建事务AOF日志 request.txn = new CreateTxn(path, createRequest.getData(),newCversion);
            - 把修改记录加入到集合容器中去 addChangeRecord(parentRecord); 对当前节点的父节点修改
            - 对当前节点的修改addChangeRecord(new ChangeRecord(request.hdr.getZxid(), path, s,0, listACL)); 把修改的记录加到list中，然后有个线程从list中将修改记录取出来持久化，更新内存。
                + outstandingChanges = new ArrayList<ChangeRecord>();
                + final HashMap<String, ChangeRecord> outstandingChangesForPath
                + zks.outstandingChanges.add(c);
                + zks.outstandingChangesForPath.put(c.path, c);
        *  当pRequest()执行到最后// 调用Sync 持久化 nextProcessor.processRequest(request);
            - queuedRequests.add(request); //将request添加如queuedRequests---LinkedBlockingQueue<Request> queuedRequests
            - 线程run()方法
        * 调用 FinalRequestProcessor.processRequest()
            - 从outstandingChanges和outstandingChangesForPath中remove掉值。












