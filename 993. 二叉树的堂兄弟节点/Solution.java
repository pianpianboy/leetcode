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
    public boolean isCousins(TreeNode root, int x, int y) {
        if(root==null)return false;
        Queue<TreeNode> queue  = new LinkedList<>();
        queue.offer(root);
        int level = 0;
        TreeNode xFatherNode = null;
        TreeNode yFatherNode = null;
        int xLevel = 0;
        int yLevel = 0;
        
        while(!queue.isEmpty()){
            int size = queue.size();
            level++;
            while(size>0){
                size--;
                root = queue.poll();
                if(root.left != null){
                    queue.offer(root.left);
                    if(x==root.left.val){
                        xFatherNode = root;
                        xLevel = level;
                    }
                    if(y==root.left.val){
                        yFatherNode = root;
                        yLevel = level;
                    }
                }
                if(root.right != null){
                    queue.offer(root.right);
                    if(x==root.right.val){
                        xFatherNode = root;
                        xLevel = level;
                    }
                    if(y==root.right.val){
                        yFatherNode = root;
                        yLevel = level;
                    }
                }
            }
        }
        return (xFatherNode!=yFatherNode)&&(xLevel==yLevel);
    }
}