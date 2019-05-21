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
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if(preorder==null||inorder==null||preorder.length==0||inorder.length==0)return null;
        return build(preorder,inorder,0,preorder.length-1,0,inorder.length-1);
    }
    
    public TreeNode build(int[] preorder,int[] inorder,int preStart,int preEnd,int inStart, int inEnd){
        if(preStart >preEnd || inStart>inEnd )return null;
        TreeNode node = new TreeNode(preorder[preStart]);
    
        int index=0;
        for(int i=inStart;i<=inEnd;i++){
            if(inorder[i]==preorder[preStart]){
                index=i;
            }
                
        }
        node.left=build(preorder,inorder,preStart+1,index,inStart,index-1);
        node.right=build(preorder,inorder,index+1,preEnd,index+1, inEnd);
        return node;
    }
    
}