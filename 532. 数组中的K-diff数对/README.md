# 题目
532. 数组中的K-diff数对

## 解题思路
- 使用HashMap
    + 优化：使用map来存储key为nums[i],value为次数
    + 遍历HashMap而不是数组nums,因为遍历数组需要处理重复的值

```java
class Solution {
    public int findPairs(int[] nums, int k) {
        if(nums==null||nums.length<2||k<0){
            return 0;
        }

        HashMap<Integer,Integer> map = new HashMap<>();
        int n=0;
        for(int i=0;i<nums.length;i++){
            map.put(nums[i],map.getOrDefault(nums[i],0)+1);
        }
        for(int val : map.keySet()){
            if(map.containsKey(val+k)){
                if(k==0){
                    n = map.get(val)>1? n+1:n;
                }else{
                    n++;
                }
            }
        }
        return n;
    }
}
```