# 解题思路

## 我的解题思路
- 题目其实很简单，就是考察string.split的使用，注意+的转意符\\+ ,再加上i分隔符 ，最终分隔符为\\+|i


```java
class Solution {
    public String complexNumberMultiply(String a, String b) {
        if(a ==null||b==null) return null;

        String[] x = a.split("\\+|i");
        String[] y = b.split("\\+|i");

        int a0 = Integer.parseInt(x[0]);
        int a1 = Integer.parseInt(x[1]);

        int b0 = Integer.parseInt(y[0]);
        int b1 = Integer.parseInt(y[1]);

        return (a0*b0-a1*b1)+"+"+(a0*b1+a1*b0)+"i";
    }
}
```
