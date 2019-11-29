# 题目
1130. 叶值的最小代价生成树

## 解题思路
- 动态规划

```java
class Solution {
    public int mctFromLeafValues(int[] arr) {
        if(arr == null||arr.length == 0) return 0;
        int len = arr.length;

        int[][] dp = new int[len][len];
        int[][] max = new int[len][len];
        for(int i=0;i<len;i++){
            //int maxValue = Integer.MIN_VALUE;
            int maxValue = 0;
            for(int j=i;j<len;j++){
                maxValue = Math.max(maxValue,arr[j]);
                max[i][j] = maxValue;
            }
        }

        for(int d=1;d<len;d++){
            for(int i=0;i<len-d;i++){
                int j=i+d;
                // if(d==0){
                //     dp[i][j] = arr[i];
                // }else
                if(d==1){
                    dp[i][j] = arr[i]*arr[j];
                }else{
                    //这一句话不要忘记加，因为在i,j之间选取k的时候需要和k-1的时候dp[i][j]进行比较，取较小值
                    dp[i][j] = Integer.MAX_VALUE;
                    for(int k=i;k<j;k++){//{1,3,6}会被划分为{1} {3，6}当k==i时候取的是{1}
                        dp[i][j] = Math.min(dp[i][j],dp[i][k]+dp[k+1][j]+max[i][k]*max[k+1][j]);
                    }
                }
            }
        }

        return dp[0][len-1];
    }
}
```
