package com.pianpianboy.leetcode.leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @ClassName Test5
 * @Description TODO
 * @Author liaomengjie
 * @Date 2020-03-20 14:27
 */
public class Test5 {
    public static void main(String[] args) {
        int[] arr = {5,4,1,3,6,2,9};
        int[] res = getLeastNumbers(arr,3);
        System.out.println();
    }

    public static int[] getLeastNumbers(int[] arr, int k) {
        if (k == 0) {
            return new int[0];
        }
        // 使用一个最大堆（大顶堆）
        // Java 的 PriorityQueue 默认是小顶堆，添加 comparator 参数使其变成最大堆
        Queue<Integer> heap = new PriorityQueue<>(k, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });

        for (int e : arr) {
            // 当前数字小于堆顶元素才会入堆
            if (heap.isEmpty() || heap.size() < k || e < heap.peek()) {
                heap.offer(e);
            }
            if (heap.size() > k) {
                heap.poll(); // 删除堆顶最大元素
            }
        }

        // 将堆中的元素存入数组
        int[] res = new int[heap.size()];
        int j = 0;
        for (int e : heap) {
            res[j++] = e;
        }
        return res;
    }

    public static int[] getKthNum(int[] arr,int k){
        if(k==0||arr.length==0) return new int[0];

        PriorityQueue<Integer> queue = new PriorityQueue<>(k, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });

        for(int i=0;i<arr.length;i++){
            queue.offer(arr[i]);
        }
        int[] res = new int[k];
        while(k>0){
            res[--k] = queue.poll();
        }
        return res;
    }
}
