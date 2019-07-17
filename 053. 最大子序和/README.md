# 解题思路

## 我的解题思路
- 使用回溯？因为此题没有剪枝条件可用，所以回溯不可哟个
- 先使用暴力解
- 使用动态规划：**通过把原问题分解为相对简单的子问题的方式求解复杂问题的方法。动态规划常常适用于有重叠子问题和最优子结构性质的问题。** 1、分析最优解的结构特征2、简历最优值的递归式3、自底向上计算最优值，并记录

```java
//暴力解
class Solution {
    public int maxSubArray(int[] nums) {
        int max = Integer.MIN_VALUE;
        for(int i=0;i<nums.length;i++){
            int tmp = 0;
            int tmax = Integer.MIN_VALUE;
            for(int j=i;j<nums.length;j++){
                tmp = tmp + nums[j];
                tmax =Math.max(tmax,tmp);
            }
            max = Math.max(max,tmax);
        }
        return max;
    }
}
```
```java
//动态规划解一
class Solution {
    public int maxSubArray(int[] nums) {
        int max = Integer.MIN_VALUE;
        int[] dp = new int[nums.length];
        for(int i=0;i<nums.length;i++){
            if(i==0)
                dp[i] = nums[0];
            else
                dp[i] = Math.max(dp[i-1]+nums[i],nums[i]);
           max = Math.max(max,dp[i]);
        }
        return max;
    }
}
```