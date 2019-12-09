# 题目
1021. 删除最外层的括号

## 解题思路
- 普通方法，利用统计'(' ')'的个数来判断
- 使用stack
    + 遍历字符串，遇到做括号就入栈，遇到右括号就出栈，每次栈为空的时候就说明找到了一个原语，
    + 记录下原语的起始位置和结束位置

```java
//普通解法
class Solution {
    public String removeOuterParentheses(String S) {
        if(S==null||S.length()<1) return "";

        char[] arr = S.toCharArray();
        StringBuilder sb = new StringBuilder();
        int count = 0;

        for(int i=0;i<arr.length;i++){

            if(arr[i]==')'){
                count--;
            }
            if(count>=1){
                sb.append(arr[i]);
            }
            if(arr[i]=='('){
                count++;
            }
        }
        return sb.toString();
    }
}
```


```java
//使用stack
class Solution {
    public String removeOuterParentheses(String S) {
        if(S==null||S.length()<1) return "";

        char[] arr = S.toCharArray();
        StringBuilder sb = new StringBuilder();
        int start = 0;
        Stack<Integer> stack = new Stack<>();


        for(int i=0;i<arr.length;i++){

            if(arr[i]=='('){
                stack.push(i);
            }
            if(arr[i]==')'){
                start = stack.pop();
            }
            if(stack.isEmpty()){
                sb.append(S.substring(start+1,i));
                start =0;
            }
        }
        return sb.toString();
    }
}
```
