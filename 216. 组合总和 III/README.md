# 解题思路

## 我的解题思路
利用回溯+增枝+剪枝
```java
//216. 组合总和 III
class Solution {
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> list = new ArrayList<>();
        if(k>n||k>9)return list;

        List<Integer> tmplist = new ArrayList<>();
        dfs(n,k,list,tmplist,1,0);
        return list;
    }

    public void dfs(int n,int k,List<List<Integer>>list, List<Integer>tmplist,int index,int sum){
        //回溯结束条件
        if(tmplist.size()==k){
            if(sum==n)
                list.add(new ArrayList<>(tmplist));
            return ;
        }

        for(int i = index;i<=9;i++){
            tmplist.add(i);
            sum+=i;
            dfs(n,k,list,tmplist,i+1,sum);
            tmplist.remove(tmplist.size()-1);
            sum-=i;
        }
    }
}
```
