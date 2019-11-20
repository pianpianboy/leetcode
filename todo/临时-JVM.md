# JVM优化案例

## 参考案例1

### 配置
1、机器配置：2C 4G
2、JVM堆内存大小：2G
3、系统运行时间：6天
4、系统运行6天内发生的Full GC次数和耗时：250次，70多秒
5、系统运行6天内发生的Young GC次数和耗时：2.6万次，1400秒
6、未优化前JVM参数
-Xms1536M -Xmx1536M -Xmn512M -Xss256K -XX:SurvivorRatio=5 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=68 -XX:+CMSParallelRemarkEnabled -XX:+UseCMSInitiatingOccupancyOnly -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintHeapAtGC

#### 总结
通过jstat统计此系统（性能已经相当的差了）：
- Full GC 1小时2次 每次full GC 在300ms左右
- Young GC 1分钟3次 每次young GC 在500ms左右

#### 分析JVM参数
- 在4G的机器上JVM堆内存是设置了1.5G的大小
- jvm虚拟机栈大小为256k
- 新生代512M 老年代1G Eden：Survivor1:Suvivor2 = 5:1:1 因此Eden区域的大小为365M，每个Survivor的区域大小大致70MB
- -XX:CMSInitiatingOccupancyFraction=68 表示老年代使用占比达到68% 即680MB就触发Full GC

#### JVM GC分析
- 20s进行一次YGC，即20S产生365MB的对象，1S产生15MB左右对象 ，所以20s左右就导致Eden区域满，然后触发一次Young GC.
- 根据参数-XX:CMSInitiatingOccupancyFraction=68 参数的设置，应该是在老年代有600MB左右的对象时大概就会触发一次Full GC，因为参数中设置1GB老年代有68%空间占满了就会触发CMS的GC了。所以系统运行30分钟就会导致老年代有600MB的对象，进而触发了CMS垃圾回收器对老年代进行GC

#### 结论1：
- 每隔20s会让300MB的Eden区满触发一次Young GC，一次Young GC耗时50毫秒左右
- 每个30分钟600MB的老年代占满，进而触发一次CMS的GC,一次Full GC耗时300ms左右
- 频繁的Full GC 的原因:
    + 有可能是Survivor区域太小，导致Young GC后存活对象太多放不下，就一直有对象进入老年代，进而导致30分钟后触发FullGC
    + 也有可能时大对象直接进入老年代

#### 再次分析jstat
- 通过jstat的观察，我们但是可以明确的看到，每次Young GC过后进入老年代里的对象很少，
- 一般来说，每次YoungGC过后大概就存活几十MB而已，那么Survovior区域就70MB，所以经常触发动态年龄判断规则，导致偶尔一次YoungGC过后有几十MB的对象进入老年代
- 因此分析到这里很奇怪，因为通过jstat 追踪观察，并不是每次young GC后都有几十MB对象进入老年代的，而是偶尔一次YoungGC才会有几十MB对象进入老年代，记住 是偶尔一次。
- 所以正常来说，应该不至于30分钟就导致老年代占用空间达到68%
- 那么老年代到底为什么会有那么多对象呢？ 答案就是大对象
- 然后就是如何定位大对象

#### 定位大对象
- 利用jstat工具观察系统，发现老年代里突然进入了几百MB大对象，就立马用jmap工具导出一份dump内存快照。
- 接着可以采用之前说过的jhat来分析dump内存快照。
- 通过内存快照的分析，直接定位出来那个几百MB的大对象，就是几个Map之类的数据结构。就是select* from table 没有where条件把表中几十万的数据直接全部查出来
- 正是因为代码层面的BUG，导致每隔一段时间系统会搞出几个几百MB的大对象，这些对象会全部直接进入老年代的。然后过一会儿随着偶尔几次YoungGC的几十MB的对象进入老年代，所以平均几十分钟就会触发一次Full GC。

