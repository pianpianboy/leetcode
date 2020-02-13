## Zookeeper服务端启动流程

#### 启动类
QuorumPeerMain.java 启动参数/Users/liaomengjie/workspace/myCode/data/zoo.cfg

#### 启动类
org.apache.zookeeper.ZooKeeperMain 无参数

![](服务端处理命令源码流程图.png)

### Zookeeper Server启动过程

### Session创建过程
Zookeeper Server 接收Clinet连接逻辑过程
zkCli -timeout 500000000 -server localhost:2281 之后Zookeeper Server 执行逻辑

### Zookeeper Server 接收客户端 create /test 1命令逻辑过程