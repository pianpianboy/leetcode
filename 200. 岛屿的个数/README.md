# 解题思路

## 自己的解法：
因为看过左程云的视频第6集，但是在第6集中只是提到了可以使用**感染法**去解决这个问题。
但是具体的实现没说，自己在分析这个问题的时候，发现有好多种情况需要考虑，代码写着写着就进入了死胡同：
第二刷：使用二维数组，二维平面中的偏移量技巧

- 使用感染函数递归就可以
- 使用UnionFind
    + 把二维矩阵grid[i][j]的i j位置包装成一个Node节点，即Node node = new Node(i,j);
    + 新建矩阵arr[i][j] = node; 新建arrayList.add(node)，同时要将water加入arrayList,arrayList.add(new Node(-1,-1))
    + 调用unionFind.init(arrayList),对并查集进行初始化
    + 然后遍历grid中每一个值，判断是否能够和并集合
- DFS
- BFS
-

```java
class Solution {
    private int[][] direction={{-1,0},{1,0},{0,1},{0,-1}};
    public int numIslands(char[][] grid) {
        if(grid==null||grid.length==0) return 0;
        int res = 0;
        int m = grid.length;
        int n = grid[0].length;

        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(grid[i][j]=='1'){
                    dfs(grid,i,j);
                    res++;
                }
            }
        }
        return res;
    }
    public void dfs(char[][] grid,int i,int j){
        if(!isArea(grid,i,j)||grid[i][j]=='0'){
            return ;
        }
        if(grid[i][j]=='1'){
            grid[i][j]='#';
            for(int k=0;k<4;k++){
                int newX = i+direction[k][0];
                int newY = j+direction[k][1];
                if(isArea(grid,newX,newY))
                    dfs(grid,newX,newY);
            }
        }
    }
    public boolean isArea(char[][] grid,int i,int j){
        if(i<0||i>=grid.length||j<0||j>=grid[0].length)
            return false;
        else
            return true;
    }
}
```

