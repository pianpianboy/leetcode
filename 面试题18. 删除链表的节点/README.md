### 题目
面试题18. 删除链表的节点

#### 解题思路

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
    public ListNode deleteNode(ListNode head, int val) {
        ListNode list = head;
        ListNode pre = head;
        if(head.val== val) return head.next;
        while(list!=null){
            if(list.val == val){
                pre.next = list.next;
                return head;
            }else{
                pre = list;
                list = list.next;
            }
        }
        return head;
    }
}
```