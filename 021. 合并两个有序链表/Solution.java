/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode res = head;
        ListNode p1 = l1;
        ListNode p2 = l2;
        while(p1!=null || p2!=null){
            int x1 = p1==null? Integer.MAX_VALUE : p1.val;
            int x2 = p2==null? Integer.MAX_VALUE : p2.val;
            
            if(x1<x2){
                head.next = new ListNode(x1);
                p1 = p1.next;
            }else{
                head.next = new ListNode(x2);
                p2 = p2.next;
            }
            
            head = head.next;
        }
        return res.next;
    }
}