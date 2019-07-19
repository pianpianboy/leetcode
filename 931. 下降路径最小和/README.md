# 解题思路

## 我的解题思路
动态规划DFS:
动态方程：
第一列：dp[i][j] = Math.min(dp[i-1][j],dp[i-1][j+1])+A[i][j];
最后一列：dp[i][j] = Math.min(dp[i-1][j-1],dp[i-1][j])+A[i][j];
中间列：dp[i][j] = Math.min(Math.min(dp[i-1][j-1],dp[i-1][j]),dp[i-1][j+1])+A[i][j];

```java
class Solution {
    public int minFallingPathSum(int[][] A) {
        int min = Integer.MAX_VALUE;
        int x = A.length;
        int y = A[0].length;
        int[][] dp = new int[x][y];

        for(int i=0;i<x;i++){
            for(int j=0;j<y;j++){
                if(i==0)
                    dp[i][j] = A[i][j];
                else if(j==0)//处理第一列
                    dp[i][j] = Math.min(dp[i-1][j],dp[i-1][j+1])+A[i][j];
                else if(j==y-1)//处理最后列
                    dp[i][j] = Math.min(dp[i-1][j-1],dp[i-1][j])+A[i][j];
                else{//处理中间列
                    dp[i][j] = Math.min(Math.min(dp[i-1][j-1],dp[i-1][j]),dp[i-1][j+1])+A[i][j];
                }
            }
        }
        for(int i=x-1,j=0;j<y;j++){
            min = Math.min(min,dp[i][j]);
        }

        return min;
    }
}
```