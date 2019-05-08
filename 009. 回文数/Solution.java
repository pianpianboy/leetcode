class Solution {
    public boolean isPalindrome(int x) {
        //1.异常处理:负数和10或者以0结尾的数
        //2.反转整数x的后半段
        //3.判断反转后的整数和x的大小对比
        
        if(x<0||(x%10==0&&x!=0)){
            return false;
        }
        int pre = x; 
        int post = 0;
        while(pre>post){
            post = post*10 + pre%10;
            pre = pre/10;
        }     
        return pre==post || (pre==post/10);
    }
}
/**/