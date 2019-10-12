# 解题思路

## 我的解题思路
- 使用深度优先遍历
    + 递归调用，设置临界条件，比如root为空，root的children为空的情况
- 使用广度优先遍历
    + 使用队列queue来实现符合人的思维方式

```java
/*s深度优先遍历
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
    public int maxDepth(Node root) {
        if(root==null) {
            return 0;
        }else if (root.children.isEmpty()) {
            return 1;
        }
        else{
            int size = root.children.size();
            int max = Integer.MIN_VALUE;
            while(size-- > 0){
                max = Math.max(max,maxDepth(root.children.get(size)));
            }
            return max+1;
        }

    }
}
```


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
    public int maxDepth(Node root) {
        if(root==null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        int res = 0;

        while(queue.size()>0){
            res++;
            int size =   queue.size();
            while(size-- >0){
                Node node = queue.poll();
                if(!node.children.isEmpty()){
                    for(Node child :node.children)
                    queue.add(child);
                }
            }
        }
        return res;
    }
}

```
