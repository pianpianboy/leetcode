# 解题思路

## 我的解题思路
- 使用java自带的优先级队列PriorityQueue
- 使用最小堆
    + 因为是求第k大的数，因此构建最小堆，最小堆的size()为k
    + root节点就是我们需要的第K个数
    + 如果元素不够k个，对剩余的元素填充最小值，然后对堆进行从下到上的堆化
    + 最小堆使用数组存储
    + 添加元素时，与堆顶元素进行比较，如果大于，则替换，进行从上到下的堆化；如果小于，则直接返回堆顶的元素


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


```java
//解法2
class KthLargest {

    int k;
    int[] nums;
    int[] minHeap;

    public KthLargest(int k, int[] nums) {
        this.k = k;
        this.nums = nums;
        minHeap = new int[k];
        int index = 0;
        Arrays.sort(nums);

        for(int i=nums.length-1;i>=0&&index<k;i--,index++){
            minHeap[index] = nums[i];
        }
        while(index<k){
            minHeap[index++] = Integer.MIN_VALUE;
        }
        generateMinHeap();
    }
    //生成堆
    public void generateMinHeap(){
        //从下往上进行堆化
        for(int i=k/2-1;i>=0;i--){
            adjustMinHeap(i,k);
        }
    }

    public void adjustMinHeap(int index,int length){
        int left = 2* index+1;
        int right = 2* index+2;
        int min = left;
        if(left>length-1)
            return;
        //if(right>length-1)
           // min = left;
        //if(minHeap[right]>minHeap[left])
            //min = left;
        //else
            //min = right;

        if(right>length-1)
            min = left;
        else {
            if(minHeap[min]>minHeap[right]){
                min = right;
            }
        }

        if(minHeap[index]<minHeap[min])
            return;

        int t = minHeap[index];
        minHeap[index] = minHeap[min];
        minHeap[min] = t;
        adjustMinHeap(min,length);
    }


    public int add(int val) {
        if(val<minHeap[0])
            return minHeap[0];
        else{
            minHeap[0] = val;
            adjustMinHeap(0,k);
            return minHeap[0];
        }
    }
}

/**
 * Your KthLargest object will be instantiated and called as such:
 * KthLargest obj = new KthLargest(k, nums);
 * int param_1 = obj.add(val);
 */
```
