
# 解题思路

## 我的解题思路
从前序与中序遍历序列构造二叉树。
提交的时候，
[1,2,3]
[2,3,1]
这个案例一直过不去，一直报栈移出的错误。代码为ErrorSolution.java;
解题的思路也是使用回溯，只不过判断preStart、preEnd和InStart和inEnd的时候使用的是index标记的。在上述案例的时候。会出现错误。


# 参考思路
关键在与前序遍历和中序遍历的特性
前序遍历：根节点是首元素
中序遍历：根节点左侧的值是其左节点，右侧的值是其右子树

因此我们首先要得到从前序序列中获取到根节点，然后遍历中序序列，找到根节点的位置，以此直到其左子树的范围；当我得到其左子树之后，事情就开始重复了，我们仍然需要根据前序序列中找到这颗左子树的根节点，然后再根据中序序列得到这颗左子树根节点的左右子树。。。。右子树同理。

因此实际上就是个回溯

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
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int preLen = preorder.length;
        int inLen = inorder.length;

        return help(preorder,inorder,0,preLen-1,0,inLen-1);
    }
    public TreeNode help(int[] preorder,int[] inorder,int preStart,int preEnd, int inStart,int inEnd){
        if(preStart == preEnd){
            return new TreeNode(preorder[preStart]);
        }
        if(preStart>preEnd||inStart>inEnd){
            return null;
        }

        int val = preorder[preStart];
        TreeNode node = new TreeNode(val);
        for(int i=inStart;i<=inEnd;i++){
            if(inorder[i] == val){
                int cap = i-inStart;
                node.left = help(preorder,inorder,preStart+1,preStart+cap,inStart,inStart+cap-1);
                node.right = help(preorder,inorder,preStart+cap+1,preEnd,i+1,inEnd);
                break;
            }
        }
        return node;
    }
}

```