# 解题思路

## 我的解题思路
首先得明白题目中子序列的意思（要区分子序列和子串的区别）
关于子序列的问题，想到了**718. 最长重复子数组** 该题目时子串问题使用了DP动态规划，同样子序列问题也可以使用DP动态规划。子序列与子串之间区别就在与转移方程有细微的差别。
暴力解答：
``` JAVA
class Solution {
    public int numMatchingSubseq(String S, String[] words) {
        int res =0;
        for(int i=0;i<words.length;i++){
            int index =0;
            int size = words[i].length();
            for(int j=0;j<S.length()&& index!=size;j++){
                if(S.charAt(j)==words[i].charAt(index))
                    index++;
            }
            if(index==size)
                res++;
        }
        return res;
    }
}
```

## 参考解题思路
题目中原意是：匹配子序列的单词数--即计算数组words[]中单词为Str的子序列的数量
可以将上述题目进行拆解：
1. 判断word 是否为Str的子序列，这一步最重要，也最难
2. 遍历数据统计为Str的子序列等数量


