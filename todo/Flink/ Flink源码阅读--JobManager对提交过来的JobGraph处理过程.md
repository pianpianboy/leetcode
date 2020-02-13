## Flink源码阅读--JobManager对提交过来的JobGraph处理过程
> 在client提交任务的源码分析那篇中我们知道了客户端提交给JobManager的是一个JobGraph对象，那么当JobManager的Dispatcher组件接收到JobGraph后做了哪些处理呢，这篇我们从源码分析一些这个处理过程。

## 源码分析
NettyRPC 接收到请求调用的是channelRead0方法，所以在JM端程序的入口：
RedirectHandler.channelRead0()
====>

