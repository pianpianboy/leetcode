# 解题思路

## 我的解题思路
首先得明白题目中子序列的意思（要区分子序列和子串的区别）
关于子序列的问题，想到了**718. 最长重复子数组** 该题目时子串问题使用了DP动态规划，同样子序列问题也可以使用DP动态规划。子序列与子串之间区别就在与转移方程有细微的差别。
暴力解答：
``` JAVA
class Solution {
    public int numMatchingSubseq(String S, String[] words) {
        int res =0;
        for(int i=0;i<words.length;i++){
            int index =0;
            int size = words[i].length();
            for(int j=0;j<S.length()&& index!=size;j++){
                if(S.charAt(j)==words[i].charAt(index))
                    index++;
            }
            if(index==size)
                res++;
        }
        return res;
    }
}
```

## 参考解题思路
题目中原意是：匹配子序列的单词数--即计算数组words[]中单词为Str的子序列的数量
可以将上述题目进行拆解：
1. 判断word 是否为Str的子序列，这一步最重要，也最难
2. 遍历数据统计为Str的子序列等数量

### 改进1
使用字典保存字符串S中字母所有索引，然后遍历word选找索引。
```JAVA
class Solution {
    public int numMatchingSubseq(String S, String[] words) {
        //对S进行预处理
        Node[] nodes = new Node[26];
        for(int i=0;i<S.length();i++){
            if(nodes[S.charAt(i)-97]==null)
                nodes[S.charAt(i)-97] = new Node();
            nodes[S.charAt(i)-97].list.add(i);
        }

        int res = 0;
        //遍历数组，求数组中每一个单词是否为子序列
        for(int i=0;i<words.length;i++){
            String word = words[i];
            if(isSubseq(nodes,S,word))
                res++;
        }
        return res;
    }
    public boolean isSubseq(Node[] nodes,String S,String word){
        if(S.length()<word.length())return false;
        int p=-1;
        //遍历单词中每一个字符，判断是否满足子序列的要求
        for(int i=0;i<word.length();i++){
            int index = word.charAt(i)-97;
            //if(nodes[index].list.size()==0||nodes[index].list.get(nodes[index].list.size()-1)<=p)
            if(nodes[index]==null||nodes[index].list.get(nodes[index].list.size()-1)<=p)
                return false;
            //  for(int j=0;j<nodes[index].list.size();j++){
            //     if(nodes[index].list.get(j)>p){
            //         p=i;
            //         break;
            //     }
            // }
            for (int x:nodes[index].list) {
                if (x>p){
                    p=x;
                    break;
                }
            }
        }
       return true;
    }
}
public class Node{
        List<Integer>list = new ArrayList<Integer>();
}
```

### 改进2
在改进1的基础上使用缓存
```JAVA
class Solution {

    public int numMatchingSubseq(String S, String[] words) {
        //对S进行预处理
        Node[] nodes = new Node[26];
        HashMap<String,Boolean> map = new HashMap<>();
        for(int i=0;i<S.length();i++){
            if(nodes[S.charAt(i)-97]==null)
                nodes[S.charAt(i)-97] = new Node();
            nodes[S.charAt(i)-97].list.add(i);
        }

        int res = 0;
        //遍历数组，求数组中每一个单词是否为子序列
        for(int i=0;i<words.length;i++){
            String word = words[i];
            if(map.containsKey(word)){
                if(map.get(words[i])){
                    res++;
                }

            }
            else if(isSubseq(nodes,S,word)){
                res++;
                map.put(word,true);
            }

        }
        return res;
    }
    public boolean isSubseq(Node[] nodes,String S,String word){
        if(S.length()<word.length())return false;
        int p=-1;
        //遍历单词中每一个字符，判断是否满足子序列的要求
        for(int i=0;i<word.length();i++){
            int index = word.charAt(i)-97;
            //if(nodes[index].list.size()==0||nodes[index].list.get(nodes[index].list.size()-1)<=p)
            if(nodes[index]==null||nodes[index].list.get(nodes[index].list.size()-1)<=p)
                return false;
            //  for(int j=0;j<nodes[index].list.size();j++){
            //     if(nodes[index].list.get(j)>p){
            //         p=i;
            //         break;
            //     }
            // }
            for (int x:nodes[index].list) {
                if (x>p){
                    p=x;
                    break;
                }
            }
        }
       return true;
    }
}
public class Node{
        List<Integer>list = new ArrayList<Integer>();
}
```



### 改进3
There are too many extreme situations in test cases , causing some abnormal solutions to become fast，such as String.indexOf()
```JAVA
public int numMatchingSubseq(String S, String[] words) {
        HashMap<String, Integer> map = new HashMap<>();
        int index = 0, count = 0;
        boolean sub;
        for (String word : words) {
            if (map.containsKey(word)) {
                count += map.get(word);
            } else {
                index = -1;
                sub = true;
                for (int i = 0; i < word.length(); i++) {
                    index = S.indexOf(word.charAt(i), index + 1);
                    if (index < 0) {
                        sub = false;
                        break;
                    }
                }
                if (sub) {
                    count++;
                    map.put(word, 1);
                } else {
                    map.put(word, 0);
                }
            }
        }
        return count;
}
```














