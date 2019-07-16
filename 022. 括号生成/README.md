# 解题思路

## 我的解题思路
本题采用回溯法（dfs+剪枝）。剪枝即对满足条件的情况进行dfs,从而大大的减少dfs的可能情况。注意回溯使用传址（引用）并回溯来优化时间复杂度。使用传值的回溯不需要回删，但是需要复制结构。 需要重复使用的变量，使用全局变量保存；基本类型（整形）使用传值，结构体（vector，string）等使用传址（引用）。

```java
class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList();
        backtrack(ans, "", 0, 0, n);
        return ans;
    }

    public void backtrack(List<String> ans, String cur, int open, int close, int max){
        if (cur.length() == max * 2) {
            ans.add(cur);
            return;
        }

        if (open < max)
            backtrack(ans, cur+"(", open+1, close, max);
        if (close < open)
            backtrack(ans, cur+")", open, close+1, max);
    }


}
```