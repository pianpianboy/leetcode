# 题目
226. 翻转二叉树

## 解题思路
- 利用递归，也可以说是先序遍历

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
    public TreeNode invertTree(TreeNode root) {
        if(root==null) return root;
        invert(root);
        return root;
    }
    public void invert(TreeNode node){
        if(node.left==null&&node.right==null){
            return;
        }
        swap(node,node.left,node.right);
        if(node.left!=null){
            invert(node.left);
        }
        if(node.right!=null){
            invert(node.right);
        }
    }

    public void swap(TreeNode node,TreeNode node1,TreeNode node2){
        node.left = node2;
        node.right = node1;
    }
}
```
