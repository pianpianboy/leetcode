class Solution {
    public String multiply(String num1, String num2) {
        if(num1==null||num2==null)return null;
        //1.将两个字符串转换位两个字符数组
        char[] ch1 = new StringBuilder(num1).reverse().toString().toCharArray();
        char[] ch2 = new StringBuilder(num2).reverse().toString().toCharArray();
        
        int[] result = new int[ch1.length+ch2.length];
        
        //2.遍历两个字符数组，取出两个字符数组中的字符进行两两相乘，将结果存入
        for(int i=0;i<ch1.length;i++){
            for(int j=0;j<ch2.length;j++){
               result[i+j] += (ch1[i]-'0')*(ch2[j]-'0');
            }
        }
        
        //3.然后遍历数组，生成结果字符串
        StringBuilder sb = new StringBuilder();
        for(int i=0;i <result.length;i++){
            int digit = result[i]%10;
            int carry = result[i]/10;
            
            if(i<result.length-1){
                result[i+1] += carry;
                sb.insert(0,digit);
            }else{
                sb.insert(0,result[i]);
            }
        }
        
        //4. 删除开头的0
        while(sb.length()>0&&sb.charAt(0)=='0'){
            sb.deleteCharAt(0);
        }
        
        return sb.length()>0? sb.toString():"0";
    }
}