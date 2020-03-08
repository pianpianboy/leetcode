### 题目
面试题20. 表示数值的字符串


#### 解题思路
- 列出所有可能

```java
class Solution {
    public boolean isNumber(String s) {
        s = s.trim();
        if(s==null||s.length()==0) return false;

        boolean numberSeen=false;
        boolean pointSeen=false;
        boolean eseen=false;
        boolean numberAfterE=true;

        for(int i=0;i<s.length();i++){
            if(s.charAt(i)>='0' && s.charAt(i)<='9'){
                numberSeen = true;
                numberAfterE = true;
            }
            else if(s.charAt(i)=='.'){
                if(eseen||pointSeen){
                    return false;
                }
                pointSeen = true;
            }else if(s.charAt(i)=='e'){
                if(eseen||!numberSeen){
                    return false;
                }
                numberAfterE = false;
                eseen = true;
            }else if(s.charAt(i)=='+'||s.charAt(i) == '-'){
                if(i!=0&&s.charAt(i-1)!='e'){
                    return false;
                }
            }else{
                return false;
            }
        }
        return numberSeen&&numberAfterE;
    }
}
```
