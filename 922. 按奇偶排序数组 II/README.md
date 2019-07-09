# 解题思路

## 我的解题思路
这道题是一道简单的题：遍历一遍数组就可以解决。
```java
class Solution {
    public int[] sortArrayByParityII(int[] A) {
        // if(A==null)return new int[];
        int len = A.length;
        int tmp1 =0;
        int tmp2 =1;
        int[] arr = new int[len];

        for(int i=0;i<arr.length;i++){
            if(A[i]%2==0){
                arr[tmp1] = A[i];
                tmp1 = tmp1+2;
            }else{
                arr[tmp2] = A[i];
                tmp2 = tmp2+2;
            }
        }
        return arr;
    }
}
```