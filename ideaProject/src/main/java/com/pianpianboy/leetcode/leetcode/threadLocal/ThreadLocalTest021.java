package com.pianpianboy.leetcode.leetcode.threadLocal;

/**
 * @ClassName ThreadLocalTest02
 * @Description TODO
 * @Author liaomengjie
 * @Date 2020-03-27 15:10
 */
public class ThreadLocalTest021 {
    public static class MyThreadLocal extends ThreadLocal{
        private byte[] a = new byte[1*1024*1024];

        @Override
        protected void finalize() throws Throwable {
            System.out.println("My threadLocal 1MB finalized");
        }
    }

    public static class My50MB{//占用内存的大对象
        private byte[] a  = new byte[1024*1024*50];

        @Override
        protected void finalize() throws Throwable {
            System.out.println("My 50MB finalized.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ThreadLocal tl = new MyThreadLocal();
                tl.set(new My50MB());

                tl = null;//断开ThreadLocal的强引用
                System.out.println("Full GC 1");
                System.gc();
            }
        }).start();

        System.out.println("Full GC 2");
        System.gc();

        Thread.sleep(1000);
        System.out.println("Full GC 3");
        System.gc();
        Thread.sleep(1000);
        System.out.println("Full GC 4");
        System.gc();
        Thread.sleep(1000);
    }
}
