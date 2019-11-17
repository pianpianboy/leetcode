# 题目
392. 判断子序列

## 解题思路
- 双指针
- 动态规划


```java
// 双指针


class Solution {
    public boolean isSubsequence(String s, String t) {
        if(s==null||t==null||s.length()>t.length())  return false;

        int indext=0;
        int indexs=0;
        int lent=t.length();
        int lens=s.length();

        while(indext<lent&& indexs<lens){
            if(t.charAt(indext)==s.charAt(indexs)){
                indexs++;
            }
            indext++;
        }
        return indexs==lens? true:false;
    }
}
```

```java
//dp

class Solution {
    public boolean isSubsequence(String s, String t) {
        if(s==null||t==null||s.length()>t.length())  return false;

        int indext=0;
        int indexs=0;
        int lent=t.length();
        int lens=s.length();

        while(indext<lent&& indexs<lens){
            if(t.charAt(indext)==s.charAt(indexs)){
                indexs++;
            }
            indext++;
        }
        return indexs==lens? true:false;
    }
}
```

