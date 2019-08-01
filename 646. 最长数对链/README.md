# 解题思路

## 我的解题思路
- 首先题目都没看懂，悲剧，题目求『能够形成的最长数对链的长度』，是求链的长度，这个链必须是最长数对链。
- 还有一个考察点就是JAVA集合的比较器

> 对二维数组进行排序,使用动态规划至低向上的思维

- 这里有两种思路，一种是按第一个数进行排序，再按第二个数进行排序。
- 还有一种是只对一个数进行排序。
```java
class Solution {
    public int findLongestChain(int[][] pairs) {
        if(pairs==null||pairs.length==0) return 0;

        //对数组进行排序
        Arrays.sort(pairs,new Comparator<int[]>(){
            @Override
            public int compare(int[] o1,int[] o2){
                if(o1[0]!=o2[0])
                    return o1[0]-o2[0];
                return o1[1]-o2[1];
            }
        });
        int [] dp = new int[pairs.length];
        int max =0;
        //自底向上求每个dp[i]的值
        for(int i=0;i<pairs.length;i++){
            dp[i]=1;
            for(int j=i-1;j>=0;j--){
                if(pairs[j][1]<pairs[i][0]){
                    dp[i] = dp[j]+1;
                    break;
                }
            }
            max = Math.max(max,dp[i]);
        }
        return max;
    }
}
```

```java
class Solution {
    public int findLongestChain(int[][] pairs) {
        if(pairs == null || pairs.length == 0)
            return 0;
        int rows = pairs.length;
        int cols = pairs[0].length;
        Arrays.sort(pairs, new Comparator<int[]>(){
            public int compare(int[] a,int[] b){
                 return a[0] - b[0];
            }
         });
        int[] dp = new int[rows];
        dp[0] = 1;
        for(int i=1;i<rows;i++){
            for(int j=0;j<i;j++){
                if(pairs[i][0] > pairs[j][1])
                    dp[i] = Math.max(dp[i],dp[j]+1);
                else
                    dp[i] = Math.max(dp[i],dp[j]);
            }
        }
        return dp[rows-1];
    }
```





