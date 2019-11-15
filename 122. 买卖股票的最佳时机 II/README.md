## 题目
122. 买卖股票的最佳时机 II

## 解题思路
- 动态规划



```java
class Solution {
    public int maxProfit(int[] prices) {
        if(prices==null||prices.length<2)
        return 0;

        int[][] dp = new int[prices.length+1][2];
        dp[0][0] = 0;
        dp[0][1] = Integer.MIN_VALUE;

        for(int i=1;i<prices.length+1;i++){
            dp[i][0] = Math.max(dp[i-1][0],dp[i-1][1]+prices[i-1]);
            dp[i][1] = Math.max(dp[i-1][1],dp[i-1][0]-prices[i-1]);
        }
        return dp[prices.length][0];
    }
}
```

