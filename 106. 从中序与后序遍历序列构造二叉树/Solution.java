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
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if(inorder==null||postorder==null||inorder.length==0||postorder.length==0){
            return null;
        }
        
        return help(inorder,0,inorder.length-1,postorder,0,postorder.length-1);
    }
    
    public TreeNode help(int[] inorder,int inStart, int inEnd, int[] postorder, int postStart, int postEnd){
        
        TreeNode  node = new TreeNode(postorder[postEnd]);
        if(postStart == postEnd)return node;
        
        int index = inStart;
        while(index<=inEnd&&inorder[index]!=postorder[postEnd]){
            index++;
        }
        int leftLen = index-inStart;
        int rightLen = inEnd-index;
        
        if(leftLen>0){
            node.left = help(inorder,inStart,inStart+leftLen-1,postorder,postStart,postStart+leftLen-1);
        }
        if(rightLen>0){
            node.right = help(inorder,index+1,inEnd,postorder,postStart+leftLen,postEnd-1);
        }
        return node;
    }
}