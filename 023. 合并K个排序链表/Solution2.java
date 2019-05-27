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
        return help(lists,0,lists.length-1);
        
    }
    
    public ListNode help(ListNode[] lists, int start, int end){
        if(start>=end)return lists[start];//结束递归
        else{
            int mid = (start+end)/2;
            ListNode leftList = help(lists,start, mid);
            ListNode rightList = help(lists,mid+1,end);
            return mergeTwoList(leftList,rightList);
        }
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