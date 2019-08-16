# 解题思路

## 我的解题思路
- 本题要求重排链表，而且要求不能单纯的改变节点的值。(最开始的思路是：找到中点，用stack存储后面的节点，然后重新遍历，插入节点，奇数个节点时，最后一个节点要判断一下。)但是代码量太复杂，放弃，后面的思路跟简单清晰
- 可以考虑先找到中点（找中点要针对此题做个改变，有个小技巧增加虚拟节点）
- 然后从中点（无论链表个数时奇数还是偶数）断开，
- 反转后半部分链表
- 同时遍历两个链表并合并

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
    public void reorderList(ListNode head) {
        if(head==null||head.next==null||head.next.next==null) return ;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode fast = dummy;
        ListNode slow = dummy;
        //找到中点
        /**
        *比如：
        * 1->2->3-4->null奇数个的时候找到3
        * 1->2->3->4->5->null 偶数个的时候找到3 因此我的反转逻辑得修改下，传入得head前需要增加一个虚拟节点dummy
        **/
        while(fast!=null&&fast.next!=null){
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode cur1 = slow.next;
        slow.next =null;
        ListNode cur2 = reverse(cur1);//反转后链表的头结点

        cur1 = head;

        //拼接链表
        while(cur1!=null&&cur2!=null){
            ListNode node1 = cur1.next;
            cur1.next = cur2;
            ListNode node2 = cur2.next;
            cur2.next = node1;
            cur1 = node1;
            cur2 = node2;
        }
    }
    public ListNode reverse(ListNode head){
        ListNode last = head;
        ListNode cur = head.next;
        while(cur!=null){//要注意此时的判断条件，当cur为最后一个节点的时候也是需要和head进行交换的，即当cur=null的时候反转结束
            last.next = cur.next;
            cur.next = head;
            head = cur;
            cur = last.next;
        }
        return head;
    }
}
```


