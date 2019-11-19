## JUC
### 目录
- ConcurrentHashMap
- CountDownLatch、Semaphore、CyclicBarrier
- AtomicInteger
- Synchronized、Volatile
- ReentrantLock
- ThreadLocal、FastThreadLocal
- AQS
- Lock、ReadWriteLock（读写锁）
- ConcurrentLinkedDeque
- s

#### JUC面试题目
0、HashMap原理？
    (1) HashMap默认的加载因子0.75，为什么是0.75?
        在java1.7中：
            1)在hashMap的初始化构造中有一个inflateTable()方法：int capacity = roundUptoPowerOf2(toSize);//转换成2的指数次幂，比如13--->16、10--->16、7--->8
            2)传统方式求下标 index = hash%16，hashMap的方式 index = hash&(16-1)；取模运算 比 位运算 慢了一个数量级；而且为了实现 hash%16 和hash&(16-1)等价，即元素能够被计算在0-15范围内，数组的长度必须要是2的指数次幂,如果不是2的指数次幂会出现数组越界和大量的hash碰撞。

            那么为什么是0.75呢？
            1）当扩容因子是1的时候查询效率降低，hash碰撞增多
            2）当扩容因子是0.5的时候空间利用率又太低
            3）如果取空间利用率和时间复杂度的均值即0.75，当然doug lea不是仅仅依靠这个就设置为0.75，
            4）真正的原因是 根据牛顿二项式推算出来，log2=0.69,最均衡的值是0.69，然后又根据空间与时间的均衡设置0.69

            在JDK1.7中HashMap会产生死锁的问题：
                死锁(链表形成了环)，在高并发下，多线程扩容，导致链表可能形成环，1.7中链表的插入，是头插法，
        在java1.8中

    (2) HashMap中链表转红黑树阈值=8，为什么阈值是8呢？
        很不幸链表转红黑树的时候长度不是8
    (3) HashMap初始容量要求必须是2的指数幂，为什么？
    (4) 数组的查询时间复杂度为O(1),使用一段连续存储单元存储数据，对于指定下标的查找，时间复杂度为O(1)，对于一般的插入删除操作，涉及到数组元素的移动，其平均复杂度为O(n)
    (5) 红黑树：
        红黑树：一种接近平衡的二叉搜索树，在每个结点上增加一个存储位表示节点的颜色，可以是Red或Black，通过对任何一条从根到叶子的路径上各个节点着色方式的限制，红黑树确保没有一条路径比其他路径长出两倍。
        特征：
            1)根结点是黑的
            2)每个叶结点（叶节点即指树尾端NULL指针或NULL节点）都是黑的。
            3)每个结点要么是红的要么是黑的
            4)如果一个节点是红的，那么它的两个儿子都是黑的。
            5)对于任意结点，其到叶结点尾端NIL指针的每条路径都包含相同数目的黑结点。
    (6)

    (7) Hash1.7死循环、Hash1.8如何解决死循环
```java
//取模运算与位运算的性能差异
public class BitAndMod {
    public static void main(String[] args) {
        bitTest();
        modTest();
    }

    public static void bitTest(){
        int number = 10000*10;
        int a =1;
        long start = System.currentTimeMillis();
        for(int i=number;i>0;i++){
            a = a & i;
        }
        long end = System.currentTimeMillis();
        System.out.println("位运算耗时："+ (end - start));
    }

    public static void modTest(){
        int number = 10000*10;
        int a =1;
        long start = System.currentTimeMillis();
        for(int i=number;i>0;i++){
            a = a % i;
        }
        long end = System.currentTimeMillis();
        System.out.println("取模运算耗时："+ (end - start));
    }
}
// 位运算耗时：557
// 取模运算耗时：15487
```

