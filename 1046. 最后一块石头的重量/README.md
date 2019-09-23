# 解题思路

## 我的解题思路
- 关键点在于每次都能拿到最重的，并且执行过程可能产生新的较轻的石头
- 因此采用优先队列完成（大顶堆）


```java
class Solution {
    public int lastStoneWeight(int[] stones) {
        if(stones==null||stones.length==0)
            return 0;

        Queue<Integer> queue = new PriorityQueue<>(new Comparator<Integer>(){
            @Override
            public int compare(Integer o1,Integer o2){
                return o2-o1;
            }
        });

        for(int i= 0;i<stones.length;i++){
            queue.offer(stones[i]);
        }

        while(queue.size()>=2){
            int x = queue.poll();
            int y = queue.poll();
            int c = x -y;
            if(c!=0)
                queue.offer(c);

        }
        return queue.size()!=0?queue.poll():0;
    }
}
```
