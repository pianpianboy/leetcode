# 解题思路

## 自己的解题思路
想着分为两次循环解决问题：
1. 第一次循环，将所有重复的值全部标记为A，如0，0，1，1，2，3，3，4变为0，A，1，A，2，3，A，4
2. 第二次循环记录每个A的位置为index，然后找下一个不为A的地方，交换；

但是代码一直过不了。

## 参考解题思路
**使用双指针**去重

