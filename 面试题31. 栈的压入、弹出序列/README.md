### 题目
面试题31. 栈的压入、弹出序列

### 解题思路
```java
class Solution {
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        Stack<Integer> stack = new Stack<>();
        int j=0;
        for(int i=0;i<pushed.length;i++){
            stack.push(pushed[i]);
            while(!stack.isEmpty()&& popped[j] == stack.peek()){
                stack.pop();
                j++;
            }
        }
        return stack.isEmpty();
    }
}
```