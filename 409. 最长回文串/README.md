### 题目
409. 最长回文串

### 解题思路
- hashmap
- 数组

```java
//hashMap
class Solution {
    public int longestPalindrome(String s) {
        if(s==null||s.length()==0) return 0;
        HashMap<Character,Integer> map = new HashMap<>();

        for(int i=0;i<s.length();i++){
            char ch = s.charAt(i);
            if(map.containsKey(ch)){
                map.put(ch, map.get(ch)+1);
            }else{
                map.put(ch,1);
            }
        }

        int res =0 ;
        for(Map.Entry<Character,Integer> entry : map.entrySet()){
            if(entry.getValue()%2==0){
                res +=  entry.getValue();
            }else {
                res += entry.getValue()-1;
            }
        }
        return res==s.length()? res: res+1;
    }
}
```

```java
class Solution {
    public int longestPalindrome(String s) {
        if(s==null||s.length()==0) return 0;
        int[] arr = new int[128];

        int res=0;
        for(char c: s.toCharArray()){
            arr[c]++;
        }
        for(int val : arr){
            if(val%2==0)
                res += val;
            else
                res += val-1;
        }

        return res==s.length()? res: res+1;
    }
}
```