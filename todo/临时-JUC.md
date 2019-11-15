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
    ThreadLocal为了防止内存泄露做了很多工作





