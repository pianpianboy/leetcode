# 解题思路

## 我的解题思路


## 参考解题思路

```java
class Solution {
    //需要的辅助变量
    //1.boolean 数组flag 用于判断该值是否已经使用过
    //2.偏移量数组：偏移量数组在二维平面内经常使用，经常使用，可以把他当成一个技巧
    //3.字符串中字符的下标
    //4.字符串的长度，数组的行列长度
    private int m ;
    private int n ;
    private boolean[][] flag;
    private int[][] direction = {{-1,0},{0,-1},{0,1},{1,0}};
    private char[][] board;
    private String word;

    public boolean exist(char[][] board, String word) {
        this.board = board;
        this.word = word;
        m = board.length;
        n = board[0].length;
        flag = new boolean[m][n];

        if(m==0)
            return false;

        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(dfs(i,j,0)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean dfs(int m,int n,int index){
        // if(index==word.length()-1){
        //     if(flag[m][n]==false&&board[m][n]==word.charAt(index)){
        //         return true;
        //     }
        // }else{
        //     if(board[m][n]==word.charAt(index)){
        //         flag[m][n]=true;
        //         for(int k=0;k<4;k++){
        //             int newX = m + direction[k][0];
        //             int newY = n + direction[k][1];
        //             dfs(newX,newY,index+1);
        //         }
        //         flag[m][n]=false;
        //     }else
        //         return false;
        // }

        //改进。。。
        if(index==word.length()-1){
            return(flag[m][n]==false&&board[m][n]==word.charAt(index));
        }
        if(board[m][n]==word.charAt(index)){
            flag[m][n]=true;
            for(int k=0;k<4;k++){
                int newX = m + direction[k][0];
                int newY = n + direction[k][1];
                if (inArea(newX, newY) && !flag[newX][newY]) {
                    if (dfs(newX, newY, index + 1)) {
                        return true;
                    }
                }
            }
            flag[m][n]=false;
        }
        return false;

    }

    private boolean inArea(int x, int y) {
        return x >= 0 && x < m && y >= 0 && y < n;
    }

}
```

