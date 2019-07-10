# 解题思路

## 我的解题思路
使用回溯
```java
class Solution {
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> list = new ArrayList<>();
        if(n<k)return list;

        List<Integer> tmplist = new ArrayList<>();
        dfs(n,k,list,tmplist,1);
        return list;
    }

    public void dfs(int n,int k,List<List<Integer>> list,List<Integer>tmplist,int index){
        if(tmplist.size()==k){
            list.add(new ArrayList<>(tmplist));
        }
        for(int i = index;i<=n;i++){
            tmplist.add(i);
            dfs(n,k,list,tmplist,i+1);
            //dfs(n,k,list,tmplist,index+1); 此处很容易出错
            tmplist.remove(tmplist.size()-1);
        }
    }
}
```
