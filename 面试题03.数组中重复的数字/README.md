###题目
面试题03. 数组中重复的数字

### 解题思路
- HashMap
- HashSet
- 桶的思想+抽屉原理
    + 由于题目中数组的元素都在指定的范围内，这个范围恰好可以和数组下标一一对应；
    + 因此看到数值，就可以知道它的位置，这里nums[i]应该放在下标i的位置，就根据这一点解题，这种思想于桶排序、哈希的思想是一致的。


```java
HashMap解法直接超时
class Solution {
    public int findRepeatNumber(int[] nums) {
      HashMap<Integer,Integer>  map = new HashMap<>();

      for(int i=0;i<nums.length;i++){
          if(map.containsValue(nums[i])){
              return nums[i];
          }else{
              map.put(i,nums[i]);
          }
      }
      return 0;
    }
}
```

```java
HashSet的实现方式，能解决
class Solution {
    public int findRepeatNumber(int[] nums) {
      HashSet<Integer> set  = new HashSet<>();
      for(int num:nums){
          if(!set.add(num)){
              return num;
          }
      }
      return -1;
    }
}
```

```java
class Solution {
    public int findRepeatNumber(int[] nums) {
      int i = 0;
      // nums[i] 应该放在下标为 i 的位置上
      while(i<nums.length){
          if(nums[i]!=i){
              if(nums[i]==nums[nums[i]]){//抽屉原理
                return nums[i];
              }else{
                swap(i,nums[i],nums);
              }
          }else{
              i++;
          }
      }
      return -1;
    }

    public void swap(int i,int j, int[] nums){
        int tmp=nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
```
