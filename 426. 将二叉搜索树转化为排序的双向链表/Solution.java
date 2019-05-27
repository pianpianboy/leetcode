426. 将二叉搜索树转化为排序的双向链表

// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;

    public Node() {}

    public Node(int _val,Node _left,Node _right) {
        val = _val;
        left = _left;
        right = _right;
    }
};
*/
class Solution {
    public Node treeToDoublyList(Node root) {
       //先进行中序遍历
        if(root==null) return root;
        List<Node> list = new ArrayList<Node>();
        Stack<Node> stack = new Stack<>();
        
        while(!stack.isEmpty()||root!=null){
            if(root!=null){
                stack.push(root);
                root = root.left;
            }
            else{
                root = stack.pop();
                list.add(root);
                root = root.right;
            }
        }
        //遍历链表生成前驱及后继节点
        for(int i = 1;i<list.size();i++){
            Node node = list.get(i);
            Node pre = list.get(i-1);
            node.left = pre;
            pre.right = node;
            
        }
        list.get(0).left = list.get(list.size()-1);
        list.get(list.size()-1).right = list.get(0);
        return list.get(0);
    }
}'