1、ConcurrentHashMap和HashTable中线程安全的区别？为啥建议用ConcurrentHashMap? 能把ConcurrentHashMap里面的实现详细的讲下吗？
答：
    HashMap线程不安全；
    HashTable线程安全，每个方法都加了 synchronized修饰。类似于Collections.synchronizedMap(hashMap)；对读写加锁，独占式，一个线程在读时其他线程必须等待，，吞吐量较低，性能较为低下。
    ConcurrentHashMap：利用CAS+Synchronized来保证并发的安全性。数据结构同HashMap.

2、保证线程安全还有其他方式么？



#### ConcurrentHashMap面试题
1、ConcurrentHashMap如何实现线程安全？



TSP+瓷砖+JVM(jstat\jmap)+Flink



#### ThreadLocal FastThreadLocal
1、问题：
    (1)ThreadLocal了解吗？项目有用过么？
    (2)知道ThreadLocal吗？讲讲你对ThreadLocal的理。
    (3)开放性面试题目：慢慢引导到这个话题上，比如提问："在多线程环境下，如何防止自己的变量被其它线程篡改"，将主动权交给你自己，剩下的考自己发挥。


2、ThreadLocal的应用场景
    (1) 需要在某处设置一个值，然后经过重重方法调用，到另外一处把这个值取出来，又要线程安全，。
    (2) ThreadLocal是用在多线程的场景。ThreadLocal归纳下来就2类用途
        1)保存线程上下文信息，在任意需要的地方可以获取
        2)线程安全的，避免某些情况需要考虑线程安全必须同步带来的性能损失。
    (3) 数据库连接池
    (4) 每个线程执行方法的数量

```java
This class provides thread-local variables. These variables differ from their normal counterparts in that each thread that accesses one (via its get or set method) has its own, independently initialized copy of the variable. ThreadLocal instances are typically private static fields in classes that wish to associate state with a thread (e.g., a user ID or Transaction ID).
For example, the class below generates unique identifiers local to each thread. A thread's id is assigned the first time it invokes ThreadId.get() and remains unchanged on subsequent calls.
   import java.util.concurrent.atomic.AtomicInteger;

   public class ThreadId {
       // Atomic integer containing the next thread ID to be assigned
       private static final AtomicInteger nextId = new AtomicInteger(0);

       // Thread local variable containing each thread's ID
       private static final ThreadLocal<Integer> threadId =
           new ThreadLocal<Integer>() {
               @Override protected Integer initialValue() {
                   return nextId.getAndIncrement();
           }
       };

       // Returns the current thread's unique ID, assigning it if necessary
       public static int get() {
           return threadId.get();
       }
   }

Each thread holds an implicit reference to its copy of a thread-local variable as long as the thread is alive and the ThreadLocal instance is accessible; after a thread goes away, all of its copies of thread-local instances are subject to garbage collection (unless other references to these copies exist).
Since:
1.2
```


#### 什么是ThreadLocal,说说你的理解？为什么使用ThreadLocal会出现内存泄露。
ThreadLocal的作用是提供线程内的局部变量，这种变量在线程的声明周期内起作用，减少同一个线程内多个函数或者组件之间一些公共变量的传递的复杂度，但是如果滥用ThreadLocal，就可能导致内存泄露。

下面，我们将围绕三个方面来分析ThreadLocal内存泄露的问题:

1. ThreadLocal实现原理
2. ThreadLocal为什么会内存泄露
3. ThreadLocal最佳实践
4. FastThreadLocal

1. ThreadLocal实现原理
    ThreadLocal的实现是：每个Thread维护一个ThreadLocalMap映射表（即在Thread内有一个类型位ThreadLocal.ThreadLocalMap 的threadLocals变量），这个映射表的key是ThreadLocal实例本身，value是真正需要存储的Object

    也就是说ThreadLocal本身不存储数据，他只是作为一个key来让线程从ThreadLocalMap中获取value.

    值得注意的是图中的虚线，表示ThreadLocalMap是使用ThreadLocal的弱引用作为key的，弱引用的对象在GC时会被回收掉。

