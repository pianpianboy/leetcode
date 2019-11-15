# 题目
309. 最佳买卖股票时机含冷冻期

## 我的解题思路
- dp 动态规划
-

```java
class Solution {
    public int maxProfit(int[] prices) {
        if(prices==null||prices.length<2) return 0;

        int[][] dp = new int[prices.length+1][2];
        dp[0][0] = 0;
        dp[0][1] = Integer.MIN_VALUE;
        dp[1][0] = 0;
        dp[1][1] = -prices[0];

        for(int i=2;i<=prices.length;i++){
            dp[i][0] = Math.max(dp[i-1][0],dp[i-1][1]+prices[i-1]);
            dp[i][1] = Math.max(dp[i-1][1],dp[i-2][0]-prices[i-1]);
        }
        return dp[prices.length][0];
    }
}

```
