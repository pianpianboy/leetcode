# 解题思路

## 我的解题思路
首先想到的是暴力解，但是暴力解提交超时，过不去。


## 参考解题思路
```
for(j:1->A.length){
    for(i:0->j){
        max = Math.max(max,A[j]-j+A[i]+i) ;
    }
}
}
`以上暴力解法当然是超时的
优化：贪心算法
从暴力法可以看出在每次计算过程中 在每个j固定的情况下A[j]-j也是不变的，计算它前面的A[i]+i的最大值（i:1->j）
```

贪心算法将问题分解为求固定j时 A[i]+i的最大值，继而求出了A[j]-j+A[i]+i的最大值。**这里值得思考的地方就是为什么不是固定i（A[i]+i）,求A[j]-j的最大值？**
**代码实现**
```java
class Solution {
    public int maxScoreSightseeingPair(int[] A) {
        int max = 0;
        int left = A[0]+0;//A[i]+i;
        //以下的过程就是求A[i]+i的最大值的过程
        for(int i=1;i<A.length;i++){
            max=Math.max(max,A[i]-i+left);
            left = Math.max(left,A[i]+i);
        }
        return max;
    }
}
```
