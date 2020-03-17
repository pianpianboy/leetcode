### 题目
面试题21. 调整数组顺序使奇数位于偶数前面

### 解题思路
- 暴力解：新建两个数组一个放奇数一个放偶数
- 首尾指针：

```java
//暴力解
class Solution {
    public int[] exchange(int[] nums) {
        if(nums==null) return nums;
        int len = nums.length;

        int[] tmp1 = new int[len];
        int index1=0;
        int index2=0;
        int[] tmp2 = new int[len];

        for(int i=0;i<len;i++){
            if(nums[i]%2==0){
                tmp2[index2++] = nums[i];
            }else{
                tmp1[index1++] = nums[i];
            }
        }
        for(int i=0;i<index2;i++){
            tmp1[index1++] = tmp2[i];
        }
        return tmp1;
    }
}
```

```java
//首尾指针
class Solution {
    public int[] exchange(int[] nums) {
        if(nums==null) return nums;
        int len = nums.length;

        int head=0;
        int tail = nums.length-1;
        while(head<tail){

            if(nums[head]%2==0&& nums[tail]%2!=0){
                int tmp = nums[head];
                nums[head] = nums[tail];
                nums[tail] = tmp;
                head++;
                tail--;
            }else{
                if(nums[head]%2!=0){
                    head++;
                }
                if(nums[tail]%2==0){
                    tail--;
                }
            }
        }
        return nums;
    }
}
```

```java
class Solution {
    public int[] exchange(int[] nums) {
        if(nums==null) return nums;
        int len = nums.length;

        int head=0;
        int tail = nums.length-1;
        while(head<tail){

            if(((nums[head]&1)==0)&& ((nums[tail]&1)!=0)){
                int tmp = nums[head];
                nums[head] = nums[tail];
                nums[tail] = tmp;
                head++;
                tail--;
            }else{
                if((nums[head]&1)!=0){
                    head++;
                }
                if((nums[tail]&1)==0){
                    tail--;
                }
            }
        }
        return nums;
    }
}
```

优化
```java
class Solution {
    public int[] exchange(int[] nums) {
        if(nums==null) return nums;
        int len = nums.length;

        int head=0;
        int tail = nums.length-1;
        while(head<tail){
            if((nums[head]&1)!=0){
                head++;
                continue;
            }
            if((nums[tail]&1)==0){
                tail--;
                continue;
            }
            int tmp = nums[head];
            nums[head] = nums[tail];
            nums[tail] = tmp;
            head++;
            tail--;
        }
        return nums;
    }
}
```