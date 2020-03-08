### 题目
面试题06. 从尾到头打印链表

#### 解题思路

```java
class Solution {
    public int[] reversePrint(ListNode head) {
        if(head==null) return new int[0];
        ListNode list = head;

        int size = 0;
        while(list!=null){
            size++;
            list =list.next;
        }

        int[] arr = new int[size];
        for(int i=size-1;i>=0;i--){
            arr[i] = head.val;
            head = head.next;
        }
        return arr;
    }
}
```