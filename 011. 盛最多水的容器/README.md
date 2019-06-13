# 解题思路


## 我的解题思路
首先想到的是暴力解，即利用双循环求解
```JAVA
class Solution {
    public int maxArea(int[] height) {
        if(height==null||height.length==0){
            return 0;
        }
        int max = 0;
        for(int i=0;i<height.length;i++){
            for(int j=i+1;j<height.length;j++){
                max= Math.max(max,Math.min(height[i],height[j])*(j-i));
            }
        }
        return max;
    }
}
```


## 参考解题思路
###（双指针）的解法
使用双指针的时间复杂度为O(n),因为只需要一遍循环
```JAVA
class Solution {
    public int maxArea(int[] height) {
        if(height==null||height.length==0){
            return 0;
        }
        int max = 0;
        int l = 0;
        int r = height.length-1;
        while(l<r){
            max = Math.max(max,Math.min(height[l],height[r])*(r-l));
            if(height[l]<=height[r])l++;
            else
                r--;
        }
        return max;
    }
}
```