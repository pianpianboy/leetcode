# 解题思路

## 我的解题思路
- 自顶向下，回溯的方法
- 自底向上，动态规划
    + dp[i]表示s中从0到i的位置是否可以由wordDict中的字典字符串组成
    + 所以 如果dp[i-j]是true并且s.substring(i+1,i+1)在wordDict里面，那么dp[i] = true。

```java
//自顶向下：回溯暴力解
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        return dfs(s,wordDict,0);
    }
    public boolean dfs(String s,List<String>wordDict,int index){
        if(index == s.length()) return true;

        for(int i = index+1;i<=s.length();i++){
            if(wordDict.contains(s.substring(index,i))&& dfs(s,wordDict,i))
                return true;
        }
        return false;
    }
}
```

```java
//自顶向下：回溯带去重
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        Boolean[] memo = new Boolean[s.length()];
        return dfs(s,wordDict,0,memo);
    }
    public boolean dfs(String s,List<String>wordDict,int index,Boolean[] memo){
        if(index == s.length()) return true;

        if(memo[index]!=null)//必须判空来去重,而且使用memo 使用Boolean定义
            return memo[index];

        for(int i = index+1;i<=s.length();i++){
            if(wordDict.contains(s.substring(index,i))&& dfs(s,wordDict,i,memo))
                return memo[index] = true;
        }
        return memo[index]=false;
    }
}

```

```java
//自底向上、动态规划
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length()];
        for(int i=0;i<s.length();i++){
            if(wordDict.contains(s.substring(0,i+1)))
                dp[i] = true;
            else{
                for(int j=0;j<=i-1;j++){
                    if(dp[j]&&wordDict.contains(s.substring(j+1,i+1))){
                            dp[i] = true;
                            break;
                    }
                }
            }
        }
        return dp[s.length()-1];
    }
}
```









