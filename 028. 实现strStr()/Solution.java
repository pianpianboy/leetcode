class Solution {
    public int strStr(String haystack, String needle) {
        if(haystack==null||needle==null||haystack.length()<needle.length())return -1;
        char[] ch1 = haystack.toCharArray();
        char[] ch2 = needle.toCharArray();
        int[] next = getNextArray(ch2);
        int i = 0;
        int j = 0;
        while(i<ch1.length && j<ch2.length){
            if(ch1[i] == ch2[j]){
                i++;
                j++;
            }else{
                if(next[j]!=-1){
                    j = next[j];
                }else{
                    i++;
                }
            }        
        }
        return j = j==ch2.length?i-j:-1;
    }
    public int[] getNextArray(char[] ch){
        if(ch.length<=1)return new int[]{-1};
        
        int[] next = new int[ch.length];
        next[0] = -1;
        next[1] = 0;
        int cn = 0;
        int i = 2;
        while(i<ch.length){
            if(ch[i-1]==ch[cn]){
                next[i++]=++cn;
            }else if(cn>0){
                cn = next[cn];
            }else{
                next[i++]=0;
            }
        }
        return next;
    }  
}