# 解题思路

## 我的解题思路
- 通过将s的字母重新排列成t来生成变位词。如果两个字符串进行排序后，相等，则为排序词。
- //利用桶排序，因为涉及到字符串的题目中可以将字符串转换为字符,而字符的取值范围 0<=char-'a'<=25, 因此在涉及到字符串的算法题中经常使用字符-'a'作为数组的下标来解题，
    + 遍历一遍长度为26的数组，时间复杂度从O(n)下降为O(1)常数项了



```java
//解法一：
class Solution {
    public boolean isAnagram(String s, String t) {
        if(s==null||t==null) return false;
        int len1 = s.length();
        int len2 = t.length();

        if(len1!=len2)return false;

        char[] str1 = s.toCharArray();
        char[] str2 = t.toCharArray();

        Arrays.sort(str1);
        Arrays.sort(str2);

        return Arrays.equals(str1,str2);
    }
}

```

```java
//解法二：
class Solution {
    public boolean isAnagram(String s, String t) {
        //利用桶排序，因为涉及到字符串的题目中可以将字符串转换为字符
        //而字符的取值范围 0<=char-'a'<=25,
        if(s==null||t==null) return false;
        int len1 = s.length();
        int len2 = t.length();

        if(len1!=len2)return false;

        int[] arr1 = new int[26];
        int[] arr2 = new int[26];

        for(int i=0;i<len1;i++){
            arr1[s.charAt(i)-'a']++;
            arr2[t.charAt(i)-'a']++;
        }

        for(int i=0;i<26;i++){
            if(arr1[i]!=arr2[i])
                return false;
        }
        return true;
    }
}
```
