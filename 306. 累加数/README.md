# 解题思路

## 我的解题思路


## 参考解题思路
- 参考： https://leetcode.com/problems/additive-number/discuss/75697/Java-Easy-Understand-DFS
- 使用dfs 回溯
    + 只有确定了前面两个值之后才能一条路走到黑
    + 前面的两个值使用两个for循环确定


```java
class Solution {
    public boolean isAdditiveNumber(String num) {
       if(num==null)return false;

        int n = num.length();
        for(int i =1;i<=(n-1)/2;i++){
            for(int j=i+1;j<n;j++){
                long a = parse(num.substring(0,i));

                long b = parse(num.substring(i,j));
                if(a == -1|| b==-1){
                    continue;
                }
                if(dfs(num.substring(j),a,b)) return true;
            }
        }
        return false;
    }

    public boolean dfs(String str,Long a,Long b){
        if(str.length()==0) return true;//有待详细考虑

        for(int i=1;i<=str.length();i++){
            long val = parse(str.substring(0,i));
            if(val == -1)continue;
            if(val == (a+b) && dfs(str.substring(i),b,val))
                return true;
        }
        return false;
    }

    public long parse(String s){
        if(!s.equals("0") && s.startsWith("0")) return -1;
        long res = 0;
        try{
             res = Long.parseLong(s);
        }catch(Exception e){

        }
        return res;
    }
}

```
