# 解题思路

## 我的解题思路
可以用DFS(深度优先搜索)解决。找到所有被X围绕的区域不容易，但是其等价于找到所有没有被X围绕的区域（连接边界的区域），这样就可以从边界上的O开始进行深度优先搜索。
```java
class Solution {
    private char[][] board;
    boolean[][] flag;
    int m =0;
    int n=0;
    int[][] direction={{-1,0},{1,0},{0,1},{0,-1}};

    public void solve(char[][] board) {
        this.board = board;
        m = board.length;
        if(m==0)
            return ;
        n = board[0].length;
        if((m==0&&n==1)||(m==1||n==0))
            return;
        flag =new  boolean[m][n];


        for(int i=0,j=0;j<n-1;j++){
            if(board[i][j]=='O'){
                dfs(board,i,j);
            }
        }
        for(int j=n-1, i=0;i<m-1;i++){
            if(board[i][j]=='O'){
                dfs(board,i,j);
            }
        }
        for(int i=m-1,j =n-1;j>0;j--){
            if(board[i][j]=='O'){
                dfs(board,i,j);
            }
        }
        for(int j=0, i =m-1;i>0;i--){
            if(board[i][j]=='O'){
                dfs(board,i,j);
            }
        }

        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(board[i][j]=='O')
                    board[i][j]='X';
                if(board[i][j]=='#')
                    board[i][j]='O';
            }
        }
    }
    //[["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]
    public void dfs(char[][] board,int i,int j){
        if(!isArea(board,i,j)||board[i][j]=='X')
            return;
        if(board[i][j]=='O'){
            board[i][j]='#';
            //flag[i][j]=true;
            for(int k=0;k<4;k++){
                int newX = i+direction[k][0];
                int newY = j+direction[k][1];
                dfs(board,newX,newY);
            }
            //flag[i][j]=false;
        }
    }
    public boolean isArea(char[][] board,int i,int j){
        if(i>=0&&i<board.length&&j>=0&&j<board[0].length)
            return true;
        else
            return false;
    }
}
```
- 改进
```java
// 执行用时 :3 ms, 在所有 Java 提交中击败了94.23%的用户
// 内存消耗 :41.6 MB, 在所有 Java 提交中击败了89.44%的用户
class Solution {
    private char[][] board;
    boolean[][] flag;
    int m =0;
    int n=0;
    int[][] direction={{-1,0},{1,0},{0,1},{0,-1}};

    public void solve(char[][] board) {
        this.board = board;
        m = board.length;
        if(m==0)return ;
        n = board[0].length;
        if((m==0&&n==1)||(m==1||n==0))return;
        flag =new  boolean[m][n];

        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if((i==0||i==m-1||j==0||j==n-1)&&board[i][j]=='O')
                    dfs(board,i,j);
            }
        }

        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(board[i][j]=='O')
                    board[i][j]='X';
                if(board[i][j]=='#')
                    board[i][j]='O';
            }
        }
    }
    public void dfs(char[][] board,int i,int j){
        if(!isArea(board,i,j)||board[i][j]=='X')
            return;
        if(board[i][j]=='O'){
            board[i][j]='#';
            flag[i][j]=true;
            for(int k=0;k<4;k++){
                int newX = i+direction[k][0];
                int newY = j+direction[k][1];
                if(isArea(board,newX,newY)&&!flag[newX][newY])
                    dfs(board,newX,newY);
            }
        }
    }
    public boolean isArea(char[][] board,int i,int j){
        if(i>=0&&i<board.length&&j>=0&&j<board[0].length)
            return true;
        else
            return false;
    }
}
```





