# 题目
235. 二叉搜索树的最近公共祖先

## 解题思路
此题题目中是二叉搜索树，如果不是二叉搜索树，那这题就难了

```java
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root==null) return null;
        if(p.val>root.val && q.val>root.val){
            return lowestCommonAncestor(root.right,p,q);
        }else if(p.val<root.val && q.val<root.val){
            return lowestCommonAncestor(root.left,p,q);
        }
        else {
            return root;
        }
    }
}
```
