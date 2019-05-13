class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if(nums==null|nums.length == 0)return res;
        
        for(int i=0; i< nums.length;i++){
            List<Integer> list = new ArrayList<>();
            list.add(nums[i]);
            help(nums,res,list);
        }
        return res;
    }
    
    public void help(int[] nums, List<List<Integer>> res, List<Integer> list){
        if(list.size() == nums.length){
            res.add(list);
            return ;
        }
        
        for(int i =0;i<nums.length;i++){
            if(!list.contains(nums[i])){
                List<Integer> tmp = new ArrayList<>();
                for(int j =0;j<list.size();j++){
                    tmp.add(list.get(j));
                } 
                tmp.add(nums[i]);
                help(nums,res,tmp);
            }
        }
    }
}