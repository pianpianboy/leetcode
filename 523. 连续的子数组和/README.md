# 解题思路

## 我的解题思路


## 参考解题思路


```java

```
另外一种思路，但依然用到前缀树
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