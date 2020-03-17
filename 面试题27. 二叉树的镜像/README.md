### 题目
面试题27. 二叉树的镜像

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
    public TreeNode mirrorTree(TreeNode root) {
        help(root);
        return root;
    }

    public void help(TreeNode node){
        if(node==null){
            return ;
        }
        help(node.left);
        help(node.right);
        TreeNode tmpNode = node.left;
        node.left = node.right==null? null : node.right;
        node.right = tmpNode;
    }
}
```