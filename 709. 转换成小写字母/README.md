# 解题思路

## 我的解题思路
先将字符串转换为字符数组，然后针对每个字符进行处理，如果字符为大写字母，则+32后强制转换为char

```java
class Solution {
    public String toLowerCase(String str) {
        if(str==null) return null;

        char[] ch = str.toCharArray();

        StringBuilder sb = new StringBuilder();

        for(int i =0; i<ch.length; i++){
            if(ch[i]>='A'&&ch[i]<='Z'){//增加判断，如果是大写字母，直接转，如果不是不转
                ch[i] = (char)(ch[i]+32);
            }
            sb.append(ch[i]);
        }
        return sb.toString();
    }
}
```