# 解题思路

## 我的解题思路
给定一个字符串，请将字符串里的字符按照出现的频率降序排序

- 优先级队列，大堆
- 桶排序

```java
class Solution {
    public String frequencySort(String s) {
        StringBuilder sb  = new StringBuilder();
        Map<Character,Integer> map = new HashMap<>();

        char[] arr = s.toCharArray();
        for(int i=0;i<s.length();i++){
            if(map.containsKey(arr[i])){
                map.put(arr[i],map.get(arr[i])+1);
            }else{
                map.put(arr[i],1);
            }
        }

        PriorityQueue<Map.Entry<Character,Integer>> queue = new PriorityQueue<>(new Comparator<Map.Entry<Character, Integer>>() {
            @Override
            public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
                return o2.getValue()-o1.getValue();
            }
        });
//        PriorityQueue<Map.Entry<Character, Integer>> queue = new PriorityQueue<>((v1, v2) -> {
//            return v2.getValue() - v1.getValue();
//        });

        queue.addAll(map.entrySet());
        while(queue.size()>0){
            char key = queue.peek().getKey();
            int num = queue.poll().getValue();
            while(num-->0){
                sb.append(key);
            }
        }
        return sb.toString();
    }
}
```

```java
class Solution {
    public static String frequencySort(String s){
        int[] hash=new int[256];
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<s.length();i++){
            hash[s.charAt(i)]++;
        }
        int[] hash1=hash.clone();//这个数组是为了找到hash数组排序后对应的下标
        Arrays.sort(hash);//将数组排序
        for(int i=255;i>=0&&hash[i]>0;i--){//为0的部分直接不用管
            for(int j=0;j<256;j++){   //从未排序的里面找到对应值
                if(hash[i]==hash1[j]){
                 while(hash1[j]-->0)
                        sb.append((char)j);
                }
            }
        }
        return sb.toString();
    }
}

```

