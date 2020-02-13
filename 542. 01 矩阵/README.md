## 题目
542. 01 矩阵

### 解题思路
- dp
 I'm not the author but here is my understanding. The first iteration is from upper left corner to lower right. It's trying to update dis[i][j] to be the distance to the nearest 0 that is in the top left region relative to (i,j). If there's a nearer 0 to the right or to the bottom of (i,j), it won't catch that. And because of the direction of the double loop, it's already in correct iterative order, meaning, you must have dealt with dis[i-1][j] and dis[i][j-1] before you deal with dis[i][j]

Then in the second loop, it goes the opposite direction from the lower right corner. So it'll find the distance to the nearest 0 in the bottom right region. Now combine that with the result from the first loop, it'll cover nearest 0 in all directions. That is where dis[i][j] takes the min of its previous value from the first loop, and the new value (distance to the nearest 0 in the lower right region)

I personally like this approach the most.

```java
class Solution {
    public int[][] updateMatrix(int[][] matrix) {
        int rlen = matrix.length;
        int clen = matrix[0].length;
        if(rlen==0) return matrix;
        int[][] res = new int[rlen][clen];
        for(int i=0;i<rlen;i++){
            for( int j=0;j<clen;j++){
                res[i][j] = Integer.MAX_VALUE-1000;//防溢出
            }
        }

        for(int i=0;i<rlen;i++){
            for( int j=0;j<clen;j++){
                if(matrix[i][j]==0){
                    res[i][j] = 0;
                    continue;
                }
                if(i>0){
                    res[i][j] = Math.min(res[i][j],res[i-1][j]+1);
                }
                if(j>0){
                    res[i][j] = Math.min(res[i][j],res[i][j-1]+1);
                }
            }
        }

        for(int i= rlen-1;i>=0;i--){
            for(int j= clen-1;j>=0;j--){
                // if(matrix[i][j]==0){
                //     res[i][j] = 0;
                //     continue;
                // }
                if(i<rlen-1){
                    res[i][j] = Math.min(res[i][j],res[i+1][j]+1);
                }
                if(j<clen-1){
                    res[i][j] = Math.min(res[i][j],res[i][j+1]+1);
                }
            }
        }

        return res;
    }
}
```
