# 解题思路

# 我的解题思路
参考第39题及3sum（三数之和去重的逻辑），采用DFS深度优先遍历，回溯剪枝
```java
class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        //使用深度优先遍历搜索DFS+3sum中去重处理
        //此题的关键在于剪枝逻辑及去重逻辑
        List<List<Integer>> list = new ArrayList<>();
        if(candidates==null||target<0)return list;

        Arrays.sort(candidates);
        List<Integer> tmplist = new ArrayList<>();
        dfs(candidates,target,list,tmplist,0);
        return list;
    }

    public void dfs(int[] candidates, int target,List<List<Integer>> list,
                                 List<Integer> tmplist, int start ){
        if(target<0)return ;
        if(target==0){
            list.add(new ArrayList(tmplist));
        }
        if(target>0){
            for(int i=start;i<candidates.length;i++){
                //去重代码位置必须要对
                //比如 A,A,A,B,B,C,D
                // i:  0,1,2,3,4,5,6
                //第一层树i=0的时候，下一层，即第二层树里面只能取 A(下标1),B(下标3),C,D
                //下标为2的A和下标为4的B都被去重去掉了
                //但是第三层的时候：假定第二层取值A(下标1)，则第三层的取值为A,B,C,D
                // while(i<candidates.length-1&&candidates[i]==candidates[i+1])
                //     i++;
                //因此去重的逻辑应该放在最后
                tmplist.add(candidates[i]);
                dfs(candidates,target-candidates[i],list,tmplist,i+1);
                tmplist.remove(tmplist.size()-1);//剪枝
                while(i<candidates.length-1&&candidates[i]==candidates[i+1])
                    i++;
            }
        }
    }
}
```