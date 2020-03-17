### 题目
面试题32 - I. 从上到下打印二叉树

### 解题思路
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
    public int[] levelOrder(TreeNode root) {
        if(root==null) return new int[0];

        List<Integer> tmp = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while(!queue.isEmpty()){
            int num = queue.size();
            while(num>0) {
                TreeNode node = queue.poll();
                if(node.left!=null){
                    queue.add(node.left);
                }
                if(node.right!=null){
                    queue.add(node.right);
                }
                tmp.add(node.val);
                num--;
            }
        }
        int[] arr = new int[tmp.size()];
        for(int i=0;i<tmp.size();i++){
            arr[i] = tmp.get(i);
        }
        return arr;
    }
}
```