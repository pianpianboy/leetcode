/**
 * @ClassName Test6
 * @Description TODO
 * @Author liaomengjie
 * @Date 2019-05-26 11:17
 */
public class Test7 {
    public String shortestPalindrome(String s) {
        if (s == null||s.length() == 0) return "";

        char[] sa = s.toCharArray();
        char[] ma = manacherString(s);
        int[] radius = new int[ma.length];
        int rightmost = -1;
        int center = -1;
        int patch = sa.length - 1;
        for(int i=0; i<=ma.length/2; i++) {
        //for(int i=0; i<ma.length; i++) {
            radius[i] = rightmost <= i ? 1 : Math.min(rightmost-i, radius[center - (i -center)]);
            while(i-radius[i]>-1 && i+radius[i]<ma.length ){
                if (ma[i-radius[i]] != ma[i+radius[i]])
                    break;
                radius[i]++;
                if(i-radius[i]==0)
                    patch = sa.length-i;
            }
            //为什么要放在里面而不放在外面
//            if(i-radius[i]==0)
//                patch = sa.length-i;


            if (rightmost < i+radius[i]) {
                rightmost = i + radius[i];
                center = i;
            }
        }
        char[] res = new char[patch];
        for(int j=0,k=sa.length-1;j<patch;j++){
            res[j] = sa[k--];
        }
        return new String(res)+s;
    }

    public char[] manacherString(String str){
        char[] arr = str.toCharArray();
        int len = arr.length;
        int index=0;
        char[] res =new char[len*2+1];
        for(int i=0;i<2*len+1;i++){
            res[i]=(i&1)==0? '#':arr[index++];
        }
        return res;
    }
    public static void main(String[] args) {
        System.out.println(new Test7().shortestPalindrome("aacecaaa"));
    }
}
