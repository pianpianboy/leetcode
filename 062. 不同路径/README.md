# 解题思路

## 我的解题思路
此题与64题的解法完全一样：动态规划DFS。其中dp[i][j]为i,j点的路径。
动态方程：dp[i][j] = dp[i-1][j]+dp[i][j-1];
时间复杂度和空间复杂度都为O(m*n)
```java
class Solution {
    public int uniquePaths(int m, int n) {
        int[][]dp = new int[n][m];

        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(i==0||j==0)
                    dp[i][j] =1;
                else
                    dp[i][j] = dp[i-1][j]+dp[i][j-1];
            }
        }
        return dp[n-1][m-1];
    }
}
```