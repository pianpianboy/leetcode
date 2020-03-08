### Flink on Yarn(1) YarnSessionClusterEntrypoint

#### ApplicationMaster
- 首先是JobManager和ApplicationMaster。在Flink on Yarn中，JobManager和ApplicationMaster是在同一个JVM进程中，这个进程的入口是YarnSessionClusterEntryPoint类，首先看下这个类，它继承了SessionClusterEntryPoint。
![]{SessionClusterEntrypoint.png}

SessionClusterEntryPoint是一个抽象类，它又继承了ClusterEntrypoint这个抽象类。

- 那么这三个类的作用是什么呢？
    + ClusterEntrypoint: 它封装了Cluster启停的逻辑，还有根据配置文件来创建RpcService，HaService，HeartbeatService,MetricRegistry等等服务的逻辑。其提供的runCluster()方法中会调用dispatcherResourceManagerComponentFactory.create()，create()会创建dipatcherRunner、ResourceManager、webMonitorEndpoint
    + SessionClusterEntrypoint继承了这个类，并且实现了createSerializableExecutionGraphStore()。这个类也只有createSerializableExecutionGraphStore这一个方法。
    + YarnSessionClusterEntrypoint，其主要实现main()方法，在main()方法中会调用父类的runClusterEntrypoint()方法


### 脚本
##### ./start-cluster.sh
```java
if [[ $HIGH_AVAILABILITY == "zookeeper" ]]; then
    # HA Mode
    readMasters

    echo "Starting HA cluster with ${#MASTERS[@]} masters."

    for ((i=0;i<${#MASTERS[@]};++i)); do
        master=${MASTERS[i]}
        webuiport=${WEBUIPORTS[i]}

        if [ ${MASTERS_ALL_LOCALHOST} = true ] ; then
            "${FLINK_BIN_DIR}"/jobmanager.sh start "${master}" "${webuiport}"
        else
            ssh -n $FLINK_SSH_OPTS $master -- "nohup /bin/bash -l \"${FLINK_BIN_DIR}/jobmanager.sh\" start ${master} ${webuiport} &"
        fi
    done

else
    echo "Starting cluster."

    # Start single JobManager on this machine
    "$FLINK_BIN_DIR"/jobmanager.sh start
fi
```

##### ./jobmanager.sh
```java
ENTRYPOINT=standalonesession
if [[ $STARTSTOP == "start-foreground" ]]; then
    exec "${FLINK_BIN_DIR}"/flink-console.sh $ENTRYPOINT "${args[@]}"
else
    "${FLINK_BIN_DIR}"/flink-daemon.sh $STARTSTOP $ENTRYPOINT "${args[@]}"
fi
```

##### ./flink-daemon.sh
因为在jobmanager.sh传递的参数ENTRYPOINT=standalonesession，则启动的是：rg.apache.flink.runtime.entrypoint.StandaloneSessionClusterEntrypoint
```java
case $DAEMON in
    (taskexecutor)
        CLASS_TO_RUN=org.apache.flink.runtime.taskexecutor.TaskManagerRunner
    ;;

    (zookeeper)
        CLASS_TO_RUN=org.apache.flink.runtime.zookeeper.FlinkZooKeeperQuorumPeer
    ;;

    (historyserver)
        CLASS_TO_RUN=org.apache.flink.runtime.webmonitor.history.HistoryServer
    ;;

    (standalonesession)
        CLASS_TO_RUN=org.apache.flink.runtime.entrypoint.StandaloneSessionClusterEntrypoint
    ;;

    (standalonejob)
        CLASS_TO_RUN=org.apache.flink.container.entrypoint.StandaloneJobClusterEntryPoint
    ;;

    (*)
        echo "Unknown daemon '${DAEMON}'. $USAGE."
        exit 1
    ;;
esac
```

