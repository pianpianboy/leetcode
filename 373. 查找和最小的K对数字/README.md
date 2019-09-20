# 解题思路

## 我的解题思路
> 求第k小的问题，然后是两个数组中的数组对
- 解法一：暴力解
    - 将所有的数对，放入行为m*n，列为2的二维数组中，
    - 将二维数组进行排序
    - 求出前k个
- 解法二：优先级队列


```java
class Solution {
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {

        //采用暴力的解法
        int len1 = nums1.length;
        int len2 = nums2.length;


        //申请一个len1*len2的数组
        int[][] arr = new int[len1*len2][2];
        List<List<Integer>> list = new ArrayList<>();

        if(len1==0||len2==0||k==0) return list;
        if(k>len1*len2) k = len1*len2;

        //将nums1和nums2中的值放入二维数组中
        int index = 0;
        for(int i=0;i<len1;i++){
            for(int j=0;j<len2;j++){
                arr[index][0] = nums1[i];
                arr[index][1] = nums2[j];
                index++;
            }
        }

        //对二维数组进行排序
        Arrays.sort(arr,(o1,o2)->((o1[0]+o1[1])-(o2[0]+o2[1])));

        //取出最终的值
        for(int i=0;i<k;i++){
            List<Integer>tmp = new ArrayList<>();
            tmp.add(arr[i][0]);
            tmp.add(arr[i][1]);
            list.add(tmp);
        }
        return list;
    }
}

```
