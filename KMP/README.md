# KMP算法

## 解题思路
- kmp算法的核心在于next数组的求解

## kmp算法的应用
- 459. 重复的子字符串


```java
public class Kmp {

    public static void main(String[] args) {
        String str = "abcabcababaccc";
        String match = "ababa";
        System.out.println(kmpMethod(str, match));
    }

    public static int kmpMethod(String s,String match){
        if(s==null||match==null) return -1;
        if(match.length()>s.length())return -1;

        int slen = s.length();
        int mlen = match.length();

        char[] sArr = s.toCharArray();
        char[] mArr = match.toCharArray();
        int[] next = getNext(mArr);

        int i=0;
        int j=0;

        while(i<slen && j<mlen){
            if(sArr[i] == mArr[j]){
                i++;
                j++;
            }else if(next[j]==-1){
                i++;
            }else{
                j = next[j];
            }
        }

        if(j==mlen)
            return j;
        else
            return -1;
    }

    public static int[] getNext(char[] arr){
        int [] next = new int[arr.length];
        next[0] = -1;
        next[1] = 0;
        for(int i=2;i<arr.length;i++){
            int index = next[i-1];
            while(index>=0){
                if(arr[i-1]==arr[index]){
                    next[i] = index + 1;
                    break;
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
//459. 重复的子字符串
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