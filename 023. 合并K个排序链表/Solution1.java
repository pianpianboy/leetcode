/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        if(lists==null||lists.length==0)return null;
        //采用逐一合并的方法
        ListNode result = null;
        for(int i=0;i<lists.length;i++){
            result = mergeTwoList(result,lists[i]);
        }
        return result;
        
    }
    public ListNode mergeTwoList(ListNode l1,ListNode l2){
        if(l1==null){
            return l2;
        }else if(l2==null){
            return l1;
        }else if(l1.val<l2.val){
            l1.next = mergeTwoList(l1.next,l2);
            return l1;
        }else {
            l2.next = mergeTwoList(l1,l2.next);
            return l2;
        }
        
    }
}