### 题目
169. 多数元素

### 解题思路
```java
class Solution {
    public int majorityElement(int[] nums) {
        HashMap<Integer,Integer> map = new HashMap<>();
        int size = nums.length;
        for(int n:nums){
            if(map.containsKey(n)){

                map.put(n,map.get(n)+1);
            }else{
                map.put(n,1);
            }
            if(map.get(n)>size/2)
                return n;
        }
        return nums[size-1];
    }
}
```
