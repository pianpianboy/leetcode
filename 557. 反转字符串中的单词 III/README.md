# 解题思路

## 我的解题思路
- 将字符串用空格split为字符串数组
- 将字符串数组中的每个字符串word进行翻转；
    + 自己实现每个word的翻转，
    + 使用StringBuilder类的reverse方法进行翻转
        * 这里就必须注意String类是没有reverse()方法的
        * StringBuilder st = new StringBuilder(); st.append(arr[i]);st.reverse();
- 使用转换完的数组生成新的字符串


```java
//自己实现单词进行翻转的逻辑
class Solution {
    public String reverseWords(String s) {
       if(s==null||s.length()==0||s.length()==1) return s;

        String[] arr = s.split(" ");
        StringBuilder sb  = new StringBuilder();

        for(int i=0;i<arr.length;i++){
            arr[i] = reverse(arr[i]);
            if(i==arr.length-1)
                sb.append(arr[i]);
            else
                sb.append(arr[i]).append(" ");

        }
        return sb.toString();
    }

    public String reverse(String s){
        int len = s.length();

        char[] ch = s.toCharArray();
        StringBuilder sb = new StringBuilder();

        for(int i = len-1;i>=0;i--){
            sb.append(ch[i]);
        }

        return sb.toString();
    }
}
```


```java
//使用StringBuilder自带的reverse()方法，但是String类没有reverse()方法
class Solution {
    public String reverseWords(String s) {
       if(s==null||s.length()==0||s.length()==1) return s;

        String[] arr = s.split(" ");
        StringBuilder sb  = new StringBuilder();

        for(int i=0;i<arr.length;i++){
            StringBuilder st = new StringBuilder();
            st.append(arr[i]);
            st.reverse();

            if(i==arr.length-1)
                sb.append(st);
            else
                sb.append(st).append(" ");

        }
        return sb.toString();
    }
}
```