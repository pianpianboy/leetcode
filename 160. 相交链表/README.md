# 解题思路

## 我的解题思路
本题有两种思路
- 一种是比较长度lenA、lenB后，比如lenA比lenB长len:len = lenA-lenB，则先让listA先走len步，然后listA和listB同步走，判断是否相等
- 另外一种解法就是将遍历过的节点存入hashMap

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(headA==null || headB==null) return null;

        ListNode tmpA = headA;
        ListNode tmpB = headB;
        int lenA = 1;
        int lenB = 1;

        while(tmpA.next !=null){
            tmpA = tmpA.next;
            lenA++;
        }
        while(tmpB.next !=null){
            tmpB= tmpB.next;
            lenB++;
        }
        if(tmpA!=tmpB) return null;
        tmpA = headA;
        tmpB = headB;
        if(lenA>lenB){
            int len = lenA-lenB;
            while(len!=0){
                tmpA = tmpA.next;
                len--;
            }
        }else{
            int len = lenB-lenA;
            while(len!=0){
                tmpB = tmpB.next;
                len--;
            }
        }

        while(tmpA.next != null){
            if(tmpA == tmpB){
                return tmpA;
            }
            tmpA = tmpA.next;
            tmpB = tmpB.next;
        }

        return tmpA;
    }
}
```
