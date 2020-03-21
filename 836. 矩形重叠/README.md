### 题目
836. 矩形重叠

### 解题思路
- 投影，将二维数据投影到一维空间

```java
class Solution {
    public boolean isRectangleOverlap(int[] rec1, int[] rec2) {
        if(rec1==null||rec2==null||rec1.length<4||rec2.length<2) return false;
        if(!(rec2[2]<=rec1[0]||rec1[2]<=rec2[0])&&!(rec2[3]<=rec1[1]||rec1[3]<=rec2[1]))
            return true;
        return false;
    }
}
```
