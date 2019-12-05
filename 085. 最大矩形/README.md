# 题目
85. 最大矩形

## 解题思路
- 使用单调栈
    + 此题是在84题的基础上改进得来

```java
class Solution {
    public int maximalRectangle(char[][] matrix) {
        if(matrix== null|| matrix.length<1||matrix[0].length<1) return 0;

        int x = matrix.length;
        int y = matrix[0].length;
        int[][] arr = new int[x][y];

        for(int i=0;i<x;i++){
            for(int j=0;j<y;j++){
                if(i==0){
                    arr[i][j] = matrix[i][j] == '0'? 0 : 1;
                }else{
                    arr[i][j] = matrix[i][j] == '0'? 0: arr[i-1][j]+1;
                }
            }
        }
        int max =0;
        for(int i=0;i<x;i++){
            max = Math.max(max,getRectangle4OneRow(arr[i]));
        }
        return max;
    }

    public int getRectangle4OneRow(int[] heights) {
        if(heights==null||heights.length==0) return 0;

        int max =0;
        //单调递增栈
        Stack<Integer> stack = new Stack<Integer>();

        for(int i=0;i<heights.length;i++){
            int val = heights[i];
            while((!stack.isEmpty())&&(heights[stack.peek()]>val)){
                int index = stack.pop();
                int left = stack.isEmpty()? -1: stack.peek();
                max = Math.max(max, heights[index] * (i-left-1));
            }
            stack.push(i);
        }

        while(!stack.isEmpty()){
            int index = stack.pop();
            int left = stack.isEmpty()? -1: stack.peek();
            max = Math.max(max, heights[index] * (heights.length -left-1));
        }
        return max;
    }
}
```

