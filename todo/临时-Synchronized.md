# Synchronized

## 1、Java性能调优实战中12讲Synchronized
## 2、Java并发编程实战
## 3、狸猫技术窝

## 1、Java性能调优实战中12讲Synchronized

### 开头
**在并发编程中，多个线程访问同一个共享资源时候，我们必须考虑如何如何维护数据的原子性**，在JDK1.5之前，Java是依靠Synchronized关键字实现锁功能来做到这点的。Synchronized是JVM实现的一种内置锁，锁的获取和释放是由JVM隐式实现的。

到了JDK1.5版本，并发包中新增了Lock接口来实现锁功能，他提供了与Synchronized关键字类似的同步功能，只是在使用时需要显示获取和释放做。

Lock同步锁时基于java实现的，而Synchronized是基于底层操作系统的Mutex Lock实现的，每次获取和释放操作都会带来用户态和内核态的切换，从而增加系统性能开销。因此在锁竞争激烈的情况下，synchronized同步锁在性能上就表现得非常糟糕，它也常被称为重量级锁。

### JDK1.6对Sychronized的优化
到了JDK1.6版本之后，Java对Synchronized同步锁做了充分的优化，甚至在某些场景下，它的性能已经超越了Lock同步锁，接下来就来看看Synchronize同步锁是通过了哪些优化，实现了性能的提升。

#### Synchronized 同步锁实现原理
通常Synchronized 实现同步锁的方式有两种，一种修饰方法，一种是修饰方法块。如下：

##### 通过源码分析
```java
//关键字在实例方法上，锁为当前实例
public synchronized void method1(){
//code
}
//关键字在代码块上，锁为括号里的对象
public void methdo2(){
    object o = new Object();
    synchronized(o){
        //code
    }
}
```
```java
执行如下命令
~/workspace/Data/JUC  javac -encoding UTF-8 SyncTest.java
~/workspace/Data/JUC  javap -v SyncTest.class
```
##### Synchronized修饰方法是怎么实现锁原理的？
JVM中的同步是基于进入和退出管程（Monitor）对象实现的。每个对象实例都会有一个Monitor,Monitor可以和对象一起创建、销毁。Monitor是由ObjectMonitor实现，而ObjectMonitor是由C++的ObjectMonitor.hpp文件实现。如下所示：

```c++
ObjectMonitor() {
   _header = NULL;
   _count = 0; // 记录个数
   _waiters = 0,
   _recursions = 0;
   _object = NULL;
   _owner = NULL;
   _WaitSet = NULL; // 处于 wait 状态的线程，会被加入到 _WaitSet
   _WaitSetLock = 0 ;
   _Responsible = NULL ;
   _succ = NULL ;
   _cxq = NULL ;
   FreeNext = NULL ;
   _EntryList = NULL ; // 处于等待锁 block 状态的线程，会被加入到该列表
   _SpinFreq = 0 ;
   _SpinClock = 0 ;
   OwnerIsThread = 0 ;
}
```

*** 当多个线程同时访问一段同步代码时，多个线程会先被存放在EntryList集合中，处于block状态的线程，都会被加入到该列表 ***



![Demo](images/test.png)

#### 锁升级

#### Java对象头

##### 1、偏向锁

##### 2、轻量级锁

##### 3、自旋锁与重量级锁

#### 动态编译实现锁消除/锁粗化

#### 减小锁粒度

### 总结






