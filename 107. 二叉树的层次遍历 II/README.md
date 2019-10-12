# 解题思路

## 我的解题思路
- 使用广度优先遍历来实现按层遍历，需要额外queue

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
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        //使用广度优先遍历算法，实现按层遍历，bfs需要使用额外queue
        List<List<Integer>> res = new LinkedList<>();

        Queue<TreeNode> queue = new LinkedList<>();
        if(root==null) return res;

        queue.add(root);
        while(queue.size()>0){
            List<Integer> list = new ArrayList<>();
            int size = queue.size();

            while(size-- >0){
                TreeNode tmp = queue.poll();
                list.add(tmp.val);
                if(tmp.left!=null)
                    queue.add(tmp.left);
                if(tmp.right!=null)
                    queue.add(tmp.right);
            }
            res.add(0,list);
        }
        return res;
    }
}
```
