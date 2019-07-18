# 解题思路

## 我的解题思路
- 暴力解：暂时只想到暴力解的方法

```java
class Solution {
    public int countSubstrings(String s) {
        int len = s.length();
        int index = 0;
        for(int i=0;i<len;i++){
            for(int j=i+1;j<=len;j++){
                if(ispalidrome(s.substring(i,j))){
                    index++;
                }
            }
        }
        return index++;
    }

    public boolean ispalidrome(String str){
        int j =str.length()-1;
        int i = 0;
        while(i<=j){
            if(str.charAt(i)!=str.charAt(j))
                return false;
            j--;
            i++;
        }
        return true;
    }
}
```

## 参考解题思路
- 动态规划：动态规划dfs公式：当j-i>2时：dp[i][j]=(s.charAt(i)==s.charAt(j)&& dp[i+1][j-1]) ；j-i<=2时：dp[i][j]=(s.charAt(i)==s.charAt(j));因为求dp[i][j]的时候需要i+1的值和j-1的值，因此需要从数组的末尾往前移动。
- 马拉车算法
```java
//动态规划解法,在动态规划的解法中需要注意的地方就是：i与j的距离之间形成的字符串，当j=i时候，处理的是a;当j=i+1的时候处理的是ab字符串；当j=i+2的时候处理的是aba字符串。a、ab、aba是三种情况是需要单独进行处理的。
class Solution {
    public int countSubstrings(String s) {
        int len = s.length();
        int num = 0;
        boolean[][] dp = new boolean[len][len];
        for(int i=len-1;i>=0;i--){
            for(int j=i;j<len;j++){
                if(j-i<=2){//2:aba; 1:bb;0:a
                    if(s.charAt(i) == s.charAt(j)){
                        dp[i][j] = true;
                        num++;
                    }else{
                        dp[i][j] = false;
                    }
                }else{
                    if(s.charAt(i)==s.charAt(j)&&dp[i+1][j-1]){
                        dp[i][j] = true;
                        num++;
                    }else{
                        dp[i][j]=false;
                    }
                }
            }
        }
        return num;
    }
}
```

```java
//manacher解法
//待处理
```