#### 源码
##### org.apache.flink.runtime.entrypoint.StandaloneSessionClusterEntrypoint
```java
StandaloneSessionClusterEntrypoint{}
public static void main(String[] args) {
    EntrypointClusterConfiguration entrypointClusterConfiguration = null;
    final CommandLineParser<EntrypointClusterConfiguration> commandLineParser = new CommandLineParser<>(new EntrypointClusterConfigurationParserFactory());

    entrypointClusterConfiguration = commandLineParser.parse(args);

    Configuration configuration = loadConfiguration(entrypointClusterConfiguration);
    StandaloneSessionClusterEntrypoint entrypoint = new StandaloneSessionClusterEntrypoint(configuration);

    ClusterEntrypoint.runClusterEntrypoint(entrypoint);
}
```
##### org.apache.flink.yarn.entrypoint.YarnSessionClusterEntrypoint
- 接下来看以下YarnSessionClusterEntrypoint的启动流程。首先入口是main函数，在main函数中新建了YarnSessionClusterEntrypoint的一个对象
- 接着调用父类的runClusterEntrypoint()方法，前面也提到过Cluster的启停逻辑是在父类ClusterEntrypoint中，支持YarnSessionClusterEntrypoint的最重要的任务已经完成。

```java
YarnSessionClusterEntrypoint{}
public static void main(String[] args) {
    Map<String, String> env = System.getenv();

    final String workingDirectory = env.get(ApplicationConstants.Environment.PWD.key());

    Configuration configuration = YarnEntrypointUtils.loadConfiguration(workingDirectory, env);

    YarnSessionClusterEntrypoint yarnSessionClusterEntrypoint = new YarnSessionClusterEntrypoint(configuration);

    ClusterEntrypoint.runClusterEntrypoint(yarnSessionClusterEntrypoint);
}
```
YarnSessionClusterEntrypoint{}.main()->ClusterEntrypoint{}runClusterEntrypoint()
##### org.apache.flink.runtime.entrypoint.ClusterEntrypoint
```java
ClusterEntrypoint{}.
public static void runClusterEntrypoint(ClusterEntrypoint clusterEntrypoint) {
    final String clusterEntrypointName = clusterEntrypoint.getClass().getSimpleName();

    clusterEntrypoint.startCluster();
}
```
```java
ClusterEntrypoint{}
public void startCluster() throws ClusterEntrypointException {
    //配置文件系统相关配置
    configureFileSystems(configuration);

    SecurityContext securityContext = installSecurityContext(configuration);

    securityContext.runSecured((Callable<Void>) () -> {
        runCluster(configuration);
        return null;
    });
}
```
- 在runCluster函数中，又做了三件事：
    + 第一件事：根据配置文件初始化RpcService，HsService、HeartService、MetricRegistry、BlobServer、ResourceManager、SerializableExecutionGraphStore等模块，并将它们赋值给ClusterEntrypoint中相应的instance variables。
    + 第二件事：就是在startClusterCompionents函数中启动这些服务。
        * 其中RpcService负责各个模块之间的rpc调用，本质上是基于Akka
        * ResourceManager主要负责与Yarn的ResourceManager进行交互，通过Yarn提供的AMRMAsyncClient,进行一些资源分配与释放的操作。
        * HaService的任务主要有ResourceManager、JobManager、Dispatcher、WebMonitor的Leader选举，checkpoint的元数据的持久化以及checkpoint的注册与跟踪，Blob数据的持久化，任务的状态监控，总之就是服务高可用相关功能。
        * HeartbeatService是负责心跳的发送与接收，被多个模块用来监控其他节点是否丢失。
        * MetricRegistry负责指标的监控
        * BlobServer负责处理对Blob数据的请求与返回
        * SerializableExecutionGraphStore则负责存储序列化之后的执行计划
    + 最后一件事就是启动Dispatcher，Dispatcher主要负责接收client提交的任务，启动并将任务传递给JobManager，以及Master节点挂掉的失败恢复。

