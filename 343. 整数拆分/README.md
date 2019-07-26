# 解题思路

## 我的解题思路


## 参考解题思路
- 动态规划：使用记忆搜索，进行剪枝
    + 先考虑用暴力解来解答此题，会发现第一想法就是使用广度优先遍历，但是发现有大量的重叠子问题，则需要考虑去重问题，因此使用记忆搜索（使用一个boolean类型的数组used来记录使用情况）就可以解决重复查找的问题。优化时间复杂度。
    + 然后对每个节点的选择进行情况总结：对于每个节点，有"不再继续分割"和"继续分割"两种情况。
    + 假定dp[n]为n的最大分割乘积。
    + 因此状态转移方法dp[n] = max(i*(n-i),i*dp[n-i]),其中i为(1...n-1)
    + **重叠子问题用记忆化搜索完成**

>使用记忆搜索的动态规划

```java
class Solution {
    private boolean[] used;
    public int integerBreak(int n) {
        //对于每个状态比较"不再继续分割"和"继续分割",并得出状态转移方程
        if(n==0) return 0;
        used = new boolean[n+1];
        int[] dp = new int[n+1];
        dp[0]=0;
        dp[1]=1;

        dfs(n,dp);
        return dp[n];
    }

    public int dfs(int n, int[] dp){
        if(n==1)
            return 1;

        if(!used[n]){
            for(int i=1;i<n;i++){
                dp[n] = Math.max(dp[n],Math.max(i*(n-i),i*dfs(n-i,dp)));
            }
            used[n] = true;
        }

        // for(int i=1;i<=n-1;i++){
        //     if(!used[i]){
        //         dp[n] = Math.max(dp[n],Math.max(i*(n-i),i*dfs(n-i,dp)));
        //         used[i] = true;
        //     }
        // }
        return dp[n];
    }
}
```
