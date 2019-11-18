# 题目
191. 位1的个数

## 解题思路
- 位运算（循环&操作）
    + 注意此时 位移操作是 <<=
    + &操作的优先级 低于!= 的优先级
- 错误解 动态规划，错误原因没看清题目，以为题目输入的是十进制整数
- lowbit
    + lowbit公式：x & -x
    + lowbit作用：可以在常数时间找出数字n中最后一个1出现的位置
    + 举个例子
        * 例如n = 10。用二进制表示就是1010，而它的负数-10用二进制补码表示则为0110，取&后得到的结果为10，这就得到了n最后一个1出现的位置对应的数字。可以试着证明一下，这里就不写了。


```java
//循环&操作
public class Solution {
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        int count= 0;
        int mask = 1;
        for(int i=0;i<32;i++){
            if((n&mask) !=0)
                count++;

            mask <<= 1;
        }
        return count;
    }
}
```

```java
//错误解
public class Solution {
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        if(n<=0) return 0;
        int[] dp =  new int [n+1];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 1;

        for(int i=3;i<n;i++){
            if(i%2==0)
                dp[i] = dp[i/2];
            else
                dp[i] = dp[i/2]+1;
        }
        return dp[n];
    }
}
```

```java
//lowbit
//知道lowbit公式后，这个题目就非常简单了。每次通过lowbit公式找到最后一个1对应的数，然后将它减去，直到n为0为止。统计减法操作的次数即可。
public class Solution {
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        int count= 0;
        int lowbit =0;
        while(n!=0){
            lowbit = n&(-n);
            count++;
            n -= lowbit;
        }
        return count;
    }
}
```

