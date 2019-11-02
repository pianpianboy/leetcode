# 题目
3. 无重复字符的最长子串

## 解题思路
- 双指针 ：其实就是滑动窗口
- 滑动窗口
    + 一般使用滑动窗口都需要使用HashMap或者单调栈来配合使用，具体是使用HashMap还是单调栈需要根据具体的问题来定。
-

```java
//双指针
class Solution {
    public int lengthOfLongestSubstring(String s) {
        if(s==null||s.length()==0) return 0;
        if(s.length()==1)return 1;
        char[] arr = s.toCharArray();
        int len = arr.length;
        HashMap<Character,Integer> map = new HashMap<>();
        map.put(arr[0],0);
        int left = 0;
        int right = 1;
        int max = Integer.MIN_VALUE;
        while(right<len){
            //第二个要注意的地方就是&&map.get(arr[right])>=left
            //因为很容易忽视的情况就是：重复值出现在left的前面，这种情况是不需要考虑的
            if(map.containsKey(arr[right])&&map.get(arr[right])>=left){
                //第一个点：就是要想到，当出现重复的字符的情况下怎么处理left和right
                //出现重复字符的情况下移动左边的边界（指针或者窗口）
                left = map.get(arr[right])+1;
            }
            map.put(arr[right],right);

            max = Math.max(max,right-left+1);
            right++;
        }
        return max;
    }
}

```
