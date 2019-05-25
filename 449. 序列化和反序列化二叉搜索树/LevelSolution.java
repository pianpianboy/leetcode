/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if(root==null)return "#!";
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        String res = root.val+"!";
        
        while(!queue.isEmpty()){
            int size = queue.size();
            while(size>0){
                size--;
                root = queue.poll();
                if(root.left!=null){
                    queue.offer(root.left);
                    res += root.left.val+"!";
                }else{
                    res += "#!";
                }
                if(root.right!=null){
                    queue.offer(root.right);
                    res += root.right.val+"!";
                }else{
                    res+="#!";
                }
                
                
            }
        }
        return res;
    }
    
    

    // Decodes your encoded data to tree.
//     public TreeNode deserialize(String data) {
//         if(data==null||data.length()==0)return null;
//         String[] arr = data.split("!");
//         return help(arr,0); 
//     }
    
//     public TreeNode help(String[] arr, int index){
//         if(2*index+2>=arr.length)return null;
//         if(arr[index].equals("#")){
//             return null;
//         }
//             TreeNode node = new TreeNode(Integer.valueOf(arr[index]));
//             node.left = help(arr, 2*index+1);
//             node.right = help(arr, 2*index+2);
//        return node;
//     }
    
    
 //参考   
//      public TreeNode deserialize(String data) {
//        String[] values = data.split("!");
// 		int index = 0;
// 		TreeNode head = generateNodeByString(values[index++]);
// 		Queue<TreeNode> queue = new LinkedList<TreeNode>();
// 		if (head != null) {
// 			queue.offer(head);
// 		}
// 		TreeNode node = null;
// 		while (!queue.isEmpty()) {
// 			node = queue.poll();
// 			node.left = generateNodeByString(values[index++]);
// 			node.right = generateNodeByString(values[index++]);
// 			if (node.left != null) {
// 				queue.offer(node.left);
// 			}
// 			if (node.right != null) {
// 				queue.offer(node.right);
// 			}
// 		}
// 		return head;
//     }
    
//     public static TreeNode generateNodeByString(String val) {
// 		if (val.equals("#")) {
// 			return null;
// 		}
// 		return new TreeNode(Integer.valueOf(val));
// 	}
    
    public TreeNode deserialize(String str) {
        if(str==null||str.length()==0){
            return null;
        }
        String[] arr = str.split("!");
        int index=0;
        //辅助队列
        Queue<TreeNode> queue = new LinkedList<>();
        if(arr[index].equals("#")){
            return null;
        }
        TreeNode node = new TreeNode(Integer.valueOf(arr[index++]));
        TreeNode head = node;
        queue.offer(head);
        
        while(!queue.isEmpty()){
                node = queue.poll();
                if(arr[index].equals("#")){
                    node.left = null;
                    index++;
                }else{
                    node.left = new TreeNode(Integer.valueOf(arr[index++]));
                }
                if(arr[index].equals("#")){
                    node.right = null;
                    index++;
                }else{
                    node.right = new TreeNode(Integer.valueOf(arr[index++]));
                }
                if(node.left!=null)queue.offer(node.left);
                if(node.right!=null) queue.offer(node.right);
            
        }
        return head;
    }
    
    
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));