# 解题思路

## 我的解题思路
此题在『300. 最长上升子序列』 的基础上改进而来，但是却更难，因为需要在寻找最长的递增子序列基础上统计每个子序列的次数。
很容出现统计次数少的情况，比如1、3、5、4、7，在统计以7结尾的最长子序列的时候，只统计1、3、5、7而少统计了1、3、4、7这种情况。

- 以5结尾的最长递增子序列长度为dp[2] = 3; 组合次数cnt[2] = 1,因为只存在1、3、5这一种组合。
- 以4结尾的最长递增子序列长度为dp[3] = 3; 组合次数cnt[3] = 1,因为只存在1、3、4这一种组合。
- 已7结尾的最长递增子序列长度为dp[4] = 4; 组合次数cnt[4] = 2,因为只存在1、3、5、7；1、3、4、7这两种种组合。即当dp[i] = dp[j]+1的时候，cnt[4] = cnt[3]+cnt[2];


```java
class Solution {
    public int findNumberOfLIS(int[] nums) {
        if(nums==null)return 0;

        int[] dp = new int[nums.length];
        int[] cnt = new int[nums.length];
        Arrays.fill(dp,1);
        Arrays.fill(cnt,1);
        int max = 0;

        for(int i=0;i<nums.length;i++){
            for(int j=0;j<i;j++){
                if(nums[j]<nums[i]){
                    if(dp[i]==dp[j]+1){
                        cnt[i]+=cnt[j];
                    }else if(dp[i]<dp[j]+1){
                        dp[i] = dp[j]+1;
                        cnt[i] = cnt[j];
                    }
                }
            }
            max = Math.max(max,dp[i]);
        }
        int res = 0;
        for(int i=0;i<nums.length;i++){
            if(dp[i]==max)
                res += cnt[i];
        }
        return res;
    }
}
```

