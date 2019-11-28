# 题目
377. 组合总和 Ⅳ

## 解题思路
- 动态规划

```java
class Solution {
    /**
     * 这里状态定义就是题目要求的，并不难，状态转移方程要动点脑子，也不难：
     * 状态转移方程：dp[i]= dp[i - nums[0]] + dp[i - nums[1]] + dp[i - nums[2]] + ... （当 [] 里面的数 >= 0）
     * 特别注意：dp[0] = 1，表示，如果那个硬币的面值刚刚好等于需要凑出的价值，这个就成为 1 种组合方案
     * 再举一个具体的例子：nums=[1, 3, 4], target=7;
     * dp[7] = dp[6] + dp[4] + dp[3]
     * 即：7 的组合数可以由三部分组成，1 和 dp[6]，3 和 dp[4], 4 和dp[3];
     *
     * @param nums
     * @param target
     * @return
     */

    public int combinationSum4(int[] nums, int target) {
        if(nums==null||nums.length==0) return 0;

        int[] dp = new int[target+1];
        //dp[0] = 0;
        dp[0] = 1;

        for(int i=1;i<=target;i++){
            for(int j=0;j<nums.length;j++){
                if(i-nums[j]>=0){
                    // dp[i] = dp[i]+dp[i-nums[j]]+1;
                    dp[i] = dp[i]+dp[i-nums[j]];
                }
            }
        }
        return dp[target];
    }
}
```

