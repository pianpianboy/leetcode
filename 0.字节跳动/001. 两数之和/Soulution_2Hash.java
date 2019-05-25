class Solution {
    public int[] twoSum(int[] nums, int target) {
       int [] res= new int[2];
        
        Map<Integer,Integer> map = new HashMap<>();
        for(int i =0; i< nums.length;i++){
           map.put(nums[i],i); 
        }

        for(int i =0; i< nums.length;i++){
            int val = target - nums[i];
            if(map.containsKey(val) && map.get(val)!=i){
                res[0] = i;
                res[1] = map.get(val);
            }
            
        }
        return res;
    }
}