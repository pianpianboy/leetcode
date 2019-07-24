# 解题思路

## 我的解题思路
- 暴力解:O(N*N*N*N*N)
- 动态规划
    - 我们用0初始化另一个矩阵dp，维数和原始矩阵维数相同
    - dp[i][j]表示的是由1组成的最大正方形边长；
    - 从(0,0)开始，对原始矩阵中的每一个1，我们将当前元素的值更新为dp(i,j) = min(dp(i-1,j),dp(i-1,j-1),dp(i,j-1))+1
    - 我们还用一个变量记录当前出现的最大边长，这样遍历一遍，找到最大的正方形边长maxsqlen,那么结果就是maxsql*maxql
```java
class Solution {
    public int maximalSquare(char[][] matrix) {
        if(matrix==null||matrix.length==0) return 0;
        int r = matrix.length;
        int c = matrix[0].length;
        int len = Math.min(r,c);
        int res = 0;

        for(int s = len; s>0;s--){
            for(int i = 0;i<r-s;i++){
                for(int j =0;j<c-s;j++){
                    //根据(i,j)起时位置及半径长度s
                    //判断（i,j）及长边s确定的正方形是否满足要求
                    boolean flag = true;
                    for(int p=i;p<i+s;p++){
                        for(int q=j;q<q+s;j++){
                            if(matrix[p][q] == '0'){
                                flag = false;
                                break;
                            }
                        }
                        if(!flag)
                            break;
                    }
                    if(flag)
                        res = Math.max(res,s*s);
                }
            }
        }
        return res;
    }
}
```

```java
class Solution {
    public int maximalSquare(char[][] matrix) {
        if(matrix==null||matrix.length==0) return 0;
        int r = matrix.length;
        int c = matrix[0].length;
        int maxsql = 0;
        int[][] dp = new int[r][c];

        for(int i=0;i<r;i++){
            for(int j=0;j<c;j++){
                if(i==0||j==0){
                    if(matrix[i][j] == '1'){
                       dp[i][j] = 1;
                        maxsql = Math.max(maxsql,1);
                    }else
                        dp[i][j] = 0;
                }else{
                    if(matrix[i][j] == '1'){
                        dp[i][j] = Math.min(Math.min(dp[i-1][j],dp[i-1][j-1]),dp[i][j-1])+1;
                        maxsql = Math.max(dp[i][j],maxsql);
                    }else
                        dp[i][j] = 0;

                }
            }
        }
        return maxsql*maxsql;
    }
}
```



