# 解题思路

## 我的解题思路
题目中是需要最长子序列，首先要明白什么是子序列？然后再求最长的子序列。然后再弄明白最长上升子序列。

> 暴力解

- 1.求所有上升子序列。
    + 求子序列的方法：数组中的每个值都有两种情况（考虑or不考虑）。
    + 求上升子序列的方法：数组中的每个值都有两种情况，如果后面的值大于前面的值，考虑or不考虑。
- 2.在所有上升子序列中求出最长的。

```java
class Solution {
    public int lengthOfLIS(int[] nums) {
        if(nums==null)return 0;

        return help(nums,Integer.MIN_VALUE,0);
    }

    public int help(int[] nums, int pre, int index){
        if(index == nums.length)
            return 0;
        int res1 = 0;
        if(nums[index]>pre){
            res1 = 1 + help(nums,nums[index],index+1);
        }
        int res2 = help(nums,pre,index+1);
        return Math.max(res1,res2);
    }
}
```
- 动态规划解法
    + 注意区分"子序列"和"子串"，子串一定是连续的，而子序列不一定是连续的。
    + 动态规划的核心思想是数学归纳法。我们设计动态规划算法，需要一个dp数组，我们可以假设dp[0,...,i-1]都已经被算出来了，然后问自己怎么通过这些结果算出dp[i].
    + 首先dp数组的含义，即dp[i]表示以nums[i]这个数结尾的最长递增是序列长度。
    + 已知道dp[0,...,i-1]都已经被算出来了，dp[i] = Math.max(dp[i],dp[j]+1); 0《j《i
    + 还有一个细节问题，dp数组应该全部初始化为1，因为子序列最少也要包含自己，所以长度最小为1


> 动态规划 时间复杂度O(n*n)

```java
class Solution {
    public int lengthOfLIS(int[] nums) {
        if(nums==null)return 0;

        int[] dp = new int[nums.length];
        //dp 数组全都初始化为1
        Arrays.fill(dp,1);
        int max = 0;

        for(int i=0;i<nums.length;i++){
            for(int j=0;j<i;j++){
                if(nums[j]<nums[i]){
                    dp[i] = Math.max(dp[i],dp[j]+1);
                }
            }
            max = Math.max(max,dp[i]);
        }
        return max;
    }
}
```















