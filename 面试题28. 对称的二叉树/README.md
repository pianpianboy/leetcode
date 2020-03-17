### 题目
面试题28. 对称的二叉树

###解题思路
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
    public boolean isSymmetric(TreeNode node) {
        if(node==null) return true;
        return help(node.left,node.right);
    }
    public boolean help(TreeNode left,TreeNode right){
        if(left==null&&right==null){
            return true;
        }else if(left==null||right==null){
            return false;
        }
        if(left.val==right.val){
            return help(left.left,right.right)&&help(left.right,right.left);
        }else{
            return false;
        }
    }

}
```