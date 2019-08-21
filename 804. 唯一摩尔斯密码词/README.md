# 解题思路

## 我的解题思路
- 实现单个单词翻译的方法transWord()
- 将数组中每个单词都翻译一遍的结果存入HashSet，去重
- 返回set.size()即结果

```java
class Solution {
    public int uniqueMorseRepresentations(String[] words) {
        if(words==null) return 0;

        HashSet<String> set = new HashSet<>();

        for(int i=0;i<words.length;i++){
            String str = transWord(words[i]);
            set.add(str);
        }
        return set.size();
    }

    public String transWord(String str){
        String[] dict = {".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."};

        char[] ch = str.toCharArray();
        StringBuilder sb = new StringBuilder();

        for(int i=0;i<ch.length;i++){
            sb.append(dict[ch[i]-'a']);
        }
        return sb.toString();
    }
}
```
