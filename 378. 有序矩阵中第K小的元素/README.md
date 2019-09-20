# 解题思路

## 我的解题思路
- 使用二分法 ：此题与 719.找出第k小的距离对 的解法一致
    + 但是此题比719反而更有技巧，需要充分考虑统计比mid小的值的个数时候的时间复杂度优化，
    + 因为矩阵的行列都是递增的，因此考虑从末尾开始比较，如果matrix[i][j]>mid,则matrix[i+1][j]必定比mid大

```java
class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        if(matrix==null||matrix.length==0||matrix[0].length==0) return 0;
        int n = matrix.length;
        int min = matrix[0][0];
        int max = matrix[n-1][n-1];
        while(min<max){
            int mid = min+(max-min)/2;
            int count = 0;
            int row = 0;
            //统计复杂度太高，没有考虑到利用上一行的数据来来减少时间复杂度
            // while(row<n){
            //     for(int i=0;i<n;i++){
            //         if(matrix[row][i]<mid){
            //             count++;
            //         }else{
            //             row++;
            //             break;
            //         }
            //     }
            // }

            //j为列，从末尾开始统计，因为当前行的列值j一定要比上一行的列值j小
            //因为矩阵的行列都是递增的
            int j=n-1;
            for(int i=0;i<n;i++){
                while(j>=0&&matrix[i][j]>mid){
                    j--;
                }
                count += j+1;
            }

            if(count<k) min = mid+1;
            else
                max = mid;
        }

        return min;
    }
}
```
