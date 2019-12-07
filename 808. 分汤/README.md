# 题目
808. 分汤

## 解题思路
- 动态规划
    + 分析状态：dp[i][j] 表示A剩余i,B剩余j的概率。题目中求dp[N][N]
    + 状态转移方程：dp[i][j] = 0.25*(dp[i-4][j]+dp[i-3][j-1]+dp[i-2][j-2]+dp[i-1][j-3])
        * 优化后：dp[i][j] = 0.25*(dp[Math.max(0,i-4)][j]+dp[Math.max(0,i-3)][Math.max(0,j-1)]+dp[Math.max(0,i-2)][Math.max(0,j-2)]+dp[Math.max(0,i-1)][Math.max(0,j-3)]);
    + 边界条件 ：dp[0][0] = 0.5
        * dp[0][0] = 0.5
        * dp[0][j] = 1  j>1
        * dp[i][0] = 0  i>1  默认创建数组的时候值都为0，因此可以不用处理


```java
class Solution {
    public double soupServings(int N) {
        N= N/25+((N%25==0)? 0:1);
        //将N除以25,缩小了25倍，有余数补1
        if(N>=500) return 1L;

        double[][] dp = new double[500][500];
        Arrays.fill(dp[0],1L);
        dp[0][0] = 0.5;

        for(int i=1;i<=N;i++){
            for(int j=1;j<=N;j++){
                dp[i][j] = 0.25*(dp[Math.max(0,i-4)][j]+dp[Math.max(0,i-3)][Math.max(0,j-1)]+dp[Math.max(0,i-2)][Math.max(0,j-2)]+dp[Math.max(0,i-1)][Math.max(0,j-3)]);
            }
        }
        return dp[N][N];


    }
}

```
