class Solution {
    public int trap(int[] height) {
        if(height==null||height.length==0)return 0;
        int sum =0;
        for(int i=0;i<height.length;i++){
            int left_max = Integer.MIN_VALUE;
            int right_max = Integer.MIN_VALUE;
            
            for(int j = i;j>=0;j--){
                if(height[j]>left_max)left_max = height[j];
            }
            for(int k =i;k<height.length;k++){
                if(height[k]>right_max)right_max = height[k];
            }
            sum = sum + Math.min(left_max,right_max)-height[i];
        }
        return sum;
    }
}