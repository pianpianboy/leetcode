# 解题思路

# 我的解题思路
参考了907.子数组最小值之和 这一道题的解题思路，使用单调栈来解决。
```java
class Solution {
    public int largestRectangleArea(int[] heights) {
        //使用单调栈来求解
        if(heights==null||heights.length<1)return 0;

        //由于求最大面积，则需要找到左边界和有边界（小）
        //则使用单调递增栈

        Stack<Integer> stack = new Stack<>();
        int len  = heights.length;
        int res = Integer.MIN_VALUE;

        for(int i=0;i<=len;i++){
            while(!stack.isEmpty() && (heights[stack.peek()] > (i==len? 0:heights[i])) ){
                //弹出单调栈中的元素
                int cur = stack.pop();
                int left = stack.isEmpty()?-1:stack.peek();
                res = Math.max(res,heights[cur]*(i-left-1));
            }
            stack.push(i);
        }
        //注意此处很容易写成return  res;
        return res==Integer.MIN_VALUE?0:res;
    }
}
```

