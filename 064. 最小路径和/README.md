# 解题思路

## 我的解题思路
- 使用动态规划df+加额外的数组求解，但是但是动态规划的方程式写错了。错误代码如下
```java
class Solution {
    public int minPathSum(int[][] grid) {
        if(grid==null||grid.length==0)return 0;
        int x = grid.length;
        int y = grid[0].length;
        int[][] dp = new int[x][y];

        for(int i=0;i<x;i++){
            for(int j=0;j<y;j++){
                if(i==0&&j==0)
                    dp[i][j] = grid[i][j];
                else if(i==0)
                    dp[i][j] = dp[i][j-1] + grid[i][j];
                else if(j==0)
                    dp[i][j] = dp[i-1][j] + grid[i][j];
                else
                    dp[i][j] = grid[i-1][j]<=grid[i][j-1]?dp[i-1][j]+grid[i][j]:dp[i][j-1]+grid[i][j];//错误方程式的代码
            }
        }
        return dp[x-1][y-1];
    }
}
```
- 正确方程式的代码
```java
class Solution {
    public int minPathSum(int[][] grid) {
        if(grid==null||grid.length==0)return 0;
        int x = grid.length;
        int y = grid[0].length;
        int[][] dp = new int[x][y];

        for(int i=0;i<x;i++){
            for(int j=0;j<y;j++){
                if(i==0&&j==0)
                    dp[i][j] = grid[i][j];
                else if(i==0)
                    dp[i][j] = dp[i][j-1] + grid[i][j];
                else if(j==0)
                    dp[i][j] = dp[i-1][j] + grid[i][j];
                else
                dp[i][j] = grid[i][j] + (dp[i - 1][j] < dp[i][j - 1] ? dp[i - 1][j] : dp[i][j - 1]);            }
        }
        return dp[x-1][y-1];
    }
}
```

```java
class Solution {
    public int minPathSum(int[][] grid) {
        if(grid==null||grid.length==0)return 0;
        int x = grid.length;
        int y = grid[0].length;
        int[][] dp = new int[x][y];
        dp[0][0] = grid[0][0];
        for(int i=1;i<x;i++){
            dp[i][0] = dp[i-1][0]+grid[i][0];
        }

        for(int j=1;j<y;j++){
            dp[0][j] = dp[0][j-1]+grid[0][j];
        }

        for(int i=1;i<x;i++){
            for(int j=1;j<y;j++){
                //dp[i][j] = grid[i-1][j]<=grid[i][j-1]?dp[i-1][j]+grid[i][j]:dp[i][j-1]+grid[i][j];
                dp[i][j] = grid[i][j] + (dp[i - 1][j] < dp[i][j - 1] ? dp[i - 1][j] : dp[i][j - 1]);
            }
        }
        return dp[x-1][y-1];
    }
}
```

- 不需要额外存储空间的解法
```java
class Solution {
    public int minPathSum(int[][] grid) {
    for(int i = 0; i <= grid.length - 1; i++){
        for(int j = 0; j <= grid[0].length - 1; j++){
            if( i == 0 && j != 0)
                grid[i][j] = grid[i][j] + grid[i][j-1];
            else if(j == 0 && i != 0)
                grid[i][j] = grid[i][j] + grid[i-1][j];
            else if(i != 0 && j != 0)
                grid[i][j] =  grid[i][j] + Math.min(grid[i-1][j],grid[i][j-1]);
        }
    }
    return grid[grid.length - 1][grid[0].length - 1];
}
}
```