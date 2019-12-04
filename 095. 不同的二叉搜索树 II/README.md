# 题目
95. 不同的二叉搜索树 II

## 解题思路
- 递归 ,利用查找二叉树的性质
- 动态规划
    + 任何递归都可以改成动态规划---动态规划就是空间换时间
    + i属于1到n,则以i为根节点
    + dp[i-1]表示1到i-1的所有List<TreeNode>
    + 这个时候需要处理右边的 i+1到n之间的组合可能性，i+1到n与dp[n-i]有啥关系
        * dp[n-i]表示1到n-i的所有组合，
        * i+1到n的可能性组 == dp[n-i]中每个值加上i

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
    public List<TreeNode> generateTrees(int n) {
        //使用递归的解法：利用到了查找二叉树的性质
        List<TreeNode> ans = new ArrayList<>();
        if(n==0) return ans;

        ans = getAns(1,n);
        return ans;
    }

    public List<TreeNode> getAns(int start,int end){
        List<TreeNode> ans = new ArrayList<>();

        if(start>end){//此时没有数字，将null加入结果
            ans.add(null);
            return ans;
        }
        if(start==end){//当前数字只有一个，将当前数字作为一棵树
            TreeNode node = new TreeNode(start);
            ans.add(node);
            return ans;
        }

        for(int i=start;i<=end;i++){
            List<TreeNode> leftTrees = getAns(start,i-1);
            List<TreeNode> rightTrees = getAns(i+1,end);

            //左子树右子树两两合并
            for(TreeNode leftTree:leftTrees){
                for(TreeNode rightTree:rightTrees){
                    TreeNode root = new TreeNode(i); //将每个结点作为根节点
                    root.left = leftTree;
                    root.right = rightTree;
                    ans.add(root);
                }
            }
        }
        return ans;
    }
}
```

```java
//DP
class Solution {
    public List<TreeNode> generateTrees(int n) {
        List<TreeNode> ans  = new ArrayList<>();
        if(n==0) return ans;

        List<TreeNode>[] dp = new ArrayList[n+1];
        //边界条件
        dp[0] = new ArrayList<TreeNode>();
        dp[0].add(null);

        for(int len =1;len<=n;len++){
            List<TreeNode> tmp  = new ArrayList<>();
            for(int i=1;i<=len;i++){
                //以i为root结点
                List<TreeNode>leftTrees = dp[i-1];
                List<TreeNode> rightTrees = dp[len-i];//[i+1,len]->[1,len-i]
                for(TreeNode leftTree: leftTrees){
                    for(TreeNode rightTree:rightTrees){
                        TreeNode root = new TreeNode(i);
                        root.left = leftTree;
                        root.right = clone(rightTree,i);
                        tmp.add(root);
                    }
                }
            }
            dp[len] = tmp;
        }
        return dp[n];
    }
    public TreeNode clone(TreeNode treeNode,int offset){
        if(treeNode ==null)
        return null;

        TreeNode node = new TreeNode(treeNode.val+offset);
        node.left = clone(treeNode.left,offset);
        node.right = clone(treeNode.right,offset);
        return node;
    }
}
```

