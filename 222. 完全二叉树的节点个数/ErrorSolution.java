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
        int depth = getDeep(root);
        return get(root, depth);
    }
    public int get(TreeNode node, int depth){
        if(node==null)return 0;
        if(depth==1)return 1;
        int res = 0;
        int size = getDeep(node.right);
        if(size==depth){
           res = res+(1<<depth-1) + get(node.right,depth-1); 
        }else{
           res = res+(1<<(depth-1-1))+get(node.left,depth-1); 
        }
        return res;
    }
    
    public int getDeep(TreeNode node){
        if(node==null) return 0;
        int depth =0;
        while(node!=null){
            node = node.left;
            depth++;
        }
        return depth;
    }
}