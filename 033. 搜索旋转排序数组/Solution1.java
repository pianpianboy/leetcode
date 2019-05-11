class Solution {
    public int search(int[] nums, int target) {
        if(nums==null || nums.length==0){
            return -1;
        }
        int left = 0 ;
        int right = nums.length-1;
        while(left<=right){
            int mid = (left+right)>>1;
            if(nums[mid] == target){
                return mid;
            }
            if(nums[mid]<nums[right]){
                //则是右边有序
                if(target>nums[mid]&&target<=nums[right]){
                    left = mid + 1;
                }else{
                    right = mid - 1;
                }
            }else{
                //则是左边有序
                if(target>=nums[left]&&target<nums[mid]){
                    right = mid - 1;
                }else{
                    left = mid + 1;
                }
            }
        }
        return -1;
    }
}