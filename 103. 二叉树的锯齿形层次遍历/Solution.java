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
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        if(root==null)return list;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean flag = true;
        
        while(!queue.isEmpty()){
            int size = queue.size();
            List<Integer> tmp = new ArrayList<>();
            while(size>0){
                root = queue.poll();
                if(root.left !=null){
                    queue.offer(root.left);
                }
                if(root.right!=null){
                    queue.offer(root.right);
                }
                tmp.add(root.val);
                size--;
            }
            if(!flag)
                Collections.reverse(tmp);
            flag=!flag;
            list.add(tmp);
        }
        return list;
    }
}