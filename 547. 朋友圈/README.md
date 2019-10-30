# 题目
547. 朋友圈

## 解题思路
- 并查集
    + 并查集的特点是：孩子节点指向父亲节点，两个节点连接在一起即他们有相同的根节点。
- 广度优先遍历
- 深度优先遍历

```java
//使用并查集
class Solution {
    public int findCircleNum(int[][] M) {
        if(M==null||M.length==0||M[0]==null) return 0;
        int r = M.length;
        int c = M[0].length;

        UnionFind uf = new UnionFind(r);

        for(int i=0;i<r;i++){
            for(int j=0;j<i;j++){
                if(M[i][j]==1){
                    uf.union(i,j);
                }
            }
        }
        return uf.count;
    }

    public class UnionFind{
        int count;
        int[] fatherArr;
        int[] sizeArr;
        public UnionFind(int n){
            count =n;
            fatherArr = new int[n];
            sizeArr = new int[n];
            for(int i=0;i<n;i++){
                this.fatherArr[i]=i;
                this.sizeArr[i]=1;
            }
        }

        public int findFather(int val){
            int father = fatherArr[val];
            if(father!=val){
                father = findFather(father);
            }
            fatherArr[val] = father;
            return father;
        }

        public void union(int a, int b){
            int afather = findFather(a);
            int bfather = findFather(b);
            if(afather!=bfather){
                int asize = sizeArr[afather];
                int bsize = sizeArr[bfather];
                if(asize>=bsize){
                    fatherArr[bfather] = afather;
                    sizeArr[afather] = asize+bsize;
                }else{
                    fatherArr[afather] = bfather;
                    sizeArr[bfather] = asize+bsize;
                }
                count--;
            }
        }
    }
}
```