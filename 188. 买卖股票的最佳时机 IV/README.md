# 题目
188. 买卖股票的最佳时机 IV

## 解题思路
- DP动态规划（也可以说是状态机dp table）
    + 动态规划的正常步骤-->分析状态(题目变量个数)-->写出状态转移方程-->分析边界情况-->写出边界条件-->压缩
    + dp[i][j][0] = Math.max(dp[i-1][j][0],dp[i-1][j][1]+prices[i-1]);
    + dp[i][j][1] = Math.max(dp[i-1][j][1],dp[i-1][j-1][0]-prices[i-1]);
    +  //如果超过了你n/2天之后，就是属于无限制的题目了

- d

```java
class Solution {
    public int maxProfit(int k, int[] prices) {

        if(prices==null||prices.length<2||k==0)return 0;

        //if(k>prices.length/2) k = prices.length/2;
        //如果超过了你n/2天之后，就是属于无限制的题目了
        if(k>prices.length/2)
            return maxProfit(prices);

        int[][][] dp = new int[prices.length+1][k+1][2];

        for(int i=k;i>=1;i--){
            dp[0][i][0] = 0;
            dp[0][i][1] = Integer.MIN_VALUE;
        }

        for(int i=1;i<=prices.length;i++){
            for(int j=k;j>=1;j--){
                dp[i][j][0] = Math.max(dp[i-1][j][0],dp[i-1][j][1]+prices[i-1]);
                dp[i][j][1] = Math.max(dp[i-1][j][1],dp[i-1][j-1][0]-prices[i-1]);
            }
        }

        int max = 0;
        for(int i=k;i>=1;i--){
            max = Math.max(max,dp[prices.length][i][0]);
        }

        return max;
    }

    public int maxProfit(int[] prices){
        int[][] dp = new int[prices.length+1][2];
        dp[0][0] = 0;
        dp[0][1] = Integer.MIN_VALUE;

        for(int i=1;i<=prices.length;i++){
            dp[i][0] = Math.max(dp[i-1][0],dp[i-1][1]+prices[i-1]);
            dp[i][1] = Math.max(dp[i-1][1],dp[i-1][0]-prices[i-1]);
        }
        return dp[prices.length][0];
    }
}
```