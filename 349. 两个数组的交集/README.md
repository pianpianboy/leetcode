# 解题思路

## 我的解题思路
- 求两个数组的交集，很容易想到先将数组进行排序，然后使用双指针遍历两个数组。
- 但是有个两个细节得考虑
    + 一个就是获取的结果怎么去重，可以考虑使用set集合
    + 当使用set集合后怎么将set集合转换为数组，set.toString(),但是set集合是Integer对象没办法转换为int。而set又没有set.get之类的获取值的方法，只能使用迭代器interator
- 优化的地方，将数组nums1和nums2都转化为set集合去重

```java
//未优化之前的方法
class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        HashSet<Integer> set = new HashSet<>();
        int len1 = nums1.length;
        int len2 = nums2.length;

        Arrays.sort(nums1);
        Arrays.sort(nums2);

        int index1= 0;
        int index2 =0;

        while(index1<len1&&index2<len2){
            if(nums1[index1]==nums2[index2]){
                set.add(nums1[index1]);
                index1++;
                index2++;
            }else if(nums1[index1]>nums2[index2]){
                index2++;
            }else{
                index1++;
            }
        }
        int len = set.size();
        Iterator<Integer> it = set.iterator();
        int[] arr = new int[len];
        int i=0;
        while(it.hasNext()){
            arr[i] = it.next();
            i++;
        }

        return arr;
    }
}
```

```java
class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set = new HashSet<>();
        int minLen = Math.min(nums1.length, nums2.length);
        int[] res = new int[minLen];

        for(int num : nums1){
            set.add(num);
        }

        int k = 0;
        for(int num : nums2){
            if(set.contains(num)){
                res[k++] = num;
                set.remove(num);
            }
        }

        return Arrays.copyOf(res, k);
    }
}

```

```java
class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> s1 = new HashSet<>();
        Set<Integer> s2 = new HashSet<>();

        for(int num : nums1){
            s1.add(num);
        }

        for(int num : nums2){
            s2.add(num);
        }

        s1.retainAll(s2); // 2个set交集

        int[] res = new int[s1.size()];
        int k = 0;
        for(int num : s1){
            res[k++] = num;
        }

        return res;
    }
}


```