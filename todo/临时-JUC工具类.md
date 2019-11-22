# 并发工具类
- join类
- CountDownLatch类
- CyclicBarrier

## join类


## CountDownLatch类
### CountDownLatch类使用
```java
 /**执行时间达到时，所有线程需要依次退出，主线程才开始统计执行事务总数*/
    private static CountDownLatch countDownLatch = new CountDownLatch(50);
    //在另外的线程中执行
    new Thread(new Runnable(){
        for(){
            countDownLatch.countDown();
        }
    }).start();

    //主线程等到所有线程都退出，则开始统计
    countDownLatch.await();

    //主要应用于tps计算，main线程需要等待线程池中所有线程执行完成任务后才能计算tps

```
### CountDownLatch类的底层原理



## CyclicBarrier


## 如何让线程步调一致？ 让线程按顺序执行的方法有哪些？


