2. ThreadLocal为什么会内存泄露
    ThreadLocal为了防止内存泄露做了很多工作：
        1、ThreadLocalMap使用ThreadLocal的弱引用作为key，如果一个ThreadLocal没有外部强引用来引用它，那么系统GC的时候，这个ThreadLocal势必会被回收。

        2、这样一来，ThreadLocalMap中就会出现key为null的Entry，就没有办法访问这些key为null的Entry的value，如果当前线程再迟迟不结束的话，这些key为null的Entry的value就会一直存在一条强引用链：Thread Ref -> Thread -> ThreadLocalMap -> Entry ->value 永远无法回收，造成内存泄露

        3、其实，ThreadLocalMap的设计中已经考虑到了这种情况，也加上了一些防护措施：
            在ThreadLocal的get()、remove()、set()的时候都会清除线程ThreadLocalMap里所有key为null的value。

    但是这些被动的预防措施并不能保证 不会内存泄露：
        1、使用static的ThreadLocal，延长了ThreadLocal的生命周期，可能导致内存泄露
            为什么要使用static的ThreadLocal：
                这个变量是针对一个线程内所有操作共享的，所以设置为静态变量，所有此类实例共享此静态变量，也就是说在类第一次使用时装载，只分配一块存储空间，所有此类的对象（只要是在这个线程内定义的）都可以操控这个变量。
        2、分配使用了ThreadLocal又不再调用get()、set()、remove()方法，那么就会导致内存泄露。

3. 为什么使用弱引用
    从表面上看内存泄露的根源在于使用了弱引用。网上的文章大多着重分析ThreadLocal使用了弱引用会导致内存泄露，但是另外一个问题同样也值得思考：为什么使用弱引用而不是强引用？
    我们先看看官方文档的说法：
    > to help deal with very large and long-lived usages,the hash table entries use WeakReferences for keys.
    即：为了应对非常大和长时间的用途，哈希表使用弱引用的key.

    下面我们分两种情况讨论：
    1、key使用强引用：
        引用的ThreadLocal对象被回收了，但是ThreadLocalMap还持有ThreadLocal的强引用，如果没有手动删除，ThreadLocal不会被回收，导致Entry内存泄漏
    2、key使用弱引用：
        引用的ThreadLocal的对象被回收了，ThreadLocalMap持有ThreadLocal的弱引用，即使没有手动删除，ThreadLocal也会被回收。Value在下一次ThreadLocalMap调用set,get,remove的时候会被清楚。

    > 由于ThreadMap的生命周期跟Thread一样长，如果没有手动删除对应key，会导致内存泄漏，但是使用弱引用可以多一层保障；弱引用ThreadLocal不会内存泄漏，对应的Value在下一次ThreadLocalMap调用set,get,remove的时候会被清除。
    >
    > 因此，ThreadLocal内存泄漏的根源是：由于ThreadLocalMap的生命周期跟Thread一样长，如果没有手动删除对应key就会导致内存泄漏，而不是弱引用。比如在线程池中没有手动删除
    >
ThreadLocal最佳实践
- 综合上面的分析，我们可以理解ThreadLocal内存泄漏的前因后果，那么怎么避免内存泄漏呢？
- 每次使用完ThreadLocal，都调用它的remove()方法么，清除数据
- 在使用线程池的情况下，没有及时清理ThreadLocal，不仅是内存泄露的的问题，更严重的可能导致业务逻辑出现问题，
- 所以,使用ThreadLocal就跟加完锁要解锁一样，用完就清理
https://mp.weixin.qq.com/s?__biz=MzI4Njc5NjM1NQ==&mid=2247486589&idx=1&sn=9905a86f2b3597064bb8399770a8c4a2&chksm=ebd63351dca1ba4793ea3e9afeed41bceae567bcc1c6cfe1fa6c53a8a1b927ccabff5e04ccc0&mpshare=1&scene=1&srcid=1016jqjCxamlIxY0aUUTXWaC%23rd
https://mp.weixin.qq.com/s?__biz=MzIxMjE5MTE1Nw==&mid=2653198611&idx=2&sn=f914bc1ae1ff4b93c5578e99590afb98&chksm=8c99ebc9bbee62df3caa745fbb86ec37f67271303cd72e734d63b6d23e8f86b6ec3853ee497a&mpshare=1&scene=1&srcid=%23rd

