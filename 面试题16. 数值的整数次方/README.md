### 题目
面试题16. 数值的整数次方

#### 解题思路
- 分治的思想

```java
//分治的
class Solution {
    public double myPow(double x, int n) {
        long N =  n;

        if(n<0){
            return 1/myPow(x,-N);
        }
        return myPow(x,N);
    }

    public double myPow(double x, long n) {
        if(n==0) return 1;
        if(n==1) return x;
        if(x==1) return 1;

        if((n & 1) == 0){
            // 分治思想：分
            double square = myPow(x, n >>> 1);
            // 分治思想：合，下面同理
            return square * square;
        }else{
            //return x*myPow(x,(n-1)>>>1)*myPow(x,(n-1)>>>1);// 这样会超时，时间复杂度由log(n)变为了log(n)*log(n)
            // 是奇数的时候
            double square = myPow(x, (n - 1) >>> 1);
            return square * square * x;
        }
    }
}
```
