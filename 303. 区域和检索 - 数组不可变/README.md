# 题目
303. 区域和检索 - 数组不可变

## 解题思路
- 暴力解
    + 暴力解的实践复杂度是O(n)，也就是每次执行sumRange的时间复杂度为O(n)
- dp动态规划
    + 动态规划的解在暴力解的基础上进行了改进，将dp的每一个值在构造函数NumArray中就已经计算好了，当每次执行sumRange的实践复杂度为O(1)
    + 分析变量 变量其实只有一个下标index
    + 分析状态转移方程 dp[i] = dp[i-1]+nums(i-1) 即dp[i] = sum(nums[0]~nums[i-1])
    + 分析边界条件dp[0] = 0;
- 利用缓存
    + 利用缓存的方法其实与动态规划很类似，在构造函数的时候就将每个值保存在了内存中，执行sumRange的时候时间复杂度为O(1)
- 前缀和


```java
//暴力jie
private int[] data;

public NumArray(int[] nums) {
    data = nums;
}

public int sumRange(int i, int j) {
    int sum = 0;
    for (int k = i; k <= j; k++) {
        sum += data[k];
    }
    return sum;
}
```

```java
//动态规划1
class NumArray {

    private static int[] dp;

    public NumArray(int[] nums) {
        dp = new int[nums.length+1];
        dp[0] = 0;

        for(int i=1;i<nums.length+1;i++){
            dp[i] = dp[i-1] + nums[i-1];
        }
    }

    public int sumRange(int i, int j) {
        return dp[j+1] - dp[i];
    }
}
```

```java
//缓存
private int[] sum;

public NumArray(int[] nums) {
    sum = new int[nums.length + 1];
    for (int i = 0; i < nums.length; i++) {
        sum[i + 1] = sum[i] + nums[i];
    }
}

public int sumRange(int i, int j) {
    return sum[j + 1] - sum[i];
}
```


