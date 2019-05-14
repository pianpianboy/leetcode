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
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        help(root,list);
        return list;
    }
    
    public void help(TreeNode node, List<Integer> list){
        if(node == null) return ;
        help(node.left, list);
        list.add(node.val);
        help(node.right,list);
    }
}