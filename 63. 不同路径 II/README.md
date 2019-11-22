# 题目
63. 不同路径 II

## 解题思路
- 动态规划

```java
class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if(obstacleGrid==null){
            return 0;
        }
        if(obstacleGrid[0][0]==1) return 0;
        int x = obstacleGrid.length;
        int y = obstacleGrid[0].length;
        int[][] dp = new int[x][y];
        dp[0][0] = 1;
        for(int i=1;i<x;i++){
            dp[i][0] = obstacleGrid[i][0] == 1? 0: dp[i-1][0];
        }
        for(int j=1;j<y;j++){
            dp[0][j] = obstacleGrid[0][j] == 1? 0: dp[0][j-1];
        }

        for(int i=1;i<x;i++){
            for(int j=1;j<y;j++){
                if(obstacleGrid[i][j]==1){
                    dp[i][j] = 0;
                }else{
                    dp[i][j] = dp[i-1][j]+dp[i][j-1];
                }
            }
        }
        return dp[x-1][y-1];
    }
}
```

