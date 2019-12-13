# 题目
225. 用队列实现栈

## 解题思路
- 使用双队列实现
    + push()和top()时间复杂度O(1),Pop()时间复杂度O(n)
    + Queue有哪些操作：add()\offer()和poll()\remove()
- 使用单队列（双向链表）实现
    + Deque有哪些操作？ add()\offer()和getFirst()\getLast()和removeLast()\removeFirst


- sdf

```java
class MyStack {

    private static Queue<Integer> data;
    private static Queue<Integer> help;
    private static int top ;

    /** Initialize your data structure here. */
    public MyStack() {
        data = new LinkedList<Integer>();
        help = new LinkedList<Integer>();
    }

    /** Push element x onto stack. */
    public void push(int x) {
        data.add(x);
        top = x;
    }

    /** Removes the element on top of the stack and returns that element. */
    public int pop(){
        while(data.size()>1){
            top = data.poll();
            help.add(top);
        }
        int res = data.poll();
        swap();
        return res;
    }

    /** Get the top element. */
    public int top() {
        return top;
    }

    /** Returns whether the stack is empty. */
    public boolean empty() {
        return data.isEmpty();
    }

    public void swap(){
        Queue<Integer> queue = new LinkedList<Integer>();
        queue = data;
        data = help;
        help = queue;
    }
}

/**
 * Your MyStack object will be instantiated and called as such:
 * MyStack obj = new MyStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * boolean param_4 = obj.empty();
 */
```


```java
class MyStack {

    private static Deque<Integer> data;


    /** Initialize your data structure here. */
    public MyStack() {
        data = new LinkedList<Integer>();
    }

    /** Push element x onto stack. */
    public void push(int x) {
        data.add(x);
    }

    /** Removes the element on top of the stack and returns that element. */
    public int pop(){
        int res =data.removeLast();
        return res;
    }

    /** Get the top element. */
    public int top() {
        return data.getLast();
    }

    /** Returns whether the stack is empty. */
    public boolean empty() {
        return data.isEmpty();
    }

}

/**
 * Your MyStack object will be instantiated and called as such:
 * MyStack obj = new MyStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * boolean param_4 = obj.empty();
 */
```