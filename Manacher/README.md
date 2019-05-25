# 解题思路

# 参考解题思路
1、manacherString() 该方法解决了长度为偶数的情况
ABAB->#A#B#A#B#
ABA->#A#B#A#
2、计算最大回文长度 maxLength()

3、求每个下标对应的回文半径
    更新数组中每个下标i对应 值 的回文半径pArr[i]，<!--  -->
    1）、利用当前index所对应的pR值和i下标对应的 pAee[2*index-i](即i'对应的回文半径)和pR-char[i]的差值进行比较，两者取最小值，如果i>=pR 回文半径为1
    2）、只要i+pArr[i]不越界，比较charArr[i + pArr[i]] == charArr[i - pArr[i]]
    3）、更新pR及index