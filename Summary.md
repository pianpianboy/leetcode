# 总结

## 数组
1. 将数组二分或者从中间分为左右两部分 分别进行处理例如：548将数组分割成和相等的子数组、
2. 使用类似kmp的next的数组，使用sum数组保存 下标0到每个点i的和sum[i];例如：548将数组分割成和相等的子数组、
3. 使用hashMap,左部分遍历的时候map.add(val)/set.add(val),然后右半部分验证 map.containsKey(val)/set.contains(val) ;例如：548将数组分割成和相等的子数组、