# 解题思路

# 我的解题思路
1. 使用暴力解，但是时间超时
2. 想到此题类似子串的题目，感觉应该可以使用滑动窗口进行解题，但是没想出答案

```java
//暴力解
class Solution {
    public int subarraysDivByK(int[] A, int K) {
        if(A==null||A.length==0)return 0;
        int res = 0;

        for(int i=0;i<A.length;i++){
            int sum = A[i];
            if(sum%K==0){
                    res++;
                }
            for(int j=i+1;j<A.length;j++){
                sum = sum + A[j];
                if(sum%K==0){
                    res++;
                }
            }
        }
        return res;
    }
}
```
# 参考解题思路
1. 使用HashMap
2. prefix sum，前缀和
