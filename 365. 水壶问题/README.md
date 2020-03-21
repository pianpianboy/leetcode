###题目
365. 水壶问题

#### 解题思路
BFS广度优先遍历

```java
class Solution {
    public boolean canMeasureWater(int x, int y, int z) {
        if(x+y< z||z<0) return false;

        HashSet<Integer> set = new HashSet<>();
        Queue<Integer> queue= new LinkedList<>();
        queue.offer(0);

        while(!queue.isEmpty()){
            int val = queue.poll();
            if(val+x<=x+y&&set.add(val+x)){
                queue.offer(val+x);
            }
            if(val+y<=x+y&&set.add(val+y)){
                queue.offer(val+y);
            }
            if(val-x>=0 && set.add(val-x)){
                queue.offer(val-x);
            }
            if(val-y>=0 && set.add(val-y)){
                queue.offer(val-y);
            }
            if(set.contains(z)){
                return true;
            }
        }
        return false;
    }
}

```
