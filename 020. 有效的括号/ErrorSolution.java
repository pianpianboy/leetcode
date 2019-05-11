class Solution {
    public boolean isValid(String s) {
        if(s==null||s.length()==0){
            return true;
        }
        if(s.length()%2!=0)return false;
        
        int len = s.length()/2;
        for(int i=0;i<len;i++){
            if(s.charAt(i)=='('&&s.charAt(i+len)!=')'||
              s.charAt(i)=='{'&&s.charAt(i+len)!='}'||
              s.charAt(i)=='['&&s.charAt(i+len)!=']'){
                return false;
            }
        }
        return true;
    }
}