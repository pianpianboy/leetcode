//使用二维数组的动态DP的解法
class Solution {
    public int findLength(int[] A, int[] B) {
        if(A.length==0||B.length==0)return 0;
        int max = 0;
        int[][] sameCount = new int[A.length][B.length];
        
        //构建数组（二维数组）的两个边界
        for(int i=0;i<A.length;i++){
            sameCount[i][0] = A[i]==B[0]?1:0;
        }
        for(int j=0;j<B.length;j++){
            sameCount[0][j] = A[0]==B[j]?1:0;
        }
        //根据转移方程数组计算最大连续子串长度
        for(int i=1;i<A.length;i++){
            for(int j=1;j<B.length;j++){
                sameCount[i][j] =A[i]==B[j]? sameCount[i-1][j-1]+1:0;
                max = max>sameCount[i][j]? max:sameCount[i][j];
            }
        }
        
        return max;
    }
}

//使用一维数组的动态DP解法
//再进一步则是将矩阵降为一维矩阵：dp矩阵变为一维，大小为Min(m,n).

//然后通过迭代的方式来不断的进行匹配，例如固定第二个字符串，然后使用第一个字符串的每一个字符都在第二个字符串中匹配，把匹配结果迭代到下一次的匹配中即可。
public int findLength(int[] A, int[] B) {
        //时间复杂度小
        if(A.length<B.length){
            return findLength(B, A);
        }
        int max=0;
        int[] samecount=new int[A.length];
        //初始化第一行
        for(int i=0;i<A.length;i++){
            samecount[i]=0;
            if(B[0]==A[i]){
                samecount[i]=1;
            }
        }
       
        for(int i=1;i<B.length;i++){
            int[] tempsamcount=new int[A.length];
            for(int j=0;j<A.length;j++){
                if(A[j]==B[i]){
                    if(j==0){
                        tempsamcount[j]=1;
                    }
                    else{
                        //状态转移
                        tempsamcount[j]=samecount[j-1]+1;
                    }
                    //全局比较
                    max=max>tempsamcount[j]?max:tempsamcount[j];
                }
            }
            //下一次迭代
            samecount=tempsamcount;
        }
        return max;
    }

