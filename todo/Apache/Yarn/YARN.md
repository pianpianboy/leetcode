## YARN
- 1、简述mapReduce1工作机制
- 2、简述YARN(mapReduce1)工作机制
- 3、简述YARN在application master 向resource manager申请资源的过程
- 4、比较mapreduce1和YARN(mapreduce2)
- 5、简述YARN和Resource Manager总体结构
- 6、简述YARN中资源调度器(在resource manager中)
- 7、简述YARN中内存和CPU的资源管理(调度)和资源隔离机制。
- 8、简述YARN中的Container概念
- 9、YARN中的各个部分运行故障如何处理？（作业运行失败可能的原因）
- 10、YARN中常见问题已经解决方案
- 11、YARN中的Resource Manager的高可用实现（主备切换的底层实现）
- 12、YARN中ResourceManager的高可用当中"脑裂"问题的解决
- 13、实验
- 14、Flink的中yarn的架构图
-


### 14、Flink中yarn的架构图

#### 14.1、Flink的Standalone模式
图中Flink运行时相关组件

- **Client**: 任务提交，生成JobGraph
- **JobManager**: 调度Job，协调Task，通信，资源申请等
- **TaskManager**: 具体任务的执行，请求资源

![](FlinkStandalone.png)

#### 14.2、Flink的yarn模式
##### yarn架构原理-组件
- **ResourceManager(RM)**
    + 处理客户端请求、启动、监控App Master、监控NodeManager，资源的分配与调度
    + 包含Scheduler和ApplicationManager两个部分
- **ApplicationMaster（AM）**
    + 运行在Slave的Container容器中（第一个申请分配的容器），负责数据切分，申请资源和分配，任务监控和容错
    + 非常重要的组件，要运行Flink应用需要实现一个自己的AM
- **NodeManager(NM)**
    + 运行在Slave上，单节点资源管理，与AM/RM通信，汇报状态
- **Container**
    + 资源抽象，包括内存、CPU、磁盘、网络等资源

##### Yarn的架构原理-交互

![](yarn架构原理交互.png)

##### Flink on Yarn -Per Job


















因为tmp目录下没有权限查看只能通过hadoop命令
hadoop fs -ls -R /tmp

客户端在提交job运行的时候 共享资源路径
/tmp/hadoop-yarn/staging/hadoop/.staging/job