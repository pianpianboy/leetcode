package com.pianpianboy.leetcode.leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @ClassName Test3
 * @Description TODO
 * @Author liaomengjie
 * @Date 2020-03-19 12:25
 */
public class Test3 {
    public static void main(String[] args) {
        TreeNode head = new TreeNode(1);
        head.left = new TreeNode(2);
        head.right = new TreeNode(3);
        head.right.left = new TreeNode(4);
        head.right.right = new TreeNode(5);
        System.out.println(serialize(head));
    }

    public static String serialize(TreeNode root) {
        if(root==null) return "[]";
        Queue<TreeNode> queue = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        queue.offer(root);

        while(!queue.isEmpty()){
            TreeNode node = queue.poll();
            sb.append("null,");
            sb.append(node.val).append(",");
            if(node.left != null){
                queue.offer(node.left);
            }
            if(node.right != null){
                queue.offer(node.right);
            }
        }
        String str = sb.toString();
        System.out.println(str.substring(0,str.length()-1)+"]");
        return str.substring(0,str.length()-1)+"]";
    }
}
