# 解题思路

## 我的解题思路
- 暴力解，线遍历一遍将字符作为key，出现次数作为value,然后再遍历一遍判断是否为第一个不重复的字符

```java
class Solution {
    public int firstUniqChar(String s) {
        if(s==null||s.length()<1) return -1;

        HashMap<Character,Integer> map  = new HashMap<>();
        for(int i=0;i<s.length();i++){
            if(map.containsKey(s.charAt(i))){
                map.put(s.charAt(i),map.get(s.charAt(i))+1);
            }else{
                map.put(s.charAt(i),1);
            }
        }

        for(int i=0;i<s.length();i++){
            if(map.get(s.charAt(i))==1)
               return i;
        }
        return -1;
    }
}
```
