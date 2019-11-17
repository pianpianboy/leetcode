# 题目
121. 买卖股票的最佳时机

## 解题思路
- 暴力解：时间复杂度O(n*n)
- 使用两个变量min、max,一次遍历 时间复杂度O(n)
- DP动态规划


```java
//暴力解
class Solution {
    public int maxProfit(int[] prices) {
        if(prices==null||prices.length<2) return 0;
        int max =0;
        for(int i=0;i<prices.length-1;i++){
            for(int j=i+1;j<prices.length;j++){
                max=Math.max(max,prices[j]-prices[i]);
            }
        }

        return max;
    }
}
```

```java
//一次遍历
class Solution {
    public int maxProfit(int[] prices) {
        if(prices==null||prices.length<2) return 0;
        int min = Integer.MAX_VALUE;
        int max = 0;
        for(int i=0;i<prices.length;i++){
            if(prices[i]<min){
                min=prices[i];
            }else if(prices[i]-min>max){
                max=prices[i]-min;
            }
        }
        return max;
    }
}
```
