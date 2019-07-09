# 解题思路

## 我的解题思路
这题可以参照46全排列的解法，采用回溯+增枝+剪枝来实现。

- 先使用回溯找出所有的排序，然后在结果中找到第k个，这种会超时，需要改进
- 改进算法，不必求出所有排序，在求排序过程中判断是否达到k

```java
//以下解法，先使用回溯找出所有的排序，然后在结果中找到第k个，但是提交超时。
class Solution {
    public String getPermutation(int n, int k) {
        int result = 1;
        for(int i=1;i<=n;i++){
            result= result*i;
        }
        if(k>result) k = result;
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        List<Integer> tmplist = new ArrayList<>();

        dfs(n,k,list,tmplist);
        res = list.get(k-1);
        String str = "";
        for(Integer inte:res){
            str = str+inte;
        }

        return str;
    }

    public void dfs(int n,int k,List<List<Integer>> list,List<Integer> tmplist){
        if(tmplist.size()==n){
            list.add(new ArrayList<>(tmplist));
        }
        for(int i=1;i<=n;i++){
            if(tmplist.contains(i))
                continue;
            tmplist.add(i);
            dfs(n,k,list,tmplist);
            tmplist.remove(tmplist.size()-1);
        }
    }
}
```

改进：如n=4,k=9,使用回溯+剪枝的思想得到了输出"2314"

1. 第一层，以1开头，后面3个树，"2,3,4"可选。以1开头的全排列一共有3！=6个，并且6 < k=9，因此所求全排列不一定以1开头，可以在这里"剪枝"。
2. 然后第一层，以2开头，后面3个树"1,3,4"可选，注意此时k应该减去上一轮剪枝的叶子节点个数，这里k=9-3!=3<6个，故所求的全排列一定在这个分支里，即所求的全排列一定以2开头，需要继续递归求解。
3. 第二层，以1开头，后面有2个数"3,4"可选,故以"21"开头的全排列一共有2！=2个，此时k=3>2,因此所求的全排列一定不以"21"开头，可以在这里剪枝。根据之前的分析，k应该再减去"剪枝"的叶子结点个数，即k=3-2=1.
4. 第三层，以"231"开头，后面有1个数"4"可选。故以"231"开头的全排列一共有1！=1个，此时k=1=1,因此，所求的全排列一定在以"231"开头的这个分支里。需要递归求解

```java
class Solution {
    public String getPermutation(int n, int k) {

        if(k>getValue(n)) k = getValue(n);
        StringBuilder result = new StringBuilder();
        List<Integer> tmplist = new ArrayList<>();

        return dfs(n,k,0,tmplist);
    }

    public String dfs(int n,int k,int depth,List<Integer> tmplist){
        if(depth==n){
            StringBuilder sb = new StringBuilder();
            for(int val : tmplist){
                sb = sb.append(val+"");
            }
            return sb.toString();
        }
        int value = getValue(n-tmplist.size()-1);
        for(int i=1;i<=n;i++){
            if(tmplist.contains(i))
                continue;

            if(value<k){
                k = k-value;
                continue;
            }
            tmplist.add(i);
            return dfs(n,k,depth+1,tmplist);
        }
        throw new RuntimeException("参数错误");
    }

    public int getValue(int n){
        int result = 1;
        for(int i=1;i<=n;i++){
            result= result*i;
        }
        return result;
    }
}
```
```java
class Solution {
    public String getPermutation(int n, int k) {
        if(k>getValue(n)) k = getValue(n);
        List<Integer> tmplist = new ArrayList<>();
        return dfs(n,k,tmplist);
    }

    public String dfs(int n,int k,List<Integer> tmplist){
        if(tmplist.size()==n){
            StringBuilder sb = new StringBuilder();
            for(int val : tmplist){
                sb = sb.append(val+"");
            }
            return sb.toString();
        }
        for(int i=1;i<=n;i++){
            if(tmplist.contains(i))
                continue;
            int value = getValue(n-tmplist.size()-1);
            if(value<k){
                k = k-value;
                continue;
            }
            tmplist.add(i);
            return dfs(n,k,tmplist);
            //tmplist.remove(tmplist.size()-1);
        }
        throw new RuntimeException("参数错误");
    }

    public int getValue(int n){
        int result = 1;
        for(int i=1;i<=n;i++){
            result= result*i;
        }
        return result;
    }
}
```
















