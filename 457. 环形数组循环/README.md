# 解题思路

## 我的解题思路
对与判断是否为环:

1. 链表是否为环+数组是否为环，可以使用快慢双指针来判断，因为有环存在，快慢指针一定会再次相遇。
2. 看到时间复杂度和空间复杂度要求，那就知道一定是依据一些固定的算法才能实现，这个题目考察的是快慢指针的用法，如果知道快慢指针，那么做起来还是比较简单的。


```JAVA
class Solution {
    public boolean circularArrayLoop(int[] nums) {
       if(nums==null||nums.length<2) return false;
        boolean res = false;
        for(int i=0;i<nums.length;i++){
            int j = i;
            int k = getNextIndex(nums,j);
            //为什么不是k =i;让k和j都同时从i位置出发
            //有可能从数组中某一个位置能形成环
            while(nums[j]*nums[i]>0&&nums[k]*nums[i]>0&&
                  nums[getNextIndex(nums,k)]*nums[i]>0){
                //无论是快指针还是慢指针都得满足和第一个值的正负相同，以为快指针走两步，因此快指针下一步的值也得与开始值正负相同。
                if(j==k){//快指针和慢指针再次相遇，则循环终止，
                    //但是循环长度为1的情况需要排除
                    if(j==getNextIndex(nums,j))
                        break;//在下一个i开始的情况下判断是否为环
                    else
                        return true;
                }
                //j跳一步:慢指针
                j=getNextIndex(nums,j);
                //k跳两步:快指针
                k=getNextIndex(nums,getNextIndex(nums,k));
            }
        }
        return res;
    }

    public int getNextIndex(int[] nums, int i){
        //当前数组的值分为正负两种情况进行讨论
        int len = nums.length;
        int nextPosition = nums[i]+i;
        return nextPosition>=0? nextPosition%len:len+(nextPosition%len);
    }
}
```

## 参考阶梯思路