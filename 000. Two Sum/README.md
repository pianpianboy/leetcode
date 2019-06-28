# 解题思路

## 我的解题思路
- 暴力解：两个for循环计算所有可能
- 先排序，再使用双指针   但是排序后破坏了原数组的稳定性，下标发生了变化，因此不能使用这个方法
- 使用字典表（HashMap）

```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
       int[] res = new int[2];

        for(int i=0;i<nums.length;i++){
            for(int j=i+1;j<nums.length;j++){
                if(nums[i]+nums[j] == target){
                    res[0]=i;
                    res[1]=j;
                }
            }
        }
        return res;
    }
}
```

```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
       int[] res = new int[2];

        if(nums==null||nums.length<2)return res;

        HashMap<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            int  tmp =nums[i];
            if(map.containsKey(target-tmp)){
                res[0] = map.get(target-tmp);
                res[1] = i;
                break;
            }else
                map.put(tmp,i);
        }

        return res;

    }
}
```

