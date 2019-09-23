# 解题思路

## 我的解题思路
- 使用java自带的优先级队列PriorityQueue
- 使用最小堆


```java
class KthLargest {

    int k;
    int[] nums;
    Queue<Integer> queue;
    public KthLargest(int k, int[] nums) {
        this.k = k;
        this.nums = nums;
        queue = new PriorityQueue<Integer>(k);
        for(int i=0;i<nums.length;i++){
            add(nums[i]);
        }
    }

    public int add(int val) {
        if(queue.size()<k)
            queue.offer(val);
        else if(queue.peek()<val){
            queue.poll();
            queue.offer(val);
        }
        return queue.peek();
    }
}

/**
 * Your KthLargest object will be instantiated and called as such:
 * KthLargest obj = new KthLargest(k, nums);
 * int param_1 = obj.add(val);
 */

```
