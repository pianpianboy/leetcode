## 题目
1008. 先序遍历构造二叉树

### 解题思路
- 递归的思想
    + 因为是先序遍历，则第一个值肯定是 根节点root
    + 根据搜索二叉树的特性，根节点root的有孩子一定比他大
    + 遍历数组找出第一个比root.val大的值的下标 i
    + 则start+1->i之间为root的左子树
    + i->end之间为root的右子树

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
    public TreeNode bstFromPreorder(int[] preorder) {
        if(preorder == null|| preorder.length<=0) return null;
        return help(preorder,0,preorder.length-1);
    }

    public TreeNode help(int[] preorder, int start, int end){
        if(start ==end){
            return new TreeNode(preorder[start]);
        }
        if(start>end) return null;

        int val = preorder[start];
        TreeNode node  = new TreeNode(val);

        for(int i=start+1;i<=end;i++){
            if(preorder[i] > val){
                node.left = help(preorder,start+1,i-1);
                node.right = help(preorder,i, end);
                return node;
            }
        }
        node.left = help(preorder,start+1,end);//注意此处要处理只有左子树的情况 比如4、3、2、1
        return node;
    }
}
```
