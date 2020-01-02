## Flink Time和Watermark的理解
### 1. Time
#### 背景
在实际开发过程中，我们可能需要接入各种流数据源，比如在线业务用户点击流数据、监控系实时收集到的事件流数据、从传感器采集到的实时数据，等等，为了处理方便他们可能会写入Kafka消息中间件集群中某个/某些topic中，或者选择其它的缓冲/存储系统。这些数据源中数据元素具有固定的时间属性，是在流数据处理系统之外的其它系统生成的。比如，上亿用户通过手机终端操作触发生成的事件数据，都具有对应的事件时间；再特殊一点，可能我们希望回放（Replay）上一年手机终端用户的历史行为数据，与当前某个流数据集交叉分析才能够得到支持某类业务的特定结果，这种情况下，基于数据所具有的事件时间进行处理，就具有很重要的意义了。
下面，我们先从Flink支持的3个与流数据处理相关的时间概念（Time Notion）：ProcessTime、EventTime、IngestionTime。有些系统对时间概念的抽象有其它叫法，比如，Google Cloud Dataflow中称为时间域（Time Domain）。在Flink中，基于不同的Time Notion来处理流数据，具有不同的意义和结果，所以了解这3个Time Notion非常关键。

#### Time Notion
我们先看下，Apache Flink官网文档给出的一张概念图，非常形象地展示了Process Time、Event Time、Ingestion Time这三个时间分别所处的位置，如下图所示：
![Demo](images/flinktime.png)

下面，分别对这3个Time Notion进行说明如下：

##### ProcessTime--事件被处理时当前系统的时间
Flink中有对数据处理的操作进行抽象，称为Transformation Operator，而对于整个Dataflow的开始和结束分别对应着Source Operator和Sink Operator，这些Operator都是在Flink集群系统所在的主机节点上，所以在基于ProcessTime的Notion进行与时间相关的数据处理时，数据处理依赖于Flink程序运行所在的主机节点系统时钟（System Clock）。

因为我们关心的是数据处理时间（Process Time），比如进行Time Window操作，对Window的指派就是基于当前Operator所在主机节点的系统时钟。也就是说，每次创建一个Window，计算Window对应的起始时间和结束时间都使用Process Time，它与外部进入的数据元素的事件时间无关。那么，后续作用于Window的操作（Function）都是基于具有Process Time特性的Window进行的。

使用ProcessTime的场景，比如，我们需要对某个App应用的用户行为进行实时统计分析与监控，由于用户可能使用不同的终端设备，这样可能会造成数据并非是实时的（如用户手机没电，导致2小时以后才会将操作行为记录批量上传上来）。而此时，如果我们按照每分钟的时间粒度做实时统计监控，那么这些数据记录延迟的太严重，如果为了等到这些记录上传上来（无法预测，具体什么时间能获取到这些数据）再做统计分析，对每分钟之内的数据进行统计分析的结果恐怕要到几个小时甚至几天后才能计算并输出结果，这不是我们所希望的。而且，数据处理系统可能也没有这么大的容量来处理海量数据的情况。结合业务需求，其实我们只需要每分钟时间内进入的数据记录，依赖当前数据处理系统的处理时间（Process Time）生成每分钟的Window，指派数据记录到指定Window并计算结果，这样就不用考虑数据元素本身自带的事件时间了。

##### EventTime--事件产生的时间，它通常由事件中的时间戳描述
流数据中的数据元素可能会具有不变的事件时间（Event Time）属性，该事件时间是数据元素所代表的行为发生时就不会改变。最简单的情况下，这也最容易理解：所有进入到Flink处理系统的流数据，都是在外部的其它系统中产生的，它们产生后具有了事件时间，经过传输后，进入到Flink处理系统，理论上（如果所有系统都具有相同系统时钟）该事件时间对应的时间戳要早于进入到Flink处理系统中进行处理的时间戳，但实际应用中会出现数据记录乱序、延迟到达等问题，这也是非常普遍的。

