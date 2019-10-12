# 解题思路

## 我的解题思路
- 解法一：优先级队列(最大堆)时间复杂度为NlogN
- 解法二：优先级队列（必须使用最小堆）时间复杂度为NlogK
    + O(nlogK)的最关键在于堆的大小只能限定到k这么大，要使用最小堆实现
    + 使用最小堆的麻烦之处在于，最后输出元素的时候，得逆序一下才行，不然就是频率从小到大的排序了


```java
//解法一
class Solution {
    public List<String> topKFrequent(String[] words, int k) {
        if(words==null||words.length==0)return null;
        if(k>words.length)k = words.length;

        List<String>list = new ArrayList<>();

        HashMap<String,Integer> map = new HashMap<>();

        PriorityQueue<Map.Entry<String,Integer>> queue = new PriorityQueue<>(new Comparator<Map.Entry<String,Integer>>(){
            @Override
            public int compare(Map.Entry<String,Integer> o1, Map.Entry<String,Integer> o2){
                int res = o2.getValue()-o1.getValue();
                if(res ==0){
                    //实现按照字母顺序排序
                    res = o1.getKey().compareTo(o2.getKey());
                }
                return res;
            }
        });

        for(int i=0;i<words.length;i++){
            if(map.containsKey(words[i])){
                map.put(words[i],map.get(words[i])+1);
            }else{
                map.put(words[i],1);
            }
        }

        queue.addAll(map.entrySet());

        while(k-- >0){
            list.add(queue.poll().getKey());
        }
        return list;
    }
}
```

```java
//解法二
class Solution {
    public List<String> topKFrequent(String[] words, int k) {
        if(words==null||words.length==0)return null;
        if(k>words.length)k = words.length;

        List<String>list = new ArrayList<>();

        HashMap<String,Integer> map = new HashMap<>();

        PriorityQueue<Map.Entry<String,Integer>> queue = new PriorityQueue<>(k,new Comparator<Map.Entry<String,Integer>>(){
            @Override
            public int compare(Map.Entry<String,Integer> o1, Map.Entry<String,Integer> o2){
                int res = o1.getValue()-o2.getValue();
                if(res ==0){
                    //实现按照字母顺序排序
                    res = o2.getKey().compareTo(o1.getKey());
                }
                return res;
            }
        });

        for(int i=0;i<words.length;i++){
            if(map.containsKey(words[i])){
                map.put(words[i],map.get(words[i])+1);
            }else{
                map.put(words[i],1);
            }
        }

        //queue.addAll(map.entrySet());
         for(Map.Entry<String,Integer> entry:map.entrySet()){
            if(queue.size()<k){
                queue.add(entry);
            }else{
                if(queue.peek()<entry.getValue){
                    queue.poll();
                    queue.put(entry);
                }
            }
        }

        while(k-- >0){
            list.add(queue.poll().getKey());
        }
        return list;
    }
}
```
