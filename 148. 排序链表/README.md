# 解题思路

## 我的解题思路
因为本题要求的是使用O(n*logn)的时间复杂度，因此可以考虑使用归并递归的解法。
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
    public ListNode sortList(ListNode head) {
        if(head == null||head.next==null)
            return head;
        ListNode slow  = head;
        ListNode fast = head;
        //归并利用了分治的思想：分+治（合并）
        while(fast.next!=null && fast.next.next!=null){//注意判断条件同时处理奇偶的情况
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode right = sortList(slow.next);
        slow.next = null;
        ListNode left = sortList(head);

        ListNode node = merge(left,right);
        return node;
    }

    public ListNode merge(ListNode left,ListNode right){
        ListNode dummy = new ListNode(0);
        ListNode h = dummy;

        while(left!=null&&right!=null){//注意判断是lef还是left.next
            if(left.val<right.val){
                h.next = left;
                left = left.next;
            }else{
                h.next = right;
                right = right.next;
            }
            h = h.next;
        }

        h.next = left==null? right:left;
        return dummy.next;
    }
}
```