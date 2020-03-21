### 题目
面试题34. 二叉树中和为某一值的路径

### 解题思路
```java
class Solution {
    LinkedList<List<Integer>> list = new LinkedList<>();
    LinkedList<Integer> tmpList = new LinkedList<>();

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        //if(root == null)return list;
        dfs(root, sum,0);
        return list;
    }

    public void dfs(TreeNode node, int sum, int res){
        if(node==null) return;
        res+=node.val;
        tmpList.add(node.val);

        if(res==sum && node.left==null && node.right==null){
            list.add(new LinkedList(tmpList));
        }
        dfs(node.left,sum, res);
        dfs(node.right,sum,res);
        tmpList.removeLast();
    }
}
```