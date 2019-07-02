# 解题思路

## 我的解题思路
- 使用双指针来解题
具体的思路就是，先排序(去重)，然后4sum转换为 3sum（固定一个值），再3sum装换为2sum。coding中最难的就是去重的处理，分别要进行四次去重

```java
class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> list = new LinkedList<>();
        if(nums==null||nums.length<4)return list;

        Arrays.sort(nums);

        for(int i=0;i<nums.length;i++){
            if(i==0||nums[i]!=nums[i-1]){
                for(int j=i+1;j<nums.length;j++){
                    if(j==i+1||nums[j]!=nums[j-1]){
                        int k = j+1;
                        int n = nums.length-1;
                        int sum = target - nums[i]-nums[j];
                        while(k<n){
                            if((k!=j+1&&nums[k]==nums[k-1])||nums[k]+nums[n]<sum){
                                k++;
                                continue;
                            }
                            if((n!=nums.length-1&&nums[n]==nums[n+1])||nums[k]+nums[n]>sum)
                            {
                                n--;
                                continue;
                            }
                            if(nums[k]+nums[n] == sum){
                                List<Integer> tmp = new ArrayList<>();
                                tmp.add(nums[i]);
                                tmp.add(nums[j]);
                                tmp.add(nums[k]);
                                tmp.add(nums[n]);
                                list.add(tmp);
                                k++;
                                n--;
                            }
                        }
                    }
                }
            }
        }
        return list;
    }
}
```

- 使用K数和来解决问题
ksum的解决通用办法，使用递归
```java
class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> list = new LinkedList<>();
        if(nums==null||nums.length<4)return list;

        Arrays.sort(nums);
        return ksum(nums,target,4,0);
    }

    public List<List<Integer>> ksum(int[] nums,int target,int k,int start){
        List<List<Integer>> list = new ArrayList<>();
        if(start>=nums.length)return list;

        if(k==2){//处理两数之和问题
            //使用双指针来解决
            int left = start;
            int right = nums.length-1;
            while(left<right){
                if(nums[left]+nums[right]==target){
                    List<Integer> tmp = new ArrayList<>();
                    tmp.add(nums[left]);
                    tmp.add(nums[right]);
                    list.add(tmp);
                    //去重逻辑
                    while(left<right&&nums[left]==nums[left+1]){
                        left++;
                    }
                    while(right>left&&nums[right]==nums[right-1]){
                        right--;
                    }
                    left++;
                    right--;
                }
                else if(nums[left]+nums[right]<target){
                    left++;
                    //此处不用单独再去重
                    // while(left<right&&nums[left]==nums[left+1]){
                    //     left++;
                    // }
                }else
                    right--;
                    //此处不用单独再去重
                    // while(right>left&&nums[right]==nums[right-1]){
                    //     right--;
                    // }
            }
            return list;
        }

        if(k>2){
            for(int i=start;i<nums.length-k+1;i++){
                List<List<Integer>>tmp = ksum(nums,target-nums[i],k-1,i+1);
                if(tmp!=null){
                    for(List<Integer>l:tmp){
                        l.add(0,nums[i]);
                        // list.add(l);
                    }
                    list.addAll(tmp);
                }
                //去重
                while(i<nums.length-1&&nums[i]==nums[i+1]){
                    i++;
                }
            }
        }
        return list;
    }
}
```