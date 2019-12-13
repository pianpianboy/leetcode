# 题目
使用数组实现队列和栈

## 解题思路
- 使用数组实现队列
- 使用数组实现栈
-

```java
//使用数组实现栈
public class ArrayToStack {

    public static class ArrayStack{

        private Integer[] arr;  // 使用数组表示栈这个容器
        private Integer index;  // 使用index表示栈当前可以存放元素的下标

        public ArrayStack(Integer initSize) {
            if(initSize < 0) {
                throw new IllegalArgumentException("stack's size should > 0");
            }
            arr = new Integer[initSize];
            index = 0;
        }

        // peek(): 只返回栈顶的值，而不弹出栈顶的元素
        public Integer peek() {
            if(index == 0) {
                return null;
            }
            return arr[index - 1];
        }

        // push(): 压栈
        public void push(Integer element) {
            if(index == arr.length) {
                throw new ArrayIndexOutOfBoundsException("This stack is full!");
            }
            // 先给index下标处赋值，然后index+1
            arr[index++] = element;
        }

        // pop(): 弹栈
        public Integer pop() {
            if(index == 0) {
                throw new ArrayIndexOutOfBoundsException("This stack is empty!");
            }
            return arr[--index];
        }
    }
}
```


```java
//数组实现队列
public class ArrayToQueue {

    public static class ArrayQueue {

        private Integer[] arr;  // 使用数组表示一个队列
        private Integer size;   // size表示队列中元素的个数
        private Integer start;  // start表示从队列中取数的索引
        private Integer end;    // end表示从队列中放数的索引
        // start被size约束，end被size约束，但start和end没有关系，一个只管取数，一个只管放数

        public ArrayQueue(int initSize) {
            if(initSize < 0) {
                throw new IllegalArgumentException("queue's size should > 0");
            }
            arr = new Integer[initSize];
            size = 0;
            start = 0;
            end = 0;
        }

        public Integer peek() {
            if(size == 0) {
                return null;
            }
            return arr[start];
        }

        public void push(int element) {
            if(size == arr.length) {
                throw new ArrayIndexOutOfBoundsException("This queue is full!");
            }
            size++;
            arr[end] = element;
            // 如果当前元素是放到数组的最后一个位置处，那么把end置为0，表示下次放数的时候从数组第一个位置开始放
            end = (end == arr.length - 1 ? 0 : end + 1);
        }

        public Integer poll() {
            if(size == 0) {
                throw new ArrayIndexOutOfBoundsException("This queue is empty!");
            }
            size--;
            int tmp = start;
            start = (start == arr.length - 1 ? 0 : start + 1);
            return arr[tmp];
        }

    }

}
```
