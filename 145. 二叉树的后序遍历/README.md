# 解题思路

## 我的解题思路
先用递归的方法实现，后使用非递归的方法实现。
非递归的实现：
后续遍历二叉树为：左-右-中； 而前序遍历的时候为中-左-右；
改造前序遍历为中-右-左；使用栈存储中-右-左，从栈中弹出的即为：左-右-中
使用两个栈就可以实现二叉树的非递归后序遍历。