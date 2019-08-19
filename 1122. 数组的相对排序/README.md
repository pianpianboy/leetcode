# 解题思路

## 我的解题思路
此题中有两个数组ar1,arr2，同样是两个数组的操作，349和350使用双指针。本题却用双指针很难做。因为题目要求：

- 对arr1中元素进行排序，是arr1中相对顺序和arr2中的相对顺序相同
- arr1中未在arr2中出现过的元素需要按照升序放在arr1的末尾。

使用计数排序

```java
class Solution {
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        int[] arr = new int[1001];
        //遍历arr1
        for(int val:arr1){
            arr[val]++;
        }
        int [] res = new int[arr1.length];
        int index=0;

        for(int val:arr2){
            int count = arr[val];
            while(count>0){
                res[index++] = val;
                count--;
            }
            arr[val] = 0;
        }

        //遍历arr数组 处理arr1中未在arr2中出现过的元素需要按照升序放在arr1的末尾
        // for(int val:arr){
        //     if(val!=0){
        //         res[index++] = val;
        //     }
        // }
        //下面的代码很容出错（写成上面那样就错了），计数排序中下标才是值
        for(int i=0;i<1001;i++){
            while(arr[i]>0){
                res[index++] = i;
                arr[i]--;//未出现的情况下也可能出现重复
            }
        }
        return res;
    }
}
```
