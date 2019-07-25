# 解题思路

## 我的解题思路
- 暴力解:考虑每一对元素和他们对应的区间，然后遍历这一段区间来检查相邻元素之间的差值是不是相等的
    + 将此题翻译为：需要从一个数组中找出**长度大于3**的子数组，并且子数组中每个值的差相等
    + 可以先固定一个起始值S:0->A.length-2;然后固定一个尾部E:S+2->A.length;
    + 然后判断S->E之间是否满足差相等，若出现一个不等，则当前循环直接break，上一层的循环E:S+2->A.length也要跟着break;
- 暴力解优化：每次区间的遍历都会重复判断，这部分的重复比较是可以优化的。
    + 比如遍历了S->E之后
    + 遍历S->E+1,又会重复比较S->E之间的值
- 递归
    + 若A[index]-A[index-1]==A[index-1]-A[index-2] 则以index结尾的等差子数组个数num2,以index-1结尾的等差子数组个数num1 = num1+1
    + 因为除了区间(0,index)以外，其余的区间如(1,index),(2,index),...(i-2,index),这些够可以对应到之前的区间(0,index),(1,index),...(i-3,index)
- 暴力解改动态规划
    + 此题动态规划与递归的区别就在与动态规划时从头往后遍历
    + dp[i]表示以1结尾的等差子数组的个数。

> 代码

```java
//暴力解
class Solution {
    public int numberOfArithmeticSlices(int[] A) {
        if(A==null||A.length<3)return 0;

        int count = 0;
        int len  =A.length;
        for(int s=0;s<len-2;s++){
            int d = A[s+1]-A[s];
            for(int e=s+2;e<len;e++){
                boolean flag = false;
                for(int i=s+1;i<=e;i++){
                    if(A[i]-A[i-1]!=d){
                        flag = true;
                        break;
                    }
                }
                if(flag)
                    break;
                else
                    count++;
            }
        }
        return count;
    }
}
```
>暴力解优化

```java
class Solution {
    public int numberOfArithmeticSlices(int[] A) {
        if(A==null||A.length<3)return 0;

        int count = 0;
        int len  =A.length;
        for(int s=0;s<len-2;s++){
            int d = A[s+1]-A[s];
            for(int e=s+2;e<len;e++){
                if(A[e]-A[e-1]!=d)
                    break;
                else
                    count++;
            }
        }
        return count;
    }
}
```
>递归

```java
class Solution {
    private int sum = 0;
    public int numberOfArithmeticSlices(int[] A) {
        if(A==null||A.length<3)return 0;
        digui(A,A.length-1);
        return sum;
    }

    public int digui(int[] A, int index){
        if(index<2)return 0;
        int dp = 0;
        if(A[index]-A[index-1]==A[index-1]-A[index-2])
            dp = 1 + digui(A,index-1);
        else
            digui(A,index-1);
        sum = sum + dp;
        return dp;
    }
}
```
> 动态规划

```java
class Solution {

    public int numberOfArithmeticSlices(int[] A) {
        if(A==null||A.length<3)return 0;
        int sum =0 ;
        int[] dp = new int[A.length];
        dp[0]=0;
        dp[1]=0;

        for(int i=2;i<A.length;i++){
            if(A[i]-A[i-1] == A[i-1]-A[i-2]){
                dp[i] = dp[i-1] + 1;
                sum += dp[i];
            }else
                dp[i] = 0;
        }

        return sum;
    }
}
```



