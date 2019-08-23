# 解题思路

## 我的解题思路
- 求出s.length()/(2*k)，即循环的次数，还剩余待处理的次数s.length()%(2*k)
- 在每次循环中进行翻转
- 存在优化点
    + 第一个就是将字符数组char[] ch 转换位字符串 使用new String(ch)；
    + 使用 ^= 运算符进行 交换操作


```java
//未经优化的解法
class Solution {
    public String reverseStr(String s, int k) {
        if(s==null||s.length()<=1) return s;
      int n = s.length()/(2*k);
      int m = s.length()%(2*k);
      int i=0;
      for(;i<n;i++){
        s = reverse(s,i,k,k);
      }
      if(m!= 0)
        s = reverse(s,i,k,m);
      return s;
    }

    public String reverse(String s, int index, int k ,int m){
      if(m>=k) m=k;
      char[] ch = s.toCharArray();
      int start = index*2*k;
      int end = start+m-1;

      while(start<end){
        char tmp = ch[start];
        ch[start] = ch[end];
        ch[end] = tmp;
        start++;
        end--;
      }
      StringBuilder sb = new StringBuilder();
      for(char c:ch){
          sb.append(String.valueOf(c));
      }
      return sb.toString();
    }
}
```


```java
//优化1代码
class Solution {
    public String reverseStr(String s, int k) {
        if(s==null||s.length()<=1) return s;
        int n = s.length()/(2*k);
        int m = s.length()%(2*k);

        char[] ch = s.toCharArray();

        int i=0;
        for(;i<n;i++){
            reverse(ch,i,k,k);
        }

        if(m!= 0)
            reverse(ch,i,k,m);

        StringBuilder sb = new StringBuilder();
        for(char c:ch){
            sb.append(String.valueOf(c));
        }
        return sb.toString();
    }

    public void reverse(char[] ch, int index, int k ,int m){
        if(m>=k) m=k;
        int start = index*2*k;
        int end = start+m-1;

        while(start<end){
            char tmp = ch[start];
            ch[start] = ch[end];
            ch[end] = tmp;
            start++;
            end--;
        }
    }
}
```

```java
//最终优化
class Solution {
    public String reverseStr(String s, int k) {
        if(s==null||s.length()<=1) return s;
        int n = s.length()/(2*k);
        int m = s.length()%(2*k);

        char[] ch = s.toCharArray();

        int i=0;
        for(;i<n;i++){
            reverse(ch,i,k,k);
        }

        if(m!= 0)
            reverse(ch,i,k,m);

        // StringBuilder sb = new StringBuilder();
        // for(char c:ch){
        //     sb.append(String.valueOf(c));
        // }
        // return sb.toString();
      return new String(ch);
    }

    public void reverse(char[] ch, int index, int k ,int m){
        if(m>=k) m=k;
        int start = index*2*k;
        int end = start+m-1;

        while(start<end){
            ch[start] ^= ch[end];
            ch[end] ^= ch[start];
            ch[start] ^= ch[end];
            start++;
            end--;
        }
    }
}
```