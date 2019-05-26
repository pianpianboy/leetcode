class Solution {
    public int findKthNumber(int n, int k) {
        int cur = 1;
        k = k-1;//原因：向下一层寻找，肯定要减少前面的父节点，即在上一层中的第k个数，在下一层是k-1个数，
        while(k>0){
            int steps = getSteps(n,cur,cur+1);
            if(steps<=k){//第k小的数不在当前树即树的直接点上
                cur += 1;//继续向同一层节点寻找
                k -= steps;//向下一层寻找，肯定要减少前面的所有的字节点
            }else{//在子数中，进入子树
                cur *=10;//继续向下一层选择
                k -= 1;//向下一层寻找，肯定要减少前面的父节点，即 在上一层中的第k个数，在下一层中是第k-1个数
            }
        }            
        return cur;
    }
    
    public int getSteps(int n,long first,long last){
        int steps = 0;
        while(first<=n){
            steps += Math.min(n+1,last)-first;
            first *= 10;
            last *= 10;
        }
        return steps;
    }
}