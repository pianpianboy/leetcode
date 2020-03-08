###题目
面试题10- II. 青蛙跳台阶问题

#### 解题思路
- 动态规划- 自底而上

```java
class Solution {
    public int numWays(int n) {
        int[] arr = new int[101];
        arr[1] = 1;
        arr[2] = 2;
        return dp(arr,n);
    }

    public int dp(int[]arr,int n){
        if(n==0) return 1;
        if(n<=2) return n;
        for(int i=3;i<=n;i++){
            arr[i] = (arr[i-1]+arr[i-2])%1000000007;
        }
        return arr[n];
    }
}

```
