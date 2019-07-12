# 解题思路

## 自己的解法：
因为看过左程云的视频第6集，但是在第6集中只是提到了可以使用**感染法**去解决这个问题。
但是具体的实现没说，自己在分析这个问题的时候，发现有好多种情况需要考虑，代码写着写着就进入了死胡同：
第二刷：使用二维数组，二维平面中的偏移量技巧
```java
class Solution {
    private int[][] direction={{-1,0},{1,0},{0,1},{0,-1}};
    public int numIslands(char[][] grid) {
        if(grid==null||grid.length==0) return 0;
        int res = 0;
        int m = grid.length;
        int n = grid[0].length;

        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(grid[i][j]=='1'){
                    dfs(grid,i,j);
                    res++;
                }
            }
        }
        return res;
    }
    public void dfs(char[][] grid,int i,int j){
        if(!isArea(grid,i,j)||grid[i][j]=='0'){
            return ;
        }
        if(grid[i][j]=='1'){
            grid[i][j]='#';
            for(int k=0;k<4;k++){
                int newX = i+direction[k][0];
                int newY = j+direction[k][1];
                if(isArea(grid,newX,newY))
                    dfs(grid,newX,newY);
            }
        }
    }
    public boolean isArea(char[][] grid,int i,int j){
        if(i<0||i>=grid.length||j<0||j>=grid[0].length)
            return false;
        else
            return true;
    }
}
```

## 参考解法：
**算法核心框架：深度搜索+递归**
1. 深度搜索：整个刀鱼是一个"二维矩阵",使用深度优先搜索遍历一遍整个矩阵；
2. 递归：当扫描到'1'时，调用infect()函数，将该'1'上下左右相邻的位置都感染为'2'，递归调用infect()的结果是从起时的'1'开始而相连的一片'1'都被感染为'2'

