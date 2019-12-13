# 题目
232. 用栈实现队列

## 解题思路
- 使用两个Stack实现

```java
class MyQueue {

    private static Stack<Integer> pushStack;
    private static Stack<Integer> popStack;

    /** Initialize your data structure here. */
    public MyQueue() {
        pushStack = new Stack<Integer>();
        popStack = new Stack<Integer>();
    }

    /** Push element x to the back of queue. */
    public void push(int x) {
        while(!popStack.isEmpty()){
            pushStack.push(popStack.pop());
        }
        pushStack.push(x);
    }

    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        while(!pushStack.isEmpty()){
            popStack.push(pushStack.pop());
        }
        return popStack.pop();
    }

    /** Get the front element. */
    public int peek() {
        if(!popStack.isEmpty()){
            return popStack.peek();
        }
        while(!pushStack.isEmpty()){
            popStack.push(pushStack.pop());
        }
        return popStack.peek();
    }

    /** Returns whether the queue is empty. */
    public boolean empty() {
        if(popStack.isEmpty()&& pushStack.isEmpty()){
            return true;
        }else{
            return false;
        }
    }
}

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */

```
