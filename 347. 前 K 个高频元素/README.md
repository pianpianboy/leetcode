### 题目
347. 前 K 个高频元素

### 解题思路
- 堆：大根堆==优先级队列
```java
class Solution {
    public List<Integer> topKFrequent(int[] nums, int k) {
        List<Integer> list = new LinkedList<>();
        if(nums==null||nums.length==0||k<=0){
            return list;
        }
        HashMap<Integer,Integer> map = new HashMap<>();
        PriorityQueue<Map.Entry<Integer,Integer>> queue = new PriorityQueue<>(new Comparator<Map.Entry<Integer,Integer>>(){
            @Override
            public int compare(Map.Entry<Integer,Integer> o1, Map.Entry<Integer,Integer> o2){
                return o2.getValue()-o1.getValue();
            }
        });

        for(int i=0;i<nums.length;i++){
            int val = nums[i];
            if(map.containsKey(val)){
                map.put(val,map.get(val)+1);
            }else{
                map.put(val,1);
            }
        }

        for(Map.Entry<Integer,Integer>entry :map.entrySet()){
            queue.offer(entry);
        }

        while(k>0){
            Map.Entry<Integer,Integer> entry = queue.poll();
            int key = entry.getKey();
            list.add(key);
            k--;
        }

        return list;
    }
}
```