基于EventTime的Notion，处理数据的进度（Progress）依赖于数据本身，而不是当前Flink处理系统中Operator所在主机节点的系统时钟。所以，需要有一种机制能够控制数据处理的进度，比如一个基于事件时间的Time Window创建后，具体怎么确定属于该Window的数据元素都已经到达？如果确定都到达了，然后就可以对属于这个Window的所有数据元素做满足需要的处理（如汇总、分组等）。这就要用到WaterMark机制，它能够衡量数据处理进度（表达数据到达的完整性）。

WaterMark带有一个时间戳，假设为X，进入到数据处理系统中的数据元素具有事件时间，记为Y，如果Y<X，则所有的数据元素均已到达，可以计算并输出结果。反过来说，可能更容易理解一些：要想触发对当前Window中的数据元素进行计算，必须保证对所有进入到系统的数据元素，其事件时间Y>=X。如果数据元素的事件时间是有序的，那么当出现一个数据元素的事件时间Y<X，则触发对当前Window计算，并创建另一个新的Window来指派事件时间Y<X的数据元素到该新的Window中。

可以看到，有了WaterMark机制，对基于事件时间的流数据处理会变得特别灵活，可以根据实际业务需要选择各种组件和处理策略。比如，上面我们说到，当Y<X则触发当前Window计算，记为t1时刻，如果流数据元素是乱序的，经过一段时间，假设t2时刻有一个数据元素的事件时间Y>=X，这时该怎么办呢？如果t1时刻的Window已经不存在了，但我们还是希望新出现的乱序数据元素加入到t1时刻Window的计算中，这时可以实现自定义的Trigger来满足各种业务场景的需要。

##### IngestionTime--事件进入Flink的时间
IngestionTime是数据进入到Flink流数据处理系统的时间，该时间依赖于Source Operator所在主机节点的系统时钟，会为到达的数据记录指派Ingestion Time。基于IngestionTime的Notion，存在多个Source Operator的情况下，每个Source Operator会使用自己本地系统时钟指派Ingestion Time。后续基于时间相关的各种操作，都会使用数据记录中的Ingestion Time。

与EventTime相比，IngestionTime不能处理乱序、延迟到达事件的应用场景，它也就不用必须指定如何生成WaterMark。

##### 设定时间特性
Flink DataStream 程序的第一部分通常是设置基本时间特性。 该设置定义了数据流源的行为方式（例如：它们是否将分配时间戳），以及像 **KeyedStream.timeWindow(Time.seconds(30)) ** 这样的窗口操作应该使用上面哪种时间概念。
以下示例显示了一个 Flink 程序，该程序在每小时时间窗口中聚合事件。

```java
final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);

// 其他
// env.setStreamTimeCharacteristic(TimeCharacteristic.IngestionTime);
// env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

DataStream<MyEvent> stream = env.addSource(new FlinkKafkaConsumer09<MyEvent>(topic, schema, props));

stream
    .keyBy( (event) -> event.getUser() )
    .timeWindow(Time.hours(1))
    .reduce( (a, b) -> a.add(b) )
    .addSink(...);
```

### 2. Watermark
#### Watermark的类型
##### EventTime和Watermarks
- 在使用eventTime的时候如何处理乱序数据？

- 我们知道，流处理从事件产生，到流经source，再到operator，中间是有一个过程和时间的。虽然大部分情况下，流到operator的数据都是按照事件产生的时间顺序来的，但是也不排除由于网络延迟等原因，导致乱序的产生，特别是使用kafka的话，多个分区的数据无法保证有序。所以在进行window计算的时候，我们又不能无限期的等下去，必须要有个机制来保证一个特定的时间后，必须触发window去进行计算了。这个特别的机制，就是watermark，watermark是用于处理乱序事件的。

- watermark可以翻译为水位线

##### 有序的流的watermarks
![Demo](images/inorderWaterMarker.png)

##### 多并行度流的watermarks
注意：多并行度的情况下，watermark对齐会取所有channel最小的watermark
![Demo](images/manyWaterMark.png)

##### 在Apache Flink中使用watermark的4个理解
当人们第一次使用Flink时，经常会对watermark感到困惑。但其实watermark并不复杂。让我们通过一个简单的例子来说明为什么我们需要watermark，以及它的工作机制是什么样的。

