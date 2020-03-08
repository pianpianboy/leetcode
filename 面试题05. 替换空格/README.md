### 题目
面试题05. 替换空格

#### 解题思路

```java
class Solution {
    public String replaceSpace(String s) {
        if(s==null) return s;
        int len = s.length();
        StringBuilder sb = new StringBuilder();

        for(int i=0;i<len;i++){
            if(s.charAt(i) == ' '){
                sb.append("%20");
            }else{
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }
}
```
