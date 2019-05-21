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
        // if(preStart >preEnd || inStart>inEnd )return null;
        TreeNode node = new TreeNode(preorder[preStart]);
        //前序序列只有根节点
        if(preStart==preEnd)return node;
    
        int index = inStart;
        // while(index<=inEnd){
        //     if(inorder[index]!= preorder[preStart])
        //         index++;
        // }
        //遍历中序序列，找到跟节点的位置。注意上面的写法很容易超时
         while(index<=inEnd&&inorder[index]!= preorder[preStart]){
                index++;
        }
        //左子树和右子树的长度
        int leftLen = index-inStart;
        int rightLen = inEnd-index;
        
        //左子树非空
        if(leftLen>0){
            //重建左子树
            node.left = build(preorder,inorder,preStart+1,preStart+leftLen,inStart, index-1);
        }
        //右子树非空
        if(rightLen>0){
            //重建右子树
            node.right = build(preorder,inorder,preStart+leftLen+1,preEnd,index+1,inEnd);
        }
        return node;
    }
    
}