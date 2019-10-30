# 题目
502. IPO

## 解题思路
- 使用大根堆和小根堆
    +
- 2


```java
class Solution {
    public static int findMaximizedCapital(int k, int W, int[] Profits, int[] Capital) {

        if(k>Profits.length) k= Profits.length;
        //利润 大根堆
        PriorityQueue<Node> profitQ = new PriorityQueue<>(
                new Comparator<Node>(){

                    @Override
                    public int compare(Node o1, Node o2) {
                        return o2.profit-o1.profit;
                    }
                }
        );
        //成本 小根堆
        PriorityQueue<Node> capacityQ = new PriorityQueue<>(
                new Comparator<Node>(){
                    @Override
                    public int compare(Node o1,Node o2){
                        return o1.capital-o2.capital;
                    }
                }
        );

        //遍历数组，将Node先放入成本队列
        for(int i=0;i<Capital.length;i++){
            capacityQ.add(new Node(Profits[i],Capital[i]));
        }
        while(k-- >0){
            while(capacityQ.size()>0 && capacityQ.peek().capital<=W){
                profitQ.add(capacityQ.poll());
            }
            if(profitQ.size()>0){
                W = W + profitQ.poll().profit;
            }
        }

        return W;
    }

}
class Node{
    int profit;
    int capital;
    public Node(int profit,int capital){
        this.profit = profit;
        this.capital = capital;
    }
}
```