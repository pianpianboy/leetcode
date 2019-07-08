# 解题思路

## 我的解题思路
本题和46的思路一直，使用回溯dfs解题，但是因为数组内有重复的值，增加了去重条件的考虑，因此难度增加
```java
class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if(nums==null) return res;
        Arrays.sort(nums);
        List<Integer> list = new ArrayList<>();
        int[] flag = new int[nums.length];
        dfs(nums,res,list,flag);
        return res;
    }

    public void dfs(int[] nums, List<List<Integer>> res, List<Integer> list,int[] flag){
        if(list.size()==nums.length){
            res.add(new ArrayList<>(list));
            return ;
        }
        for(int i =0;i<nums.length;i++){
            if(flag[i]==1){
                continue;
            }

            if(i>0&&nums[i]==nums[i-1]&&flag[i-1]==0){
                continue;
            }
            list.add(nums[i]);
            flag[i]=1;
            dfs(nums,res,list,flag);
            list.remove(list.size()-1);
            flag[i]=0;
        }
    }
}
```