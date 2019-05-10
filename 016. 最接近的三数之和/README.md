# 解题思路

## 我的解题思路
初读题目，感觉和015三数之和使用同样的方法；
但是又有不同的地方:
1. 在**三数之和**中：使用了三个指针i , j ,k，其中先固定i, j=i+1, k = nums.length-1;并先将数组进行排序，根据nums[i]+nums[k]+nums[j]与0的大小去移动j或者k，即如果和大于0的时候将k往前移（和会变小）；如果和小于0的时候将j往后移（和会变大）；
2. 在**最接近的三数之和**中，同样也可以使用三个指针 i,j,k，其中先固定i, j=i+1, k = nums.length-1;并先将数组进行排序，声明一个最小值int min = Integer.MAX_VALUE; 然后计算val = nums[i]+nums[k]+nums[j]-target, 比较Math.abs(val)与 Math.abs(min)的大小关系，如果val小于0的时候将j往后移（和会变大）；同理如果和大于0的时候将k往前移（和会变小）；最后返回min+target

