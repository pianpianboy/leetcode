# 解题思路

## 我的解题思路

- 暴力解：提交超时
```JAVA
class Solution {
    public int numSubarrayBoundedMax(int[] A, int L, int R) {
        int res=0;
        for(int i=0;i<A.length;i++){
            int max=Integer.MIN_VALUE;
            for(int j=i;j<A.length;j++){
                max = Math.max(max,A[j]);
                if(max>=L&&max<=R){
                    res++;
                }
            }
        }
        return res;
    }
}
```

## 参考解题思路
**利用滑动窗口的思想**
标记两个指针left及right,使用滑动窗口移动right和left,没移动一次right计算一次res和。

1. L=<A[right]<=R的情况：则从left开始以A[right]结尾的子数组都满足要求，其数量满足right-left+1
2. A[right]<L的情况:则以A[right]结尾的子数组中：只有A[right]一个数的子数组不满足要求，则寻找left到right之间A[t]满足 L=<A[t]<=R，此时从left到t之间开始并以A[right]结尾的子数组都满足要求，其数量为t-left+1
3. A[right]>R的情况:则以A[right]结尾的子数组不满足要求，此时直接跳过当前值，让left和right同时指向下一个值

```java
class Solution {
    public int numSubarrayBoundedMax(int[] A, int L, int R) {
        //利用滑动窗口的思想
        int left = 0;
        int right = 0;
        int res =0 ;

        while(right<A.length){
            if(A[right]>=L&&A[right]<=R){
                res+=right-left+1;
                right++;
            }else if(A[right]<L){
                for(int i=right-1;i>=left;i--){
                    if(A[i]>=L){
                        res += i-left+1;
                        break;
                    }
                }
                right++;
            }else
                left=++right;

        }
        return res;
    }
}
```

