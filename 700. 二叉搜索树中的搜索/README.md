# 题目
700. 二叉搜索树中的搜索

## 解题思路


```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode searchBST(TreeNode root, int val) {
        if(root==null) return null;
        if(val>root.val){
            return searchBST(root.right,val);
        }
        if(val<root.val){
            return searchBST(root.left,val);
        }
            return root;

    }
}
```
