### 题目
面试题25. 合并两个排序的链表

### 解题思路
- 双指针

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
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if(l1==null) return l2;
        if(l2==null) return l1;
        ListNode list = new ListNode(0);
        ListNode res = list;
        while(l1!=null&& l2!=null){
            if(l1.val<=l2.val){
                list.next = new ListNode(l1.val);
                l1 = l1.next;
            }else{
                list.next = new ListNode(l2.val);
                l2 = l2.next;
            }
            list = list.next;
        }
        while(l1!=null){
            list.next = new ListNode(l1.val);
            l1= l1.next;
            list = list.next;
        }
        while(l2!=null){
            list.next = new ListNode(l2.val);
            l2= l2.next;
            list = list.next;
        }
        return res.next;
    }
}
```

```java
//改进版本

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if(l1==null) return l2;
        if(l2==null) return l1;
        ListNode list = new ListNode(0);
        ListNode res = list;
        while(l1!=null&& l2!=null){
            if(l1.val<=l2.val){
                list.next = new ListNode(l1.val);
                l1 = l1.next;
            }else{
                list.next = new ListNode(l2.val);
                l2 = l2.next;
            }
            list = list.next;
        }
        list.next= l1==null ? l2 :l1;
        return res.next;
    }
}
```
