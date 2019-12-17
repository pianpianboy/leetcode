# 题目
938. 二叉搜索树的范围和


## 解题思路
- 深度优先遍历DFS解 BST的问题
    + 首先题目很难理解，给出的数组其实是搜索树的按层遍历的结点排列
    + 在求解时候需要理解的是：一颗BST的左右子树仍然是一颗BST，即可以使用递归
- 2

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
    public int rangeSumBST(TreeNode root, int L, int R) {
        return help(root, L , R);
    }

    public int help(TreeNode root, int L, int R) {
        if(root==null) {
            return 0;
        }
        if(root.val < L){
            return help(root.right,L,R);
        }else if(root.val>R){
            return help(root.left,L,R);
        }else{
            return root.val+help(root.left,L,R)+help(root.right,L,R);
        }
    }
}
```