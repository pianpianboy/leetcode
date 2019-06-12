# 解题思路

## 我的解题思路
首先想到的是暴力解，但是暴力解提交超时，过不去。


## 参考解题思路
```
for(j:1->A.length){
    for(i:0->j){
        max = Math.max(max,A[j]-j+A[i]+i) ;
    }
}
}
`以上暴力解法当然是超时的
优化：贪心算法
从暴力法可以看出在每次计算过程中 在每个j固定的情况下A[j]-j也是不变的，计算它前面的A[i]+i的最大值（i:1->j）
```

贪心算法将问题分解为求固定j时 A[i]+i的最大值，继而求出了A[j]-j+A[i]+i的最大值。**这里值得思考的地方就是为什么不是固定i（A[i]+i）,求A[j]-j的最大值？**
**代码实现**
```java
class Solution {
    public int maxScoreSightseeingPair(int[] A) {
        int max = 0;
        int left = A[0]+0;//A[i]+i;
        //以下的过程就是求A[i]+i的最大值的过程
        for(int i=1;i<A.length;i++){
            max=Math.max(max,A[i]-i+left);
            left = Math.max(left,A[i]+i);
        }
        return max;
    }
}
```
### 改进1
使用字典保存字符串S中字母所有索引，然后遍历word选找索引。
```
class Solution {
    public int numMatchingSubseq(String S, String[] words) {
        //对S进行预处理
        Node[] nodes = new Node[26];
        for(int i=0;i<S.length();i++){
            if(nodes[S.charAt(i)-97]==null)
                nodes[S.charAt(i)-97] = new Node();
            nodes[S.charAt(i)-97].list.add(i);
        }

        int res = 0;
        //遍历数组，求数组中每一个单词是否为子序列
        for(int i=0;i<words.length;i++){
            String word = words[i];
            if(isSubseq(nodes,S,word))
                res++;
        }
        return res;
    }
    public boolean isSubseq(Node[] nodes,String S,String word){
        if(S.length()<word.length())return false;
        int p=-1;
        //遍历单词中每一个字符，判断是否满足子序列的要求
        for(int i=0;i<word.length();i++){
            int index = word.charAt(i)-97;
            //if(nodes[index].list.size()==0||nodes[index].list.get(nodes[index].list.size()-1)<=p)
            if(nodes[index]==null||nodes[index].list.get(nodes[index].list.size()-1)<=p)
                return false;
            //  for(int j=0;j<nodes[index].list.size();j++){
            //     if(nodes[index].list.get(j)>p){
            //         p=i;
            //         break;
            //     }
            // }
            for (int x:nodes[index].list) {
                if (x>p){
                    p=x;
                    break;
                }
            }
        }
       return true;
    }
}
public class Node{
        List<Integer>list = new ArrayList<Integer>();
}
```

### 改进2
在改进1的基础上使用缓存
```
```



### 改进3
There are too many extreme situations in test cases , causing some abnormal solutions to become fast，such as String.indexOf()
```

```













