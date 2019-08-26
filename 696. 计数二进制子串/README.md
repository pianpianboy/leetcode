# 解题思路

## 我的解题思路
- 方法一
    - 使用last来记录之前一种数字的个数，使用cur来记录当前数字的个数
    - 当last>=cur的时候，res++

- 方法二
    - 遍历字符数组，记录每种数字连续出现的次数，使用list来记录，
    - 然后在遍历两两去较小的值
    - 比如 111100011000，得到4323；在4323中的任意相邻两个数字
    - 4与3取3，3与2取3，2与3取2

```java
class Solution {
    public int countBinarySubstrings(String s) {
      if(s==null || s.length() <= 1) return 0;

      char[] ch = s.toCharArray();
      int res = 0;
      int cur = 1;
      int last = 0;

      for(int i=1;i<ch.length;i++){
        if(ch[i] == ch[i-1]) cur++;
        else{
          last = cur;
          cur = 1;
        }
        if(last>=cur) res++;
      }

      return res;
    }
}

```


```java
class Solution {
    public int countBinarySubstrings(String s) {
      if(s==null || s.length() <= 1) return 0;
      char[] ch = s.toCharArray();
      int res = 0;

      List<Integer> list = new ArrayList<Integer>();
      int num = 1;
      for(int i=1;i<ch.length;i++){
        if(ch[i]==ch[i-1]) num++;
        else{
          list.add(num);
          num=1;
        }
      }
      list.add(num);
      int last = list.get(0);
      for(int i=1;i<list.size();i++){
        res += list.get(i) >= last? last:list.get(i);
        last = list.get(i);
      }

      return res;
    }
}
```
