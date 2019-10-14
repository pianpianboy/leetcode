# 解题思路

## 我的解题思路
- 使用bfs
- 使用dfs

```java
//bfs
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
    public int findBottomLeftValue(TreeNode root) {
        int res =0;

        if(root == null) return res;
        Queue<TreeNode> queue = new LinkedList<>();

        queue.add(root);

        while(queue.size()>0){
            int size = queue.size();
            while(size-- >0){
                TreeNode node = queue.poll();
                res = node.val;
                if(node.right !=null)
                    queue.add(node.right);
                if(node.left !=null)
                    queue.add(node.left);
            }
        }
        return res;
    }
}
```


```java
//dfs
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
    int maxDepth = -1;
    int res = -1;

    public int findBottomLeftValue(TreeNode root) {
        help(root,0);
        return res;
    }

    public void help(TreeNode node ,int depth){
        if(node ==null) return;
        //中序遍历
        help(node.left, depth+1);
        if(depth>maxDepth){
            maxDepth=depth;
            res = node.val;
        }
        help(node.right, depth+1);
    }
}

```
