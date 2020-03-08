### 题目
面试题15. 二进制中1的个数

#### 解题思路
- 循环位与，移动
-


```java
//循环位与移动
public class Solution {
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        int tmp = 1;
        int res = 0;
        for(int i=1;i<=32;i++){
            if((n&tmp)!=0){
                res++;
            }
            tmp <<= 1;
        }
        return res;
    }
}
```