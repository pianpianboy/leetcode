# 解题思路

## 我的解题思路
- 解法一：最先想到的就是一种数据结构能从尾部出然后从头部入，第一想法就是使用双端队列，但是需要考虑两种情况，k比链表的长度短，k比链表的长度长。
- 解法二：将链表首尾相连形成一个环，然后在n-k这个节点和 n-k+1节点处断开，n-k节点形成新链表的尾，n-k+1形成新链表的头

```java
//为考虑k比链表的长度长的情况，导致超时

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode rotateRight(ListNode head, int k) {
        if(head==null||head.next==null)return head;

        LinkedList<Integer> list = new LinkedList<>();
        while(head!=null){
            list.addLast(head.val);
            head = head.next;
        }

        for(int i=0;i<k;i++){
            int tmp = list.removeLast();
            list.addFirst(tmp);
        }
        ListNode node = new ListNode(0);
        ListNode cur = node;
        while(!list.isEmpty()){
            node.next=new ListNode(list.removeFirst());
            node = node.next;
        }
        return cur.next;
    }
}

```

```java
//改进算法

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode rotateRight(ListNode head, int k) {
        if(head==null||head.next==null)return head;

        LinkedList<Integer> list = new LinkedList<>();
        int len =0;
        while(head!=null){
            list.addLast(head.val);
            head = head.next;
            len++;
        }
        if(len<k) k = k%len;

        for(int i=0;i<k;i++){
            int tmp = list.removeLast();
            list.addFirst(tmp);
        }
        ListNode node = new ListNode(0);
        ListNode cur = node;
        while(!list.isEmpty()){
            node.next=new ListNode(list.removeFirst());
            node = node.next;
        }
        return cur.next;
    }
}
```

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
    public ListNode rotateRight(ListNode head, int k) {
        if(head==null||head.next==null)return head;

        int len =0;
        ListNode cur = head;
        ListNode newTail = head;
        ListNode newHead = head;

        while(cur.next !=null){//注意此处使用cur.next是因为需要保留尾部指针用于指向head形成环
            len++;
            cur = cur.next;
        }
        cur.next = head;
        len++;
        if(k>=len) k = k%len;

        //拆分链表
        for(int i=0;i<len-k-1;i++){
            newTail = newTail.next;
        }
        newHead = newTail.next;
        newTail.next = null;
        return newHead;

    }
}
```


