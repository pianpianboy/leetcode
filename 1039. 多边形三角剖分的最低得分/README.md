# 题目
1039. 多边形三角剖分的最低得分

## 解题思路
- 动态规划://时间复杂度为O(n*n*n)
    + 这题动态规划其实不好想，涉及将多边形的模型给抽取出来
    + 当前值的最小值dp[i][j] = Math.min(dp[i][j], dp[i][k]+dp[k][j]+A[i]*A[j]*A[k])


```java
//时间复杂度为O(n*n*n)
class Solution {
    public int minScoreTriangulation(int[] A) {
        if(A==null||A.length<3) return 0;

        int n = A.length;
        int[][] dp = new int[n][n];

        for(int d=2;d<n;d++){
            for(int i=0;i<n-d;i++){
                int j = i+d;
                dp[i][j] = Integer.MAX_VALUE;
                for(int k=i+1;k<j;k++){
                    dp[i][j] = Math.min(dp[i][j],dp[i][k]+dp[k][j]+A[i]*A[j]*A[k]);
                }
            }
        }

        // for(int i=0;i<n;i++){
        //     for(int j=i;j<n;j++){
        //         if(j!=i&&j!=i+1){
        //             dp[i][j] = Integer.MAX_VALUE;
        //              for(int k=i+1;k<j;k++){
        //                 dp[i][j] = Math.min(dp[i][j],dp[i][k]+dp[k][j]+A[i]*A[j]*A[k]);
        //             }
        //         }else{
        //             dp[i][j] = 0;
        //         }
        //     }
        // }
        return dp[0][n-1];
    }
}
```