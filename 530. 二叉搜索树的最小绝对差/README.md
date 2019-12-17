# 题目
530. 二叉搜索树的最小绝对差

## 解题思路
- 使用二叉搜索树的性质：二叉搜索树的中序遍历是单调递增的
    + 先中序遍历二叉搜索树生成单调递增的list
    + 再遍历list求最小差值（最小差值肯定在相邻两数之间）
- 优化后的算法
    + 在中序遍历的时候计算最小差值

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
    public int getMinimumDifference(TreeNode root) {
        //中序遍历
        if(root == null) return 0;
        List<Integer> list =  new ArrayList<>();
        int pre = Integer.MAX_VALUE;
        int min = Integer.MAX_VALUE;
        help(list,root);
        for(int val:list){
            min = Math.min(min, Math.abs(pre-val));
            pre = val;
        }
        return min;
    }

    public void help(List<Integer>list, TreeNode node){
        if(node==null) return;
        help(list, node.left);
        list.add(node.val);
        help(list, node.right);
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
    TreeNode pre = null;
    int min = Integer.MAX_VALUE;
    public int getMinimumDifference(TreeNode root) {
        //中序遍历
        if(root == null) return 0;

        inorder(root);
        return min;
    }

    public void inorder(TreeNode node){
        if(node==null) return ;
        inorder(node.left);
        if(pre!=null){
            min = Math.min(min, Math.abs(pre.val-node.val));
        }
        pre = node;
        inorder(node.right);
    }
}
```
