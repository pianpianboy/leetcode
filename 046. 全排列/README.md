# 解题思路

## 这题也是一个拿到题目毫无思路的题目
在参考解题思路中的确用到了回溯思想，但是没有用到剪枝。
回溯+剪枝
```java
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if(nums==null) return res;
        List<Integer> list = new ArrayList<>();
        dfs(nums,res,list);
        return res;
    }

    public void dfs(int[] nums, List<List<Integer>> res, List<Integer> list){
        if(list.size()==nums.length){
            res.add(new ArrayList<>(list));
            return ;
        }
        for(int i =0;i<nums.length;i++){
            if(!list.contains(nums[i])){
                list.add(nums[i]);
                dfs(nums,res,list);
                list.remove(list.size()-1);
            }
        }
    }
}
```


## 参考解题思路
**使用回溯**
举个例子：
1，2，3，4 总
                                    【1】
                            【1，2】，【1，3】，【1，4】
        【1，2，3】，【1，2，4】，【1，3，2】，【1，3，4】，【1，4，2】，【1，4，3】
【1，2，3，4】，【1，2，4，3】，【1，3，2，4】，【1，3，4，2】，【1，4，2，3】，【1，4，3，2】

总的来说，就是拿上一次的结果，加上自己后，进行递归。
递归就是循环选择数组里面还没在list里面的元素，加在后面；
这个代码很难写，多品一品
有个小技巧，不一定非得拿返回值，把结果存在参数里也行。这样直观一点，像全局变量一样，拿返回值很复杂

```java
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if(nums==null) return res;

        // for(int i=0;i<nums.length;i++){
        //     List<Integer> list = new ArrayList<>();
        //     list.add(nums[i]);
        //     dfs(nums,res,list);
        // }
        List<Integer> list = new ArrayList<>();
        dfs(nums,res,list);
        return res;
    }

    public void dfs(int[] nums, List<List<Integer>> res, List<Integer> list){
        if(list.size()==nums.length){
            res.add(list);
            return ;
        }
        for(int i =0;i<nums.length;i++){
            if(!list.contains(nums[i])){
                List<Integer> tmp = new ArrayList<>(list);
                tmp.add(nums[i]);
                dfs(nums,res,tmp);
            }
        }
    }
}
```