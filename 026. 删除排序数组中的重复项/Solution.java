class Solution {
    public int removeDuplicates(int[] nums) {
        if(nums==null||nums.length==0)return 0;
        if(nums.length==1)return 1;
        
        int n1 = 0;
        int n2 = 0;
        while(n2!=nums.length){
            if(nums[n1] == nums[n2])
                n2++;
            else{
                int tmp = nums[n1+1];
                nums[++n1] = nums[n2];
                nums[n2] = tmp;
                n2++;
            }

        }
        return n1+1;
    }
}