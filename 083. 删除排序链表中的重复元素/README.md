# 解题思路

## 我的解题思路
- 使用hashset来解题
- 考虑链表已经排序，只需要将当前值与前面的值比较<q></q>


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
        if(head ==null)return null;
        HashSet<Integer> set = new HashSet<>();

        ListNode tmpNode = head;
        ListNode pre = head;

        while(tmpNode.next!=null){
            if(set.contains(tmpNode.val)){
                pre.next = tmpNode.next;
                tmpNode = tmpNode.next;
            }else{
                pre = tmpNode;
                set.add(tmpNode.val);
                tmpNode = tmpNode.next;
            }
        }
        //增加边界条件的处理，因为前面只考虑了tmpNode.next并没有考虑tmpNode
        if(set.contains(tmpNode.val)){
            pre.next = null;
        }
        return head;
    }
}
```
