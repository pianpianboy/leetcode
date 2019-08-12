# 解题思路

## 我的解题思路
本题设计到局部反转，比206链表反转要难在进行局部反转。比如要反转m=2到n=4之间的链表sublist

- 先设置一个头结点，来确保一些情况能够满足
- 由于只能遍历一次，所以需要一个count位来指示从哪一位开始翻转，哪一位停止翻转，并且要连成一个完整的链表。
- 此时需要把list链表在1->2之间断开，4->5之间断开，线程list1、sublist、list2三段链表。
- 然后再将sublist进行反转，然后list1.next = sublist.head  ; sublist.tail = list2

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
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if(head==null||head.next==null)return head;

        if(n<m)return head;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        int count = 1;
        ListNode pre = dummy;

        while(count<m){
            pre = pre.next;
            count++;
        }

        ListNode tmpHead = pre.next;
        ListNode last = pre.next;
        ListNode cur = pre.next.next;

        while(count<n&&cur!=null){
            last.next = cur.next;
            cur.next = tmpHead;
            tmpHead = cur;
            cur = last.next;
            count++;
        }
        pre.next = tmpHead;
        return dummy.next;
    }
}
```

