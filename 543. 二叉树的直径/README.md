### 题目
543. 二叉树的直径

### 解题思路
递归

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
    private int max = -1;
    public int diameterOfBinaryTree(TreeNode root) {
        if(root==null) return 0;
        help(root);
        return max;
    }
    public int help(TreeNode node){
        if(node==null) return -1;//这里很重要
        int left = help(node.left);
        int right = help(node.right);
        int n = left+right+2;
        max = Math.max(n,max);
        return Math.max(left,right)+1;
    }
}
```
