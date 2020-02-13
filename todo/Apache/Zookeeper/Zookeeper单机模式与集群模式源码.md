### Zookeeper单机模式与集群模式源码
- 问题一、SendThread的作用
- 问题二、当关闭或者CloseSession后服务端做了哪些事情
- 问题三、create一个节点并添加watcher是在什么时候添加watcher的？Watcher会随着packet一起发送到服务端么？
- 问题四、为什么在处理Watches的时候需要有两个Map<String, Set<Watcher>>？即在ZKWatchManager中为何有两个Map？dataWatches与existWatches的区别和关系？
- 2020.2.3 视频4：40：51
- 问题五：客户端是怎么把事件给抛出来的？
- 问题六：服务端是如何处理请求的？
- 问题七：WatchManager中watchTables和watch2Paths有什么用？
- 问题八：Watcher什么时候被加入到zk的服务器或者说Watcher什么时候被加入到watchTables和watch2Paths中
- 问题九：客户端向服务端注册Watcher的时候，是吧Watcher对象都传递过去么？
- 问题十：客户端注册Watcher的时候都会实现一个process，那么服务端需要实现process么？服务端其实也需要实现一个process，当服务端事件触发时进行处理
- 问题十一：quit命令及close session之后客户端Client和服务端Server都做了哪些事？
- 问题十二：ACL机制中各个命令执行逻辑

#### 问题一：SendThread的作用
- 服务端抛出来的事件，客户端会存在EventThread.waitingEvents队列当中；
    + 将WatchedEvent event 包装成WatcherSetEventPair pair
    + waitingEvents.add(pair);
    + 线程run()方法
        * 从waitingEvents队列中取出数据 event = waitingEvents.take();
        * processEvent(evnet)

#### 问题二：

#### 问题三
- 在ClientCnxn类的内部类：SendThread类中方法 readResponse(incomingBuffer)中 最终执行 finishPacket(packet);
    + finishPacket(packet)的作用就是从packet中取出对应的Watcher并注册到ZKWatchManager中去
    + 为什么要在最后执行添加watcher，因为只有create操作成功返回后执行添加watcher才合适，不然中间失败了怎么办
    + p.watchRegistration.register(p.replyHeader.getErr())
        * Register the watcher with the set of watches on path.
        * watchers = new HashSet<Watcher>();
        * watches.put(clientPath, watchers);

ClientCnxn{}.EventThread{}.finishPacket() --> ClientCnxn{}.EventThread{}.queueEvent()-->Zookeeper{}.materialize()【Set<Watcher>】==> pair = new WatcherSetEventPair(Set<Watcher>)==> LinkedBlockingQueue<Object> waitingEvents.add(pair);

将pair(Set<Watcher>)塞入到waitingEvents之后 什么时候取出来呢？

答案：EventThread的run()方法中。在run()方法中将waitingEvents中的Watcher Set取出来 ,取出来的值为event = waitingEvents.take();

ClientCnxn{}.EventThread{}.run() --> ClientCnxn{}.EventThread{}.processEvent(event) --> 如果是正常的正常的watch事件通知：watcher.process(pair.event);==>这个process是需要我们自己实现的，也就是在zookeeper操作的添加watcher的时候：如下代码所示

```java
client.getData("/luban", new Watcher() {
    @Override
    public void process(WatchedEvent event) {
        if(Event.EventType.NodeDataChanged.equals(event.getType())){
            System.out.println("数据发送了改变");
        }
    }
},stat);
```
**上述的过程就是客户端处理watcher的实现。**

#### 问题四、为什么在处理Watches的时候需要有两个Map<String, Set<Watcher>>？即在ZKWatchManager中为何有两个Map？dataWatches与existWatches的区别和关系？
在原生的Zookeeper的客户端添加的Watcher（监听器），只有当监听的事件第一次发生时才会触发watcher。那么为什么呢？

因为在zookeeper类中的eventThread中materialize()中的实现逻辑是 addTo(dataWatcher.remove(clientPath),result); +++ addTo(existWatches.remove(clientPath),result);


