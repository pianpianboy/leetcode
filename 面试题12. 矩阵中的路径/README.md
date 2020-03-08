### 题目
面试题12. 矩阵中的路径

#### 解题思路
- 回溯


```java
class Solution {
    private boolean[][] flag;

    public boolean exist(char[][] board, String word) {
        if(board==null||board.length==0) return false;

        int len = word.length();
        int rNum = board.length;
        int cNum = board[0].length;
        if(cNum * rNum < word.length()) return false; // 总个数小于单词数量，没必要遍历了，这个优化从7ms->4ms
        flag = new boolean[rNum+1][cNum+1];

        for(int i=0;i<rNum;i++){
            for(int j=0;j<cNum;j++){
                if(help(board,i,j,word,0)){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean help(char[][] board, int i,int j,String word,int k){
        if(k==word.length()-1){
            return board[i][j] == word.charAt(k);
        }
        boolean res = false;
        if(board[i][j]==word.charAt(k)){
            flag[i][j] = true;
            if(i+1<board.length&&!flag[i+1][j]){
                res = res || help(board,i+1,j,word,k+1);
            }
            if(j+1<board[0].length&&!flag[i][j+1]){
                res = res || help(board,i,j+1,word,k+1);
            }
            if(i>0&&!flag[i-1][j]){
                res = res || help(board,i-1,j,word,k+1);
            }
            if(j>0&&!flag[i][j-1]){
                res = res || help(board,i,j-1,word,k+1);
            }

            flag[i][j] = false;
            return res;
        }else{
            return false;
        }
    }
}
```