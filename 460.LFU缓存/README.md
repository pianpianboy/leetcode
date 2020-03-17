### 题目
✔[460]LFU缓存

### 解题思路
```java
class LFUCache {
    HashMap<Integer,Node> map;
    HashMap<Integer,DLList> freq;
    int count;
    int capacity;
    int maxFrequency;
    public LFUCache(int capacity){
        this.capacity = capacity;
        this.count = 0;
        this.maxFrequency = 0;
        map = new HashMap<>();
        freq = new HashMap<>();
    }


    public int get(int key) {
        Node node = map.get(key);
        if(node==null) return -1;

        int preCnt = node.cnt;
        DLList preDLList = freq.get(preCnt);
        preDLList.removeNode(node);

        int curCnt = preCnt+1;
        DLList curDLList = freq.getOrDefault(curCnt, new DLList());


        maxFrequency = Math.max(maxFrequency,curCnt);
        //此处必须是先cnt++在将node加入list
        node.cnt++;
        curDLList.addNode(node);

        freq.put(preCnt,preDLList);
        freq.put(curCnt,curDLList);
        return node.value;
    }

    public void put(int key, int value) {
        if(capacity==0) return ;
        Node node = map.get(key);
        if(node!=null){
            node.value =value;
            get(key);
            return;
        }

        Node newNode = new Node(key,value);
        DLList curDLList = freq.getOrDefault(1,new DLList());
        curDLList.addNode(newNode);
        count++;
        if(count>capacity){
            //删除末尾的节点
            if(curDLList.len>1){
                curDLList.removeTail();
            }else{
                for(int i=2;i<=maxFrequency;i++){
                    if(freq.get(i)!=null&&freq.get(i).len>0){
                        freq.get(i).removeTail();
                        break;
                    }
                }
            }
            count--;
        }

        //容易忘记将修改后的list放会HashMap freq
        freq.put(1,curDLList);
    }

    public class Node{
        private Node pre;
        private int key;
        private int value;
        private Node next;
        int cnt;

        public Node(int key,int value){
            this.cnt = 1;
            this.key = key;
            this.value = value;
        }
    }

    public class DLList{
        private Node head;
        private Node tail;
        int len;//当前链表的长度
        public DLList(){
            this.head = new Node(0,0);
            this.tail = new Node(0,0);
            this.len = 0;
            head.next = tail;
            tail.pre = head;
            head.pre = null;
            tail.next = null;
        }
        public void addNode(Node node){
            Node next = head.next;
            head.next = node;
            node.pre = head;
            node.next = next;
            next.pre = node;
            len++;
            map.put(node.key,node);
        }

        public void removeNode(Node node){
            Node pre = node.pre;
            Node next = node.next;
            pre.next = next;
            next.pre = pre;
            len--;
            map.remove(node.key);
        }
        public void removeTail(){
            Node node = tail.pre;
            removeNode(node);
        }
    }
}
```
