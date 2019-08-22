# 排序算法

## 冒泡排序

## 选择排序

## 插入排序
- 时间复杂度为O(N*logN), 三个时间复杂度为O(N*logN) 归并排序，堆排序、快排序

## 归并排序
- 实现过程
    ```java
    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        mergeSort(arr, 0, arr.length - 1);
    }

    public static void mergeSort(int[] arr, int l, int r) {
        if (l == r) {
            return;
        }
        int mid = l + ((r - l) >> 1);
        mergeSort(arr, l, mid);
        mergeSort(arr, mid + 1, r);
        merge(arr, l, mid, r);
    }

    public static void merge(int[] arr, int l, int m, int r) {
        int[] help = new int[r - l + 1];
        int i = 0;
        int p1 = l;
        int p2 = m + 1;
        while (p1 <= m && p2 <= r) {
            help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= m) {
            help[i++] = arr[p1++];
        }
        while (p2 <= r) {
            help[i++] = arr[p2++];
        }
        for (i = 0; i < help.length; i++) {
            arr[l + i] = help[i];
        }
    }
    ```

- 代码实现

- 应用及使用场景
    + 小和问题
        * 题目：在一个数组种，每一个左边比当前数小的数累加起来，叫做这个数组的小和，求一个数组的小和。常见左神初级第一季，最后几分钟pdf
        * 分析：小和问题，就是先求列出每个值前面比他小的所有数，然后求和；比如1 2 4 7 3 6 5，列出所有小和为1， 1 2，1 2 4， 1 2， 1 2 4 3， 1 2 4 3；可以将上述过程转换为求每一个值arr[i]后面有多少个比他大的数，并求其和，例如1的后面，有留个数比他大，和为1*6，对比前面1 的确出现了6次。
        * 为什么能解决小和问题？：在归并排序的merge过程种，就能求出left到mid，mid到right这两段中，对于left到mid中的值，mid到right中有多少个比其大的（此时mid到right已经是有序的了）。
        * 代码实现
        ```java
        public static int smallSum(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return mergeSort(arr, 0, arr.length - 1);
    }

    public static int mergeSort(int[] arr, int l, int r) {
        if (l == r) {
            return 0;
        }
        int mid = l + ((r - l) >> 1);
        return mergeSort(arr, l, mid) + mergeSort(arr, mid + 1, r) + merge(arr, l, mid, r);
    }

    public static int merge(int[] arr, int l, int m, int r) {
        int[] help = new int[r - l + 1];
        int i = 0;
        int p1 = l;
        int p2 = m + 1;
        int res = 0;
        while (p1 <= m && p2 <= r) {
            res += arr[p1] < arr[p2] ? (r - p2 + 1) * arr[p1] : 0;
            help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= m) {
            help[i++] = arr[p1++];
        }
        while (p2 <= r) {
            help[i++] = arr[p2++];
        }
        for (i = 0; i < help.length; i++) {
            arr[l + i] = help[i];
        }
        return res;
    }
        ```
    + 逆序对问题
        * 题目：一个数组种，左边的数如果比右边的数大，则这两个数构成一个逆序对，请打印所有逆序对？
        * 为什么能解决逆序对问题
        * 代码实现

















