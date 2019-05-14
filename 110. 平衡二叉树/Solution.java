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
    public boolean isBalanced(TreeNode root) {
        return help(root).flag;
    }
    
    public Result help(TreeNode node){
        if(node == null) return new Result(true,0);
        
        Result resLeft = help(node.left);
        if(!resLeft.flag){
            return new Result(false,0);
        }
        Result resRight = help(node.right);
        if(!resRight.flag){
            return new Result(false,0);
        }
        if(Math.abs(resLeft.h-resRight.h)>1){
            return new Result(false,0);
        }
        
        return new Result(true,Math.max(resLeft.h,resRight.h)+1);
        
    }
}
public class Result{
    public boolean flag;
    public int h;
    public Result(boolean flag,int h){
        this.h = h;
        this.flag = flag;
    }
}