### 题目
面试题37. 序列化二叉树

### 解题实例
```java
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
        if(root==null) return "[]";
        Queue<TreeNode> queue = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        queue.offer(root);

        while(!queue.isEmpty()){
            TreeNode node = queue.poll();

            if(node!=null){
                sb.append(node.val).append(",");
                queue.offer(node.left);
                queue.offer(node.right);
            }else{
                sb.append("null,");
            }
        }
        String str = sb.toString();
        System.out.println(str.substring(0,str.length()-1)+"]");
        return str.substring(0,str.length()-1)+"]";
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if(data==null||data.length()==0||data.equals("[]")) return null;
        String str = data.substring(1,data.length()-1);
        System.out.println(str);
        String[] arr = str.split(",");

        Queue<TreeNode> queue = new LinkedList<>();
        int index = 0;
        TreeNode head = generateTreeNode(arr[index++]);
        queue.offer(head);

        while(!queue.isEmpty()){
            TreeNode node = queue.poll();
            node.left = generateTreeNode(arr[index++]);
            node.right = generateTreeNode(arr[index++]);
            if(node.left!=null){
                queue.offer(node.left);
            }
            if(node.right !=null){
                queue.offer(node.right);
            }
        }
        return head;
    }

    public TreeNode generateTreeNode(String value){
        if("null".equals(value))return null;
        return new TreeNode(Integer.valueOf(value));
    }
}


// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));
```