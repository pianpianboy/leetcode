class Solution {
    public boolean splitArray(int[] nums) {
        //暴力解，时间超时
        if(nums.length<7)return false;
        
        for(int i = 1;i<nums.length-5;i++){
            int sum1 = getSum(nums,0,i-1);
            for(int j=i+2;j<nums.length-3;j++){
                int sum2 = getSum(nums,i+1,j-1);
                for(int k=j+2;k<nums.length-1;k++){
                    int sum3 = getSum(nums,j+1,k-1);
                    int sum4 = getSum(nums,k+1,nums.length-1);
                    if(sum1==sum2&&sum2==sum3&&sum3==sum4)
                        return true;
                }
            }
        }
        return false;
    }
    
    public int getSum(int[] nums,int start,int end){
        int sum =0 ;
        for(int i=start;i<=end;i++){
            sum += nums[i];
        }
        return sum;
    }
}