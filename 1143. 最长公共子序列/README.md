# 题目
1143. 最长公共子序列

## 解题思路
- dp 参考392. 判断子序列，可以再做这题前把392这道题给做了。
    + 分析状态变量：text1的下标i,text2的下标j,dp[i][j] 为text1从0到i之间的字符串str1与text2从0到j之间的字符串str2之间的最长公共子序列
    + 分析状态转移方程：
        * dp[i][j] = dp[i-1][j-1] +1; 当text1.charAt(i-1)== text2.charAt(j-1)
        * dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]); 当text1.charAt(i-1)!= text2.charAt(j-1)
    + 分析边界条件: 当text1为空字符串或者text2为空字符串，则dp为空，即最长公共子序列为0，dp[0][j] =0;dp[i][0]=0
- d



```java
//dp
class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        if(text1==null||text1.length()==0||text2==null||text2.length()==0) return 0;

        int len1 = text1.length();
        int  len2 = text2.length();

        int[][] dp= new int[len1+1][len2+1];

        for(int i=0;i<=len1;i++){
            dp[i][0] = 0;
        }
        for(int j=0;j<=len2;j++){
            dp[0][j] = 0;
        }

        for(int i=1;i<=len1;i++){
            for(int j=1;j<=len2;j++){
                if(text1.charAt(i-1)== text2.charAt(j-1) ){
                    dp[i][j] = dp[i-1][j-1] +1;
                }else{
                    dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }
        return dp[len1][len2];
    }
}
```