### Top K 问题及快排quickSort即相关题型的解答
使用到快排技术的题目：

比如荷兰国旗问题，求前k个最小值问题，但是所有的快排问题需要解决经典快排的缺陷。

比如 面试题40. 最小的k个数这道题：这道题是一个经典的 Top K 问题，是面试中的常客。Top K 问题有两种不同的解法，一种解法使用堆（优先队列），另一种解法使用类似快速排序的分治法。这两种方法各有优劣，最好都掌握。本文用图解的形式讲解这道问题的两种解法，包括三个部分：
方法一：堆，时间复杂度 O(nlogK) 方法二：快排变形，（平均）时间复杂度 O(n) 两种方法的优劣比较

- 面试题40 最小的k个数
- 面试题 17.09 第 k 个数
- 60.第k个排列
- 402.移掉K位数字
- 347.前 K 个高频元素
- 692.前K个高频单词
- 560.和为K的子数组
- 23.合并K个排序链表
- 440.字典序的第K小数字
- 703.数据流中的第K大元素
- 698.划分为k个相等的子集
- 378.有序矩阵中第K小的元素
- 658.找到 K 个最接近的元素
- 面试题54.二叉搜索树的第k大节点
- 面试题22.链表中倒数第k个节点
- 215.数组中的第K个最大元素
- 973.最接近原点的 K 个点
- 192.统计词频
- 451.根据字符出现频率排序
- 659.分割数组为连续子序列

### Top K 问题的解法
- 堆（最小堆、最大堆）
    + 比较直观的想法是使用堆数据结构来辅助得到最小的k个数。堆的性质是每次可以找出最大或最小的元素。我们可以使用一个大小为k的最大堆（大顶堆），将数组中的元素依次入堆，当堆的大小超过 k时，便将多出的元素从堆顶弹出。
    + 这样，由于每次从堆顶弹出的数都是堆中最大的，最小的 k 个元素一定会留在堆里。这样，把数组中的元素全部入堆之后，堆中剩下的 k 个元素就是最大的 k 个数了。
    + 堆的内部结构，因为这部分内容并不重要。我们只需要知道堆每次会弹出最大的元素即可。在写代码的时候，我们使用的也是库函数中的优先队列数据结构，如 Java 中的 PriorityQueue。在面试中，我们不需要实现堆的内部结构，把数据结构使用好，会分析其复杂度即可。
- 快排

#### 两种方法的优劣性比较
在面试中，另一个常常问的问题就是这两种方法有何优劣。看起来分治法的快速选择算法的时间、空间复杂度都优于使用堆的方法，但是要注意到快速选择算法的几点局限性：

第一，算法需要修改原数组，如果原数组不能修改的话，还需要拷贝一份数组，空间复杂度就上去了。

第二，算法需要保存所有的数据。如果把数据看成输入流的话，使用堆的方法是来一个处理一个，不需要保存数据，只需要保存 k 个元素的最大堆。而快速选择的方法需要先保存下来所有的数据，再运行算法。当数据量非常大的时候，甚至内存都放不下的时候，就麻烦了。所以当数据量大的时候还是用基于堆的方法比较好。

PriorityQueue的用法：
```java
//对应的是小根堆（即最小的节点在根节点）记忆技巧o1在o2的前面
PriorityQueue<Integer> queue = new PriorityQueue<>(k, new Comparator<Integer>() {
    @Override
    public int compare(Integer o1, Integer o2) {
        return o1-o2;
    }
});
```
```java
//对应的是大根堆（即最大的节点在根节点）记忆技巧o2在o1的前面
PriorityQueue<Integer> queue = new PriorityQueue<>(k, new Comparator<Integer>() {
    @Override
    public int compare(Integer o1, Integer o2) {
        return o2-o1;
    }
});
```

