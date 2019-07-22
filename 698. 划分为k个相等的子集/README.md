# 解题思路

## 我的解题思路
整体就是一个暴力解，先算出子集的和是多少，并抽象成k个桶，每个桶的值是子集的和。然后尝试所有不同的组合（即放数到桶中），如果存在一种组合可以使每隔桶都正好放下，那么返回可以。如果不存在，返回不可以。

如果在暴力解的基础上加上剪枝就能缩小时间复杂度。

在回溯的函数中是有返回值的，为什么有的dfs函数有返回值，有的没有返回值？而本题有返回值是因为本题只要找到一个结果就行了，需要在找到这个结果就立刻返回，这就是本题的if(!used[i]&&curval+nums[i]<=target){used[i] = true;}如果需要找到所有的结果**（所有划分）**,那么这个时候dfs()不需要返回值，这个时候需要添加一个参数ans（一个容器）,用来装全部的结果。
```java
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        if(k==0||k>nums.length)return false;

        int sum =0;
        int max =0;
        for(int i=0;i<nums.length;i++){
            sum+=nums[i];
            if(nums[i]>max)max = nums[i];
        }

        int target = sum/k;
        if(sum%k!=0||max>target){
            return false;
        }

        boolean[] used = new boolean[nums.length];
        return dfs(nums,k,target,0,0,used);
    }

    public boolean dfs(int[] nums,int k,int target,int curval,int start,boolean[] used){
        //结束回溯条件
        if(k==0){
           return true;
        }
        if(curval == target){//结束当前桶,构建下一个集合，curval为0，start为0
            return dfs(nums,k-1,target,0,0,used);
        }
        //增枝->向下遍历
        //剪枝->回溯
        for(int i=start;i<nums.length;i++){
            if(!used[i]&&curval+nums[i]<=target){
                used[i] = true;
                if(dfs(nums,k,target,curval+nums[i],i+1,used))return true;
                used[i] = false;
            }
        }
        return false;
    }
}
```