# 解题思路

## 我的解题思路
- 使用快慢指针，起点0->线程环的位置(距离x)->相遇点的位置(距离为y)->到形成环的位置(距离为z)
- 慢指针走过的路径为x+y,快指针走过的路径为x+y+z+y。因为是慢指针没走一步快指针走两步，所以2*(x+y) = x+y+z+y 得出x=z；
- 因此当快慢指针相遇的时候，然后慢指针回到起点，然后慢指针和快指针每次都只走一步，最后相遇的地点就是环的入点，慢指针走的步数就是所求值。
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
    public boolean hasCycle(ListNode head) {
        if(head == null||head.next==null) return false;
        ListNode slow = head;
        ListNode fast = head;
        //while(fast.next.next!=null){//少考虑了一种情况，比如1->2->3->4,即快指针比下面的少移动了一次
        while(fast!=null && fast.next!=null){
            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast) return true;
        }
        return false;
    }
}
```