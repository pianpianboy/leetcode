class Solution {
    public boolean isValid(String s) {
        if(s==null||s.length()==0){
            return true;
        }
        if(s.length()%2!=0)return false;
        int num1 =0;
        int num2 =0;
        int num3 =0;
        for(int i=0; i< s.length();i++){
            if(s.charAt(i)=='('){
                num1++;
            }
              if(s.charAt(i)=='{'){
                  num2++;
              }
              if(s.charAt(i)=='['){
                num3++;
              }
            
            if(s.charAt(i)==')'){
                num1--;
            }
              if(s.charAt(i)=='}'){
                  num2--;
              }
              if(s.charAt(i)==']'){
                num3--;
              }
           
        }
        
        return num1==0&&num2==0&&num3==0;
    }
}