```java
ClusterEntrypoint{}
private void runCluster(Configuration configuration) throws Exception {
synchronized (lock) {
    //这里会调用下面的haServices的创建
①    initializeServices(configuration);

    // write host information into configuration
    configuration.setString(JobManagerOptions.ADDRESS, commonRpcService.getAddress());
    configuration.setInteger(JobManagerOptions.PORT, commonRpcService.getPort());

②    final DispatcherResourceManagerComponentFactory dispatcherResourceManagerComponentFactory = createDispatcherResourceManagerComponentFactory(configuration);

③    clusterComponent = dispatcherResourceManagerComponentFactory.create(
        configuration,
        ioExecutor,
        commonRpcService,
        haServices,
        blobServer,
        heartbeatServices,
        metricRegistry,
        archivedExecutionGraphStore,
        new RpcMetricQueryServiceRetriever(metricRegistry.getMetricQueryServiceRpcService()),
        this);
}
}
```
##### ① initializeServices(configuration);
```java
//Initializing cluster services.
protected void initializeServices(Configuration configuration){
        synchronized (lock) {
            final String bindAddress = configuration.getString(JobManagerOptions.ADDRESS);
            final String portRange = getRPCPortRange(configuration);

            commonRpcService = createRpcService(configuration, bindAddress, portRange);

            configuration.setString(JobManagerOptions.ADDRESS, commonRpcService.getAddress());
            configuration.setInteger(JobManagerOptions.PORT, commonRpcService.getPort());
            //新建线程池
            ioExecutor = Executors.newFixedThreadPool(
                Hardware.getNumberCPUCores(),
                new ExecutorThreadFactory("cluster-io"));
            //使用线程池新建HaServers
            haServices = createHaServices(configuration, ioExecutor);

            //BlobServer实现BLOB服务器。BLOB服务器负责listener传入的请求Request和生成线程以处理这些请求。此外，它负责创建目录结构来存储BLOB或临时缓存它们。
            blobServer = new BlobServer(configuration, haServices.createBlobStore());
            blobServer.start();

            //HeartbeatServices允许访问心跳所需的所有服务。这包括创建心跳接收器和心跳发送器。
            heartbeatServices = createHeartbeatServices(configuration);

            //创建Metric服务
            metricRegistry = createMetricRegistry(configuration);

            final RpcService metricQueryServiceRpcService = MetricUtils.startMetricsRpcService(configuration, bindAddress);
            metricRegistry.startQueryService(metricQueryServiceRpcService, null);

            final String hostname = RpcUtils.getHostname(commonRpcService);

            processMetricGroup = MetricUtils.instantiateProcessMetricGroup(
                metricRegistry,
                hostname,
                ConfigurationUtils.getSystemResourceMetricsProbingInterval(configuration));

            archivedExecutionGraphStore = createSerializableExecutionGraphStore(configuration, commonRpcService.getScheduledExecutor());
        }
    }
```
其中createRpcService创建RPC的逻辑如下：
```java
private RpcService createRpcService(Configuration configuration, String bindAddress, String portRange) {
    return AkkaRpcServiceUtils.createRpcService(bindAddress, portRange, configuration);
}
```

##### ②createDispatcherResourceManagerComponentFactory(configuration);
org.apache.flink.runtime.entrypoint.component.DefaultDispatcherResourceManagerComponentFactory
```java
public static DefaultDispatcherResourceManagerComponentFactory createSessionComponentFactory(
        ResourceManagerFactory<?> resourceManagerFactory) {
    return new DefaultDispatcherResourceManagerComponentFactory(
        DefaultDispatcherRunnerFactory.createSessionRunner(SessionDispatcherFactory.INSTANCE),
        resourceManagerFactory,
        SessionRestEndpointFactory.INSTANCE);
}
```

