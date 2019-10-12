# 解题思路

## 我的解题思路
- 使用广度优先遍历，实现按层遍历

```java
/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val,List<Node> _children) {
        val = _val;
        children = _children;
    }
};
*/
class Solution {
    public List<List<Integer>> levelOrder(Node root) {
        //使用广度优先遍历算法，实现按层遍历，bfs需要使用额外queue
        List<List<Integer>> res = new LinkedList<>();

        Queue<Node> queue = new LinkedList<>();
        if(root==null) return res;

        queue.add(root);
        while(queue.size()>0){
            List<Integer> list = new ArrayList<>();
            int size = queue.size();

            while(size-- >0){
                Node tmp = queue.poll();
                list.add(tmp.val);
                if(!tmp.children.isEmpty()){
                    for(Node child:tmp.children){
                        queue.add(child);
                    }
                }
            }
            res.add(list);
        }
        return res;
    }
}

```
