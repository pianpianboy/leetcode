### 题目
1013. 将数组分成和相等的三个部分

### 解题思路
```java
class Solution {
    public boolean canThreePartsEqualSum(int[] A) {
        if(A==null||A.length<3) return false;
        int sum =0;
        int res = 0;

        int tmp = 0;

        for(int i=0;i<A.length;i++){
            sum+=A[i];
        }
        if(sum%3!=0){return false;}

        tmp = sum/3;

        int flag = 0;
        for(int val : A){
            res+=val;
            if(res == tmp){
                flag++;
                res =0;
            }
        }
        return flag>=3;
    }
}
```