#### 问题六：服务端是如何处理请求的？
```java
- NIOServerCnxnFactory{}
- - run()
- - - NIOServerCnxn c.doIO(SelectionKey k)
- - - - NIOServerCnxn{}.readPayload();
- - - - - NIOServerCnxn{}.readRequest();
- - - - - - ZooKeeperServer{}.processPacket();
- - - - - - - ZooKeeperServer{}.submitRequest(Request si);
- - - - - - - - ZooKeeperServer{}.firstProcessor.processRequest(si); == PreRequestProcessor{}.processRequest(si)
- - - - - - - - - LinkedBlockingQueue<Request> submittedRequests.add(request);

最终将request请求加入到了ZooKeeperServer zks的队列 submittedRequests中了。

那么什么时候取出来呢？

PreRequestProcessor是一个线程：public class PrepRequestProcessor extends ZooKeeperCriticalThread implements RequestProcessor {}

在PreRequestProcessor的run()方法中：有while(true)循环
while{
    Request request = submittedRequests.take();
    pRequest(request);
}

- - - - - - - - - PreRequestProcessor{}.LinkedBlockingQueue<Request> submittedRequests.add(request);
- - - - - - - - - PreRequestProcessor{}.run()
- - - - - - - - - - PreRequestProcessor{}.pRequest()
- - - - - - - - - - - PreRequestProcessor{}.pRequest2Txn()
- - - - - - - - - - - - PreRequestProcessor{}.addChangeRecord(parentRecord);+ PreRequestProcessor{}.addChangeRecord(new ChangeRecord(request.hdr.getZxid(), path, s,0, listACL));
- - - - - - - - - - - - - PreRequestProcessor{}.List<ChangeRecord> zks.outstandingChanges.add(c); + HashMap<String, ChangeRecord> zks.outstandingChangesForPath.put(c.path, c);

这里是将请求加入数据加入到zks.outstandingChanges，而zks又是ZooKeeperServer变量

那么问题来了：加入到zks.outstandingChanges最后什么时候被取出来
- - FinalRequestProcessor.processRequest()
- - - ChangeRecord cr = zks.outstandingChanges.remove(0);
- - - zks.outstandingChangesForPath.remove(cr.path);
- - - rc = zks.processTxn(hdr, txn);
- - - - ZooKeeperServer{}.processTxn() ==> ProcessTxnResult
- - - - - getZKDatabase().processTxn()
- - - - - - ZKDatabase{}.processTxn()
- - - - - - DataTree{}  dataTree.processTxn(hdr, txn);
- - - - - - - 执行内存操作 setNode( createTxn.getPath(), createTxn.getData(), createTxn.getAcl(), createTxn.getEphemeral() ? header.getClientId() : 0, createTxn.getParentCVersion(), header.getZxid(), header.getTime());
- - - - - - - - DataTree{}.setData()
- - - - - - - - - WatchManager{}.dataWatches.triggerWatch(path, EventType.NodeDataChanged);
- - - - - - - - - - NIOServerCnxn{}.process()

服务端在这个地方就开始触发事件，也就是触发path这个路径的node的NodeDataChanged事件。

```

#### 问题八：Watcher什么时候被加入到zk的服务器或者说Watcher什么时候被加入到watchTables和watch2Paths中
- 在 FinalRequestProcessor的case OpCode.getData: {}
    + byte b[] = zks.getZKDatabase().getData(getDataRequest.getPath(), stat,getDataRequest.getWatch() ? cnxn : null);
    + rsp = new GetDataResponse(b, stat);
        * DataTree{}.getData()
            - WatchManager dataWatches.addWatch(path,watcher)
                + watchTable.put(path, list);
                + watch2Paths.put(watcher, paths);


#### 问题九：客户端向服务端注册Watcher的时候，是吧Watcher对象都传递过去么？
答案：不是的，如果需要注册watcher传递的是一个true，然后在服务端的FinalRequestProcessor类中 ：会根据 getDataRequest.getWatch() ? cnxn : null,有watch就会用cnxn，即客户端传递的是true，则注册cnxn
```java
byte b[] = zks.getZKDatabase().getData(getDataRequest.getPath(), stat,getDataRequest.getWatch() ? cnxn : null);
```
cnxn是ServerCnxn的对象实例。

其中：ServerCnxn实现了Watcher
public abstract class ServerCnxn implements Stats, Watcher {}

#### 问题十：客户端注册Watcher的时候都会实现一个process，那么服务端需要实现process么？
服务端其实也需要实现一个process，当服务端事件触发时进行处理

在NIOServerCnxn中：public class NIOServerCnxn extends ServerCnxn {} 而ServerCnxn又实现了Watcher。因此NIOServerCnxn其实就是一个Watcher。

```java
for (Watcher w : watchers) {
    if (supress != null && supress.contains(w)) {
        continue;
    }
    w.process(e);
}
```

```java
@Override
synchronized public void process(WatchedEvent event) {
    ReplyHeader h = new ReplyHeader(-1, -1L, 0);
    f (LOG.isTraceEnabled()) {
        ZooTrace.logTraceMessage(LOG, ZooTrace.EVENT_DELIVERY_TRACE_MASK,
                                "Deliver event " + event + " to 0x"
                                 + Long.toHexString(this.sessionId)
                                 + " through " + this);
    }

    // Convert WatchedEvent to a type that can be sent over the wire
    WatcherEvent e = event.getWrapper();

    sendResponse(h, e, "notification");
}
```
最终通过sendResponse通知客户端。这样就最终完成了一个事件触发。

