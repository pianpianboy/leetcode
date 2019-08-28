# 解题思路

## 我的解题思路
题目中给定两个单词word1和word2，需要计算出将word1装换成word2 所使用的**最少**操作数。

- 首先题目中的关键字眼是最少，关于最少，一般想到的是最优解，然后最优解又想到 动态规划和贪心，动态规划和贪心在某种成都上说其实思路很类似
- 动态规划一般的思路都是 自顶而下（数学倒推法）

## 参考解题思路
- 问题1：如果word1[0..i-1] 到word2[0..j-1]的变换需要消耗k步，那么word1[0..i]到word2[0..j]的变换需要几步呢？
    - 首先使用k步，把word1[0..i-1]变换到word[0..j-1],消耗k步。再把word1[i]改成word2[j],就行了。如果word1[i] == word2[j]什么也不用做，一共消耗了k步，否则需要修改，一共消耗k+1步。
- 问题2：如果word1[0..i-1]到word2[0..j]的变换需要消耗k步，那么word1[0..i]到word2[0..j]的变换需要几步呢？
    - 首先经过k步，把word1[0..i-1]变换到word2[0..j]，消耗掉k步骤，再把word1[i]删除，这样，把word1[0..i]到word2[0..j]的变换需要k+1步。
- 问题3：如果word1[0..i]到word2[0..j-1]的变换需要消耗k步，那么word1[0..i]到word2[0..j]的变换需要几步呢？
    -  首先经过k步，将word1[0..i]到word2[0..j-1]，消耗掉k步，接下来，再插入一个字符word2[j],word1[0..i]就完全变为word2[0..j]。
- 从上面的问题来看 word1[0..i]到word2[0..j]的变换 主要有三种手段，哪个消耗少就用哪个。

```java
class Solution {
    public int minDistance(String word1, String word2) {
        if(word1==null||word2==null) return 0;

        int len1 = word1.length();
        int len2 = word2.length();
        int[][] dp = new int[len1+1][len2+1];

        for(int i=0;i<=len1;i++){
            dp[i][0] = i;
        }

        for(int j =0;j<=len2;j++){
            dp[0][j] = j;
        }
        for(int i=1;i<=len1;i++){
            for(int j=1;j<=len2;j++){
                if(word1.charAt(i-1)==word2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1];
                }else{
                    dp[i][j] = 1 + Math.min(dp[i-1][j-1],Math.min(dp[i-1][j],dp[i][j-1]));
                }
            }
        }
        return dp[len1][len2];
    }
}
```
