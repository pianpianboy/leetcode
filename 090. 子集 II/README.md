# 解题思路

## 我的解题思路
此题与78题思路一致，在78的基础上增加了重复数据，需要进行去重，采用先对数组进行排序，然后再去重。
但是比较麻烦的就是如何准确的去重，既不多去也不少去，比如[1,2,2,2]的时候：1(下标0)->2(下标1)->2(下标2)->2(下标3),然后回溯至 1->2(下标为1的位置)，接下来如果不去重的话为：1(下标0)->2(下标1)->2(下标3)，此时去重的应该是2(下标为3)这个位置的值。
```java
class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
      List<Integer> list = new ArrayList<>();
        List<List<Integer>> res = new ArrayList<>();
        if(nums==null) return res;
        Arrays.sort(nums);//去重不要忘记排序
        dfs(0,nums,res,list);
        return res;
    }

    public void dfs(int index,int[] nums, List<List<Integer>> res, List<Integer> list){

        res.add(new ArrayList<>(list));

        for(int i=index;i<nums.length;i++){
            if(i>index&&nums[i]==nums[i-1])
                continue;
            list.add(nums[i]);
            dfs(i+1,nums,res,list);
            list.remove(list.size()-1);
        }
    }
}
```