# 解题思路

## 我的解题思路
- 使用双指针，但是对于快指针的边界条件需要考虑清楚，考虑两个条件：fast.next!=null&&fast.next.next!=null

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
    public ListNode middleNode(ListNode head) {
        if(head == null||head.next==null) return head;


        ListNode slow = head;
        ListNode fast = head;

        while(fast.next!=null && fast.next.next != null){//注意此处的判断条件，有可能fast.next就不存在了，再求fast.next.next肯定报错
            slow = slow.next;
            fast = fast.next.next;
        }
        if(fast.next != null){
            slow = slow.next;
        }
        return slow;
    }
}
```

