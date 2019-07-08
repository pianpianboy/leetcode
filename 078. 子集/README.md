# 解题思路

## 我的解题思路
1、2、3与1、3、2重复，如何去重？这个没有想到怎么解决。

## 参考解题思路
使用回溯，dfs 增枝+剪枝
```java
class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<Integer> list = new ArrayList<>();
        List<List<Integer>> res = new ArrayList<>();
        if(nums==null) return res;
        dfs(0,nums,res,list);
        return res;
    }

    public void dfs(int index,int[] nums, List<List<Integer>> res, List<Integer> list){
        res.add(new ArrayList<>(list));

        for(int i=index;i<nums.length;i++){
            list.add(nums[i]);
            dfs(i+1,nums,res,list);
            list.remove(list.size()-1);
        }
    }
}
```
