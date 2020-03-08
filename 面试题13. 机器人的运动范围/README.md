### 题目
面试题13. 机器人的运动范围

#### 解题思路
- 回溯深度优先
    + 固定一个点（0，0）,然后深度优先+回溯

```java
class Solution {
    private boolean[][] flag;
    private int res = 0;
    public int movingCount(int m, int n, int k) {
        flag = new boolean[m][n];
        help(0,0,m,n,k);
        return res;
    }

    public void help(int i,int j,int m,int n,int k){
        if(count(i,j,k)&&!flag[i][j]){
            flag[i][j] = true;
            res++;
            if(i+1<m) help(i+1,j,m,n,k);
            if(j+1<n) help(i,j+1,m,n,k);
        }
        return;
    }

    public boolean count(int m, int n, int k){
        int mValue, nValue =0;
        if(m==100){
            mValue =1;
        }else{
            mValue = m/10+m%10;
        }

        if(n==100){
            nValue =1;
        }else{
            nValue = n/10+n%10;
        }
        return (mValue+nValue) <= k ?true:false;
    }
}
```