### 题目
面试题40. 最小的k个数

### 解题思路
- 快排
    + 1.先取一个基准位置的值val,经典快排都是取数组最右位置的值val=arr[R]
    + 2.初始化小于区域less=L-1, 初始化大于区域为more=R(这里要主要注意为什么不是more= R+1，在荷兰国旗问题中more=R+1为什么呢因为荷兰国旗问题是求小于给定值num放在左边，等于放在中间，大于num放在右边，但是快排中或者k小的值的问题中并没有给定这个基准值val，因此需要在数组中指定，一般指定为数组末尾的值，而这个值要固定不变，因此只能交换R前一个位置的值)
    + 3.初始化index=L，从L开始遍历数组，知道L《more则遍历结束，因为最终数组会被分为[L,less]小于部分,[less+1,index]等于部分，[index+1,more]大于部分，这三部分。
    + 4.arr[index]《val,则index的值和小于等于区域（less）的下一个位置进行交换（即小于等于区域扩了一个位置），然后index+1
    + 5.arr[index]>val,则index的值和more的前一个位置进行交换，index的值继续不变
    + 6.arr[index]==val，则index++
- 堆排序

```java
class Solution {
    public int[] getLeastNumbers(int[] arr, int k) {
        if(k==0||arr.length==0) return new int[0];
        partitionArr(0,arr.length-1,arr,k-1);

        int[] res = new int[k];
        for(int i=0;i<k;i++){
            res[i] = arr[i];
        }
        return res;

    }

    public void partitionArr(int L,int R,int[] arr, int k){
        int m = partition(L,R,arr,k);
        if(m>k){
           partitionArr(L,m-1,arr,k);
        }else if(m<k){
           //partitionArr(L,m+1,arr,k-m); 容易出错的地方
            partitionArr(m+1,R,arr,k);
        }else{
           return;
        }
    }

    /**
     * 快排的实现思路：
     * 1.先取一个基准位置的值val,经典快排都是取数组最右位置的值val=arr[R]
     * 2.初始化小于区域less=L-1, 初始化大于区域为more=R(这里要主要注意为什么不是more= R+1，在荷兰国旗问题中more=R+1为什么呢因为荷兰国旗问题是求小于给定值num放在左边，等于放在中间，大于num放在右边，但是快排中或者k小的值的问题中并没有给定这个基准值val，因此需要在数组中指定，一般指定为数组末尾的值，而这个值要固定不变，因此只能交换R前一个位置的值)
     * 3.初始化index=L，从L开始遍历数组，知道L<more则遍历结束，因为最终数组会被分为[L,less]小于部分,[less+1,index]等于部分，[index+1,more]大于部分，这三部分。
     * 4.arr[index]<val,则index的值和小于等于区域（less）的下一个位置进行交换（即小于等于区域扩了一个位置），然后index+1
     * 5.arr[index]>val,则index的值和more的前一个位置进行交换，index的值继续不变
     * 6.arr[index]==val，则index++
     */
    public int partition(int L,int R, int[] arr, int k){
        int less = L-1;
        int more = R;//这个地方也是容易出错的地方
        int val = arr[R];
        int index = L;
        while(index<more){
            if(arr[index]<val){//当前值比index处的值要小
                swap(arr,++less,index++);
            }else if(arr[index]>val){
                swap(arr,index,--more);
            }else{//当前值和index处的值相等，index++
                index++;
            }
        }
        //最后交换基准中心位置的值 arr[R]和index位置的值，因为需要将数组 变为左边比arr[R]小右边比arr[R]大
        swap(arr,index,R);
        System.out.println("index: "+index);
        return index;
    }

    public void swap(int[] arr,int i,int j){
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
```