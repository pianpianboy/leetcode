# 解题思路

## 我的解题思路
- 1、2、3与1、3、2重复，如何去重？这个没有想到怎么解决。
- 10月16日第二次刷此题：为了再次搞懂子集、子序列、子串的通用解法
    + 其中940. 不同的子序列 II 采用的是dp的解法，得好好思考为什么是用DP的解法而不是回溯或者深度优先遍历
    + 其中78. 子集 却采用的是 回溯 dfs等算法，为什么？

- 78. 子集  的解法
    + 位运算：也可以理解为01背包问题
    + 回溯：回溯和深度优先搜索算法的关系，回溯算法时求问题的解，使用的是DFS(深度优先搜索)。在DFS的过程中发现不是问题的解，那么就开始回溯到上一层或者上一个节点。DFS是遍历整个搜索空间，而不管时问题的解。所以更觉得回溯算法时DFS的一种应用，DFS更像时一种工具。
    + 树的遍历(中序遍历、先序、后序)[]->1->2->3,2->3,1->2->3,1->3

## 我的解题方法
```java
//位运算
class Solution {
    public List<List<Integer>> subsets(int[] nums) {

        List<List<Integer>> res = new ArrayList<>();

        int len = nums.length;
        for(int i=0;i<(1<<len);i++){
            List<Integer> subset = new ArrayList<>();
            for(int j=0;j<len;j++){
                    if((i&(1<<j))!=0)
                        subset.add(nums[j]);

            }
            res.add(subset);
        }
        return res;
    }
}
```

```java
//回溯：添加一个数，递归，删除之前的数，下次循环。
class Solution {
    public List<List<Integer>> subsets(int[] nums) {

        List<List<Integer>> res = new ArrayList<>();

        trackback(nums,0,new ArrayList<>(),res);
        return res;
    }

    public void trackback(int[] nums,int start,List<Integer> tmp, List<List<Integer>> res){
        res.add(new ArrayList<>(tmp));
        for(int i=start;i<nums.length;i++){
            if(i>start&&nums[i]==nums[i-1])continue;
            tmp.add(nums[i]);
            trackback(nums,i+1,tmp,res);
            tmp.remove(tmp.size()-1);
        }
    }
}
```

```java
//树的中序遍历
class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<Integer> subset = new ArrayList<>();
        List<List<Integer>> res = new ArrayList<>();

        inorder(nums,0,subset,res);
        res.add(subset);
        return res;
    }

    public void inorder(int[] nums,int i,List<Integer> subset,List<List<Integer>> res){
        if(i>=nums.length)return;
        subset = new ArrayList<Integer>(subset);

        inorder(nums,i+1,subset,res);
        subset.add(nums[i]);
        res.add(subset);
        inorder(nums,i+1,subset,res);
    }

    public void postorder(int[] nums,int i,List<Integer> subset,List<List<Integer>> res){
        if(i>=nums.length)return;
        subset = new ArrayList<Integer>(subset);

        postorder(nums,i+1,subset,res);
        subset.add(nums[i]);

        postorder(nums,i+1,subset,res);
        res.add(subset);
    }
}
```

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
