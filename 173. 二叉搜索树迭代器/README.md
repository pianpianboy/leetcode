# 题目
173. 二叉搜索树迭代器

## 解题思路
- 使用二叉搜索树的性质：中序遍历的结果是单调递增的
    + 效率34%
- 进行优化，
    + 将List<Integer> list = new LinkedList<>();
    + 变为：Queue<Integer> queue = new LinkedList<>();

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
class BSTIterator {

    List<Integer> list = new LinkedList<>();
    int num = 0;
    int preIndex = -1;

    public BSTIterator(TreeNode root) {
        if(root == null){
            num = 0;
        }
        inOrder(root);
    }
    public void inOrder(TreeNode root){
        if(root == null) return ;
        inOrder(root.left);
        list.add(root.val);
        num++;
        inOrder(root.right);
    }

    /** @return the next smallest number */
    public int next() {
         if(hasNext()){
             preIndex++;
            return list.get(preIndex);

        }else
            return 0;
    }

    /** @return whether we have a next smallest number */
    public boolean hasNext() {
        if(preIndex==list.size()-1){
            return false;
        }
        return true;
    }
}

/**
 * Your BSTIterator object will be instantiated and called as such:
 * BSTIterator obj = new BSTIterator(root);
 * int param_1 = obj.next();
 * boolean param_2 = obj.hasNext();
 */
```

```java
class BSTIterator {

    Queue<Integer> queue = new LinkedList<>();

    public BSTIterator(TreeNode root) {
        inorderTraversal(root);
    }

    private void inorderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
        inorderTraversal(root.left);
        queue.offer(root.val);
        inorderTraversal(root.right);
    }

    /** @return the next smallest number */
    public int next() {
        return queue.poll();
    }

    /** @return whether we have a next smallest number */
    public boolean hasNext() {
        return !queue.isEmpty();
    }
}

```

