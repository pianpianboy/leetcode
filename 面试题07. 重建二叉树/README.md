### 题目
面试题07. 重建二叉树

####
解题思路
```java
class Solution {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return help(preorder,0,preorder.length-1,inorder,0,inorder.length-1);
    }

    public TreeNode help(int[] preorder,int preleft, int preright, int[] inorder,int inleft, int inright){
        if(preright<preleft||inright<inleft) return null;
        int val = preorder[preleft];
        TreeNode node = new TreeNode(val);
        int i = inleft;
        while(inorder[i]!=val&&i<=inright){
            i++;
        }
        node.left = help(preorder,preleft+1,preleft+(i-inleft),inorder,inleft,i-1);
        node.right= help(preorder,preleft+(i-inleft)+1,preright,inorder,i+1,inright);
        return node;
    }
}
```

```java
//优化版本，用空间换时间
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
    private HashMap<Integer,Integer> map = new HashMap<>();
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        for(int i=0; i<inorder.length;i++){
            map.put(inorder[i],i);
        }

        return help(preorder,0,preorder.length-1,inorder,0,inorder.length-1);
    }

    public TreeNode help(int[] preorder,int preleft, int preright, int[] inorder,int inleft, int inright){
        if(preright<preleft||inright<inleft) return null;
        int val = preorder[preleft];
        TreeNode node = new TreeNode(val);
        int i = map.get(val);
        node.left = help(preorder,preleft+1,preleft+(i-inleft),inorder,inleft,i-1);
        node.right= help(preorder,preleft+(i-inleft)+1,preright,inorder,i+1,inright);
        return node;
    }
}
```