### 题目
面试题35. 复杂链表的复制

### 解题思路
```java
class Solution {
    public Node copyRandomList(Node head) {
      if(head==null) return null;
      Node node = head;
      HashMap<Node,Node> map = new HashMap<>();

      while(node != null){
          Node newNode = new Node(node.val);
          map.put(node,newNode);
          node = node.next;
      }

      node = head;
      while(node!=null){
          Node curNode  = map.get(node);
          if(node.next!=null) {
              curNode.next = map.get(node.next);
          }else
              curNode.next = null;

          if(node.random!=null){
              curNode.random = map.get(node.random);
          }else{
              curNode.random = null;
          }
          node = node.next;
      }
      return map.get(head);
    }
}
```