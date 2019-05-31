class Solution {
    public int trap(int[] height) {
        if(height==null||height.length==0)return 0;
        Stack<Integer> stack = new Stack<>();
        int sum =0;
        for(int i=0;i<height.length;i++){
            while(!stack.isEmpty() && height[i]>height[stack.peek()]){
                int tmp = stack.pop();
                if(stack.isEmpty())
                    break;
                sum = sum + (Math.min(height[stack.peek()],height[i])-height[tmp])*(i-stack.peek()-1);
            }
            stack.push(i);
        }
        return sum;
    }
}