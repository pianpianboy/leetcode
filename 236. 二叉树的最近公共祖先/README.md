# 题目
236. 二叉树的最近公共祖先

## 解题思路
- 后序遍历--递归（也可以说是深度优先遍历DFS）
    + 后序遍历二叉树，对遍历到的每个结点的状态mid ，右结点状态、左结点状态进行标记，
    + 最后根据右结点状态、左结点状态 及结点与p和q的值的对比(mid) 生成结点新的状态
- 先序遍历--使用递归（建立结点和结点父亲结点的映射关系）
    + 此方法有点类似于求两个链表List1和list2的相交点的值或者位置
    + 先建立整个二叉树的node——>parent的映射map buildMap
    + 将p的父亲结点保存于set(包括p自身)
    + 然后逐步遍历q的map q'=map.get ,然后判断set中是否set.contains(q),存在即q'为最近公共祖先

```java
class Solution {
    public static TreeNode res;
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        //后序遍历--递归
        postOrder(root,p,q);
        return res;
    }

    public boolean postOrder(TreeNode root, TreeNode p, TreeNode q){
        if(root==null){
            return false;
        }
        int left = postOrder(root.left,p,q)?1:0;
        int right = postOrder(root.right,p,q)?1:0;
        //后序
        int mid = (root.val == p.val||root.val==q.val)? 1:0;
        if(mid+right+left>=2){
            res = root;
        }
        return (mid+right+left>0);
    }
}
```

```java
//递归解法
class Solution {
    private HashMap<TreeNode , TreeNode> map  = new HashMap<>();

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        buildMap(root,null);
        HashSet<TreeNode> set = new HashSet<>();
        set.add(p);
        TreeNode tmp = map.get(p);
        while(tmp!=null){
            set.add(tmp);
            tmp = map.get(tmp);
        }

        TreeNode tmp2 = q;
        while(tmp2!=null){
            if(set.contains(tmp2)){
                return tmp2;
            }
            tmp2 = map.get(tmp2);
        }
        return null;
    }

    public void buildMap(TreeNode node, TreeNode parent){
        if(node==null) return ;
        if(parent!=null){
            map.put(node,parent);
        }

        if(node.left!=null){
            buildMap(node.left, node);
        }
        if(node.right!=null){
            buildMap(node.right,node);
        }
        return;
    }
}
```

```java
//对上述递归解法的优化
class Solution {
    private HashMap<TreeNode , TreeNode> map  = new HashMap<>();
    private int flag = 2;

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        buildMap(root,null,p,q);
        HashSet<TreeNode> set = new HashSet<>();
        set.add(p);
        TreeNode tmp = map.get(p);
        while(tmp!=null){
            set.add(tmp);
            tmp = map.get(tmp);
        }

        TreeNode tmp2 = q;
        while(tmp2!=null){
            if(set.contains(tmp2)){
                return tmp2;
            }
            tmp2 = map.get(tmp2);
        }
        return null;
    }

    public void buildMap(TreeNode node, TreeNode parent,TreeNode p, TreeNode q){
        if(node==null) return ;
        if(parent!=null){
            map.put(node,parent);
        }
        if(node==p){
            flag -= 1;
        }
        if(node==q){
            flag -= 1;
        }
        if(flag == 0){
            return;
        }

        if(node.left!=null){
            buildMap(node.left, node,p,q);
        }
        if(node.right!=null){
            buildMap(node.right,node,p,q);
        }
        return;
    }
}
```
