# 题目
392. 判断子序列

## 解题思路
- 双指针
- 动态规划
    + 分析状态即变量：dp[i][j]为s从开头到第i的字符串是否为t从开头到第j的字符串的子序列
    + 状态转移方程
        * dp[i][j] = dp[i-1][j-1] 当s.charAt(i) == t.charAt(j)
        * dp[i][j] = dp[i][j-1] 当s.charAt(i) != t.charAt(j)
    + 边界条件：dp[0][j]=true,空字符串一定是t的子字符串。


```java
// 双指针
class Solution {
    public boolean isSubsequence(String s, String t) {
        if(s==null||t==null||s.length()>t.length())  return false;

        int indext=0;
        int indexs=0;
        int lent=t.length();
        int lens=s.length();

        while(indext<lent&& indexs<lens){
            if(t.charAt(indext)==s.charAt(indexs)){
                indexs++;
            }
            indext++;
        }
        return indexs==lens? true:false;
    }
}
```

```java
//dp
class Solution {
    public boolean isSubsequence(String s, String t) {
        if(s==null||s.length()==0) return true;
        if(t==null||s.length()>t.length())  return false;

        int tlen=t.length();
        int slen=s.length();

        boolean[][] dp = new boolean [slen+1][tlen+1];
        for(int j=0;j<=tlen;j++){
            dp[0][j] = true;
        }
        for(int i=1;i<=slen;i++){
            for(int j=1;j<=tlen;j++){
                    if(s.charAt(i-1)==t.charAt(j-1)){
                        dp[i][j] = dp[i-1][j-1];
                    }else{
                        dp[i][j] = dp[i][j-1];
                    }
                    if((i==slen)&&dp[i][j])
                        return true;
            }
        }
        return false;
    }
}
```

