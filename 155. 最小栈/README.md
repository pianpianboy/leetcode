# 题目

## 解题思路
- 使用辅助的Stack解决

```java
class MinStack {

    private Stack<Integer> stack;
    private Stack<Integer> min;
    /** initialize your data structure here. */
    public MinStack() {
        stack = new Stack<Integer>();
        min = new Stack<Integer>();
    }

    public void push(int x) {
        stack.push(x);
        if(min.isEmpty()||x<=min.peek()){
            min.push(x);
        }else{
            min.push(min.peek());
        }
    }

    public void pop() {
        if(stack.isEmpty()){
            return;
        }else{
            stack.pop();
            min.pop();
        }
    }

    public int top() {
        if(!stack.isEmpty()){
            return stack.peek();
        }else{
            throw new RuntimeException("stack is empty");
        }

    }

    public int getMin() {
        if(!stack.isEmpty()){
           return min.peek();
        }else{
            throw new RuntimeException("stack is empty");
        }
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */

```
