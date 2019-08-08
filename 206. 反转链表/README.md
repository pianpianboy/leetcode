# 解题思路

## 我的解题思路
题目要求分别使用递归和迭代来实现，无论是递归和迭代都需要使用三个指针来解决，head指针（迭代head每次都指向最新的头结点，递归中，每次指向需要处理并即将称为头结点的点）、last指针（递归和迭代都是指向每次操作后新生成的最后一个节点），cur指针（在迭代中就是本次需要处理的节点，这个节点也就是将要成为头结点的点）
比如1->2->3->4->5->null,经过第一次迭代之后变为2->1->3->4->5->null,经过第三次迭代之后变为3->2->1->4->5->null

```java
//解法1：迭代
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode reverseList(ListNode head) {
        if(head==null||head.next==null){
            return head;
        }
        ListNode last = head;
        ListNode cur = head.next;

        //其中last为链表实时的最后一个节点
        //其中cur为需要处理的下一个节点
        while(cur != null){
            //重新生成两跳next指针
            last.next = cur.next;
            cur.next = head;

            //再次移动head及cur指针
            head = cur;
            cur = last.next;
        }
        return head;
    }
}
```


```java
//解法2：递归
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode reverseList(ListNode head) {
        if(head==null||head.next==null){
            return head;
        }
        ListNode last = head;
        ListNode cur = head.next;

        return reverse(head,last,cur);
    }

    public ListNode reverse(ListNode head,ListNode last, ListNode cur){
        if(cur == null){
            return head;
        }
        //先进行next指针的变更
        last.next = cur.next;
        cur.next = head;

        //再进行head即cur的移动
        head = cur;
        cur = last.next;

        return reverse(head,last,cur);
    }
}

```