在下文中的例子中，我们有一个带有时间戳的事件流，但是由于某种原因它们并不是按顺序到达的。图中的数字代表事件发生的时间戳。第一个到达的事件发生在时间4，然后它后面跟着的是发生在更早时间（时间2）的事件，以此类推：
![Demo](images/flink020.png)

注意这是一个按照事件时间处理的例子，这意味着时间戳反映的是事件发生的时间，而不是处理事件的时间。事件时间（Event-Time）处理的强大之处在于，无论是在处理实时的数据还是重新处理历史的数据，基于事件时间创建的流计算应用都能保证结果是一样的。

现在假设我们正在尝试创建一个流计算排序算子。也就是处理一个乱序到达的事件流，并按照事件时间的顺序输出事件。

##### 理解1
数据流中的第一个元素的时间是4，但是我们不能直接将它作为排序后数据流的第一个元素并输出它。因为数据是乱序到达的，也许有一个更早发生的数据还没有到达。事实上，我们能预见一些这个流的未来，也就是我们的排序算子至少要等到2这条数据的到达再输出结果。

有缓存，就必然有延迟。

##### 理解2
如果我们做错了，我们可能会永远等待下去。首先，我们的应用程序从看到时间4的数据，然后看到时间2的数据。是否会有一个比时间2更早的数据到达呢？也许会，也许不会。我们可以一直等下去，但可能永远看不到1。

最终，我们必须勇敢地输出 2 作为排序流的第一个结果

##### 理解3
我们需要的是某种策略，它定义了对于任何带时间戳的事件流，何时停止等待更早数据的到来。

这正是 watermark 的作用，他们定义了何时不再等待更早的数据。

Flink中的事件时间处理依赖于一种特殊的带时间戳的元素，成为watermark，它们会由数据源或是watermark生成器插入数据流中。具有时间戳t的watermark可以被理解为断言了所有时间戳小于或等于t的事件都（在某种合理的概率上）已经到达了。

> 注：此处原文是“小于”，译者认为应该是 “小于或等于”，因为 Flink 源码中采用的是 “小于或等于” 的机制。

何时我们的排序算子应该停止等待，然后将事件2作为首个元素输出？答案是当收到时间戳为2（或更大）的watermark时。

##### 理解4
我们可以设想不同的策略来生成watermark。

我们知道每个事件都会延迟一段时间才到达，而这些延迟差异会比较大，所以有些事件会比其他事件延迟更多。一种简单的方法是假设这些延迟不会超过某个最大值。Flink 把这种策略称作 “有界无序生成策略”（bounded-out-of-orderness）。当然也有很多更复杂的方式去生成watermark，但是对于大多数应用来说，固定延迟的方式已经足够了。

如果想要构建一个类似排序的流应用，可以使用Flink的ProcessFunction。它提供了对事件时间计时器（基于watermark触发回调）的访问，还提供了可以用来缓存数据的托管状态接口。

#### Watermark案例
##### 1.watermarks的生成方式
通常，在接收到source的数据后，应该立刻生成watermark；但是，也可以在source后，应用简单的map或者filter操作后，再生成watermark。

注意：如果指定多次watermark，后面指定的会覆盖前面的值。

生成方式

###### With Periodic Watermarks

- 周期性的触发watermark的生成和发送，默认是100ms

- 每隔N秒自动向流里注入一个WATERMARK

- 时间间隔由ExecutionConfig.setAutoWatermarkInterval 决定.

- 每次调用getCurrentWatermark 方法, 如果得到的WATERMARK

- 不为空并且比之前的大就注入流中

- 可以定义一个最大允许乱序的时间，这种比较常用

- 实现AssignerWithPeriodicWatermarks接口

###### With Punctuated Watermarks

- 基于某些事件触发watermark的生成和发送

- 基于事件向流里注入一个WATERMARK，每一个元素都有机会判断是否生成一个WATERMARK.

- 如果得到的WATERMARK 不为空并且比之前的大就注入流中

- 实现AssignerWithPunctuatedWatermarks接口

##### 2.watermark和window案例
这里写了一个watermark&window的flink程序，从socket读取数据
代码：

