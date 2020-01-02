# 题目
701. 二叉搜索树中的插入操作

##解题思路
- 本题其实就是搜索二叉树的更新策略

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
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if(root == null){
            return new TreeNode(val);
        }
        TreeNode node = root;
        insert(node ,val);
        return root;
    }

    public void insert(TreeNode node ,int val){
        if(node.val>val){
            if(node.left==null){
                node.left = new TreeNode(val);
                return ;
            }else{
                insert(node.left,val);
            }
        }else if(node.val<val){
            if(node.right==null){
                node.right = new TreeNode(val);
                return ;
            }else{
                insert(node.right,val);
            }
        }
        return ;
    }
}
```
