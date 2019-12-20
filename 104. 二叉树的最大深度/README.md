# 题目
104. 二叉树的最大深度

## 解题思路
- 递归
    + root结点的深度 = Math.max(root左子树的深度，root右子树的深度)+1
- 迭代
    + 使用queue保存每一行的数据

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
    public int maxDepth(TreeNode root) {
        if(root==null) return 0;
        int leftlevel = maxDepth(root.left);
        int rightlevel = maxDepth(root.right);
        return Math.max(leftlevel+1,rightlevel+1);
    }
}
```

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
    public int maxDepth(TreeNode root) {
        if(root==null) return 0;

        Queue<TreeNode> queue = new LinkedList<>();
        int level =0;
        queue.add(root);

        while(!queue.isEmpty()){
            int size = queue.size();
            level++;
            while(size>0){
                TreeNode tmp = queue.poll();
                if(tmp.left!=null) queue.add(tmp.left);
                if(tmp.right!=null) queue.add(tmp.right);
                size--;

            }
        }
        return level;
    }
}
```































