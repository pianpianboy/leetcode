# 解题思路

## 我的解题思路
- 1.使用贪心，但是思路有问题，认为排序后从数组的最大值往前遍历减去每个值，减到0时得到最优解，但是提交[1,2,3,4,5,6,7]无法通过;说明思路有问题。
    + 先求总sum和及最大值max,并判断sum%2!=0||max>sum/2的情况
    + 求出每个桶中值target = sum/2
    + 对数组进行排序
    + 从数组的最大值往前遍历减去每个值，减到0时得到最优解


```java
class Solution {
    public boolean canPartition(int[] nums) {
        int sum= 0;
        int max = 0;
        for(int i=0;i<nums.length;i++){
            sum+=nums[i];
            if(max<nums[i]){
                max = nums[i];
            }
        }
        if(sum%2!=0||max>sum/2) return false;
        int target = sum/2;
        Arrays.sort(nums);
        for(int i=nums.length-1;i>=0;i--){
            if(target==0)
                return true;
            else if(target<0)
                return false;
            else
                target = target-nums[i];
        }
        return false;
    }
}
```

- 正确解法：使用动态规划,其实也是回溯,增枝剪枝,但是提交超时
```java
class Solution {
    public boolean canPartition(int[] nums) {
        int sum= 0;
        int max = 0;
        for(int i=0;i<nums.length;i++){
            sum+=nums[i];
            if(max<nums[i]){
                max = nums[i];
            }
        }
        if(sum%2!=0||max>sum/2) return false;
        boolean[] used = new boolean[nums.length];
        return dfs(nums,sum/2,0,used);
    }

    public boolean dfs(int[] nums,int target, int sum, boolean[] used){
        if(sum == target)return true;

        for(int i=0;i<nums.length;i++){
            if(!used[i]&& sum+nums[i]<=target){
                used[i] = true;
                //dfs(nums,target,sum+nums[i],used);
                if(dfs(nums,target,sum+nums[i],used))
                    return true;
                used[i] = false;
            }
        }
        return false;
    }
}
```
在上述的代码基础上稍微修改下就能过，先对数组进行排序，从数组的尾部开始找
```java
class Solution {
    public boolean canPartition(int[] nums) {
        int sum= 0;
        int max = 0;
        for(int i=0;i<nums.length;i++){
            sum+=nums[i];
            if(max<nums[i]){
                max = nums[i];
            }
        }
        if(sum%2!=0||max>sum>>1) return false;
        boolean[] used = new boolean[nums.length];
        Arrays.sort(nums);
        return dfs(nums,sum>>1,0,used);
    }

    public boolean dfs(int[] nums,int target, int sum, boolean[] used){
        if(sum == target)return true;

        for(int i=nums.length-1;i>=0;i--){
            if(!used[i]&& sum+nums[i]<=target){
                used[i] = true;
                //dfs(nums,target,sum+nums[i],used);
                if(dfs(nums,target,sum+nums[i],used))
                    return true;
                used[i] = false;
            }
        }

        return false;
    }
}
```
- 使用动态规划：0-1背包问题为背景的题
>给定一个只包含正整数的非空数组。是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。

将上述题翻译为：
>给定一个包含正整数的非空数组。是否可以从这个数组中挑选中出一些正整数，每个数只能使用一次，使得这些数的和是整个数组元素和的一般。

0-1背包问题也是最基础的背包问题，它的特点：待挑选的物品有且仅有一个，也可以选择也可以不选择。
dp[i][j] ：表示从数组的 [0, i] 这个子区间内挑选一些正整数，每个数只能用一次，使得这些数的和等于 j
    + 如果不选择num[i],这在[0,i-1]这个子区间已经有一部分元素，使得它们的和为j,那么dp[i][j]=true;
    + 如果选择nums[i],在[0,i-1]这个子区间内就得到一部分元素，使得他们的和为j-nums[i],,这里讨论的前提条件是hinums[i]<j;

状态转移方程：
dp[i][j] = dp[i-1][j] or dp[i][j-nums[i]], nums[i]<j

```java
public class Solution {

    /**
     * 常规 0-1 背包问题的写法
     *
     * @param nums
     * @return
     */
    public boolean canPartition(int[] nums) {
        int size = nums.length;

        int s = 0;
        for (int num : nums) {
            s += num;
        }

        // 特判 2：如果是奇数，就不符合要求
        if ((s & 1) == 1) {
            return false;
        }

        int target = s / 2;

        // 创建二维状态数组，行：物品索引，列：容量
        boolean[][] dp = new boolean[size][target + 1];
        // 先写第 1 行
        for (int i = 1; i < target + 1; i++) {
            if (nums[0] == i) {
                dp[0][i] = true;
            }
        }
        for (int i = 1; i < size; i++) {
            for (int j = 0; j < target + 1; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j >= nums[i]) {
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i]];
                }
            }
        }
        return dp[size - 1][target];
    }

```

























