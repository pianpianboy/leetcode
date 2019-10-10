# 解题思路

## 我的解题思路
- 优先级队列
- 基于快速排序算法中的partition（切分）来解决

```java
class Solution {
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<>((o1,o2)->{return o1-o2;});
        int len =0;
        if(k>=nums.length) {
            len = nums.length;
        }else
            len = k;

        for(int i=0;i<len;i++){
            queue.offer(nums[i]);
        }
        if(k>nums.length)
            return queue.peek();
        else{
            int n = k;
            while(n<nums.length){
                if(queue.peek()<nums[n]){
                    queue.poll();
                    queue.offer(nums[n]);
                }
                n++;
            }
        }
        return queue.peek();
    }
}
```

```java
//利用快排的解法
class Solution {
    public int findKthLargest(int[] nums, int k) {
        int left =0;
        int right = nums.length-1;

        int target = nums.length-k;
        while(left<=right){
            int  index = partition(nums,left,right);
            if(index==target){
                return nums[index];
            }else if(index<target){
                left = index+1;
            }else
                right = index-1;
        }
        return 0;
    }

    public int partition(int[] nums,int left, int right){
        int privot = nums[left];
        int j = left;
        for(int i=left+1;i<=right;i++){
            if(nums[i]<privot){
                j++;
                swap(nums,i,j);
            }
        }
        swap(nums,j,left);
        return j;
    }

    public void swap(int[] nums,int i,int j){
        int t = nums[i];
        nums[i]= nums[j];
        nums[j]=t;
    }
}
```