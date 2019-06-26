# 解题思路

## 我的解题思路
想办法求出所有子数组，然后求出子数组中的最小值，暴力解O(n*n*n)

## 参考解题思路
- 使用单调栈https://leetcode.com/problems/sum-of-subarray-minimums/discuss/178876/stack-solution-with-very-detailed-explanation-step-by-step
1. 使用单个栈（Stack）
```java
class Solution {
    public int sumSubarrayMins(int[] A) {
        //使用单调栈解决
        if(A==null||A.length<1)return 0;

        Stack<Integer> stack = new Stack<>();
        //或者使用双端对队列queue?

        //建立一个单调递增的栈
        //为什么是单调递增栈呢：因为要找到左边第一个比cur值小的同时又要找到右边第一个比他小的值
        //但出现A[i]比栈顶的元素小的情况(由于此时栈是单调递增的)，则弹出栈中比A[i]小的元素，这可以计算子数组的个数，继而求出以A[i]为最小值的所有子数组的和
        int res = 0;
        int mod = (int)1E9+7;
        // int j=0;
        // int k=0;

        for(int i=0;i<=A.length;i++){
            while(!stack.isEmpty()&&A[stack.peek()]>(i==A.length?0:A[i])){
                int cur  = stack.pop();
                int left = stack.isEmpty()? -1:stack.peek();
                res = (res+(i-cur)*(cur-left)*A[cur])%mod;
            }
            stack.push(i);
        }
        return res;
    }
}
```
2. 使用栈（deque双端队列）
```java

```


3. 使用两个栈
```java

```
