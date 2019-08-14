# 解题思路

## 我的解题思路
- 解法1：因为是先做的 "25. K 个一组翻转链表""这道题，所以将这道题目的解法拿来改改就可以了。有点大材小用了
- 解法2：非递归的简单解法，增加虚拟节点后，遍历链表



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


```java
//非递归的简单解法
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode swapPairs(ListNode head) {
        if(head==null||head.next == null)
            return head;

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = dummy;

        while(pre.next!=null && pre.next.next!=null){
            ListNode tmp = pre.next;
            pre.next = tmp.next;
            tmp.next = pre.next.next;
            pre.next.next = tmp;
            pre = tmp;
        }
        return dummy.next;
    }
}
```