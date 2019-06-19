# 解题思路

## 我的解题思路
此题的思路使用前缀和，但是此题需要考虑的边界实在是太多。提交一致过不去。
最简单的解法也是最容易理解和想到的方法，时间复杂度O(n*n)
```java
class Solution {
    public boolean checkSubarraySum(int[] nums, int k) {
        if(nums==null||nums.length<2)return false;

        //统计出每一个位置的和形成数组
        int[] presum = new int[nums.length+1];
        //为什么是nums.length+1的长度？
        for(int i=1;i<nums.length+1;i++){
            presum[i] = presum[i-1]+nums[i-1];
        }
        //此处涉及到的一个技巧就是求0到1之间的两个数组成子数组，使用：presum[2]-presum[0]
        //即：(nums[1]+nums[0]+presum[0]) -  (presum[0]) = nums[1]+nums[0]
        //则只有使用int[] presum = new int[nums.length+1];presum[i] = presum[i-1]+nums[i-1];才能满足要求
        //如果是int[] presum = new int[nums.length]; presum[i] = presum[i-1]+nums[i];不能满足要求


        //遍历所有的子数组，判断是否满足要求
        //使用left和right双指针遍历子数组
        for(int left=0;left<nums.length;left++){
            for(int right=left+2;right<=nums.length;right++){
                //由于要求子数组的大小至少为2，则right=left+2
                if(k==0){
                    if(presum[right]-presum[left]==0)
                        return true;
                }else{//判断left到right-1之间的子数组是否满足要求
                    if((presum[right]-presum[left])%k==0)
                        return true;
                }
            }
        }
        return false;
    }
}
```

## 参考解题思路




使用prefix sum
```java
/** Key point: if we can find any two subarray of prefix sum have same mod value, then their difference MUST be
 * divisible by k. So we can use a map to store mod value of each prefix sum in map, with its index. Then check
 * if map contains the same mod value with size > 2 when we have new mod value in every iteration */
public boolean checkSubarraySum(int[] nums, int k) {
    if (nums.length < 2) {
        return false;
    }

    Map<Integer, Integer> map = new HashMap<>();
    //It is used for covering the case where the running sum(sum_i) is exactly equal to k(or n*k). (Recall that sum_i represents the running sum starting from index 0 and ending at i)
    // After the execution of runningSum %= k, the mod becomes 0, so we have to put a k-v pair with key = 0 to the map in advance to handle this case.
    // value = -1 is due to the fact that the size of the subarray is required to be least two.
    //we need to put mod value 0 and index -1 to make it as a true case

    //     I think it's the most tricky part!
    // <0,-1> can allow it to return true when the runningSum%k=0,
    // In addition, it also avoids the first element of the array is the multiple of k, since 0-(-1)=1 is not greater than 1.
    // I think it's really beautiful and elegant here!
    map.put(0, -1);
    int curSum = 0;
    for (int i = 0; i < nums.length; i++) {
        curSum += nums[i];

        // corner case: k CANNOT be 0 when we use a number mod k
        if (k != 0) {
            curSum = curSum % k;
        }
        if (map.containsKey(curSum)) {
            if (i - map.get(curSum) > 1) {
            //Because if (i - prev > 1) means that the length is more than 2
                return true;
            }
        }
        else {
            map.put(curSum, i);
        }
    }
    return false;
}
```
另外一种思路，但依然用到前缀和
```java
class Solution {
    public boolean checkSubarraySum(int[] nums, int k) {
       //使用前缀树
        if(nums==null||nums.length<2)return false;

        int sum =0;
        int prev =0;
        HashSet<Integer> set = new HashSet<>();

        for(int i=0;i<nums.length;i++){
            sum += nums[i];
            int mod = (k==0)?sum:sum%k;
            if(set.contains(mod))
                return true;
            set.add(prev);
            prev = mod;
        }

        return false;
    }
}
```