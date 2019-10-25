# UnionFind并查集的具体实现方式

## 实现逻辑思路
1. 实现节点Node class
2. 实现UnionFind class类定义
    - UnionFind构造函数
    - Union()集合合并方法
    - init() UnionFind初始化方法
    - findFather() 查找father节点


## 具体代码思想
```java
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

```