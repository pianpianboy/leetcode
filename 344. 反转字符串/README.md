# 解题思路

## 我的解题思路
- 使用双指针，交换头尾

```java
class Solution {
    public void reverseString(char[] s) {
        if(s == null || s.length==0 || s.length==1) return ;

        int len = s.length;
        int start =0;
        int end = len-1;

        while(start<end){
            char tmp = s[start];
            s[start] = s[end];
            s[end] = tmp;
            start++;
            end--;
        }
    }
}
```