#### 面试题40. 最小的k个数
```java
//使用小根堆解法
class Solution {
    public int[] getLeastNumbers(int[] arr, int k) {
        if(k==0||arr.length==0) return new int[0];
        PriorityQueue<Integer> queue = new PriorityQueue<Integer>(k,new Comparator<Integer>(){
            public int compare(Integer val1,Integer val2){
                return val1-val2;
            }
        });

        for(int i=0;i<arr.length;i++){
            queue.offer(arr[i]);
        }
        int[] res  = new int[k];
        while(k>0){
            res[--k] = queue.poll();
        }
        return res;
    }
}
```


```java
//快排
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
上述解法虽然能AC但是是有缺陷的，上述代码使用的是经典快排在最差的情况下时间复杂度可能会退化成O(n*n)，需要对其进行改进，可以在上述代码的partition()方法中增加如下代码即可改善时间复杂度。
```java
 swap(arr,L+(int)(Math.random()*(R-L+1)),R);
```

#### 快排
```java
package basic_class_01;

import java.util.Arrays;

public class Code_04_QuickSort {

    public static void quickSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        quickSort(arr, 0, arr.length - 1);
    }

    public static void quickSort(int[] arr, int l, int r) {
        if (l < r) {
            swap(arr, l + (int) (Math.random() * (r - l + 1)), r);
            int[] p = partition(arr, l, r);
            quickSort(arr, l, p[0] - 1);
            quickSort(arr, p[1] + 1, r);
        }
    }

    public static int[] partition(int[] arr, int l, int r) {
        int less = l - 1;
        int more = r;
        while (l < more) {
            if (arr[l] < arr[r]) {
                swap(arr, ++less, l++);
            } else if (arr[l] > arr[r]) {
                swap(arr, --more, l);
            } else {
                l++;
            }
        }
        swap(arr, more, r);
        return new int[] { less + 1, more };
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // for test
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            quickSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        int[] arr = generateRandomArray(maxSize, maxValue);
        printArray(arr);
        quickSort(arr);
        printArray(arr);

    }

}

```



#### 荷兰国旗问题

```java
package basic_class_01;

public class Code_08_NetherlandsFlag {

    public static int[] partition(int[] arr, int l, int r, int p) {
        int less = l - 1;
        int more = r + 1;
        while (l < more) {
            if (arr[l] < p) {
                swap(arr, ++less, l++);
            } else if (arr[l] > p) {
                swap(arr, --more, l);
            } else {
                l++;
            }
        }
        return new int[] { less + 1, more - 1 };
    }

    // for test
    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // for test
    public static int[] generateArray() {
        int[] arr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 3);
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] test = generateArray();

        printArray(test);
        int[] res = partition(test, 0, test.length - 1, 1);
        printArray(test);
        System.out.println(res[0]);
        System.out.println(res[1]);

    }
}

```

### 347. 前 K 个高频元素
```java
//堆：大根堆==优先级队列
class Solution {
    public List<Integer> topKFrequent(int[] nums, int k) {
        List<Integer> list = new LinkedList<>();
        if(nums==null||nums.length==0||k<=0){
            return list;
        }
        HashMap<Integer,Integer> map = new HashMap<>();
        PriorityQueue<Map.Entry<Integer,Integer>> queue = new PriorityQueue<>(new Comparator<Map.Entry<Integer,Integer>>(){
            @Override
            public int compare(Map.Entry<Integer,Integer> o1, Map.Entry<Integer,Integer> o2){
                return o2.getValue()-o1.getValue();
            }
        });

        for(int i=0;i<nums.length;i++){
            int val = nums[i];
            if(map.containsKey(val)){
                map.put(val,map.get(val)+1);
            }else{
                map.put(val,1);
            }
        }

        for(Map.Entry<Integer,Integer>entry :map.entrySet()){
            queue.offer(entry);
        }

        while(k>0){
            Map.Entry<Integer,Integer> entry = queue.poll();
            int key = entry.getKey();
            list.add(key);
            k--;
        }

        return list;
    }
}
```



