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
        //使用前序遍历序列化
        if(root==null)return "#!";
        String res = root.val+"!";
        res += serialize(root.left);
        res += serialize(root.right);
        return res;
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        //使用前序遍历反序列化
        if(data==null)return null;
        Queue<String>queue = new LinkedList<>();
        String[] arr = data.split("!");
        for(int i=0; i<arr.length;i++){
            queue.offer(arr[i]);
        }
        return help(queue);
     }
    public TreeNode help(Queue<String>queue){
        //if(queue.size()==0)return null;
        String value = queue.poll();
        //if(value == "#")return null;
        if(value.equals("#"))return null;
        TreeNode node = new TreeNode(Integer.valueOf(value));
        node.left = help(queue);
        node.right = help(queue);
        return node;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));