# 解题思路

## 我的解题思路
- 使用java自带的优先级队列解决，但是效率不高
- 利用快速排序的partition操作找出前k个


```java
class Solution {
    public int[][] kClosest(int[][] points, int k) {
        int row  = points.length;
        HashMap<Integer ,Integer> map = new HashMap<>();

        for(int i=0;i<row;i++){
            int val = Math.abs(points[i][0]*points[i][0])+points[i][1]*points[i][1];
            map.put(i,val);
        }

        PriorityQueue<Map.Entry<Integer,Integer>> queue = new PriorityQueue<>((o1,o2)->{return o1.getValue()-o2.getValue();});

        queue.addAll(map.entrySet());

        int[][] res = new int[k][2];

        for(int i=0;i<k;i++){
            res[i][0] = points[queue.peek().getKey()][0];
            res[i][1] = points[queue.poll().getKey()][1];
        }
        return res;
    }
}
```


```java
class Solution {
    public int[][] kClosest(int[][] points, int k) {
        int row = points.length;
        int left =0;
        int right = row-1;

        while(left<right){
            int index = partition(points,left,right);
            if(index<k)
                left = index+1;
            else if(index<k)
                right = index-1;
            else
               break;
        }
        return Arrays.copyOf(points,k);
    }
    public int partition(int[][] points, int left, int right){
        int privot = points[left][0]*points[left][0] + points[left][1]*points[left][1];
        int j = left;
        for(int i=j+1;i<=right;i++){
            if(points[i][0]*points[i][0] + points[i][1]*points[i][1] < privot)
                                   swap(points,i,++j);
        }
        swap(points,left,j);
        return j;
    }

    public void swap(int[][] points, int i,int j){
        int t0 = points[i][0];
        int t1 = points[i][1];

        points[i][0] = points[j][0];
        points[i][1] = points[j][1];

        points[j][0] = t0;
        points[j][1] = t1;
    }
}
```
