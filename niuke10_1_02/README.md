# 题目
一群孩子做游戏，现在请你根据游戏得分来发糖果，要求如下: 1.每个孩子不管得分多少， 起码分到 1 个糖果。 2.任意两个相邻的孩子之间，得分较多的孩子必须拿多一些的糖果。 给定一个数组 arr 代表得分数组，请返回最少需要多少糖果。 例如:arr=[1,2,2]，糖果 分配为[1,2,1]，即可满足要求且数量最少，所以返回 4。
【进阶】
原题目中的两个规则不变，再加一条规则: 3.任意两个相邻的孩子之间如果得分一样，糖 果数必须相同。
给定一个数组 arr 代表得分数组，返回最少需要多少糖果。 例如:arr=[1,2,2]，糖果分 配为[1,2,2]，即可满足要求且数量最少，所以返回 5。
【要求】
arr 长度为 N，原题与进阶题都要求时间复杂度为 O(N)，额外空间复杂度为 O(1)。

# 解题思路

## 我的解题思路
- 额外空间复杂度为O(n)，使用两个数组，正着遍历一遍，反着遍历一遍
- 额外空间复杂度为O(1)

```java
//解法一
class Solution {
    public int candy(int[] ratings) {
        if(ratings==null||ratings.length==0)return 0;
        int len = ratings.length;
        int[] right = new int[len];
        int[] left = new int[len];

        left[0] =1;
        right[len-1] =1;
        for(int i=1;i<len;i++){
            if(ratings[i]>ratings[i-1]){
                left[i] = left[i-1]+1;
            }else{
                left[i]=1;
            }
        }

        for(int j=len-2;j>=0;j--){
            if(ratings[j]>ratings[j+1]){
                right[j] = right[j+1]+1;
            }else{
                right[j]=1;
            }
        }

        int res =0;
        for(int i=0;i<len;i++){
            res += Math.max(left[i],right[i]);
        }
        return res;
    }
}

```
