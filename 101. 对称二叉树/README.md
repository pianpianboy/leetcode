## 题目
101. 对称二叉树

### 解题思路
这题虽然是简单的标签，但是刚开始想的思路却是错的：任务二叉树的中序遍历左->中->右遍历的结果和 右->中->左 相等就是对称二叉树，但是在[1,2,2,2,null,2]的情况下没法AC

- 递归
- 迭代

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

    public boolean isSymmetric(TreeNode root) {
        return help(root,root);
    }

    public boolean help(TreeNode node1,TreeNode node2){
        if(node1==null&& node2==null){
            return true;
        }else if(node1 == null||node2 == null){
            return false;
        }

        if(node1.val==node2.val){
            return help(node1.left, node2.right)&&help(node1.right,node2.left);
        }
        return false;
    }

}
```

```java
public boolean isSymmetric(TreeNode root) {
    Queue<TreeNode> q = new LinkedList<>();
    q.add(root);
    q.add(root);
    while (!q.isEmpty()) {
        TreeNode t1 = q.poll();
        TreeNode t2 = q.poll();
        if (t1 == null && t2 == null) continue;
        if (t1 == null || t2 == null) return false;
        if (t1.val != t2.val) return false;
        q.add(t1.left);
        q.add(t2.right);
        q.add(t1.right);
        q.add(t2.left);
    }
    return true;
}


```
