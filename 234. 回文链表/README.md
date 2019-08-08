# 解题思路

## 我的解题思路
- 解法1：使用快慢指针+栈，快慢指针主要是找到链表的中点（这个时候的难点就在于对于链表长度奇偶情况的讨论）。栈是用来解决回文问题。空间复杂度为O(n)
- 解法2：使用快慢指针+剩余链表反转，最后从两头遍历判断是否为回文。空间复杂度为O(1)

```java
//解法1：
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public boolean isPalindrome(ListNode head) {
        if(head==null||head.next==null) return true;

        Stack<Integer> stack = new Stack<>();
        ListNode slow = head;
        ListNode fast = head;

        //如何处理奇偶的情况
        while(true){
            if(fast.next==null){
                //奇数情况
                slow = slow.next;
                break;
            }
            if(fast.next.next ==null){
                stack.push(slow.val);
                slow = slow.next;
                break;
            }
            stack.push(slow.val);
            slow = slow.next;
            fast = fast.next.next;
        }

        while(slow!=null){
            if(slow.val!=stack.pop())
                return false;
            slow = slow.next;
        }
        return true;
    }
}
```

```java
//解法2：

```