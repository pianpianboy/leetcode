# 解题思路

# 我的解题思路
此题是一个简单的题，但是却没有思路，更别提写出代码了

# 参考解题思路
- 采用暴力解，先获取len/2即字符串的一半长度，然后循环判断间隔gap （取值范围：i=1->len/2,但是必须满足len%i==0 的条件）
- 利用kmp解题
    + 利用kmp的next数组来解题
    + 假设str的长度为len，重复的子串的长度为k,如果真的由连续多个长度为k的子串重复构成str，那么在对str求next时，由于连续对称性，会从next[k+1]开始，1，2，3，递增，直到next[len]=len-k;
    + 如果next[len]>0且（len-k）%k==0 表示str中有整数个k，
    + 其中k= len-next[len]，由next[len]=len-k 变换求得
    + 其中的技巧就是 s= s.concat("A"),这样才能求得next[len],不然next数组越界。
- 利用小技巧解题
    + 1.将原字符串给出拷贝一遍组成新字符串；
    + 2.掐头去尾留中间；
    + 3.如果还包含原字符串，则满足题意。



```java
class Solution {
    //使用KMP的next数组来解题
    public boolean repeatedSubstringPattern(String s) {
        if(s==null||s.length()==0) return false;
        // if(s.length()==1) return true;
        int len = s.length();
        s =s.concat("A");
        char[] arr = s.toCharArray();
        int[] next = getNext(arr);
        int k = len -next[len];
        if(next[len]>0 && next[len]%k==0)
            return true;
        else
            return false;

    }

    public int[] getNext(char[] arr){
        int[] next = new int[arr.length];
        next[0] = -1;
        if(arr.length>1){
            next[1] = 0;
        }

        for(int i=2;i<arr.length;i++){
            int index = next[i-1];
            while(index>=0){
                //比较arr[i-1]
                if(arr[i-1]==arr[index]){
                    next[i] = index + 1;
                    break;//一定不要忘记写了
                }else{
                    index = next[index];
                }
            }
        }
        return next;
    }
}

```


```java
class Solution {
    //使用KMP的next数组来解题
    public boolean repeatedSubstringPattern(String s) {
        if(s==null||s.length()==0) return false;
        String str = s+s;
        if(str.substring(1,str.length()-1).contains(s))
            return true;
        else
            return false;
    }
}
```
