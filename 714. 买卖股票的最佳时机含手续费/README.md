# 题目
714. 买卖股票的最佳时机含手续费

## 我的题解
- 动态规划（dp table）
    + 分析变量：此题中存在的变量1：天数i,变量2：交易次数j（交易次数会影响fee），变量3：股票持有状态（0或者1）
    + 分析状态转移方程：
        * dp[i][j][0] = Math.max(dp[i-1][j][0],dp[i-1][j][1]+prices[i-1]);
        * dp[i][j][1] = Math.max(dp[i-1][j][1],dp[i-1][j-1][0]-prices[i-1]);
    + 分析边界条件
        * dp[0][k][0] = 0;
        * dp[0][k][1] = Integer.MIN_VALUE;
- df

```java
class Solution {
    //本来的状态转移方程及边界为：
    // dp[i][j][0] = Math.max(dp[i-1][j][0],dp[i-1][j][1]+prices[i-1]);
    // dp[i][j][1] = Math.max(dp[i-1][j][1],dp[i-1][j-1][0]-prices[i-1]);
    // dp[0][k][0] = 0;
    // dp[0][k][1] = Integer.MIN_VALUE;
    // 但是分析发现 每次减fee的同时 k-1,即 k-1和-fee是同时出现的，因此可以省掉k的考虑
    public int maxProfit(int[] prices, int fee) {
        if(prices==null||prices.length<2){
            return 0;
        }
        int[][] dp = new int[prices.length+1][2];
        dp[0][0] = 0;
        dp[0][1] = Integer.MIN_VALUE;

        for(int i=1;i<= prices.length;i++){
            dp[i][0] = Math.max(dp[i-1][0] , dp[i-1][1] + prices[i-1]);
            dp[i][1] = Math.max(dp[i-1][1] ,dp[i-1][0] - prices[i-1]-fee) ;
        }
        return dp[prices.length][0];
    }
}
```