##### ③ dispatcherResourceManagerComponentFactory.create()
org.apache.flink.runtime.entrypoint.component.DefaultDispatcherResourceManagerComponentFactory
```java
//DefaultDispatcherResourceManagerComponentFactory.java
//这里是实现的create这个函数，看看有多少个相关的服务
@Override
public DispatcherResourceManagerComponent create(
        Configuration configuration,
        ......) {
    LeaderRetrievalService dispatcherLeaderRetrievalService = null;
    LeaderRetrievalService resourceManagerRetrievalService = null;
    WebMonitorEndpoint<?> webMonitorEndpoint = null;
    ResourceManager<?> resourceManager = null;
    ResourceManagerMetricGroup resourceManagerMetricGroup = null;
    DispatcherRunner dispatcherRunner = null;

    try {
        dispatcherLeaderRetrievalService = highAvailabilityServices.getDispatcherLeaderRetriever();

        resourceManagerRetrievalService = highAvailabilityServices.getResourceManagerLeaderRetriever();

        final LeaderGatewayRetriever<DispatcherGateway> dispatcherGatewayRetriever = new RpcGatewayRetriever<>(
            rpcService,
            DispatcherGateway.class,
            DispatcherId::fromUuid,
            10,
            Time.milliseconds(50L));

        final LeaderGatewayRetriever<ResourceManagerGateway> resourceManagerGatewayRetriever = new RpcGatewayRetriever<>(
            rpcService,
            ResourceManagerGateway.class,
            ResourceManagerId::fromUuid,
            10,
            Time.milliseconds(50L));

        final ExecutorService executor = WebMonitorEndpoint.createExecutorService(
            configuration.getInteger(RestOptions.SERVER_NUM_THREADS),
            configuration.getInteger(RestOptions.SERVER_THREAD_PRIORITY),
            "DispatcherRestEndpoint");

        final long updateInterval = configuration.getLong(MetricOptions.METRIC_FETCHER_UPDATE_INTERVAL);
        final MetricFetcher metricFetcher = updateInterval == 0
            ? VoidMetricFetcher.INSTANCE
            : MetricFetcherImpl.fromConfiguration(
                configuration,
                metricQueryServiceRetriever,
                dispatcherGatewayRetriever,
                executor);

        //【重点】创建WebMonitorEndpoint，并启动
③        webMonitorEndpoint = restEndpointFactory.createRestEndpoint(
            configuration,
            dispatcherGatewayRetriever,
            resourceManagerGatewayRetriever,
            blobServer,
            executor,
            metricFetcher,
            highAvailabilityServices.getClusterRestEndpointLeaderElectionService(),
            fatalErrorHandler);

        log.debug("Starting Dispatcher REST endpoint.");
        webMonitorEndpoint.start();

        final String hostname = RpcUtils.getHostname(rpcService);

        resourceManagerMetricGroup = ResourceManagerMetricGroup.create(metricRegistry, hostname);

        //【重点】创建resourceManager并启动
④        resourceManager = resourceManagerFactory.createResourceManager(
            configuration,
            ResourceID.generate(),
            rpcService,
            highAvailabilityServices,
            heartbeatServices,
            fatalErrorHandler,
            new ClusterInformation(hostname, blobServer.getPort()),
            webMonitorEndpoint.getRestBaseUrl(),
            resourceManagerMetricGroup);

        final HistoryServerArchivist historyServerArchivist = HistoryServerArchivist.createHistoryServerArchivist(configuration, webMonitorEndpoint);

        final PartialDispatcherServices partialDispatcherServices = new PartialDispatcherServices(
            configuration,
            highAvailabilityServices,
            resourceManagerGatewayRetriever,
            blobServer,
            heartbeatServices,
            () -> MetricUtils.instantiateJobManagerMetricGroup(metricRegistry, hostname),
            archivedExecutionGraphStore,
            fatalErrorHandler,
            historyServerArchivist,
            metricRegistry.getMetricQueryServiceGatewayRpcAddress());

        log.debug("Starting Dispatcher.");
        dispatcherRunner = dispatcherRunnerFactory.createDispatcherRunner(
            highAvailabilityServices.getDispatcherLeaderElectionService(),
            fatalErrorHandler,
            new HaServicesJobGraphStoreFactory(highAvailabilityServices),
            ioExecutor,
            rpcService,
            partialDispatcherServices);

        log.debug("Starting ResourceManager.");
        resourceManager.start();

        resourceManagerRetrievalService.start(resourceManagerGatewayRetriever);
        dispatcherLeaderRetrievalService.start(dispatcherGatewayRetriever);

        return new DispatcherResourceManagerComponent(
            dispatcherRunner,
            resourceManager,
            dispatcherLeaderRetrievalService,
            resourceManagerRetrievalService,
            webMonitorEndpoint);
    }
}
```

至此yarnCluster启动完成

#### 总结
综上，YarnSessionClusterEntrypoint可以理解为Flink在Yarn上的ApplicationMaster，同时也是JobManager。它们之间分属两个线程，之间的交互通过Akka的消息驱动的模式来实现任务调度与资源分配的分离，而对应的JobManager与ResourceManager也有相应的子模块组成。








