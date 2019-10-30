# 题目
128. 最长连续序列

## 解题思路
- 使用并查集 时间复杂度O(n)
    + 使用并查集的难点在于"并"，而不在于"查"，并就是什么两个集合能够合并，很显然对于此题是当两个值是相连的时候可以"并"，比如nums[i]=10,nums[j]=11就可以合并
    + 就是在遍历数组的时候，比如遍历到i的时候，"查" nums[i]-1 是否在集合中，如果在就合"并"
- 暴力解 时间复杂度O(log n)

```java
//使用并查集解题
class Solution {
    public int longestConsecutive(int[] nums) {
        if(nums==null||nums.length==0) return 0;

        UnionFind uf = new UnionFind(nums);
        for(int i=0;i<nums.length;i++){
            if(uf.fatherMap.containsKey(nums[i]-1)){
                uf.union(nums[i]-1,nums[i]);
            }
        }
        return uf.max;
    }
    public class UnionFind{
        int max;
        HashMap<Integer,Integer> fatherMap;
        HashMap<Integer,Integer> sizeMap;

        public UnionFind(int[] nums){
            max = 1;//处理nums中只有一个元素的情况下，默认为1
            fatherMap = new HashMap<>();
            sizeMap = new HashMap<>();

            for(int val: nums){
                fatherMap.put(val,val);
                sizeMap.put(val,1);
            }
        }

        public int findFather(int val){
            int father = fatherMap.get(val);
            if(father != val){
                father = findFather(father);
            }
            fatherMap.put(val,father);
            return father;
        }

        public void union(int a,int b){
            int aFather = findFather(a);
            int bFather = findFather(b);
            if(aFather != bFather){
               int  aSize = sizeMap.get(aFather);
               int  bSize = sizeMap.get(bFather);
                if(aSize<=bSize){
                    fatherMap.put(aFather,bFather);
                    sizeMap.put(bFather,aSize+bSize);
                }else{
                     fatherMap.put(bFather,aFather);
                    sizeMap.put(aFather,aSize+bSize);
                }
                max = Math.max(max,aSize + bSize);
            }
        }
    }
}
```
