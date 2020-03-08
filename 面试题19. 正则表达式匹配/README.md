### 题目
面试题19. 正则表达式匹配

#### 解题思路
- 递归
    + 将大问题分解为一个小问题
- 动态规划：自顶向下
- 动态规划：自底向上

```java
//递归1
class Solution {
    public boolean isMatch(String s, String p) {
        //错误写法,注意区别
        //if(p.isEmpty()) return s.isEmpty();
        if(p.isEmpty()) return s.isEmpty();

        if(p.length()>1&& p.charAt(1)=='*'){
            //s:abc p:c*abc
            //s:aac p:a*c  或者s:aaac p:a*c
            return isMatch(s,p.substring(2)) || (!s.isEmpty()&&(s.charAt(0)==p.charAt(0)||p.charAt(0)=='.')&&isMatch(s.substring(1),p));
        }else{
            return (!s.isEmpty()&&!p.isEmpty()&&(s.charAt(0)==p.charAt(0)||p.charAt(0)=='.' )) && isMatch(s.substring(1),p.substring(1));
        }
    }
}
```

```java
//递归2
class Solution {
    public boolean isMatch(String s, String p) {

        return dp(0,0,s,p);
    }

    public boolean dp(int i,int j,String s, String p){
        if(j==p.length()){
            return i==s.length();
        }

        if((j<p.length()-1) && p.charAt(j+1)=='*'){
            //s:abc p:c*abc
            //s:aac p:a*c  或者s:aaac p:a*c
            return dp(i,j+2,s,p) || (i<s.length()&&(s.charAt(i)==p.charAt(j)||p.charAt(j)=='.')&&dp(i+1,j,s,p));
        }else{
            return i<s.length()&&(s.charAt(i)==p.charAt(j)||p.charAt(j)=='.')&&dp(i+1,j+1,s,p);
        }
    }
}
```

```java
//DP + 备忘录 自顶向下
class Solution {
    private HashMap<Character,boolean> map = new HashMap<>();
    public boolean isMatch(String s, String p) {
        return dp(0,0,s,p);
    }

    public boolean dp(int i,int j,String s, String p){
        boolean res = false;
        if(j==p.length()){
            res = i==s.length()
            map.put(new String(i+"_"+j),res);
            return res;
        }


        if((j<p.length()-1) && p.charAt(j+1)=='*'){
            //s:abc p:c*abc
            //s:aac p:a*c  或者s:aaac p:a*c

            if(map.containsKey(new String(i+"_"+(j+2)))){
                res = res || map.get(new String(i+"_"+j));
            }else{
                res = res || dp(i,j+2,s,p);
            }

            if(map.containsKey(new String((i+1)+"_"+j))){
                res = res || (i<s.length()&&(s.charAt(i)==p.charAt(j)||p.charAt(j)=='.')&&map.get(new String((i+1)+"_"+j)));
            }else{
                res = res || (i<s.length()&&(s.charAt(i)==p.charAt(j)||p.charAt(j)=='.')&&dp(i+1,j,s,p));
            }

            map.put(new String(i+"_"+j),res);
            return res;
        }else{
            if(map.containsKey(new String((i+1)+"_"+(j+1)))){
                res = i<s.length()&&(s.charAt(i)==p.charAt(j)||p.charAt(j)=='.')&&map.get(new String((i+1)+"_"+(j+1)));
            }else{
               res = i<s.length()&&(s.charAt(i)==p.charAt(j)||p.charAt(j)=='.')&&dp(i+1,j+1,s,p);
            }

            map.put(new String(i+"_"+j),res);
            return res;
        }
    }
}
```

```java
//DP+ 自顶向下
class Solution {
    private HashMap<String,Boolean> map = new HashMap<>();
    public boolean isMatch(String s, String p) {
        return dp(0,0,s,p);
    }

    public boolean dp(int i,int j,String s, String p){
        boolean res = false;
        if(j==p.length()){
            res = i==s.length();
            map.put(new String(i+"_"+j),res);
            return res;
        }


        if((j<p.length()-1) && p.charAt(j+1)=='*'){
            //s:abc p:c*abc
            //s:aac p:a*c  或者s:aaac p:a*c

            if(map.containsKey(new String(i+"_"+(j+2)))){
                res = res || map.get(new String(i+"_"+(j+2)));
            }else{
                res = res || dp(i,j+2,s,p);
            }

            if(map.containsKey(new String((i+1)+"_"+j))){
                res = res || (i<s.length()&&(s.charAt(i)==p.charAt(j)||p.charAt(j)=='.')&&map.get(new String((i+1)+"_"+j)));
            }else{
                res = res || (i<s.length()&&(s.charAt(i)==p.charAt(j)||p.charAt(j)=='.')&&dp(i+1,j,s,p));
            }

            map.put(new String(i+"_"+j),res);
            return res;
        }else{
            if(map.containsKey(new String((i+1)+"_"+(j+1)))){
                res = i<s.length()&&(s.charAt(i)==p.charAt(j)||p.charAt(j)=='.')&&map.get(new String((i+1)+"_"+(j+1)));
            }else{
               res = i<s.length()&&(s.charAt(i)==p.charAt(j)||p.charAt(j)=='.')&&dp(i+1,j+1,s,p);
            }

            map.put(new String(i+"_"+j),res);
            return res;
        }
    }
}
```
