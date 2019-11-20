# 题目

## 解题思路
- 动态规划dp
    + 这一道题和 leetcode 70 爬楼梯 类似。
    + 虽然思路类似，但是难度却增加了很多，因为涉及到思路转换的问题
    + 正常拿到一个动态规划题分为：1、分解问题，2、分析状态，3、分析状态转移方程，4、分析边界情况
    + 尝试分解问题，思考第i层第j个数与上一层即第i-i层第j个数或者第j-1或者第j+1个数之间的关系
    + 尝试各种状态，dp[i]表示第i层获取的最小值路径，感觉无从下手(虽然最后空间复杂度为O(n)的情况下使用dp[i],但是拿到题先想到的是把题做出来，再优化)，转变思路思考dp[i][j]:表示在第i层j位置获得的最小路径是多少
    + 再就是三角形从上到下，和从下到上都不影响最终结果，但是解题的难易程度却不一样，这里难点就在于想到从下到上的状态转移方程：
        * dp[i][j] = Math.min(dp[i+1][j],dp[i+1][j+1])+tmp.get(j);


```java
//动态规划
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        if(triangle ==null) return 0;
        int x = triangle.size();
        List<Integer> list = triangle.get(x-1);
        int y = list.size();

        int[][] dp = new int[x+1][y+1];
        for(int i = x-1;i>=0;i--){
            List<Integer> tmp = triangle.get(i);
            for(int j =0;j<tmp.size();j++){
                dp[i][j] = Math.min(dp[i+1][j],dp[i+1][j+1])+tmp.get(j);
            }
        }

        return dp[0][0];
    }
}
```