```java
//使用感染函数， 第三刷
class Solution {
    public int numIslands(char[][] grid) {
        if(grid == null ||grid.length==0|| grid[0] == null) return 0;
        int r = grid.length;
        int c = grid[0].length;
        int res = 0;
        for(int i=0;i<r;i++){
            for(int j=0;j<c;j++){
                if(grid[i][j]=='1'){
                    infect(i,j,grid);
                    res++;
                }
            }
        }
        return res;
    }

    public void infect(int i, int j,char[][] grid){
        int r = grid.length;
        int c = grid[0].length;
        if(i<0||i>=r||j<0||j>=c||grid[i][j]=='2'||grid[i][j]=='0'){
            return;
        }
        if(grid[i][j]=='1'){
           grid[i][j]='2';
        }

        infect(i+1,j,grid);
        infect(i,j+1,grid);
        infect(i-1,j,grid);
        infect(i,j-1,grid);
    }
}
```
```java
//使用并查集的解法
class Solution {
    public int numIslands(char[][] grid) {
        //if(grid==null||grid[0]==null||grid.length==0||grid[0].length==0)return 0;
        if(grid == null ||grid.length==0|| grid[0] == null) return 0;
        ArrayList<Node> arrayList = new ArrayList<>();

        int r = grid.length;
        int c = grid[0].length;
        Node[][] arr = new Node[r][c];

        for(int i=0;i<r;i++){
            for(int j=0;j<c;j++){
                Node node = new Node(i,j);
                arrayList.add(node);
                arr[i][j] = node;
            }
        }
        Node water = new Node(-1,-1);
        arrayList.add(water);

        UnionFind union = new UnionFind();
        union.init(arrayList);
        for(int i=0;i<r;i++){
            for(int j=0;j<c;j++){
                if(grid[i][j]=='1'){
                    if(isValid(grid,i,j+1)&&grid[i][j+1]=='1'){
                        union.union(arr[i][j],arr[i][j+1]);
                    }

                    if(isValid(grid,i+1,j)&&grid[i+1][j]=='1'){
                        union.union(arr[i][j],arr[i+1][j]);
                    }
                }else{
                    union.union(arr[i][j],water);
                }
            }
        }

        return union.sizeMap.size()-1;
    }


    public boolean isValid(char[][]grid,int i, int j){
        return i>=0&&i<grid.length&&j>=0&&j<grid[0].length;
    }
    //并查集算法：
    //1.Class Node
    //2.Class UnionFind
    //2.1 UnionFind构造方法（包括两个HashMap的初始化）
    //2.2 init()方法，使用传递的Collection对UnionFind中的两个HahsMap进行初始化
    //2.3 集合的合并方法union
    //2.4 查找father节点 findFather()

    public class Node{
        int x ;
        int y;
        public Node(int x, int y ){
            this.x = x;
            this.y = y;
        }
    }

    public class UnionFind{
        HashMap<Node, Node> fatherMap ;
        HashMap<Node, Integer> sizeMap ;

        public UnionFind(){
            fatherMap = new HashMap<>();
            sizeMap = new HashMap<>();
        }

        public void init(ArrayList<Node> collections){
            fatherMap.clear();
            sizeMap.clear();
            for(Node node:collections){
                fatherMap.put(node,node);
                sizeMap.put(node,1);
            }
        }

        public void union(Node a, Node b){
            if(a==null||b==null){
                return;
            }
           Node aHead = findFather(a);
           Node bHead = findFather(b);
            if(aHead != bHead){
               int aSize = sizeMap.get(aHead);
               int  bSize = sizeMap.get(bHead);
                if(aSize < bSize){
                    fatherMap.put(aHead,bHead);
                    sizeMap.put(bHead,aSize+bSize);
                    sizeMap.remove(aHead);
                }else{
                    fatherMap.put(bHead,aHead);
                    sizeMap.put(aHead,aSize+bSize);
                    sizeMap.remove(bHead);
                }
            }
        }

        // public Node findFather(Node node){
        //     Node father = fatherMap.get(node);
        //     while(father != node){
        //         father = fatherMap.get(father);
        //     }
        //     fatherMap.put(node,father);
        //     return father;
        // }

        //循环版
        private Node findFather(Node co){
            //findBoss中之所以用栈, 是为了路径压缩! 可以加速Find操作
            Stack<Node> s = new Stack<>();
            //如果当前元素不是掌门(掌门的表现形式是什么? 在bossMap中,key和value一样的是掌门)
            while(co != fatherMap.get(co)){
                s.push(co);
                co = fatherMap.get(co);
            }
            //路径压缩, 让树更扁
            while(!s.isEmpty()){
                fatherMap.put(s.pop(), co);
            }
            return co;
        }
    }
}
```

```java
//深度优先遍历算法，就是一条路走到黑，再返回起点
class Solution {
    public int numIslands(char[][] grid) {
        //if(grid==null||grid[0]==null||grid.length==0||grid[0].length==0)return 0;
        if(grid == null ||grid.length==0|| grid[0] == null) return 0;
        int res = 0;
        int r = grid.length;
        int c = grid[0].length;
        int[][] arr = new int[r][c];

        for(int i=0;i<r;i++){
            for(int j=0;j<c;j++){
                if(arr[i][j]==0&&grid[i][j]!='0'){
                    res++;
                    dfs(grid,arr,i,j);
                }
            }
        }
        return res;

    }

    public void dfs(char[][] grid,int[][] arr ,int i,int j){
        if(i<0||i>=grid.length||j<0||j>=grid[0].length||arr[i][j]==1||grid[i][j]=='0')
            return;
        if(grid[i][j]=='1'){
            arr[i][j]=1;
            dfs(grid,arr,i-1,j);
            dfs(grid,arr,i+1,j);
            dfs(grid,arr,i,j-1);
            dfs(grid,arr,i,j+1);
       }

    }
}
```


## 参考解法：
**算法核心框架：深度搜索+递归**
1. 深度搜索：整个刀鱼是一个"二维矩阵",使用深度优先搜索遍历一遍整个矩阵；
2. 递归：当扫描到'1'时，调用infect()函数，将该'1'上下左右相邻的位置都感染为'2'，递归调用infect()的结果是从起时的'1'开始而相连的一片'1'都被感染为'2'

