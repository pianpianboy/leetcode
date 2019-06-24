# 解题思路

##  我的解题思路
- 采用前缀和的暴力解，时间复杂度为O(n*n)
```java
class Solution {
    public int minSubArrayLen(int s, int[] nums) {
        if(nums==null||nums.length<1)
            return 0;
        int res = Integer.MAX_VALUE;
        int[] presum = new int[nums.length+1];

        for(int i=1;i<nums.length+1;i++){
            presum[i] = presum[i-1]+nums[i-1];
        }
        for(int i=0;i<nums.length;i++){
            for(int j=i+1;j<=nums.length;j++){
                if(presum[j]-presum[i]>=s)
                    res = Math.min(res,j-i);
            }
        }
        return res==Integer.MAX_VALUE?0:res;
    }
}
```

- 使用滑动窗口(或者说是双指针)将时间复杂度由O(n*n)降为O(n)
但是使用滑动窗口的时候，是如何设计循环代码考虑到i==j的情况，即窗口只有一个值的情况。即要考虑窗口和sum之间的关系。最好使用的是窗口的和等于sum+nums[j];
```java
class Solution {
    public int minSubArrayLen(int s, int[] nums) {
        if(nums==null||nums.length<1)
            return 0;
        int res = Integer.MAX_VALUE;
        int i = 0;
        int j = 0;
        int sum = 0;
        //使用滑动窗口
        while(j<nums.length){
            if(sum + nums[j]>=s){//说明窗口内值大于等于s，将i向右移动，窗口缩小
                res = Math.min(res,j-i+1); // i、j为下标，故需要 + 1
                sum = sum - nums[i++];
            }else{//说明窗口内值小于等于s,j向右移动，窗口变大
                sum = sum + nums[j++];
            }
        }
        return res==Integer.MAX_VALUE?0:res;
    }
}
```
