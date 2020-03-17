### 题目
463. 岛屿的周长

### 解题思路
遍历所有的格子，如果格子的值为1，则判断它的周长

```java
class Solution {
    public int islandPerimeter(int[][] grid) {
        if(grid==null||grid.length==0) return 0;
        int res =0;
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[0].length;j++){
                if(grid[i][j]==1){
                    res += cal(i,j,grid);
                }
            }
        }

        return res;
    }

    public int cal(int i,int j,int[][]grid){
        int num=0;
        if(i-1<0||(i-1>=0 && grid[i-1][j]==0)){
            num++;
        }
        if(j-1<0||(j-1>=0 && grid[i][j-1]==0))
            num++;

        if(i+1>=grid.length||(i+1<grid.length&&grid[i+1][j]==0))
            num++;

        if(j+1>=grid[0].length||(j+1<grid[0].length&&grid[i][j+1]==0)){
            num++;
        }
        return num;
    }
}
```