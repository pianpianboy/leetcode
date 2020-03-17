### 题目
695. 岛屿的最大面积

### 解题思路
- dfs 深度优先
    + 感染函数
        * 沉岛思想
        * 朋友圈思想
- 并查集unionSet

```java
//朋友圈思想
class Solution {
    // 深度优先遍历，固定一个点，然后一条路走到黑
    public int maxAreaOfIsland(int[][] grid) {

        if(grid==null||grid.length ==0){
            return 0;
        }
        int res = 0;
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[0].length;j++){
                res = Math.max(res,dfs(i,j,grid));
            }
        }
        return res;
    }

    public int dfs(int i,int j,int[][]grid){
        if(i<0||j<0||i>=grid.length||j>=grid[0].length||grid[i][j]==0||grid[i][j]==2)
            return 0;
        int num = 1;
        grid[i][j] = 2;

        num+=dfs(i-1,j,grid);
        num+=dfs(i,j-1,grid);
        num+=dfs(i+1,j,grid);
        num+=dfs(i,j+1,grid);
        return num;
    }
}
```

```java
//沉岛思想
class Solution {
    // 深度优先遍历，固定一个点，然后一条路走到黑
    public int maxAreaOfIsland(int[][] grid) {
        if(grid==null||grid.length ==0){
            return 0;
        }
        int res = 0;
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[0].length;j++){
                res = Math.max(res,dfs(i,j,grid));
            }
        }
        return res;
    }

    public int dfs(int i,int j,int[][]grid){
        if(i<0||j<0||i>=grid.length||j>=grid[0].length||grid[i][j]==0)
            return 0;
        int num = 1;
        grid[i][j] = 0;

        num+=dfs(i-1,j,grid);
        num+=dfs(i,j-1,grid);
        num+=dfs(i+1,j,grid);
        num+=dfs(i,j+1,grid);
        return num;
    }
}
```
