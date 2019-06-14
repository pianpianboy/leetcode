# 解题思路

## 我的解题思路
此题的思路还是很明确的，别陷入局部怎么旋转打印，必须从整体分析；
【1】2 3   4
5  6  7   8
9 10 11 【12】

1. 固定住左上角，固定住右下角，然后遍历当前一圈,但是遍历的时候存在一个小技巧1->2->3;4->8;12->11->10; 9->5。必须如此打印。不然代码太难写；
2. 最后左上角的行和列都执行一次自增操作，右下角的行和列执行自减操作。

```JAVA
class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
            List<Integer> list = new ArrayList<>();
            if(matrix==null)return null;
            if (matrix.length == 0 || matrix[0].length==0) return list;

            int startR = 0;//行
            int startC = 0;//列
            int endR = matrix.length-1;
            int endC = matrix[0].length-1;

            while(startR <= endR && startC<=endC){
                if(startC==endC){
                    for(int i=startR;i<=endR;i++){
                        list.add(matrix[i][startC]);
                    }
                }else if(startR == endR){
                    for(int i=startC;i<=endC;i++){
                        list.add(matrix[startR][i]);
                    }
                }else{
                    for(int i=startC;i<endC;i++){
                        list.add(matrix[startR][i]);
                    }
                    for(int i=startR;i<endR;i++){
                        list.add(matrix[i][endC]);
                    }
                    for(int i=endC;i>startC;i--){
                        list.add(matrix[endR][i]);
                    }
                    for(int i=endR;i>startR;i--){
                        list.add(matrix[i][startC]);
                    }
                }
                    startR++;
                    startC++;
                    endR--;
                    endC--;
            }
            return list;
        }
}
```
