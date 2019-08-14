# 解题思路

## 我的解题思路
- 解法1：使用额外的空间，遍历原链表的同时使用Map保存map.put(原链表节点,新链表节点)
- 解法2：使用不使用额外的空间，在原链表上进行操作。


```java
//解法一：
/*
// Definition for a Node.
class Node {
    public int val;
    public Node next;
    public Node random;

    public Node() {}

    public Node(int _val,Node _next,Node _random) {
        val = _val;
        next = _next;
        random = _random;
    }
};
*/
class Solution {
    public Node copyRandomList(Node head) {
      if(head==null)return head;
        HashMap<Node,Node> map = new HashMap<>();
        Node dummy = new Node(0,null,null);
        Node cur = head;
        Node ncur = dummy;

        //生成链表并将原链表节点和新链表节点的对应关系保存到map中
        while(cur!=null){
            Node tmp = new Node(cur.val,null,null);
            map.put(cur,tmp);
            ncur.next= tmp;
            ncur = ncur.next;
            cur = cur.next;
        }
        ncur.next = null;
        ncur = dummy.next;
        cur = head;
        while(cur!=null){
            if(cur.random!=null){
                ncur.random = map.get(cur.random);
            }
            cur = cur.next;
            ncur= ncur.next;
        }
        return dummy.next;
    }
}
```


```java
//解法2：
/*
// Definition for a Node.
class Node {
    public int val;
    public Node next;
    public Node random;

    public Node() {}

    public Node(int _val,Node _next,Node _random) {
        val = _val;
        next = _next;
        random = _random;
    }
};
*/
class Solution {
    public Node copyRandomList(Node head) {
        if(head==null)return head;

        Node cur = head;

        //在原链表中增加复制节点
        while(cur!=null){
            Node tmp = new Node(cur.val,null,null);
            Node next = cur.next;
            cur.next = tmp;
            tmp.next = next;
            cur = next;
        }

        cur = head;
        //在原链表的基础上增加随机节点
        while(cur!=null){
            Node tmp = cur.random;
            if(tmp!=null)
                cur.next.random = tmp.next;
            else
                cur.next.random = null;
            cur = cur.next.next;
        }

        //拆分链表
        cur = head;
        Node dummy = new Node(0,null,null);
        Node ncur = dummy;
        while(cur!=null){
            Node tmp = cur.next;
            cur.next = tmp.next;
            ncur.next = tmp;
            ncur = ncur.next;
            cur = cur.next;
        }
        ncur.next = null;

        return dummy.next;
    }
}
```