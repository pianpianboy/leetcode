

class Solution {
    public boolean isPalindrome(int x) {
        //1.异常处理:负数和10或者以0结尾的数
        //2.反转整数x
        //3.判断反转后的整数和x的大小对比
        
        if(x<0||(x%10==0&&x!=0)){
            return false;
        }
        int n = x; 
        int res = 0;
        while(n!= 0){
            int tmp = n%10;
            res = res*10 + tmp;
            n = n/10;
        }       
        return x==res;
    }
}
