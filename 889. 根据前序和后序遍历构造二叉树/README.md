# 题目
889. 根据前序和后序遍历构造二叉树

## 解题思路
- 使用递归的思想
    + 将二叉树分为 前序：根+ 左+ 右； 后序： 左 + 右 + 根
    + 前序中"左"的第一个字符肯定也是根节点node，这个节点node在后续遍历中的"左+node+右"中间,找出这个node的下标


```java
class Solution {
    public TreeNode constructFromPrePost(int[] pre, int[] post) {
        if(pre==null||post==null){
            return null;
        }
        int prelen= pre.length;
        int postlen = post.length;

        return help(pre,post,0,prelen-1,0,postlen-1);
    }
    public TreeNode help(int[] pre, int[] post, int preStart, int preEnd, int postStart, int postEnd){
        if(preStart == preEnd) return new TreeNode(pre[preStart]);
        if(preStart>preEnd||postStart>postEnd){
            return null;
        }
        TreeNode node = new TreeNode(pre[preStart]);
        int val = pre[preStart+1];

        for(int i=postStart;i<postEnd;i++){
            if(post[i] == val){
                int cap = i - postStart;
                node.left = help(pre, post,preStart+1,preStart+cap+1, postStart,i);
                node.right = help(pre, post,preStart+cap+2,preEnd,i+1,postEnd-1);
                break;
            }
        }
        return node;


        // for(int i = postStart; i <= postEnd; i++){
        //     if(post[i] == val){
        //         int length = i - postStart;
        //         node.left = help(pre, post, preStart + 1, preStart + length + 1, postStart, i);
        //         node.right = help(pre, post, preStart + length + 2, preEnd, i + 1, postEnd - 1);
        //         return node;
        //     }
        // }
        // return null;
    }
}
```
