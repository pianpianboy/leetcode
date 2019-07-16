# 解题思路

## 我的解题思路

## 参考解题思路
使用回溯解决，但是难点在于想出每次都切割出一个新的子字符串，在在新的字符串中进行回溯
```java
class Solution {
    public List<List<String>> partition(String s) {
        List<List<String>> list = new ArrayList<>();
        List<String> tmplist = new ArrayList<>();
        if(s==null||s.length()==0)return list;

        dfs(list,tmplist,s);
        return list;
    }

    public void dfs(List<List<String>> list,List<String> tmplist,String subString ){
        if(subString.length()==0){
            list.add(new ArrayList<>(tmplist));
        }

        for(int i=1;i<=subString.length();i++){
            if(isPalidrome(i,subString)){
                tmplist.add(subString.substring(0,i));
                dfs(list,tmplist,subString.substring(i));
                tmplist.remove(tmplist.size()-1);
            }
        }
    }

    public boolean isPalidrome(int i,String str){
        int right = i-1;
        int left = 0;
        while(left<=right){
            if(str.charAt(left)!=str.charAt(right))
                return false;
            left++;
            right--;
        }
        return true;
    }

}
```