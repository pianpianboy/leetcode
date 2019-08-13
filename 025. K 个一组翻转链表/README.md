# 解题思路

## 我的解题思路
给链表增加一个虚拟节点,使用四个指针 pre,start,end,next，以k为单位遍历链表；

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
    public ListNode reverseKGroup(ListNode head, int k) {
        if(head == null||head.next==null)return head;

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode pre = dummy;
        ListNode start = head;
        ListNode end = head;
        ListNode next = head;

        while(next!=null){
            int count =0;
            start = pre.next;
            end = pre;
            for(int i=0;i<k;i++){
                end = end.next;
                count++;
                if(end==null)break;
            }
            if(count<k)break;
            if(end==null)break;//for循环break后在while循环中依然可能为空
            next = end.next;

            end.next =null;
            pre.next = reverse(start);//连接反转后的头到pre
            start.next = next;//连接反转后的尾部到next;
            pre = start;
        }
        return dummy.next;
    }

    //翻转
    public ListNode reverse(ListNode head){
        ListNode tmpHead = head;
        ListNode last = head;
        ListNode cur = head.next;

        while(cur!=null){
            last.next = cur.next;
            cur.next = tmpHead;
            tmpHead = cur;
            cur = last.next;
        }
        return tmpHead;
    }


}
```
