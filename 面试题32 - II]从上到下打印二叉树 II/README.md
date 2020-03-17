### 题目


### 解题思路
```java
class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        if(root ==null )return list;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        boolean flag = true;//从左到有打印

        while(!queue.isEmpty()){
            int size = queue.size();
            List<Integer> tmp = new ArrayList<>();
            while(size>0){
                TreeNode node = queue.poll();

                tmp.add(node.val);
                if(node.left!=null)queue.add(node.left);
                if(node.right!=null)queue.add(node.right);
                size--;
            }
            if(!flag){
                List<Integer> newTmp = new ArrayList<>();
                for(int i= tmp.size()-1;i>=0;i--){
                    newTmp.add(tmp.get(i));
                }
                list.add(newTmp);

            }else{
                list.add(tmp);
            }
            flag = !flag;
        }
        return list;
    }
}
```
