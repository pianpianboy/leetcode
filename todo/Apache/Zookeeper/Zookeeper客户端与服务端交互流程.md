### Zookeeper客户端与服务端交互流程
- 问题一： 但在命令行输入 create /data 1之后做了哪些事情？ 然后又是如何与服务端交互。
- 问题二： 服务端收到create /data 1 命令之后进行了哪些处理？Watcher机制？ ACL机制？
- 问题三： 简述下NIO
- 问题四： 既然submitRequest()会往OutgoingQueue塞入一个Packet，那么什么时候取出OutgoingQueue中的数据？
- 问题五： Client是如何发送心跳的？心跳的逻辑在哪？
- 问题六： pendingQueue与outgoingQueue的区别？
- 问题七： Client执行命令--发送消息给客户端和从服务端接收命令返回，是属于生产者和消费者模型么？

### 问题一：
- 1、启动完脚本之后 执行ZookeeperMain中main()方法 ZookeeperMain main = new ZookeeperMain(args);
- 2、connectToZK() -->new Zookeeper()初始化Zookeeper，初始化Zookeeper的时候执行下列逻辑
    + 初始化参数cnxn及线程
        * 初始化ClientCnxn
        * 初始化SendThread
        * 初始化EventThread
    + start()方法
        * 调用SendThread.run()方法会去建立socket连接
            - 如果socket没有建立连接
                + socket建立完成后立即生成相关队列Queue
                + boolean immediateConnect = sock.connect(addr);if (immediateConnect) {//立刻连接成功 sendThread.primeConnection();}
                + sendThread.primeConnection();
                    * startConnect(InetSocketAddress addr);
                        - //NIOSocket连接 clientCnxnSocket.connect(addr);
            - 如果socket已经建立连接了
                + 计算超时时间：to = readTimeout - clientCnxnSocket.getIdleRecv();
                + 判断连接是否成功：if (state.isConnected()){//如果连接成功，就不断了ping
                    * sendPing();//进行ping 把ping包也加入到outgointQueue中
                    * clientCnxnSocket.updateLastSend();//把outgoingQueue中的packet包发送出去逻辑
                + 最后将outgoingQueue队列中的Packet数据取出来并传送出去
                    * clientCnxnSocket.doTransport(to, pendingQueue, outgoingQueue, ClientCnxn.this);


        * 调用EventThread().run()方法
    +
- 3、执行Main()方法中的main.run();方法,该方法并不是线程的run方法，是自己实现
    + 解析命令
    + 如果命令为create: if(cmd.euqals("create")) --> zk.create()
    + zk.create()
        * 声明RequestHeader、request、response
        * 然后通过cnxn.submitRequest(header,request,response);将请求传输到服务端
            - queuePacket()
        * 传输到服务端后submitRequest()会返回一个ReplyHeader
            - 执行 Packet packet = queuePacket(h,r,request,response)将数据塞入outgoingQueue,
            - 然后执行packet.wait()等待packet接收到结果后被唤醒（同步等待结果）
- 4、利用前面建立好的Socket连接进行IO操作
    + clientCnxnSocket.doTransport();
        * doIO(pendingQueue,outgoingQueue,cnxn)
            - 从outgoingQueue队列中取出第一个值--packet返回
            - 将packet中的bb(也就是byteBufer)通过socket发送给服务端
            - 当bb发送完成（if(!p.bb.hasRemaing()){//没有剩下的了}）的时候就将p(也就是packet)塞入pendingQueue,即pendingQueue.add(p); // 加入待回复的队列
    + d

- 5、执行完SendThread.readResponse()方法后（讲incomingBuffer中的数据写入packet的replyHdr），最后调用finishPacket(Packet p)
    + 如果同步操作执行：packet.notifyAll();
    + 如果异步操作执行：evetThread.queuePacket(packet);
    + 整个readResponse()是与subitRequest()--返回ReplyHeader 是对应。即subitmit()方法里的返回值是等readResponse()返回后notify submitRequest()返回的。


总结：

- 初始化socket
- zookeeper: 命令->Request->packet->outgoingQueue
- ClientCnxnSocketNIO:
    + SendThread线程
        * while()循环
            - 1. 如果socket没有连接，就会去连接
            - 2. 如果socket连接成功--> 客户端会发送一个ConnectRequest，接收服务端返回ConnectResponse(Evnet.none)
            - 3. 如果连接成功，会从OutgoingQueue中取packet(并remove),通过socket发送出去，同时，如果说penddingQueue里面会去放等待结果packet

### 简述下NIO
NIO比较核心的就是 Channel（通道）、Buffer（缓冲区）、Selector(选择器)

- channel
    + 相对于传统IO,Channel是双向的，即可以用来进行读操作，又可以用来进行写操作
- Buffer
    + NIO中所有的数据的读和写都离不开buffer，读取的数据只能放在Buffer中，写入的数据也是先写到Buffer中。
- Selector
    + 将Channel和Selector配合使用，必须将channel注册到selector上，通过SelectableChannel.register()方法来实现；
    + 时钟不同类型的事件：Connect、Accept、Read、Write
```java
channel.configureBlocking(false);
SelectionKey key = channel.register(selector,SelectionKey.OP_READ);
```

#### 问题五： Client是如何发送心跳的？心跳的逻辑在哪？
心跳的逻辑在 ClientCnxn.run()方法中
```java
if (state.isConnected()) {
    // 如果连接成功，就不断了ping

    //1000(1 second) is to prevent race condition missing to send the second ping
    /also make sure not to send too many pings when readTimeout is small
    int timeToNextPing = readTimeout / 2 - clientCnxnSocket.getIdleSend() -
                                ((clientCnxnSocket.getIdleSend() > 1000) ? 1000 : 0);
    //send a ping request either time is due or no packet sent out within MAX_SEND_PING_INTERVAL
    if (timeToNextPing <= 0 || clientCnxnSocket.getIdleSend() > MAX_SEND_PING_INTERVAL) {
        // 进行ping,把ping包也加入到outgoingQueue中
        sendPing();
        clientCnxnSocket.updateLastSend();
    } else {
        if (timeToNextPing < to) {
            to = timeToNextPing;
        }
    }
}
```

#### 问题六：pendingQueue与outgoingQueue的区别？
- outgoingQueue 是请求发送队列，是client存储需要被发送到server端的Packet队列
- pendingQueue是已经从client发送，但是需要等待Server响应的packet队列











