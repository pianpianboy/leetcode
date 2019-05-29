class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m-1;
        int j = n-1;
        for(int k = m+n-1;k>=0;k--){
            if(i<0){
                nums1[k] = nums2[j--];//nums1[]中全部为比较大的数
                continue;
            }
            if(j<0){
                nums1[k] = nums1[i--];//nums1[]中全部为比较小的数
                continue;
            }
            
            if(nums1[i]<nums2[j])
                nums1[k] = nums2[j--];
            else
                nums1[k] = nums1[i--];
            
        }
    }
}