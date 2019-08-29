# 解题思路

## 我的解题思路
- 解法一： 使用大顶堆(即优先级队列)
    - 测试数据bbbbbcaca
    - 构建字符次数数组
        - 先遍历字符串，将字符串中的数据出现次数存数组，下标为字符-'a'，因此数组的长度为26（这里也是 一个小技巧，用一维数组存储二维的数据）
    - 构建大顶堆
        - 然后将key-value 对象（key=字符出现的次数，value=字符即下标+'a'）存入优先级队列
        - 若出现的次数大于字符串的一般，直接返回 ""
    - 最后通过优先级队列重构字符串
        - 每次从优先级队列中拿出最大的两个取出字符进行拼接，然后将次数都减去1，然后再次放回优先级队列
    - 总结，这样输出是为了总能有一个字母可以把频率最多的字母隔开，优先级队列 是为了维持存储key-value对象的集合总是可以降序输出。

```java
class Solution {
    public String reorganizeString(String S) {
        if(S==null||S.length()<=1) return S;

        PriorityQueue<KeyValue> queue = new PriorityQueue<>(26,new Comparator<KeyValue>(){
            @Override
            public int compare(KeyValue k1,KeyValue k2){
                return k2.count-k1.count;
            }
        });

        //构建统计出现次数的数组
        int[] counts = new int[26];
        for(int i=0;i<S.length();i++){
            counts[S.charAt(i)-'a']++;
        }

        //构建优先级 队列
        for(int i=0;i<26;i++){
            if(counts[i]>((S.length()+1)/2)){//注意此处容易出错
                return "";
            }else if( counts[i]!=0){
                queue.add(new KeyValue((char)(i+'a'),counts[i]));
            }
        }

        //通过优先级队列构建字符串
        StringBuilder sb = new StringBuilder();

        while(queue.size()>1){
            KeyValue first = queue.poll();
            KeyValue second = queue.poll();
            sb.append(first.letter);
            sb.append(second.letter);

            if(--first.count>0) queue.add(first);
            if(--second.count>0) queue.add(second);
        }
        if(queue.size()>0){
            sb.append(queue.poll().letter);
        }

        return sb.toString();
    }

    public class KeyValue{
        char letter;
        int count;

        KeyValue(char letter,int count){
            this.letter = letter;
            this.count = count;
        }
    }
}

```
