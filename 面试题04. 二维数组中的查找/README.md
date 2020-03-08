### 题目
面试题04. 二维数组中的查找

#### 解题思路

```java
class Solution {
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        if(matrix==null||matrix.length==0||matrix[0].length==0) return false;
        int x = matrix.length;
        int y = matrix[0].length;
        int i = 0;
        int j = y-1;
        while(i<x&&j>=0){
            if(matrix[i][j] == target){
                return true;
            }else if(matrix[i][j] > target){
                j--;
            }else{
                i++;
            }
        }
        return false;
    }
}
```