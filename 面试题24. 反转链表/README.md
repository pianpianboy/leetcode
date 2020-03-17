### 题目
面试题24. 反转链表

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
    public ListNode reverseList(ListNode head) {
        if(head==null||head.next==null) return head;
        ListNode first = head;
        ListNode second = head.next;
        first.next = null;
        while(second!=null){
            ListNode tmp = second.next;
            second.next = first;
            first = second;
            second = tmp;
        }
        return first;
    }
}
```
