package com.pianpianboy.leetcode.leetcode.threadLocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName TestTheadLocalLeak
 * @Description TODO
 * @Author liaomengjie
 * @Date 2020-03-26 22:50
 */
public class TestTheadLocalLeak {
    private static ThreadLocal<byte[]> LOCAL = new ThreadLocal<>();
    private static int _1M = 1024*1024;

    public static void main(String[] args) {
        testUseThreadPool();

    }

    public static void testUseThread(){
        for(int i=0;i<100;i++){
            new Thread(()->LOCAL.set(new byte[_1M])).start();
        }
    }

    public static void testUseThreadPool(){
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for(int i=0;i<100;i++){
            executorService.execute(()->LOCAL.set(new byte[_1M]));
            System.out.println(LOCAL.get());
        }
        executorService.shutdown();
    }
}
