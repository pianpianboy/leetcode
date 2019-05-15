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
        
        int max = Integer.MAX_VALUE;
        int min = Integer.MIN_VALUE;
        return help(root,min, max);
    }
    
    public boolean help(TreeNode root, int min, int max){
        if(root==null) return true;
        // if(root.val>min && root.val<max)
        //     return help(root.left,min,root.val) && help(root.right,root.val,max);
        // else
        //     return false;
        if(root.val<=min || root.val>=max)return false;
        return help(root.left,min,root.val) && help(root.right,root.val,max);
       
    }
}

-----------------------------------------------------------------------------------
分界线：上面的就是不能被Accept;下面的就能被Accept


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
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    public boolean isValidBST(TreeNode root, long minVal, long maxVal) {
        if (root == null) return true;
        if (root.val >= maxVal || root.val <= minVal) return false;
        return isValidBST(root.left, minVal, root.val) && isValidBST(root.right, root.val, maxVal);
    }
}











