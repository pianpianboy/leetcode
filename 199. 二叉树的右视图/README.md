# 解题思路

## 我的解题思路
- 广度优先遍历BFS，然后将每一层的值从到右赋值给res,最后res就是最右的值。

```java
//BFS
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
    public List<Integer> rightSideView(TreeNode root) {
        //BFS 广度优先遍历
        List<Integer> list = new ArrayList<>();
        int res = 0;
        if(root==null) return list;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while(queue.size()>0){
            int size = queue.size();

            while(size-- >0){
                TreeNode node = queue.poll();
                res = node.val;
                if(node.left!=null) queue.add(node.left);
                if(node.right!=null) queue.add(node.right);
            }
            list.add(res);
        }
        return list;
    }
}
```
