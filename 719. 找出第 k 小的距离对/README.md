# 解题思路

## 我的解题思路
- 暴力解
    + 先对数组进行排序，然后求出所有的两两差，将差保存至优先级队列，取出第k个值即可
- 暴力解优化（依然会超时）
    + 前面的暴力解，是将所有的数对差都求出，其实对于k之后的对数差不用求
    + 遍历数组时下标为i，只需要先求出没对[i,i+1]的差放入优先级队列（并使用一个对象Node保存start的值即i,还有end的值即i到arr.length-1）
    + 然后遍历求第k个最小值：如果 end+1< arr.length 且j< k,则将[i,end+1]数对放入优先级队列中，比如 1、1、1、3、5、7、9的情况
- 这种题目的套路是找出上下界，以某种标准(或者计数方式)通过二分查找求解。类似解法的题有：求精确到小数点后十位的π的值。
    - 先对使用Arrays.sort(nums)对数组进行排序，
    - 求出最小值min=nums[0] ,然后求出最大值max=nums[nums.length-1]
    - 再求出中间值mid = max-min/2
    - 然后统计出 "对数值" 小于mid的个数，因为数组时有序的
    - 然后根据统计出来的count和k对比，来确定如何划分新的递归调用

+ 如果对这种类型的题目不是很了解的话，可以看看这个链接，总结得非常详细:
    > https://leetcode.com/problems/k-th-smallest-prime-fraction/discuss/115819/Summary-of-solutions-for-problems-%22reducible%22-to-LeetCode-378



```java
class Solution {
    public int smallestDistancePair(int[] nums, int k) {
        if(nums==null||nums.length==0)return 0;

        Arrays.sort(nums);
        PriorityQueue<Node> queue = new PriorityQueue<Node>(new Comparator<Node>(){
            @Override
            public int compare(Node n1,Node n2){
                return (nums[n1.end]-nums[n1.start])-(nums[n2.end]-nums[n2.start]);
            }
        });

        for(int i=0; i<nums.length-1;i++){
            queue.offer(new Node(i,i+1));
        }

        Node n = null;
        for(int i=0;i<k;i++){
             n = queue.poll();
            if(n.end+1<nums.length){
              queue.offer(new Node(n.start,n.end+1));
            }
        }
        return nums[n.end]-nums[n.start];
    }

    public class Node{
        private int start;
        private int end;

        public Node(int start,int end){
            this.start = start;
            this.end = end;
        }
    }
}
```

```java
class Solution {
    public int smallestDistancePair(int[] nums, int k) {
        if(nums==null||nums.length==0)return 0;

        Arrays.sort(nums);
        int len = nums.length;
        int left = 0;
        int right = nums[len-1]-nums[0];

        while(left<right){
            int mid = left+(right-left)/2;
            int count = getCount(nums,mid);
            if(k>count) left = mid+1;
            else right = mid;
        }

        return left;
    }

//    public int getCount(int[] nums,int val){
//        int left = 0;
//        int count =0;
//        for(int i=1;i<nums.length;i++){
//            if(nums[i]-nums[left]>val) left++;
//            else
//                count+=i-left+1;
//        }
//        return count;
//    }

    //计数方式
    public int getCount(int[] nums, int mid){
        int count = 0;
        int left = 0;
        //注意这里的left，在迭代的过程中没有重置，而是一直右移
        //很容易理解，i右移，nums[i]变大(注意对数组进行过排序)，而nums[left](上一轮的)不变
        //因此差增大，必然还是比mid大，因此不需要再迭代一遍
        for(int i = 1; i < nums.length;i++){
            while(nums[i] - nums[left] > mid){
                left++;
            }
            count += i - left;
        }

        return count;
    }

}
```