#### 解决问题
- 第一步：让写这个代码的同学解决代码中的Bug,避免一些极端的情况下SQL语句不拼接where条件，不容许查询表中的全部数据，彻底解决那个时不时有几百MB对象进入老年代的问题。
- 第二步：年轻带明细过小，survivor区域空间不够，因为每次YoungGC后存活对象在几十MB左右，如果Survivor就70MB很容易触发动态年龄判断，让对象进入老年代中，所以直接调整jvm参数：
-Xms1536M -Xmx1536M -Xmn1024M -Xss256K -XX:SurvivorRatio=5 -XX:PermSize=256M -XX:MaxPermSize=256M  -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=92 -XX:+CMSParallelRemarkEnabled -XX:+UseCMSInitiatingOccupancyOnly -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintHeapAtGC
- 最后直接把年轻带空间调整为700MB左右，每个surivor是150MB，此时YoungGC 过后也就几十MB存活对象，一般不会进入老年代
- 反之老年代就留500MB就足够了，因为一般不会有对象进入老年代
- 而且调整了参数“-XX:CMSInitiatingOccupancyFraction=92”，避免老年代仅仅占用68%就触发GC，现在必须要占用到92%才会触发GC。
- 最后，就是主动设置了永久代大小为256MB，如果不主动设置会导致默认永久代就几十MB的样子，很容易导致万一系统运行时候采用了反射之类的机制，可能一旦动态加载的类过多，就会频繁触发Full GC

#### 优化后的结果
- 优化后基本上每分钟大概发生一次YoungGC,一次在几十毫秒
- Full GC几乎很少，大概可能运行10天才会发生一次，一次就耗时几百毫秒而已，频率很低

-------------------------------------------------------------------------------

## 自己所在系统JVM分析
### 配置
1、机器配置：4C 8G
2、JVM堆内存大小：4G
3、系统运行时间：6天
4、系统运行6天内发生的Full GC次数和耗时：250次，70多秒
5、系统运行6天内发生的Young GC次数和耗时：2.6万次，1400秒
6、未优化前JVM参数
-Xms4096M -Xmx4096M -Xmn2048M -Xss1024K -XX:SurvivorRatio=5 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=68 -XX:+CMSParallelRemarkEnabled -XX:+UseCMSInitiatingOccupancyOnly -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintHeapAtGC

即 新生代 2048M Eden:1400M s1:300M s2:300M 老年代:2048M  fullGC 触发比例：68%
jstat统计一周后发现大概一分钟 3次young GC 一次耗时几十ms ;一小时 2次Full GC 一次fullGC 耗时500ms
性能比较差

#### 初步分析
- 怀疑是每次Survivor 太小，每次youngGC后存活的对象太大，直接进入老年代，半个小时候撑爆老年代（2048*68% = 1400MB）
- 也有可能是存在大对象

#### 根据jstat分析
- 每次jstat之后存活对象大概有100M
- 每次young GC过后大概有100MB对象存活，那么survivor区域100MB，所以经常触发动态年龄判断，导致偶尔一次YoungGC过后有一百兆对象存活进入老年代
- 但是也只是偶尔有100MB左右对象偶尔进入老年代，半个小时不太可能占满1400MB老年代空间，猜测是大对象导致频繁full GC
- 后续通过jmap定位大对象，先通过jstat检测系统，当有几百兆对象进入老年代的时候，使用jmap工具导出一份dump内存快照
- 分析内存快照，发现是有几个大map存在，

#### 进行优化
-Xms4G -Xmx4G -Xmn3G -Xss1024K -XX:SurvivorRatio=4 -XX:PermSize=256M -XX:MaxPermSize=256M  -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=92 -XX:+CMSParallelRemarkEnabled -XX:+UseCMSInitiatingOccupancyOnly -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintHeapAtGC

即 新生代 3072M Eden:2000M s1:500M s2:500M 老年代:1024M  fullGC 触发比例：92%
jstat统计一周后发现大概一分钟 1次young GC 一次耗时几十ms ;Full GC几乎很少，大概可能运行10天才会发生一次，一次就耗时几百毫秒而已，频率很低

