### 题目

#### 解题思路
- 暴力解：递归,时间复杂度O(2^N)
- 暴力解优化：递归+备忘录：自顶向下 时间复杂度O(N),
- 动态规划：自底向上

```java
//暴力解：递归
class Solution {
    public int fib(int n) {
        if(n==0) return 0;
        if(n==1) return 1;
        return (fib(n-1)+fib(n-2))%1000000007;
    }
}
```

```java
//暴力解优化：递归+备忘录：自顶向下
class Solution {
    private int[] arr;
    public int fib(int n) {
        arr= new int[n+1];
        return help(n);
    }

    public int help(int n){
        if(n<=1) return n;
        if(arr[n]!=0) return arr[n];

        arr[n] = (help(n-1) + help(n-2))%1000000007;
        return arr[n];
    }
}
```

```java
//自底向上
class Solution {
    private int[] arr;
    public int fib(int n) {
        arr= new int[101];
        arr[1] = 1;
        return help(n);
    }

    public int help(int n){
        if(n <= 1) return n;
        for(int i=2; i<=n; i++){
            if(arr[i]==0){
                arr[i] = (arr[i-1] + arr[i-2])%1000000007;
            }
        }
        return arr[n];
    }
}
```
