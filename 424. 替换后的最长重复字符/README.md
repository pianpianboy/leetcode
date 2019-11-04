# 题目
424. 替换后的最长重复字符

## 解题思路
- 滑动窗口
    + 利用滑动窗口解题必须要解决的两个问题：
        * 确定移动窗口左边界的条件，即什么时候移动左边界
        * 确定移动窗口右边界的条件，即什么时移动右边界
    + 确定好左右边界的条件后，接下来的问题就是要解决：怎么计算当前窗口中数量最多的字符的数量。因为需要替换的字符就是当前窗口的大小减去窗口中数量最多的字符的数量
- 参考 https://mp.weixin.qq.com/s/6YeZUCYj5ft-OGa85sQegw


```java
class Solution {
    public int characterReplacement(String s, int k) {
        //
        if(s==null||s.length()==0) return 0;
        if(s.length()==1) return 1;

        char[] arr = s.toCharArray();
        int len = arr.length;


        int left =0;
        int right =0;

        int[] hash = new int[26];
        int maxcount =0;
        int res =0;

        while(right <len){
            //while为移动右边界条件

            //每来一个值，统计一次
            hash[arr[right] - 'A']++;
            maxcount = Math.max(maxcount,hash[arr[right]-'A']);

            while(right-left +1-maxcount>k){
                hash[arr[left]-'A']--;
                left++;
            }
            //此时left不停的往右移动，直到right-left +1-maxcount<=k, 满足条件，更新res
            res = Math.max(res,right-left+1);
            right++;
        }
        return res;
    }
}
```
