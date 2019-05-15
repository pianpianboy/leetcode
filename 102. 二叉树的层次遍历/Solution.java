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
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        if(root==null){
            return list;
        }
        queue.offer(root);
        
        while(queue.size()!=0){
            List<Integer> tmp = new ArrayList<>();
            int size  = queue.size();
            while(size>0){
                root = queue.poll();
                tmp.add(root.val);
                if(root.left!=null){
                    queue.offer(root.left);
                }
                if(root.right!=null){
                    queue.offer(root.right);
                }
                size--;
            }
            list.add(tmp);
        }        
        return list;
    }
}