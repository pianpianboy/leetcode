# 解题思路

## 我的解题思路
删除节点的步骤（非头结点）
- 找到该节点的前一个节点
- 进行删除操作

头结点的删除操作
- 若头结点非空使用head= head.next 移动head到下一个节点
- return 移动后的head,即使用移动节点来删除节点

1. 删除头结点另做考虑（由于头结点没有前一个节点）

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
    public ListNode removeElements(ListNode head, int val) {
        //if(head == null) return head;

        //处理头结点,因为不知道head的前一个节点，只能移动head指针来删除节点
        while(head!=null&&head.val ==val){
            head = head.next;
        }

        if(head == null) return head;//必须将这条语句放在头结点处理之后，因为处理完成头结点后，head可能为空
        ListNode tmpNode = head;

        //处理尾节点
        while(tmpNode.next != null){
            if(tmpNode.next.val == val)
                tmpNode.next = tmpNode.next.next;
            else{
                tmpNode = tmpNode.next;
            }

        }
        return head;
    }
}
```
2. 添加一个虚拟头结点，删除头结点就不用另作考虑

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
    public ListNode removeElements(ListNode head, int val) {
        //创建一个虚拟节点
        ListNode dumpNode = new ListNode(val-1);
        dumpNode.next = head;

        ListNode newNode = dumpNode;
        if(newNode.next == null) return head;
        //确保当前节点后还有头节点
        while(newNode.next!=null){
            if(newNode.next.val == val){
                newNode.next = newNode.next.next;
            }else{
                newNode = newNode.next;
            }
        }
        return dumpNode.next;
    }
}
```

3. 使用单独处理每一个节点
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
    public ListNode removeElements(ListNode head, int val) {
        if(head == null)return head;

        head.next = removeElements(head.next,val);
        if(head.val == val)
            return head.next;
        else
            return head;

    }
}
```




