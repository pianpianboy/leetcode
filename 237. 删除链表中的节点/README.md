# 解题思路

## 我的解题思路
这题就是一个智力题，题目就没看明白，为啥只传入要删除的节点，不传入链表的头结点。
- 先将node解决复制为node的后一个节点
- 再将node的后一个节点删除

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public void deleteNode(ListNode node) {

        //脑筋急转弯的题目，删除node考虑删除node后面的节点
        node.val = node.next.val;

        node.next = node.next.next;
    }
}
```