https://mp.weixin.qq.com/s?__biz=MzIzMzgxOTQ5NA==&mid=2247483704&idx=1&sn=8f4e188f6dbe6684e165da55a476df96&chksm=e8fe9d31df8914277327bc70e791ec385dae98339bbf002d83d9109d5902016ba0535d896530&mpshare=1&scene=1&srcid=0423bceFoSaR2Rzfz7AOwWzG%23rd

https://mp.weixin.qq.com/s?__biz=MzAxNjM2MTk0Ng==&mid=2247488007&idx=2&sn=eecd21ef0dc3c1d7a446c4b17be22488&chksm=9bf4a2b2ac832ba4d1615e6fb52eb7e02220f1f7d2803a3fbfb4ea8032682a47b1c187ea9634&mpshare=1&scene=1&srcid=&sharer_sharetime=1564618992191&sharer_shareid=6e56c4c46055b28f308537063448710b%23rd

https://mp.weixin.qq.com/s?__biz=MzAxODcyNjEzNQ==&mid=2247488266&idx=2&sn=9580e23007491c57cd62cc6f00b42c36&chksm=9bd0be92aca737843f3dec1bffcca52bd58dfc379d246a5036094475caf220d5348c1a459fee&mpshare=1&scene=1&srcid=&sharer_sharetime=1567730764940&sharer_shareid=6e56c4c46055b28f308537063448710b%23rd

https://mp.weixin.qq.com/s?__biz=MzIwMzY1OTU1NQ==&mid=2247485242&idx=1&sn=7345157dbce38650457bb05d02a3c99c&chksm=96cd4776a1bace60f30685fece2b2a5b78cc69fe4bb61f2b3d164c7e285093e595be93fa7865&mpshare=1&scene=1&srcid=0423E4reAkVSBC0kM4KjcweV%23rd


## FastThreadLocal
- FastThreadLocal的创建
- FastThreadLocal的get()方法
- FastThreadLocal的set()方法

1. FastThreadLocal的创建

2. FastThreadLocal的get()方法
    (1) 获取ThreadLocalMap
    (2) 直接通过索引取出对象
    (3) 初始化

## FastThreadLocal
FastThreadLocal是netty提供的，在池化内存分配等都有涉及到!
关于FastThreadLocal，准备从这几个方面进行复习
- ThreadLocal的工作原理
- FastThreadLocal的工作原理
- FastThreadLocal的使用
- FastThreadLocal并不是什么情况都快，要用对才会快
- FastThreadLocal利用字节填充来解决伪共享问题
- FastThreadLocal比ThreadLocal快，并不是空间换时间
- FastThreadlocal为什么快？

1、ThreadLocal的工作原理：
    (1) 每个线程内部维护了一个ThreadLocal.ThreadLocalMap类型的变量threadLocals
    (2) 每个ThreadLocalMap是由数组实现的Map，key为ThreadLocal,value为对应的变量
    (3) 对ThreadLocal进行get/set操作时，会获取当前Thread内部的ThreadLocal.ThreadLocalMap，然后以ThreadLocal为key，从这个Map中获取对应的value就是结果。
1.1、设计理念
    (1) ThreadLocal中的数据实际存放于Thread中，线程死亡时，这些数据会被自动释放，减小开销。
    (2) 一般来说，一个ThreadLocal对应Thread的数量远多于一个ThreadLocal数量，因此Thread内部维护的ThreadLocal.ThreadLocalMap的长度一般老说是较短的，寻址快
