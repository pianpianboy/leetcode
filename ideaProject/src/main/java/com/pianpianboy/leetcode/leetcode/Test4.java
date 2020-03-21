package com.pianpianboy.leetcode.leetcode;

/**
 * @ClassName Test4
 * @Description TODO
 * @Author liaomengjie
 * @Date 2020-03-20 12:06
 */
public class Test4 {

    public static void main(String[] args) {
        int[]arr = {7,3,1,2,1,6,4,5};
        partition(0,7,arr,5);
        System.out.println();
    }
    public static int partition(int L,int R, int[] arr, int k){
        int less = L-1;
        int more = R-1;
        int val = arr[R];
        int index = L;
        while(index<more){
            if(arr[index]<val){//当前值比index处的值要小
                swap(arr,++less,index++);
            }else if(arr[index]>val){
                swap(arr,index,more--);
            }else{//当前值和index处的值相等，index++
                index++;
            }
        }
        System.out.println("index: "+index);
        return index;
    }

    public  static void swap(int[] arr,int i,int j){
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
