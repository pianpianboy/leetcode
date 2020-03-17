### 题目
面试题29. 顺时针打印矩阵

```java
class Solution {
    public static int[] spiralOrder(int[][] matrix) {
        if(matrix==null||matrix.length==0) return new int[0];

        int lenR = matrix.length;
        int lenC = matrix[0].length;
        int[] res = new int[lenR*lenC];
        int startR = 0;
        int startC = 0;
        int endR = lenR-1;
        int endC = lenC-1;
        int index =0;
        while(startR<=endR&&startC<=endC){
            if(startR==endR){
                for(int i=startC;i<=endC;i++){
                    res[index++] = matrix[startR][i];
                }

            }else if(startC==endC){
                for(int i=startR;i<=endR;i++){
                    res[index++] = matrix[i][startC];
                }

            }else{
                for(int i=startC;i<endC;i++){
                    res[index++] = matrix[startR][i];
                }
                for(int i= startR;i<endR;i++){
                    res[index++] = matrix[i][endC];
                }
                for(int i=endC;i>startC;i--){
                    res[index++] = matrix[endR][i];
                }
                for(int i=endR;i>startR;i--){
                    res[index++] = matrix[i][startC];
                }
            }

            startR++;
            startC++;
            endR--;
            endC--;
        }
        return res;
    }
}
```