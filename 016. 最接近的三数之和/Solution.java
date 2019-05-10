class Solution {
    public int threeSumClosest(int[] nums, int target) {
        if(nums==null||nums.length<3){
            return 0;
        }
        Arrays.sort(nums);
        int min = Integer.MAX_VALUE;
        for(int i=0;i<nums.length;i++){
            int j = i + 1;
            int k = nums.length - 1;
            while(j<k){
                int val = nums[i]+nums[j]+nums[k]-target;
                if(Math.abs(val)< Math.abs(min) )min = val;
                
                if(val>0)
                    k--;
                else if(val<0)
                    j++;
                else return min+target;
            } 
        }
        return min+target;
    }
}