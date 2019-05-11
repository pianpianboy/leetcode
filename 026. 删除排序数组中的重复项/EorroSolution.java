class Solution {
    public int removeDuplicates(int[] nums) {
        if(nums==null||nums.length==0)return 0;
        if(nums.length==1)return 1;
        
        int tmp = nums[0];
        for(int i=1;i<nums.length;i++){
            if(nums[i]==tmp) 
                nums[i] = Integer.MAX_VALUE;
            else
                tmp = nums[i];
        }
        int index = 0;
        int res = 0;
        int i = 1;
        while(i<nums.length){
            if(nums[i] == Integer.MAX_VALUE)
                index = i++;
            else{
                nums[index] = nums[i];
                nums[i] = Integer.MAX_VALUE;
                res++;
            }
        }
        return res+1;
    }
}