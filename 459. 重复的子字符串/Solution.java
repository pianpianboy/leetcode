class Solution {
    public boolean repeatedSubstringPattern(String s) {
        if(s==null||s.length()<=1)return false;
        boolean flag = false;
        for(int i=1;i<=s.length()/2;i++){
            if(s.length()%i==0){
                if(help(s,i)){
                    flag = true;
                    break;
                };
            }
        }
        return flag;
    }

    public boolean help(String str, int gap){
        for(int i = 0;i<gap;i++){
            for(int j = i+gap;j < str.length(); j+=gap){
                if(str.charAt(i)!=str.charAt(j))
                    return false;
            }
        }
        return true;
    }
}