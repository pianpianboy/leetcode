# 题目
198. 打家劫舍

## 解题思路
- 动态规划

```java
class Solution {
    public int rob(int[] nums) {
        if(nums==null||nums.length==0) return 0;

        int dp[] = new int[nums.length+2];
        dp[0] = 0;
        dp[1] = 0;
        for(int i =2;i<nums.length+2;i++){
            dp[i] = Math.max(dp[i-1],dp[i-2]+nums[i-2]);
        }
        return dp[nums.length+1];
    }
}

```

