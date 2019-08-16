# 解题思路

##我的解题思路
链表中在原表进行操作太麻烦，而且需要保证原链表中的顺序性，因此再额外新建两个链表，链表1放比x小的数，链表2放比x大的数，好比发货（荷兰国旗问题）

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
    public ListNode partition(ListNode head, int x) {
        if(head==null) return head;
        ListNode dummy1 = new ListNode(0);
        ListNode node1 = dummy1;
        ListNode dummy2 = new ListNode(0);
        ListNode node2 = dummy2;

        ListNode cur = head;

        while(cur!=null){
            if(cur.val>=x){
                node2.next = new ListNode(cur.val);
                node2 = node2.next;
            }else{
                node1.next = new ListNode(cur.val);
                node1 = node1.next;
            }

            cur = cur.next;
        }

        node1.next = dummy2.next;
        return dummy1.next;
    }
}
```