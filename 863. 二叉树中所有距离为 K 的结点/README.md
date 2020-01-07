# 题目
863. 二叉树中所有距离为 K 的结点

## 解题思路
- 公共祖先与距离计算
    + 本题涉及到二叉树中距离的求解
    + 那么如何在二叉树中求两个节点的距离呢？
        * 找到两个结点的最近公共祖先，然后分别计算结点与最近公共祖先的高度差，并相加。
    + 为了获取所有结点到目标结点的距离，需要获取目标结点与所有结点的最近公共祖先
        * 首先考虑目标结点的祖先， **目标结点的祖先即从根节点到目标结点这一路径**
        * 对于每个祖先，目标结点到其的距离为i，那么其他节点到当前祖先结点的距离（高度）应为k-i,由此便很容易计算出所有满足题意的结点
- DFS
    + DFS = 深度优先遍历 = 先序遍历
- BFS
    + BFS = 广度优先遍历 = 分层遍历（辅助使用FIFIO Queue队列）
    + 具体实现步骤：
        * 将每个结点的父亲结点及子节点全部求出来即Map<Node,List<TreeNode>>
            - 如何求一个结点的父亲结点？
        * 使用一个visited 保存已经访问过的Node,避免重复
        * 使用二叉树的按层遍历（按层遍历需要使用FIFO Queue队列）

![Demo](images/leetcode863BFS.png)

```java
//dfs
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    //深度优先遍历其实就是先序变量遍历
    HashMap<TreeNode,Integer> map = new HashMap<>();
    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        List<Integer> list = new ArrayList<>();
        //找到离跟结点到目标结点路径上所有的结点（利用递归的回溯过程）设置map.add(root,distanceToTarget)
        findTargetPath(root,target);
        //dfs深度优先遍历，找到离跟结点距离为k的所有结点
        dfs(root,target,K,map.get(root),list);
        return list;
    }

    public int findTargetPath(TreeNode node, TreeNode target){
        if(node==null) return -1;
        if(node==target){
            map.put(node,0);
            return 0;
        }
        int res = findTargetPath(node.left, target);
        if(res>=0){
            map.put(node, ++res);
            return res;
        }
        res = findTargetPath(node.right,target);
        if(res>=0){
            map.put(node,++res);
            return res;
        }
        return -1;
    }

    public void dfs(TreeNode node, TreeNode target,int K, int length, List<Integer> list){
        if(node==null) return ;
        if(map.containsKey(node)) {
            length = map.get(node);
        }
        if(length == K){
            list.add(node.val);
            //return; //此处不能直接返回，因为如果在 路径中出现了length==2,就可能直接返回，而漏掉了target下面距离为2的点å
        }
        dfs(node.left,target,K,length+1,list);
        dfs(node.right,target,K,length+1,list);
    }

}
```


```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    //广度优先遍历其实就是按层遍历
    HashMap<TreeNode,List<TreeNode>> map = new HashMap<>();//map其实就是新的多叉树
    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        List<Integer> list = new ArrayList<>();
        if(root==null||K<0){
            return list;
        }
        buildMap(root,null);
        if(!map.containsKey(target)){
            return list;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(target);//注意此处不能写成queue.add(root); 我们是从target开始处理的，也就是说target是新生成的多叉树的头结点
        Set<TreeNode> visited = new HashSet<>();
        visited.add(target);
        while(!queue.isEmpty()){
            int size = queue.size();

            if(K==0){
                for(int i=0;i<size;i++){
                    list.add(queue.poll().val);//在多叉树中，第K层的所有结点即为所求的结果
                }
                return list;
            }
            for(int i=0;i<size;i++){
                TreeNode node = queue.poll();
                for(TreeNode childNode : map.get(node)){//在新的多叉树中取出node的"子节点""
                    if(visited.contains(childNode)){
                        continue;
                    }
                    visited.add(childNode);
                    queue.add(childNode);
                }
            }
            K--;
        }
        return list;
    }

    //先序遍历 找出所有结点的父节点和直接点，将二叉树变为多叉树
    public void buildMap(TreeNode node, TreeNode parent){
        if(node==null) return;
        if(!map.containsKey(node)){
            //注意只有在map中没有node结点，也就是第一次遍历到node结点的时候去处理下面逻辑，
            //因为递归会有重复回到node结点的情况,所以这样做是为了避免重复
            map.put(node,new ArrayList<TreeNode>());
            if(parent!=null){
                map.get(node).add(parent);
                map.get(parent).add(node);
            }
        }
        buildMap(node.left,node);
        buildMap(node.right,node);
    }

}
```