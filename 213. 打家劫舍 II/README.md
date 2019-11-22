# 题目
213. 打家劫舍 II

## 解题思路
- 动态规划
    + 这一题和打家劫舍I 的解题思路，但是却比I要稍微难，因为增加了一个限制条件
    + 这里有个小技巧就是我们线性考虑[0,n-2]和[1,n-1]，然后求二者的最大值。


```java
class Solution {
    public int rob(int[] nums) {
        if(nums==null||nums.length==0)return 0;
        if(nums.length==1) return nums[0];
        if(nums.length==2) return Math.max(nums[0],nums[1]);

        int res1 = help(Arrays.copyOfRange(nums,0,nums.length-1));
        int res2 = help(Arrays.copyOfRange(nums,1,nums.length));

        return Math.max(res1,res2);
    }

    public int help(int[] arr){
        int len = arr.length;
        int[] dp = new int[len];

        dp[0] = arr[0];
        dp[1] = Math.max(arr[0],arr[1]);

        for(int i=2;i<len;i++){
            dp[i] = Math.max(dp[i-1],dp[i-2]+arr[i]);
        }
        return dp[len-1];
    }
}
```