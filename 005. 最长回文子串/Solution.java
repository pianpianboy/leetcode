class Solution {
    public String longestPalindrome(String s) {
        if(s==null||s.length()==0)return "";
        char[] arr = manacher(s);
        int[] pArr = new int[arr.length];
        int index = -1;
        int pR = -1;
        int max = Integer.MIN_VALUE;
        int indexVal = 0;
        
        for(int i=0;i<arr.length;i++){
            pArr[i] = i<pR? Math.min(pArr[2 * index - i],pR-i):1;
            while(i+pArr[i]<arr.length&&i-pArr[i]>-1){
                if(arr[i+pArr[i]] == arr[i-pArr[i]])
                    pArr[i]++;
                else
                    break;
            }
            //更新index 及pR
            if(i+pArr[i]>pR){
                pR = i + pArr[i];
                index = i;
            }
            
            indexVal = pArr[i]>=max? i:indexVal;
            max = Math.max(max,pArr[i]);
        }
        return buildStr(arr,indexVal,pArr[indexVal]);
    }
    public String buildStr(char[] arr,int index,int r){
        StringBuilder res = new StringBuilder();
        for(int i = index-r+1;i < index+r-1;i++){
            if(arr[i]!='#'){
               res.append(arr[i]); 
            }
        }
        return res.toString();
    }
    
    public char[] manacher(String str){
        char[] arr = str.toCharArray();
        int len = arr.length;
        int index=0;
        char[] res =new char[len*2+1];
        for(int i=0;i<2*len+1;i++){
            res[i]=(i&1)==0? '#':arr[index++];
        }
        return res;
    }
}