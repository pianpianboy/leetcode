# 题目
740. 删除与获得点数

## 解题思路
- 动态规划
    + 分析状态 dp[i],其中i为数组中的每个值，比如nums[j],dp[i]即dp[nums[i]]的最大删除获得点数
    + 为了更好的处理nums[i],将nums数组装换为 arr，arr数组下标为nums[j]的值，arr[nums[j]]为出现的次数
    + 分析状态转移方程：dp[i] = Math.max(dp[i-1],dp[i-2]_i*arr[i]_)
    + 分析边界条件
        * dp[0] = 0; 即0*次数
        * dp[1] = arr[1]; 即1*次数


```java
class Solution {
    public int deleteAndEarn(int[] nums) {
        if(nums==null||nums.length==0) return 0;

        int len = nums.length;
        int max = 0;
        for(int i=0;i<len;i++){
            max = Math.max(max,nums[i]);
        }
        int[] arr = new int[max+1];

        for(int i=0;i<len;i++){
            arr[nums[i]]++;
        }
        int[] dp = new int[max+1];
        dp[0] = 0;
        dp[1] = arr[1];// arr[1]*1

        for(int i=2;i<=max;i++){
            dp[i] = Math.max(dp[i-1], dp[i-2]+ arr[i]*i);
        }
        return dp[max];
    }
}

```
