class Solution {
    public boolean isValid(String s) {
        if(s==null||s.length()==0){
            return true;
        }
        if(s.length()%2!=0)return false;
        int num =0;
        
        for(int i=0; i< s.length();i++){
            if(s.charAt(i)=='('||
              s.charAt(i)=='{'||
              s.charAt(i)=='['){
                num++;
            }else{
                num--;
            }
        }
        
        return num==0;
    }
}