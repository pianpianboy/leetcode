# 解题思路

## 我的解题思路
### 此题是一个**最长公共子序列问题**，若先求出了最长公共子序列的长度为len,那么题中的最小步数为：res = word1.length()+word.length()-2*len 。 所以问题的关键就在于如何求 最长公共子序列
- 解法一：使用暴力解，即使用递归，提交会超时
    - help(String word1, String word2, int i, int j)
    - i和j分别表示word1中第i个位置和j个位置
    - 若word1.charAt(i)的值和word2.charAt(j)的值相等，则考虑i-1,j-1;若不想等则需要考虑help(String word1, String word2, int i, int j-1)，help(String word1, String word2, int i-1, int j)两种情况
    - 当i<0或者j<0，则递归结束
-
-

```java
class Solution {
    public int minDistance(String word1, String word2) {
        return word1.length() + word2.length() - 2*help(word1,word2,word1.length()-1, word2.length()-1);
    }

    public int help(String word1, String word2, int i, int j){
        if(i<0||j<0) return 0;
        if(word1.charAt(i) == word2.charAt(j)){
            return help(word1,word2,i-1,j-1) + 1;
        }else{
            int res1 = help(word1,word2,i-1,j);
            int res2 = help(word1,word2,i,j-1);
            return res1>res2? res1: res2;
        }
    }
}
```


```java
//解法2：记忆化
class Solution {
    public int minDistance(String word1, String word2) {
        int[][] memo = new int[word1.length()][word2.length()];
        return word1.length() + word2.length() - 2*help(word1,word2,word1.length()-1, word2.length()-1,memo);
    }

    public int help(String word1, String word2, int i, int j,int[][] memo){
        if(i < 0 || j < 0) return 0;

        if(memo[i][j]!=0){
            return memo[i][j];
        }else{
            if(word1.charAt(i) == word2.charAt(j)){//相等
                int res = help(word1,word2,i-1,j-1,memo) + 1;
                memo[i][j] = res;
                return res;
            }else{//i,j的值不相等
                int res1 = help(word1,word2,i-1,j,memo);
                int res2 = help(word1,word2,i,j-1,memo);
                memo[i][j] =Math.max(res1,res2);
                return memo[i][j];
            }
        }
    }
}
```

```java
//动态规划
class Solution {
    public int minDistance(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();

        int[][] dp= new int[len1+1][len2+1];
        for(int i=0;i<=len1;i++){
            for(int j=0;j<=len2;j++){
                if(i==0 || j==0) {
                    dp[i][j] = 0;
                    continue;
                }
                if(word1.charAt(i-1) == word2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1]+1;
                }else
                    dp[i][j] = Math.max(dp[i-1][j] ,dp[i][j-1] );
            }
        }

        return len2 + len1- 2*dp[len1][len2];
    }
}
```