#### 问题十一：quit命令及close session之后客户端Client和服务端Server都做了哪些事？
##### 11.1、Client
```java
- ZooKeeper{}.close()
- - cnxn.close()
- - - ClientCnxn{}.close();
- - - - submitRequest();
- - - - - ClientCnxn.submitRequest()
- - - - - - Packet packet = queuePacket(h, r, request, response, null, null, null,null, watchRegistration);
- - - - - - packet.wait();

最后通过ClientCnxn中run()方法将packet取出来发送给server
```

##### 11.2、Server
在Server端的处理链中
```java
- case OpCode.closeSession: pRequest2Txn(request.type, zks.getNextZxid(), request, null, true);
- - PreRequestProcessor{}.pRequest2Txn()
- - - case OpCode.closeSession: 其实在第一个处理器并没有实现closeSession的相关逻辑，在最后一个处理器中实现了closeSession的相关逻辑
- - - SyncRequestProcessor{}
- - - FinalRequestProcessor{}：在这个处理中处理CloseSession的相关逻辑


PreRequestProcessor{}.run()-->PreRequestProcessor{}.pRequest()-->PreRequestProcessor{}.Request2Txn()-->PreRequestProcessor{}.addChangeRecord()
//把修改的记录加到list中，然后有个线程会从list中将修改记录取出来持久化，更新内存
void addChangeRecord(ChangeRecord c) {
    synchronized (zks.outstandingChanges) {
        zks.outstandingChanges.add(c);
        zks.outstandingChangesForPath.put(c.path, c);
    }
}

上面的操作是在PreRequestProcessors中将ChangeRecord加入到outstandingChanges中（是ZooKeeperServer的类变量）。

然后接下来就是在FinalRequestProcessors中将outstandingChanges中的ChangeRecord取出来。
```java
- FinalRequestProcessor{}.processRequest()
- - ChangeRecord cr = zks.outstandingChanges.remove(0); 获取修改记录
- - ProcessTxnResult rc = zks.processTxn(hdr, txn);
- - - ZooKeeper{}.processTxn()
- - - - rc = getZKDatabase().processTxn(hdr, txn);
- - - - - ZKDatabase zkDb.processTxn(hdr,txn);//具体处理数据的逻辑
- - - - - - DataTree{}.dataTree.processTxn(hdr,txn);//the process txn on the data
- - - - - - - DataTree{}.killSession(header.getClientId(), header.getZxid());
- - - - - - - - DataTree{}.deleteNode();//删除临时节点

```
#### 问题十二：ACL机制中各个命令执行逻辑
ACL的命令

- setAcl /node auth:zhangsan:123123: adrw
- setData /node 1 (checkACL)
- getAcl
- addauth digest zhangsan:123123

##### 执行命令 addauth digest zhangsan:123123 之后的逻辑
- NIOServerCnxnFactory
```java
- NIOServerCnxnFactory{}.run()--> while(true){}
- - NIOServerCnxn{}.doIO(k);
- - - NIOServerCnxn{}.readPayload();
- - - - NIOServerCnxn{}.readRequest();
- - - - - NIOServerCnxn{}.zkServer.processPacket(this, incomingBuffer);

// This class implements a simple standalone ZooKeeperServer. It sets up the
// following chain of RequestProcessors to process requests:
// PrepRequestProcessor -> SyncRequestProcessor -> FinalRequestProcessor
```
为什么auth部分的操作在 PrepRequestProcessor -> SyncRequestProcessor -> FinalRequestProcessor之前就完成；就好比与netty的中授权在handlers之前；

**如果是auth操作：**在ZooKeeperServer类中，ZooKeeperServer.processPacket --> auth --> cnxn.sendResponse

**如果不是auth操作：**在ZooKeeperServer类中，ZooKeeperServer.processPacket --> submitRequest(si) （其中submitRequest(si)执行逻辑就是提交给firstProcessor.processRequest(si);）

- ZookeeperServer
```java
- ZookeeperServer{}.processPacket()
- - AuthenticationProvider ap = ProviderRegistry.getProvider(scheme);//ap = DigestAuthenticationProvider
- - authReturn = ap.handleAuthentication(cnxn, authPacket.getAuth());
- - - DigestAuthenticationProvider{}.handleAuthentication(cnxn, authPacket.getAuth());
- - - - ServerCnxn cnxn.addAuthInfo(new Id(getScheme(), digest));
- - - - - ServerCnxn{}.addAuthInfo(Id id);
- - - - - - ArrayList<Id> authInfo.add(id);
```



