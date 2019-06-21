# 解题思路

## 我的解题思路
此题是一道合并区间的题目：先对二维数组进行排序，然后再根据排序后的二维数组进行merge。
即合并的方法为：

首先对每个元素的起点元素进行升序排序，因为两个元素的起始元素已经排好序，所以当后一个元素的起点元素小于等于前一个元素的末尾元素时，两个区间可以合并，如[1,3],[2,5]。 合并后新区间为：[前一个元素的起始元素，Math.max(前一个元素的结尾元素,后一个元素的结尾元素)，如[1,3],[2,5]->[1,Math.max(3,5)]->[1,5]

```java
class Solution {
    public int[][] merge(int[][] intervals) {
        if(intervals==null||intervals.length<2)
            return intervals;
        List<int[]> list = new ArrayList<>();
        Arrays.sort(intervals,new Comparator<int[]>(){
            @Override
            public int compare(int[] a,int[]b){
                return a[0]-b[0];
            }
        });
        for(int i=0;i<intervals.length;i++){
            int[] tmpArr = new int[2];
            tmpArr[0] = intervals[i][0];
            tmpArr[1] = intervals[i][1];
            //开始合并重叠区域
            // while(i<intervals.length-1&&intervals[i][1]>=intervals[i+1][0]){
            //     tmpArr[1] = Math.max(intervals[i][1],intervals[i+1][1]);
            //     i++;
            // }
            //注意上述注释代码中错误的地方，正确的应该是使用tmpArr[1]和intervals[i+1][0]去比较，而不能使用原数组中的值intervals[i][1]和intervals[i+1][0]比较
            while(i<intervals.length-1&&tmpArr[1]>=intervals[i+1][0]){
                tmpArr[1] = Math.max(tmpArr[1],intervals[i+1][1]);
                i++;
            }
            list.add(tmpArr);
        }
        int[][] res = new int[list.size()][2];
        return list.toArray(res);//因为题目中要求返回的是一个二维数组，所以先使用ArrayList进行操作，后序将Arraylist转换为Array
    }
}
```
