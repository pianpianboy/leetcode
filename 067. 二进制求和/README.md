# 解题思路

## 我的解题思路
-  //使用a b 相加，将两个字符串较短的补0，然后从末尾进行遍历，遍历的时候进行拼接，最后进行反转（只有StringBuilder有翻转的函数reverse()）

```java
class Solution {
    public String addBinary(String a, String b) {
        //使用a b 相加，位数不足补0，遍历的时候进行拼接，最后进行反转
        int len1 = a.length();
        int len2 = b.length();
        int flag =0;//存储进位值
        StringBuilder sb = new StringBuilder();

        //需要从末尾开始遍历
        for(int i=len1-1,j=len2-1;i>=0||j>=0;i--,j--){
            int sum = flag;
            sum += i>=0? a.charAt(i)-'0':0;
            sum += j>=0? b.charAt(j)-'0':0;
            sb.append(sum%2+"");
            flag = sum/2;
        }
        //在for循环中可以使用i<=len1||i<=len2替代下面的代码
        //最后进位的处理
         if(flag!=0)
            sb.append("1");
        return sb.reverse().toString();
    }
}
```