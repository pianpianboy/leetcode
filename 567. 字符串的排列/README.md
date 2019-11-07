# 题目
567. 字符串的排列

## 解题思路
- 滑动窗口
    + 分析题目，题意中是固定窗口，即固定window的长度
    + 当window的长度大于s1.length() （window的长度）之后。就开始移动左边界。
    + 采用HashMap<Character,Integer> 保存s1中的值。
- d


```java
class Solution {
    public boolean checkInclusion(String s1, String s2) {
        if(s1==null||s2==null||s1.length()>s2.length())
            return false;
        if("".equals(s1)) return true;

        HashMap<Character,Integer> map = new HashMap<>();
        int len1 = s1.length();
        int len2 = s2.length();
        char[] arr1 = s1.toCharArray();
        char[] arr2 = s2.toCharArray();
        for(int i=0;i<len1;i++){
            int val = map.getOrDefault(arr1[i],0);
            map.put(arr1[i],val+1);
        }

        int left =0;
        int right =0;
        int count =0;

        while(right<len2){
            int val = map.getOrDefault(arr2[right],0);
            map.put(arr2[right],val-1);
            if(val-1>=0)
                count++;

            if(count==len1)
                return true;

            if(right-left+1>=len1){
                map.put(arr2[left],map.get(arr2[left])+1);

                if(map.get(arr2[left])>0){
                    count--;
                }
                left++;
            }
            right++;
        }
        return false;
    }
}
```