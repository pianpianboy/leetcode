### 题目
面试题26. 树的子结构

### 解题思路
- 先将Tree序列化为字符串然后使用KMP算法，但是这种算法不能通过case
    + 比如中序遍历 TreeNode A【2,3,2,1】 TreeNode B【3,null,2,2】的结果为：
    + #1#3#2#2# 、#3#2#2#
    + 序列化后的结果使用kmp返回为ture
    + 但是实际上B不是A的子树
- 递归
```java
// 递归
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
    public  boolean isSubStructure(TreeNode A, TreeNode B) {
        if(A==null||B==null) return false;
        if(A.val==B.val){
            return isSub(A,B);
        }
        return isSubStructure(A.left,B)||isSubStructure(A.right,B);
    }

    public boolean isSub(TreeNode a,TreeNode b){
        if(b==null){
            return true;
        }else if(a==null&&b!=null){
            return false;
        }
        if(a.val != b.val)
            return false;
        else{
            return isSub(a.left,b.left)&&isSub(a.right,b.right);
        }
    }

}
```

```java
//kmp case不过
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
    public static boolean isSubStructure(TreeNode A, TreeNode B) {
        if(A==null||B==null) return false;

        StringBuilder sba = new StringBuilder();
        StringBuilder sbb = new StringBuilder();
        preOrder(A,sba);
        preOrder(B,sbb);
        String aStr = sba.toString();
        String bStr = sbb.toString();
        if(aStr.length()<bStr.length()) return false;

        return KMP(aStr,bStr);
    }
    public static void preOrder(TreeNode node,StringBuilder sb){
        if(node == null){
            sb.append("#");
            return;
        }
        sb.append(node.val);
        preOrder(node.left,sb);

        preOrder(node.right,sb);
    }

    public static boolean KMP(String str,String match){
        int i=0;
        int j =0;
        int lenStr = str.length();
        int lenMatch = match.length();
        int[] next = getNext(match);
        while(i<lenStr&&j<lenMatch){
            if(str.charAt(i)==match.charAt(j)){
                i++;
                j++;
            }else if(next[j]==-1){
                i++;
            }else{
                j = next[j];
            }
        }
        if(j==lenMatch)
            return true;
        else
            return false;
    }

    public static int[] getNext(String str){
        int[] next = new int[str.length()];
        next[0] = -1;
        if(str.length()<2){
            return next;
        }
        next[1] = 0;
        for(int i=2;i<str.length();i++){
            int j = next[i-1];
            while(j>=0){
                if(str.charAt(i-1)==str.charAt(j)){
                    next[i] = next[i-1]+1;
                    break;
                }else if(next[j]==-1){
                    break;
                }else{
                    j = next[j];
                }
            }
        }
        return next;
    }
}
```
