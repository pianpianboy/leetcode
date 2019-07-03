# 解题思路

## 我的解题思路


## 参考的解题思路
- 使用DFS深度优先遍历递归实现：深度优先搜索来实现对数据的查找

注意：通过对原数组进行排序来实现 **剪枝** ,同时为了避免结果的重复，若第一个选定的为candidates[i],那么下一个元素只能选择它自己或者在它之后的元素，不能选在它之前的，这样就可以避免重复。
```java
class Solution {
    //
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        //使用排序+回溯进行剪枝及重新增枝
        List<List<Integer>> list = new ArrayList<>();
        if(candidates==null||target<0)return list;

        Arrays.sort(candidates);//排序之后方便剪枝
        List<Integer> tmplist = new ArrayList<>();
        dfs(candidates, target,list, tmplist,0);
        return list;
    }

    //使用dfs实现回溯功能
    public void dfs(int[] candidates, int target,List<List<Integer>> list,List<Integer> tmplist,int start){
        if(target<0)return;//退出当前深度遍历，回溯上一层

        if(target==0){
            list.add(new ArrayList<>(tmplist));
        }

        if(target>0){
            for(int i=start;i<candidates.length;i++){//重新考虑当前层所有的可能性
                tmplist.add(candidates[i]);//增枝
                dfs(candidates, target-candidates[i],list, tmplist,i);
                //target-candidate[i]<0就会返回，执行下面的语句，target-candidate[i]==0,虽然再递归一层后也会返回，但是会把结果存在list中
                tmplist.remove(tmplist.size()-1);//剪枝
            }
        }
    }
}
```

