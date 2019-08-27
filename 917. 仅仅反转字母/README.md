# 解题思路

## 我的解题思路
- 双指针

```java
class Solution {
    public String reverseOnlyLetters(String s) {
        if(s==null||s.length()<=1) return s;

        char[] ch = s.toCharArray();
        int left = 0;
        int right = s.length()-1;

        while(left<right){
            if(ch[left]<'A'||ch[left]>'z'||(ch[left]>'Z'&&ch[left]<'a')){
                left++;
                continue;
            }
            if(ch[right]<'A'||ch[right]>'z'||(ch[right]>'Z'&&ch[right]<'a')){
                right--;
                continue;
            }
            ch[left] ^= ch[right];
            ch[right] ^= ch[left];
            ch[left] ^= ch[right];
            // char tmp = ch[left];
            // ch[left] = ch[right];
            // ch[right] = tmp;
            left++;
            right--;

        }
        return new String(ch);
    }
}
```
