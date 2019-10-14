# 解题思路

## 我的解题思路
- 关于二叉树的解法：树形DP、深度优先遍历dfs、广度优先遍历bfs

```java
/** 使用BFS
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public int minDepth(TreeNode root) {
        if(root==null) return 0;

        int res = 0;
        Queue<TreeNode> queue  = new LinkedList<>();
        queue.add(root);
        int flag =0;
        while(queue.size()>0){
            res++;
            int size = queue.size();

            while(size-- >0){
                TreeNode node = queue.poll();
                if(node.left==null&& node.right==null){
                    flag = 1;
                    break;
                }

                if(node.left !=null)
                    queue.add(node.left);
                if(node.right != null)
                    queue.add(node.right);
            }
            if(flag==1) break;
        }
        return res;
    }
}

```

```java
//DFS
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
    public int minDepth(TreeNode root) {
        if(root ==null) return 0;
        if(root.left==null && root.right==null)
           return 1;
        if(root.left==null)
            return minDepth(root.right)+1;
        if(root.right==null)
            return minDepth(root.left)+1;
        return Math.min(minDepth(root.left),minDepth(root.right))+1;
    }
}

```


