## 对二维数据进行排序

已知一个二维数组arr = {{9,2},{-1,2},{3,5},{0,2}} 实现对二维数组的排序，即根据二维数组中每一行第一个值的大小进行排序，排完序之后的结果是
{{-1,2},{0,2},{3,5},{9,2}}
```java
        Arrays.sort(arr,new Comparator<int[]>(){
            @Override
            public int compare(int[] a, int[] b){
                return a[0]-b[0];
            }
        });
```
```java
比较器可以简写为： Arrays.sort(arr, (a, b) -> (a[0] - b[0]));
```



