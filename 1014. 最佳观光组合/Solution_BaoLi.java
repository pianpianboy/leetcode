class Solution {
    public int maxScoreSightseeingPair(int[] A) {
        int max = 0;
        
        for(int i=0;i<A.length;i++){
            for(int j=i+1;j<A.length;j++){
                max = max>A[i]+A[j]+i-j?max :A[i]+A[j]+i-j;
            }
        }
        return max;
    }
}