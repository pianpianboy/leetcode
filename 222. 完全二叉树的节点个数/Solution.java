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
    public int countNodes(TreeNode root) {
            if(root==null)return 0;
        int depth = mostLeftLevel(root,1);
        return get(root, 1,depth);
    }
    
    public int mostLeftLevel(TreeNode node,int level){
        while(node!=null){
            level++;
            node = node.left;
        }
        return level-1;
    }
    
    public int get(TreeNode node, int level, int depth){
        if(level == depth)return 1;
        int res = 0;
        int tmp = mostLeftLevel(node.right, level+1);
        if(tmp==depth){
            res = (1<<(depth-level-1+1))+get(node.right,level+1,depth) ;
        }else{
            res = (1<<(depth-level-1-1+1))+get(node.left,level+1,depth);
        }
        return res;  
    }
}