1.2、存在的问题：
    ThreadLocal的问题存在于ThreadLocal.ThreadLocalMap中查找时，采用线性探测法，一般情况下时间复杂度是O(1),但是发生哈希冲突时，可能退化到O(n)的时间复杂度。
    Netty中针对此处做了优化。
1.3 ThreadLocal的最佳实践做法

```java
try{
    //其它业务逻辑
}finally{
    threadLocal对象.remove();
}
```

```java
ThreadLocalMap.get()
    public T get() {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);//获取当前线程内部维护的ThreadLocalMap对象
        if (map != null) {
            ThreadLocalMap.Entry e = map.getEntry(this);//以当前TheadLocal为key，在ThreadLocalMap中查询数据
            if (e != null) {
                @SuppressWarnings("unchecked")
                T result = (T)e.value;
                return result;
            }
        }
        return setInitialValue();
    }

ThreadLocal.ThreadLocalMap.getEntry()
        /**
         * Get the entry associated with key.  This method
         * itself handles only the fast path: a direct hit of existing
         * key. It otherwise relays to getEntryAfterMiss.  This is
         * designed to maximize performance for direct hits, in part
         * by making this method readily inlinable.
         *
         * @param  key the thread local object
         * @return the entry associated with key, or null if no such
         */
        private Entry getEntry(ThreadLocal<?> key) {
            int i = key.threadLocalHashCode & (table.length - 1);//ThreadLocal的threadLocalHashCode是在定义ThreadLocal时产生的一个伪随机数，可以理解为ThreadLocal的hashCode，此处用其计算ThreadLocal在ThreadLocalMap中的下标
            Entry e = table[i];//寻址
            if (e != null && e.get() == key)//命中
                return e;
            else
                return getEntryAfterMiss(key, i, e);//未命中，目标地址上存储了另外一个ThreadLocal及其对应的value(hash碰撞)
        }

ThreadLocal.ThreadLocalMap.getEntryAfterMiss()
        private Entry getEntryAfterMiss(ThreadLocal<?> key, int i, Entry e) {
            Entry[] tab = table;
            int len = tab.length;

            while (e != null) {
                ThreadLocal<?> k = e.get();
                if (k == key)
                    return e;
                if (k == null)
                    expungeStaleEntry(i);//如果key为null，则删除对应的value（由于ThreadLocalMap中的Entry扩展于WeakReference，因此如果ThreadLocal没有强引用的情况下，ThreadLocal会被gc回收掉，此时key为空。为了便于gc，需要同时删除对value的引用）
                else
                    i = nextIndex(i, len);//查找ThreadLocalMap中的下一个元素，直到命中为止（很明确的线性探测法）
                e = tab[i];
            }
            return null;
        }
```
2、Netty中FastThreadLocal原理
原理简介：
    (1) FastThreadLocal的构造方法中，会为当前ThreadLocal分配一个index，这个index是由一个全局唯一的static类的AtomicInteger产生的，可以保证每个FastThreadLocal的index不同
    (2) FastThreadLocal需要与FastThreadLocalThread配套使用



3、Java中的引用
说到ThreadLocal肯定是需要谈到引用
> 先来提个问题：threadLocal的key是弱引用，那么在threaLocal.get()的时候，发生GC之后，key是否是null？

这个问题晃眼一看，弱引用嘛，还有垃圾回收那肯定是为null，这其实是不对的，因为题目说的是在做threadLocal.get()操作，证明还是有强引用存在的。所以key并不为null。如果我们的强引用不存在的话，那么key就会被回收，也就会出现我们value没被回收，key被回收，导致value永远存在，出现内存泄露，这也是ThreadLocal经常会被很多书籍提醒到需要remove()的原因。

>也许你会问：看到很多源码的ThreadLocal并没有写remove依然能用的很好呢？

那是因为很多源码中ThreadLocal是经常作为静态变量存在的，其生命周期和class是一样的。而remove（）需要使用在那些--在方法或对象里面使用ThreadLocal的情况，因为方法栈或者对象的销毁导致强引用的丢失，从而导致内存泄露。