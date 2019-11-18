# 题目
338. 比特位计数

## 解题思路
- 动态规划
    + 分析变量: i(0~num)
    + 分析状态方程:
        * dp[i] = dp[i/2] 当i为偶数的时候
        * dp[i] = dp[i/2] + 1 当i为奇数的时候
    + 分析边界条件：
        * dp[0] = 0
        * dp[1] = 1;
        * dp[2] = 1;
- lowbit 参考 191. 位1的个数
    + 遍历0到num中的每一个值i
    + 利用lowbit求i中1的个数
- 使用暴力解，其实解法和lowbit很像，暴力解必须遍历完32,而lowbit到val==0的时候就结束了
    + 遍历0到num中的每一个值i
    + 利用mask<<=1 不停右移，大概移动32次
    + 每移动一次就与i 进行&运算，如果结果为1则加1

```java
//动态规划dp
class Solution {
    public int[] countBits(int num) {
        if(num<0) return null;

        int[] dp = new int[num+1];

        dp[0] = 0;
        if(num ==0 ){
            return dp;
        }

        dp[1] = 1;
        if(num ==1 ){
            return dp;
        }
        dp[2] = 1;

        for(int i=3;i<=num;i++){
            // if(i%2==0){
            //     dp[i] = dp[i/2];
            // }else{
            //     dp[i] = dp[i/2]+1;
            // }

            //或者写成
            dp[i] = dp[i>>1]+(i&1);
        }
        return dp;
    }
}

public class Solution {
  public int[] countBits(int num) {
      int[] ans = new int[num + 1];
      for (int i = 1; i <= num; ++i)
        ans[i] = ans[i >> 1] + (i & 1); // x / 2 is x >> 1 and x % 2 is x & 1
      return ans;
  }
}

```

```java
//lowbit解法
class Solution {
    public int[] countBits(int num) {
        if(num<0) return null;

        int[] res = new int[num+1];
        for(int i=0;i<num+1;i++){
            res[i] = getCount(i);
        }
        return res;
    }

    public int getCount(int val){
        int count = 0;
        int lowbit = 0;
        while(val!=0){
            lowbit = val &(-val);
            val -= lowbit;
            count++;
        }
        return count;
    }
}
```

```java
//暴力解
class Solution {
    public int[] countBits(int num) {
        if(num<0) return null;

        int[] res = new int[num+1];
        for(int i=0;i<num+1;i++){
            res[i] = getCount(i);
        }
        return res;
    }

    public int getCount(int val){
        int count = 0;
        int mask = 1;
        for(int i=0;i<32;i++){
            if((val & mask)!=0)
                count++;

            mask <<= 1;
        }

        return count;
    }
}
```
