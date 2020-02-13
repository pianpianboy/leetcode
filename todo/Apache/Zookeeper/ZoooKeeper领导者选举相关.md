### ZoooKeeper领导者选举相关
- 问题一：领导者选举算法的作用
- 问题二：服务器集群启动的时候是如何进行领导者选举？
- 问题三：当leader挂掉了是如何进行领导者选举的？
- 问题四：QuorumCnxManager.java这个类的作用？



#### 问题一：领导者选举算法的作用
领导者选举算法的作用就是确定服务器节点的角色

#### 问题四：QuorumCnxManager.java这个类的作用？
**QuorumCnxManager这个类就是传输层：**负责各台服务器之间的底层Leader选举过程中的网络通信。

> This class implements a connection manager for leader election using TCP. It  maintains one connection for every pair of servers. The tricky part is to guarantee that there is exactly one connection for every pair of servers that are operating correctly and that can communicate over the network.

##### QuorumCnxManager这个类中的属性分析
- ConcurrentHashMap<Long,SendWorker> senderWorkerMap =====> [serverId: SendWorker] 即senderWorkerMap中存储每台服务器对应的SendWorker，SendWork是一个线程，负责发送数据--从queueMap取出数据发送给SendWorkder对应的serverId，其中serverId就是myId，在代码中也是sid。
- ConcurrentHashMap<Long,ArrayBlockingQueue<ByteBuffer>> queueSendMap ====> [serverId:Queue]-- queueSendMap保存需要发送给各个服务器的消息队列。
- ArrayBlockingQueue<Message> recvQueue =====> 本台服务器接收到的消息

senderWorkerMap和queueSendMap是本Server发送给其它Server所需要的类，senderWorkerMap和queueSendMap配合使用，queueSendMap中存储数据，senderWorkerMap从queueSendMap中取出数据。两个Map负责发送数据，一个队列queue负责接收数据

- Listener.java： Thread to listen on some port; 是QuorumCnxManager的内部类


#### 流程-1 QuorumPeerMain
```java
- QuorumPeerMain{}.main()}
- - main.initializeAndRun(args);
- - - runFromConfig(config); //集群运行模式
- - - - ServerCnxnFactory cnxnFactory = ServerCnxnFactory.createFactory();
- - - - quorumPeer = getQuorumPeer();
- - - - quorumPeer.start();
- - - - - loadDataBase();// 加载数据到DataBase->DataTree
- - - - - cnxnFactory.start();//开启读取数据线程
- - - - - startLeaderElection();// 进行领导者选举，确定服务器的角色，再针对不同的服务器角色进行初始化
- - - - - super.start();//运行QuorumPeer类的run方法。
```

#### 流程-2 QuorumPeer
```java
- startLeaderElection();
- - createElectionAlgorithm(electionType);
- - - QuorumPeer{}.createElectionAlgorithm();
- - - - qcm  = createCnxnManager();//负责个台服务器之间的底层Leader选举过程中的网络通信。
- - - - QuorumCnxManager.Listener listenr = qcm.listenr;
- - - - listener.start();
- - - - new FastLeaderElection(QuorumPeer self,qcm)
```

#### 流程-3 QuorumCnxManager 通信传输层逻辑
Listener是QuorumCnxManager的内部类
```java
- qcm  = createCnxnManager();//
- - QuorumCnxManager{}} new QuorumCnxManager()
- - - listener = new Listener(); // Starts listener thread that waits for connection requests
```

```java
//Thread to listen on some port
- listener.start()
- - QuorumCnxManager{}.Listener{}.run()
- - - Listener{}.run()方法使用的是TCP socket 而不是NIO了
```

#### 流程-4：FastLeaderElection应用层逻辑分析
```java
- new FastLeaderElection(QuorumPeer,QuorumCnxManager)
- - FastLeaderElection{}.starter() // 初始化sendqueue、recvqueue并且启动WorkSender和WorkerReceiver线程
- - ss = new ServerSocket();
- - ss.bind(addr);
- - while(!shutdown){ }
- - - Socket client = ss.accept();
- - - receiveConnection(client);//一个socket调用一次
- - - QuorumCnxManager{}.receiveConnection(Socket sock)
- - - - QuorumCnxManager{}.handleConnection(Socket sock, DataInputStream din)
- - - - - sid = din.readLong(); //sid 建立socket对方的sid
- - - - - if (sid < this.mySid) {  // sid是对方的sid，即本机器sid大于对方的，则取消对象机器到本机的socket连接，换成由本机器连对方
- - - - - - SendWorker sw = senderWorkerMap.get(sid);
- - - - - - sw.finish();//关闭 与对方sid 绑定的 SendWorker
- - - - - - closeSocket(sock);//关闭socket连接
- - - - - - connectOne(sid);// create new connection to server
- - - - - }
- - - - - else{
- - - - - -SendWorker sw = new SendWorker(sock, sid);
- - - - - - RecvWorker rw = new RecvWorker(sock, din, sid, sw);
- - - - - - sw.setRecv(rw);
- - - - - - senderWorkerMap.put(sid, sw);
- - - - - - queueSendMap.putIfAbsent(sid, new ArrayBlockingQueue<ByteBuffer>(SEND_CAPACITY));
- - - - - - sw.start();
- - - - - - rw.start();
- - - - - }
```











