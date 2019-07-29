# 解题思路

## 我的解题思路
- 暴力解：一个循环解决
```java
class Solution {
    public int findLengthOfLCIS(int[] nums) {
        if(nums == null||nums.length==0)
            return 0;

        int max = 1;
        int tmp = 1;
        for(int i=1;i<nums.length;i++){
            if(nums[i] - nums[i-1]>0){
                tmp++;
            }else{
                max = Math.max(max,tmp);
                tmp=1;
            }
        }
        max = Math.max(max,tmp);
        return max;
    }
}
```
- 简单DP动态规划解决\:动态方程：dp[i]=nums[i]>nums[i-1]?dp[i-1]+1:0;
```java
class Solution {
    public int findLengthOfLCIS(int[] nums) {
        if(nums == null||nums.length==0)
            return 0;
        int[] dp = new int[nums.length];
        //使用动态规划，动态方程：dp[i]=nums[i]>nums[i-1]?dp[i-1]+1:0;
        int max = 1;
        dp[0]=1;
        for(int i=1;i<nums.length;i++){
            dp[i] = nums[i] > nums[i-1]? dp[i-1]+1 : 1;
            max = Math.max(max,dp[i]);
        }
        return max;
    }
}
```

