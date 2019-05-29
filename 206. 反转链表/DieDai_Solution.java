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
        ListNode cur  = head;
        ListNode pre = null;
        ListNode tmpNode = null;
        while(cur!=null){
            tmpNode = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmpNode;
        }
        return pre;
    }
}