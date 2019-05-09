class Solution {
    public String longestCommonPrefix(String[] strs) {
        String str = "";
        if(strs == null || strs.length < 1){
            return str;
        }
        str = strs[0];
        
        for(int i = 0; i<str.length(); i++){
            char c = str.charAt(i);
            for(int j = 1;j < strs.length ; j++){
                if(strs[j].length()-1<i || strs[j].charAt(i)!= c ){
                    return str.substring(0,i);
                }
            }
        }
        return str;
    }
}