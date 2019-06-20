# 解题思路

## 我的解题思路
- 暴力解：利用数组保存每个下标的和 『双指针』left+right确定子数组，通过两层循环计算每两个元素间的累积和。时间复杂度为
O(n*n)，空间复杂度为O(n)。
```java
class Solution {
    public int subarraySum(int[] nums, int k) {
        if(nums==null)return 0;
        int[] presum = new int[nums.length+1];
        int res=0;
        for(int i=1;i<=nums.length;i++){
            presum[i] = presum[i-1] + nums[i-1];
        }

        for(int i=0;i<nums.length;i++){
            for(int j=i+1;j<=nums.length;j++){
                if(presum[j]-presum[i]==k)
                    res++;
            }
        }
        return res;
    }
}
```
- 使用HashMap将复杂度从O(n*n)降低到O(n)
```java
class Solution {
    public int subarraySum(int[] nums, int k) {

        if(nums==null)return 0;
        HashMap<Integer,Integer> map = new HashMap<>();
        //map.put(0,1);处理1+1=2第一次出现满足和为k的情况，但是此时map中缺没有值，因此再map中塞入一个默认的值
        map.put(0,1);
        int res=0;
        int sum =0;
        //假定sum[j]-sum[i]表示从i+1到j的子数组
        //sum[j]-sum[i] = k; 即求sum[i]=sum[j]-k是否存在，如果存在在map中计算出出现的次数
        for(int i=0;i<nums.length;i++){
            sum = sum + nums[i];
            if(map.containsKey(sum-k))
                res = res+map.get(sum-k);
            //else
            //不管当前sum中sum-k是否在map中存在都要将sum 和次数塞入map中
            map.put(sum,map.getOrDefault(sum,0)+1);
        }

        return res;
    }
}
```
