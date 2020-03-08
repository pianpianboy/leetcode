### 题目
442. 数组中重复的数据

#### 解题思路
- HashMap额外空间的暴力解法
- 使用的是桶+抽屉的解法
    + 一遍在将数据放入正确的桶的同时（交换），一遍进行重复判断
    + 将数组中所有的数据放入桶之后在进行重复统计

```java
class Solution {
    public List<Integer> findDuplicates(int[] nums) {
        //暴力解：
        HashMap<Integer,Integer> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();

        for(int i=0;i<nums.length;i++){
            if(map.containsKey(nums[i])){
                map.put(nums[i],map.get(nums[i])+1);
            }else{
                map.put(nums[i],1);
            }
        }
        for(Map.Entry entry: map.entrySet()){
            if((int)entry.getValue()==2){
                list.add((Integer)entry.getKey());
            }
        }
        return list;
    }
}
```


```java
//使用的是桶+抽屉的解法
//一遍在将数据放入正确的桶的同时（交换），一遍进行重复判断
class Solution {
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> list = new ArrayList<>();
        int i=0;
        while(i<nums.length){
            if(nums[i]!=i+1){
                if(nums[i]==nums[nums[i]-1]){
                    addList(nums[i],list);
                    i++;
                }else{
                    swap(i,nums[i]-1,nums);
                }
            }else{
                i++;
            }
        }
        return list;
    }
    public void addList(int value, List<Integer>list){
        if(!list.contains(value)){
            list.add(value);
        }
        return;
    }

    public void swap(int i,int j, int[] nums){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
```


```java
public class Solution {

    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> res = new ArrayList<>();
        int len = nums.length;
        if (len == 0) {
            return res;
        }
        for (int i = 0; i < len; i++) {
            while (nums[nums[i] - 1] != nums[i]) {
                swap(nums, i, nums[i] - 1);
            }
        }
        for (int i = 0; i < len; i++) {
            if (nums[i] - 1 != i) {
                res.add(nums[i]);
            }
        }
        return res;
    }

    private void swap(int[] nums, int index1, int index2) {
        if (index1 == index2) {
            return;
        }
        nums[index1] = nums[index1] ^ nums[index2];
        nums[index2] = nums[index1] ^ nums[index2];
        nums[index1] = nums[index1] ^ nums[index2];
    }
}

```

