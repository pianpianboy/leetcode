class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if(nums==null || nums.length<3){
            return res;
        }
        
        Arrays.sort(nums);
        
        for(int i = 0;i< nums.length; i++){
            if(i>0 && nums[i] == nums[i-1]){
                              continue;  
            }

            int j = i+1;
            int end = nums.length-1;
            while(j < end){
                int sum = nums[i]+nums[j]+nums[end];
                if(sum  == 0){
                    res.add(Arrays.asList(nums[i],nums[j],nums[end]));
                    while(j<end && nums[j] == nums[j+1]){
                        j++;
                    }
                    while(j<end && nums[end] == nums[end-1]){
                        end--;
                    }
                    j++;
                    end--;
                }
                else if(sum<0){
                    j++;
                }else{
                    end--;
                }
            }
        }
        return res;
    }
}