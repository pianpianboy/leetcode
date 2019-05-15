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
    public boolean isValidBST(TreeNode root) {
        if(root==null)return true;
        
        Stack<TreeNode> stack = new Stack<>();
        TreeNode pre  = null;
        
        while(!stack.isEmpty()||root!=null){
            if(root!=null){
                stack.push(root);
                root = root.left;
            }else{
                root=stack.pop();
                if(pre!=null&&pre.val>=root.val)
                    return false;
                else
                    pre = root;
                root = root.right;
            }
        }
        return true;
    }
}