```java
public class StreamingWindowWatermark {

    private static final Logger log = LoggerFactory.getLogger(StreamingWindowWatermark.class);

    public static void main(String[] args) throws Exception {
        //定义socket的端口号
        int port = 9000;
        //获取运行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //设置使用eventtime，默认是使用processtime
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);


        //设置并行度为1,默认并行度是当前机器的cpu数量
        env.setParallelism(1);

        //连接socket获取输入的数据
        DataStream<String> text = env.socketTextStream("zzy", port, "\n");

        //解析输入的数据,每行数据按逗号分隔
        DataStream<Tuple2<String, Long>> inputMap = text.map(new MapFunction<String, Tuple2<String, Long>>() {
            @Override
            public Tuple2<String, Long> map(String value) throws Exception {
                String[] arr = value.split(",");
                return new Tuple2<>(arr[0], Long.parseLong(arr[1]));
            }
        });

        //抽取timestamp和生成watermark
        DataStream<Tuple2<String, Long>> waterMarkStream = inputMap.assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks<Tuple2<String, Long>>() {

            Long currentMaxTimestamp = 0L;
            final Long maxOutOfOrderness = 10000L;// 最大允许的乱序时间是10s

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

            /**
             * 定义生成watermark的逻辑，比当前最大时间戳晚10s
             * 默认100ms被调用一次
             */
            @Nullable
            @Override
            public Watermark getCurrentWatermark() {
                return new Watermark(currentMaxTimestamp - maxOutOfOrderness);
            }

            //定义如何提取timestamp
            @Override
            public long extractTimestamp(Tuple2<String, Long> element, long previousElementTimestamp) {
                long timestamp = element.f1;
                currentMaxTimestamp = Math.max(timestamp, currentMaxTimestamp);
                //设置多并行度时获取线程id
                long id = Thread.currentThread().getId();
                log.info("extractTimestamp=======>" + ",currentThreadId:" + id + ",key:" + element.f0 + ",eventtime:[" + element.f1 + "|" + sdf.format(element.f1) + "]," +
                        "currentMaxTimestamp:[" + currentMaxTimestamp + "|" +
                        sdf.format(currentMaxTimestamp) + "],watermark:[" + getCurrentWatermark().getTimestamp() + "|" + sdf.format(getCurrentWatermark().getTimestamp()) + "]");
//                System.out.println("currentThreadId:" + id + ",key:" + element.f0 + ",eventtime:[" + element.f1 + "|" + sdf.format(element.f1) + "],currentMaxTimestamp:[" + currentMaxTimestamp + "|" +
//                        sdf.format(currentMaxTimestamp) + "],watermark:[" + getCurrentWatermark().getTimestamp() + "|" + sdf.format(getCurrentWatermark().getTimestamp()) + "]");
                return timestamp;
            }
        });

        DataStream<String> window = waterMarkStream.keyBy(0)//分组
                .window(TumblingEventTimeWindows.of(Time.seconds(3)))//按照消息的EventTime分配窗口，和调用TimeWindow效果一样
                .apply(new WindowFunction<Tuple2<String, Long>, String, Tuple, TimeWindow>() {
                    /**
                     * 对window内的数据进行排序，保证数据的顺序
                     * @param tuple
                     * @param window
                     * @param input
                     * @param out
                     * @throws Exception
                     */
                    @Override
                    public void apply(Tuple tuple, TimeWindow window, Iterable<Tuple2<String, Long>> input, Collector<String> out) throws Exception {
                        String key = tuple.toString();
                        List<Long> arrarList = new ArrayList<Long>();
                        Iterator<Tuple2<String, Long>> it = input.iterator();
                        while (it.hasNext()) {
                            Tuple2<String, Long> next = it.next();
                            //时间戳放到了arrarList里
                            arrarList.add(next.f1);
                        }
                        Collections.sort(arrarList);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                        String result = key + "," + arrarList.size() + "," + sdf.format(arrarList.get(0)) + "," + sdf.format(arrarList.get(arrarList.size() - 1))
                                + "," + sdf.format(window.getStart()) + "," + sdf.format(window.getEnd());
                        out.collect(result);
                    }
                });
        //测试-把结果打印到控制台即可
        window.print();

        //注意：因为flink是懒加载的，所以必须调用execute方法，上面的代码才会执行
        env.execute("eventtime-watermark");

    }
}
```
