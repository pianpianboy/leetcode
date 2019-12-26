# 题目
590. N叉树的后序遍历

## 解题思路
- 递归
- 迭代
    + 借鉴二叉后续遍历迭代实现技巧：后序遍历 左右中 == 中左右的倒序，即先序遍历的倒序

```java
/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
};
*/
class Solution {
    List<Integer> res = new ArrayList<>();
    public List<Integer> postorder(Node root) {
        if(root==null) return res;

        help(root);
        return res;
    }
    public void help(Node root){
        List<Node> list = root.children;
        for(Node node:list){
            if(node!=null){
                postorder(node);
            }
        }
        res.add(root.val);
    }
}
```

```java

```

