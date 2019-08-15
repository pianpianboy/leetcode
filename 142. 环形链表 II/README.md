# 解题思路

## 我的解题思路
- 这题与"141. 环形链表"的解题思路一致，快慢指针走过的路径分别为2*（x+y）、x+y+z+y
- 则2*（x+y）= x+y+z+y  得出x=z,因此再slow=head，然后快指针和慢指针都每次只走一步，直到相遇就是入环节点

```java
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode detectCycle(ListNode head) {
        if(head==null||head.next==null)return null;

        ListNode slow = head;
        ListNode fast = head;

        while(fast!=null&&fast.next!=null){
            fast = fast.next.next;
            slow = slow.next;
            if(fast==slow) break;
        }
        if(fast!=slow)
            return null;
        else{
            slow = head;
            while(slow!=fast){
                fast = fast.next;
                slow = slow.next;
            }
            return slow;
        }
    }
}
```