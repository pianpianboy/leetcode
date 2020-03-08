### 题目
面试题09. 用两个栈实现队列
#### 解题思路
```java
class CQueue {
    Stack<Integer> stackA;
    Stack<Integer> stackB;
    public CQueue() {
        stackA = new Stack();
        stackB = new Stack();
    }

    public void appendTail(int value) {
        stackA.push(value);
        return;
    }

    public int deleteHead() {
        if(stackB.isEmpty()){
            if(stackA.isEmpty()){
                return -1;
            } else{
                while(!stackA.isEmpty()){
                    stackB.push(stackA.pop());
                }
                int val = stackB.pop();
                return val;
            }
        }else{
            return stackB.pop();
        }
    }
}

/**
 * Your CQueue object will be instantiated and called as such:
 * CQueue obj = new CQueue();
 * obj.appendTail(value);
 * int param_2 = obj.deleteHead();
 */
```