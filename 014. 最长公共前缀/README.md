# 解题思路

# 自己的思路：
    水平扫描--及每次取出所有列的值进行比对（注意有的列不存在的情况），如果有不相等的情况即返回，结束循环。
    自己在解答的时候出现的错误：
    **str.charAt(i);写成str.atChar(i)**
    **str.substring();写成str.subString()**
    在判断列存不存在的时候未考虑字符串长度与字符下标相差为1的情况：
     if(strs[j].length()-1<i || strs[j].charAt(i)!= c )写成
      if(strs[j].length()<i || strs[j].charAt(i)!= c ) 导致指针异常。