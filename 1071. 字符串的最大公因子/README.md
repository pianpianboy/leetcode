### 题目
1071. 字符串的最大公因子

### 解题思路
```java
class Solution {
    public String gcdOfStrings(String str1, String str2) {
        if(str1==null||str2==null) return null;

        if(!(str1+str2).equals(str2+str1)){
            return "";
        }
        return str1.substring(0,gcd(str1.length(),str2.length()));
    }

    public int gcd(int a, int  b){
        return b==0? a:gcd(b,a%b);
    }
}
```
