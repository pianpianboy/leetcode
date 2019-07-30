# 解题思路

## 我的解题思路
暴力解，遍历数组每个值，每个位置的值都有两种情况，使用和不使用，先不考虑重复值的情况，然后在考虑去重；但是提交超时。

```java
class Solution {
    public List<List<Integer>> findSubsequences(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        if(nums==null)return list;

        List<Integer> tmplist = new ArrayList<>();
        help(nums,Integer.MIN_VALUE,0,list,tmplist);
        return list;
    }

    public void help(int[]nums, int pre, int index, List<List<Integer>> list,List<Integer> tmplist){
        if(tmplist.size()>1&&!list.contains(tmplist))
            list.add(new ArrayList<>(tmplist));

        if(index == nums.length)
            return;

        if(nums[index]>=pre){
            tmplist.add(nums[index]);
            help(nums,nums[index],index+1,list,tmplist);
        }
        if(tmplist.size()!=0){
          tmplist.remove(tmplist.size()-1);
        }
        help(nums,pre,index+1,list,tmplist);
    }
}
```
- 使用深度优先遍历改进算法
    + 上述解决方法在遍历全部结果之后再进行去重，提交超时，应该考虑使用回溯的剪枝思想进行去重。
    + 关于去重的小技巧，题目要求的是『广度去重，深度不用去重』，比如4、6、7、7的例子，4、7于4、7是属于重复的例子，而4、6、7、7中的前后两个7不属于重复。
    + 上述的去重使用代码如何实现呢？：在for循环的前面每次重新new一个去重的HashSet。这样就能实现广度（for循环）去重，上下之间深度不会去重（因为每次dfs都会重新new HashSet）

```java
class Solution {
    public List<List<Integer>> findSubsequences(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        if(nums==null)return list;

        List<Integer> tmplist = new ArrayList<>();
        dfs(nums,Integer.MIN_VALUE,0,list,tmplist);
        return list;
    }

    public void dfs(int[]nums, int pre, int index, List<List<Integer>> list,List<Integer> tmplist){
        if(index == nums.length)return;

        HashSet<Integer> used = new HashSet<>();
        for(int i=index;i<nums.length;i++){
            if(used.contains(nums[i]))continue;

            if(nums[i]>=pre){//保证递增
                tmplist.add(nums[i]);
                used.add(nums[i]);
                if(tmplist.size()>1)//保证长度大于2
                    list.add(new ArrayList<>(tmplist));
                dfs(nums,nums[i],i+1,list,tmplist);
                tmplist.remove(tmplist.size()-1);
            }
        }
    }
}
```


