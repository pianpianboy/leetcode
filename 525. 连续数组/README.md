# 解题思路

## 我的解题思路
暴力解使用双指针（left+right）遍历出子数组：但是提交超时
```java
class Solution {
    public int findMaxLength(int[] nums) {
        if(nums==null||nums.length<2)return 0;

        int max = 0;
        int[] sum = new int[nums.length+1];
        for(int i=1;i<nums.length+1;i++){
            sum[i] = sum[i-1]+nums[i-1];
        }

        for(int i=0;i<nums.length;i++){
            for(int j=i+1;j<=nums.length;j++){
                if(2*(sum[j]-sum[i])==j-i){
                    max= Math.max(max,j-i);
                }
            }
        }
        return max;
    }
}
```
参考523中的解题思路:

1. 使用前缀和，但是需要使用到一个小技巧就是将数组中所有的0变为-1，这样题目就转变为和为0的时候的子数组；
2. 使用前缀和，但是需要使用到一个小技巧就是求数组和的时候如果遇到数组值为1则加1，如果遇到0则减1，这样题目就转变为和为0的时候的子数组；


```java
class Solution {
    public int findMaxLength(int[] nums) {
        if(nums==null||nums.length<2)return 0;
        HashMap<Integer,Integer> map = new HashMap<>();
        int sum = 0;
        int max = 0;
        map.put(0,-1);
        for(int i=0;i<nums.length;i++){
            if(nums[i]==1)
                sum++;
            else
                sum--;
            if(map.containsKey(sum)){
                max = Math.max(max, i-map.get(sum));//必须考虑处理（0，1）当前情况
            }else
                map.put(sum,i);
        }
        return max;
    }
}
```
