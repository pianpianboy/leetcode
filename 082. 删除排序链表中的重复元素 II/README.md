# 解题思路

## 我的解题思路
- 解法1：使用虚拟节点+快慢双指针，因为存在删除头结点的情况，所以使用虚拟机节点
- 解法2：使用栈来保存每个节点，如果遇到相同的节点就出栈

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
    public ListNode deleteDuplicates(ListNode head) {
        if(head==null)return null;
        //使用虚拟机节点
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        //使用快慢指针
        ListNode slow = dummy;
        ListNode fast = slow.next;
        //遍历链表
        while(slow.next !=null){
            //如果出现重复则移动fast
            if(fast.next!=null && fast.next.val == fast.val){
               while(fast!=null && fast.val==slow.next.val){
                   fast = fast.next;
               }
                slow.next = fast;
            }else{//否则移动slow
                slow = fast;
                fast = fast.next;
            }
        }
        //不能用return head 因为head节点有可能第一就被删除了
        return dummy.next;
    }
}
```

