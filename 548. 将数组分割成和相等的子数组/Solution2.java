class Solution {
    public boolean splitArray(int[] nums) {
        //暴力解2，时间超时
        if(nums.length<7)return false;
        int[] sum = new int[nums.length];
        sum[0] = nums[0];
        for(int i=1;i<nums.length;i++){
            sum[i] = nums[i] + sum[i-1];
        }
        HashSet<Integer> set = new HashSet<>();
        for(int i =1;i<nums.length-5;i++){
            int sum1 = sum[i-1];
            for(int j = i+2; j<nums.length-3; j++){
                int sum2 = sum[j-1]-sum[i];
                if(sum1==sum2)
                    set.add(sum1);
                else continue;
                for(int k = j+2;k<nums.length-1;k++){
                    int sum3 = sum[k-1]-sum[j]; 
                    int sum4 = sum[nums.length-1] - sum[k];
                    if(sum3==sum4&&set.contains(sum3))
                        return true;
                }
            }
        }
        return false;
    }
}