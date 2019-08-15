# 解题思路

## 我的解题思路
- 关于求和问题，需要考虑大量的的边界问题，比如最高位的进位问题
- 这道题中需要考虑【1】，【9】,这种新增一个进位的问题，还需要考虑 【1】，【9，9，9】这种进位问题
- 若要求不能反转链表则使用栈

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
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if(l1==null)return l2;
        if(l2==null)return l1;

        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();
        Stack<Integer> stack = new Stack<>();

        ListNode cur1 = l1;
        ListNode cur2 = l2;
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;

        while(cur1!=null){
            stack1.push(cur1.val);
            cur1 = cur1.next;
        }

        while(cur2!=null){
            stack2.push(cur2.val);
            cur2 = cur2.next;
        }
        int flag = 0;
        while(!stack1.isEmpty()&&!stack2.isEmpty()){
            int tmp = stack1.pop()+stack2.pop();
            if(flag==1){
                tmp = tmp+1;
                flag = 0;
            }
            if(tmp>9){
                tmp = tmp-10;
                flag=1;
            }
            stack.push(tmp);
        }

        while(!stack1.isEmpty()){
            //考虑最高位进位的问题
            int tmp = stack1.pop();
            if(flag==1){
                tmp = tmp +1;
                flag = 0;
            }
            if(tmp>9){
                tmp = tmp-10;
                flag=1;
            }
            stack.push(tmp);
        }

        while(!stack2.isEmpty()){
            //考虑最高位进位的问题
            int tmp = stack2.pop();
            if(flag==1){
                tmp = tmp +1;
                flag = 0;
            }
            if(tmp>9){
                tmp = tmp-10;
                flag=1;
            }
            stack.push(tmp);
        }

        //考虑最高位进位的问题
        if(flag==1){
            stack.push(1);
        }
        while(!stack.isEmpty()){
            cur.next = new ListNode(stack.pop());
            cur = cur.next;
        }
        cur.next=null;
        return dummy.next;
    }
}

```
