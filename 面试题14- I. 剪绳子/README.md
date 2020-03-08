### 题目
面试题14- I. 剪绳子

#### 解题思路
- 暴力解--暴力递归
- 暴力解改进，使用备忘录的递归，即自顶向下
- 由自底向上：动态规划


```java
//暴力解
class Solution {
    public int cuttingRope(int n) {
        if(n<1) return 0;
        return help(n);
    }

    public int help(int n){
        if(n==1) return 1;
        Integer res = Integer.MIN_VALUE;
        for(int i=1;i<n;i++){
            res = Math.max(res,Math.max(i*(n-i),i*help(n-i)));//注意到我们每次将一段绳子剪成两段时，剩下的部分可以继续剪，也可以不剪

        }
        return res;
    }
}
```

```java
//暴力解改进，使用备忘录的递归，即自顶向下
class Solution {

    private HashMap<Integer,Integer> map = new HashMap<>();
    public int cuttingRope(int n) {
        if(n<1) return 0;
        return help(n);
    }

    public int help(int n){
        if(n==1) return 1;
        Integer res = Integer.MIN_VALUE;
        int tmp = 0;
        for(int i=1;i<n;i++){
            //注意到我们每次将一段绳子剪成两段时，剩下的部分可以继续剪，也可以不剪
            if(map.containsKey(n-i)){
                res = Math.max(res,Math.max(i*(n-i),i*map.get(n-i)));
            }else{
                tmp = help(n-i);
                map.put(n-i,tmp);
                res = Math.max(res,Math.max(i*(n-i),i*tmp));
            }
        }
        return res;
    }
}
```

```java
//由自底向上：动态规划
class Solution {
    //暴力解改进，使用备忘录的递归，即自顶向下
    private HashMap<Integer,Integer> map = new HashMap<>();
    public int cuttingRope(int n) {
        if(n<1) return 0;
        return help(n);
    }

    public int help(int n){
        map.put(1,1);
        Integer res = Integer.MIN_VALUE;

        for(int i=1;i<=n;i++){
            //注意到我们每次将一段绳子剪成两段时，剩下的部分可以继续剪，也可以不剪
            for(int j=1;j<i;j++){
                res = Math.max(res,Math.max(j*(i-j),j*map.get(i-j)));
                map.put(i,res);
            }
        }
        return res;
    }
}
```