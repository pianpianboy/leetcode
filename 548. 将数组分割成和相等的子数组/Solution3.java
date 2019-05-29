class Solution {
    public boolean splitArray(int[] nums) {
        //暴力解，时间超时
        if(nums.length<7)return false; 
        
        int[] sum = new int[nums.length];
        sum[0] = nums[0];
        
        //2.技术点
        for(int i = 1;i<nums.length;i++){
            sum[i] = sum[i-1] + nums[i];
        }
        
        //3.技术点  从中间分割，
        for(int j=3;j<nums.length-3;j++){
            //1.技术点
            HashSet<Integer> set = new HashSet<>();
            
            for(int i=1;i<j-1;i++){
                //在前半部分进行搜索，看看能不能找到相同的划分
                if(sum[j-1]-sum[i] == sum[i-1])
                    set.add(sum[i-1]);
            } 
            //进行后半部分搜索，如果找到了 相同的划分 并且也存在与哈希表中   
            for(int k=j+1;k<nums.length-1;k++){
                //如果找到了，就将和加入哈希表中；
                if(sum[nums.length-1]-sum[k]==sum[k-1]-sum[j] && set.contains(sum[k-1]-sum[j]))
                    return true;
            }
        }        
        return false;
    }
}