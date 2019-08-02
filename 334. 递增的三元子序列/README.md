# 解题思路

## 我的解题思路
- 并非题目要求的O(n)的时间复杂度，但是思路很好，利用了最长递增子序列的动态规划df解法，时间复杂度介于O(n)和O(n*n)之间。
- 解法二使用两个变量最小值min和次最小值nextmin,遍历数组，先判断最小值，因为最小值必须保存在次最小值之前，在判断次最小值，若nums[i]在最小值和次最小值之间，则更新次最小值，若大于次最小值则满足条件；

```java
//DP
class Solution {
    public boolean increasingTriplet(int[] nums) {
        if(nums==null||nums.length==0)return false;

        int dp[] = new int[nums.length];
        Arrays.fill(dp,1);
        for(int i=1;i<nums.length;i++){
            for(int j=0;j<=i-1;j++){
                if(nums[j]<nums[i])
                    dp[i] = Math.max(dp[i],dp[j]+1);
            }
            if(dp[i]>=3){
                return true;
            }
        }
        return false;
    }
}


```

```java
class Solution {
    public boolean increasingTriplet(int[] nums) {
        int min = Integer.MAX_VALUE;
        int nextmin = Integer.MAX_VALUE;

        for (int n : nums) {
            if (n <= min) {
                min = n;
            } else if (n <= nextmin) {
                nextmin = n;
            } else {
                return true;
            }
        }

        return false;
    }
}
```
