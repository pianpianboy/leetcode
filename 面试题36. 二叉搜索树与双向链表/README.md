### 题目
面试题36. 二叉搜索树与双向链表

### 解题思路
- 利用二叉搜索树的中序遍历的结果顺序排列，并且利用指针pre对中序遍历进行改造

```java
class Solution {
    //指针的作用，从最小的值一直移动到最大值，即从树的左node移动到node最右节点
    private Node pre = null;
    private Node head = null;
    private Node tail = null;

    public Node treeToDoublyList(Node root) {
       if(root==null) return null;
       //中序遍历
       inorder(root);
       //连接头尾节点
       head.left = tail;
       tail.right = head;
       return head;
    }

    public void inorder(Node node){
        if(node == null)return;
        inorder(node.left);

        //中序遍历中间处理逻辑
        if(pre ==null){
            head=node;
            pre=node;
            tail = node;
        }else{
            node.left = pre;
            pre.right = node;
            pre = node;
            tail = node;
        }

        inorder(node.right);
    }
}
```