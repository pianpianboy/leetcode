### 题目
1160. 拼写单词

### 解题思路
- 暴力解
- int[26]:  遇到有提示字符串仅包含小写（或者大写）英文字母的题，
都可以试着考虑能不能构造长度为26的每个元素分别代表一个字母的数组，来简化计算

```java
class Solution {
    public int countCharacters(String[] words, String chars) {
        if(chars==null||chars.length()==0){
            return 0;
        }
        int  res = 0;
        HashMap<Character,Integer> map = new HashMap<>();
        for(int i=0;i<chars.length();i++){
            char ch = chars.charAt(i);
            if(map.containsKey(ch)){
                map.put(ch,map.get(ch)+1);
            }else{
                map.put(ch,1);
            }
        }


        for(int i=0;i<words.length;i++){
            String word = words[i];
            boolean flag = true;
            HashMap<Character,Integer> newMap = new HashMap<>();
            newMap.putAll(map);
            for(int j=0;j<word.length();j++){
                char chs = word.charAt(j);
                if(!newMap.containsKey(chs)||newMap.get(chs)<1){
                    flag = false;
                    break;
                }else{
                    newMap.put(chs,newMap.get(chs)-1);
                }
            }
            if(flag) res += word.length();
        }
        return res;
    }
}
```

```java
class Solution {
    public int countCharacters(String[] words, String chars) {
        if(chars==null||chars.length()==0){
            return 0;
        }
        int  res = 0;
        int[] arr = new int[26];
        for(int i=0;i<chars.length();i++){
            char ch = chars.charAt(i);
            arr[ch-97] = arr[ch-97]+1;
        }


        for(int i=0;i<words.length;i++){
            String word = words[i];
            boolean flag = true;
            int[] newArr = Arrays.copyOf(arr,26);
            for(int j=0;j<word.length();j++){
                char chs = word.charAt(j);
                if(newArr[chs-97]<1){
                    flag = false;
                    break;
                }else{
                    newArr[chs-97] = newArr[chs-97]-1;
                }
            }
            if(flag) res += word.length();
        }
        return res;
    }
}
```
