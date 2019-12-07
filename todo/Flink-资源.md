对于机器来说：CPU+线程+内存
对于yarn或者flink集群来说：Task Manager(TM)、JobManager(JM)、slot的数量
对于程序来说就是：parallelis的数量、JVM heap堆内存内存，堆外内存

那么slot 与 tm、cpu有啥关系， parallelism设置多大？
默认情况下，如果我们不设置，是多大呢？


##slot和parallelism的关系？
- slot是指 TaskManager最大的并发执行能力
- paralism是指 job的实际并发能力（也是TM的实际并发能力）
- parallelism 若大于 TM数 * slot数，即cheng程序设置的并行度



## Flink JVM参数
env.java.opts="-XX:NewRatio=2"

env.java.opts="-Xms2G -Xmx2G -Xmn1G -Xss1024K -XX:SurvivorRatio=4 -XX:PermSize=256M -XX:MaxPermSize=256M  -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=92 -XX:+CMSParallelRemarkEnabled -XX:+UseCMSInitiatingOccupancyOnly -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintHeapAtGC -Xloggc:flink-gc.log"

-Xms2G -Xmx2G -Xmn1G -Xss1024K -XX:SurvivorRatio=4 -XX:PermSize=256M -XX:MaxPermSize=256M  -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=92 -XX:+CMSParallelRemarkEnabled -XX:+UseCMSInitiatingOccupancyOnly -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintHeapAtGC -Xloggc:flink-gc.log