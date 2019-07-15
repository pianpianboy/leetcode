# 解题思路


## 我的解题思路
例如：
【-2，-4，2，4】;
A[1]=2A[0];
A[3]=2A[2];
A[3]=2A[2];
A[5]=2A[4];
上述情况成对出现，因此可以对数组进行排序（注意有负数情况的排序），然后判断i的值与2*i的值是否满足A[i]=2*A[i]的情况。但是排完序的数组中查找2*A[i]的时间复杂度是O(n)，时间复杂度太高，可以采用字典（哈希）表来优化时间复杂度->O(1);

```JAVA
class Solution {
    public boolean canReorderDoubled(int[] A) {
        if(A==null||A.length<2)return true;
        HashMap<Integer,Integer> map = new HashMap<>();
        //遍历数组形成字典（使用map实现）
        // for(int i=0;i<A.length;i++){
        //     if(map.containsKey(A[i])){
        //         map.put(A[i],map.get(A[i]+1));
        //     }else{
        //         map.put(A[i],1);
        //     }
        // }

        for(int x:A){
            map.put(x,map.getOrDefault(x,0)+1);
        }

        //对数组进行排序
        Integer[] B = new Integer[A.length];
        for (int i = 0; i < A.length; ++i)
            B[i] = A[i];
        Arrays.sort(B, Comparator.comparingInt(Math::abs));
        //Arrays.sort(A, Comparator.comparingInt(Math::abs));

        // for(int i=0;i<B.length;i++){
        //     if(map.get(B[i])==0)
        //         continue;
        //     if(map.getOrDefault(2*B[i])<=0)
        //         return false;
        //     else{
        //         map.put(B[i],map.get(B[i])-1);
        //         map.put(2*B[i],map.get(2*B[i])-1);
        //     B
        // }

        for(int x:B){
            if(map.get(x)==0)//当前值已经被较小的值完成过配对过，
                continue;
            if(map.getOrDefault(2*x,0)<=0)
                return false;
            map.put(x,map.get(x)-1);
            map.put(2*x,map.get(2*x)-1);
        }
        return true;
    }
}
```

## 参考解题思路
1. 哈希+大根堆解决
2.