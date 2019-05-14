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
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        if(root==null)return list;
        
        stack1.push(root);
        while(!stack1.isEmpty()){
            root = stack1.pop();
            if(root.left!=null){
                stack1.push(root.left);
            }
            if(root.right!=null){
                stack1.push(root.right);
            }
            stack2.push(root);
        }
        
        while(!stack2.isEmpty()){
            list.add(stack2.pop().val);
        }
        return list;
    }
    
}