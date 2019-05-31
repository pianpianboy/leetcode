class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if(nums==null)return null;
        int[] arr = new int[nums.length-k+1];
        if(nums.length==0||k<1)return new int[0];
        int index = 0;
        LinkedList<Integer> queue = new LinkedList<>();
        
        for(int i=0;i<nums.length;i++){
            while(!queue.isEmpty()&&nums[queue.peekLast()]<nums[i]){
                queue.pollLast();
            }
            queue.offerLast(i);
            //删除过期
            if(queue.peekFirst()<=i-k)
                queue.pollFirst();
            if(i>=k-1)
                arr[index++] = nums[queue.peekFirst()];
        }
        return arr;
    }
}