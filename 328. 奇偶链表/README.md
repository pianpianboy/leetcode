# 解题思路

## 我的解题思路
因为要求时间复杂度为O(1)，所以只能在链表上进行原地操作，使用双指针，但是需要考虑链表奇偶情况

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
    public ListNode oddEvenList(ListNode head) {
        if(head==null||head.next==null) return head;

        ListNode cur = head;
        ListNode node = cur;
        ListNode tmp = head.next;
        ListNode next = tmp;

        //while(cur!=null&&tmp!=null){
        while(tmp!=null&&tmp.next!=null){//注意判断奇偶情况，用后一个指针判断就可以了。
            cur.next = tmp.next;
            tmp.next = tmp.next.next;

            cur = cur.next;
            tmp = tmp.next;
        }
        cur.next = next;
        return node;
    }
}
```