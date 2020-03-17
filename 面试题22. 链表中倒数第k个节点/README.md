### 题目
面试题22. 链表中倒数第k个节点

### 解题思路
- 双指针

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
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode list = head;
        ListNode node = head;
        while(k>0){
            if(list==null)
                return null;
            list = list.next;
            k--;
        }
        while(list!=null){
            node = node.next;
            list = list.next;
        }
        return node;
    }
}
```