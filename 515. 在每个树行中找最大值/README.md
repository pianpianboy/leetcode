# 题目
515. 在每个树行中找最大值

## 解题思路
- 迭代
    + 使用辅助的list(LinkedList)实现二叉树按层遍历
    + 必须使用linkedList，只有linkedList才有pop()和poll()方法
- 递归
    + 是利用先序遍历+level来实现"分层处理"
    + 代码实现的是先将二叉树最左的一条向左下的边加入到list中（按照level:treenode加入）
    + 如何判断是否是最左的一条边上的结点呢？ if(level==list.size())
    + 然后遍历的过程中如果遇到同level结点，更新大值

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
    public List<Integer> largestValues(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if(root==null) return list;

        LinkedList<TreeNode> tmp = new LinkedList<>();
        tmp.add(root);
        while(!tmp.isEmpty()){
            int size = tmp.size();
            int max = Integer.MIN_VALUE;
            while(size>0){
                TreeNode node  = tmp.pop();
                max = Math.max(max,node.val);
                if(node.left!=null){
                    tmp.add(node.left);
                }
                if(node.right!=null){
                    tmp.add(node.right);
                }
                size--;
            }
            list.add(max);
        }
        return list;
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
    public List<Integer> largestValues(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if(root==null) return list;

        proOrder(0,list,root);
        return list;
    }
    public void proOrder(int level,List<Integer>list,TreeNode node){
        if(level==list.size()){
            list.add(level,node.val);
        }else{
            list.set(level,Math.max(list.get(level),node.val));
        }
        //先序遍历
        if(node.left!=null){
            proOrder(level+1,list,node.left);
        }
        if(node.right!=null){
            proOrder(level+1,list,node.right);
        }
    }
}
```