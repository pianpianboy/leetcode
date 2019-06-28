# 15. 三数之和

## 自己的思路（待再次刷新）
    先排序，然后把三个数求和的解转化为两个数相加等于某个数。
    然后求解两个数相加等于某个数，用双指针即可。
    关键在于处理重复的问题：判断相邻两个是不是相等，如果相等就移动指针。

    但是在怎么处理重复这一块的逻辑真心不会写。
    去重分为三块去重：i指针去重，j指针去重，及end指针去重；

    伪代码：
    1.异常处理
    2.排序
    3.i循环
    4.i去重
    5.j+end循环
    6.找sum==0并执行左右去重
    7.sum<0 则移动j，把j的值变大。
    8.若sum>0 则移动end，把end的值变小。

```java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();

        if(nums==null||nums.length<3)return list;

        Arrays.sort(nums);//1.排序的作用是为了去重
        HashMap<Integer,Integer> map = new HashMap<>();//2.使用HashMap去重
        for(int i=0;i<nums.length;i++){
            map.put(nums[i],i);//去重点2，在将数据放入map的过程中就已经完成了一轮去重
        }

        for(int i=0;i<nums.length;i++){
            if(i==0||nums[i]!=nums[i-1]){//去重点3
                for(int j=i+1;j<nums.length;j++){
                    if(j==i+1||nums[j]!=nums[j-1]){//去重点4
                        int sum = nums[i]+nums[j];
                        sum = -sum;
                        if(map.containsKey(sum) && map.get(sum)>j){//去重点5
                            List<Integer> tmpList = new ArrayList<>();
                            tmpList.add(nums[i]);
                            tmpList.add(nums[j]);
                            tmpList.add(sum);
                            list.add(tmpList);
                        }
                    }
                }
            }
        }

        return list;
    }
}
```


- 使用双指针
```java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        if(nums==null||nums.length<3)return list;
        Arrays.sort(nums);

        for(int i=0;i<nums.length;i++){
            //去重1
            if(nums[i]>0)
                break;
            if(i>0&& nums[i]==nums[i-1])
                continue;

            int j = i+1;
            int k = nums.length-1;

            while(j<k){
                //去重2
                if(nums[i]+nums[j]+nums[k]<0||(j>i+1&&nums[j]== nums[j-1])){
                    j++;
                //去重3
                }else if(nums[i]+nums[j]+nums[k]>0 || (k<nums.length-1&&nums[k]==nums[k+1])){
                    k--;
                }else if(nums[i]+nums[j]+nums[k]==0){
                    List<Integer> tmpList = new ArrayList<>();
                    tmpList.add(nums[i]);
                    tmpList.add(nums[j]);
                    tmpList.add(nums[k]);
                    list.add(tmpList);
                    k--;
                    j++;
                }
            }
        }
        return list;
    }
}
```
