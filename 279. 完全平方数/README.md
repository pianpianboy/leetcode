#解题思路

## 我的解题思路
- 动态规划：动态方程为：j为0->i/2; dp[i] = i为平方数？1：Math.min(dp[i],dp[j]+dp[i-j]); 如何判断i是否为平方数？增加判断j*j==i?
```java
class Solution {
    public int numSquares(int n) {
        int dp[] = new int[n+1];
        dp[0] = 0;
        dp[1] = 1;
        for(int i = 2;i<=n;i++){
            int min = Integer.MAX_VALUE;
            for(int j=1;j<=i/2;j++){
                if(j*j==i){
                    min =1;
                    break;
                }
                min = Math.min(min, dp[j]+dp[i-j]);
            }
            dp[i]=min;
        }
        return dp[n];
    }
}
```
改进版本
```java
class Solution {
    public int numSquares(int n) {
        int dp[] = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            int min = Integer.MAX_VALUE;
            for (int j = 1; j * j <= i; j++)
                min = j * j == i ? 1 : Math.min(min, dp[j * j] + dp[i - j * j]);
            dp[i] = min;
        }
        return dp[n];
    }
}

```

- BFS广度搜索优先遍历 :待完成，目前代码有问题
```java
class Solution {
    public int numSquares(int n) {
        int level = 0;
        int sum = n;
        int min = Integer.MAX_VALUE;
        bfs(n,sum,level,min);
        return min;
    }

    public void bfs(int n,int sum,int level,int min){
        if(sum == 0){
            min = Math.min(min,level);
            return;
        }
        for(int i=1;i*i<=sum;i++){
            bfs(n,sum-i*i,level+1,min);
        }
        return ;
    }
}
```


