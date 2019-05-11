package lru;

import java.util.HashMap;

/**
 * @ClassName LRUCache
 * @Description 以下LRI实现不是线程安全的，需要加上synchronized修饰符
 * @Author liaomengjie
 * @Date 2019-05-11 21:35
 */
public class LRUCache {
    private Node head;
    private Node end;
    //设置缓存上限
    private int limit;
    private HashMap<String,Node> hashmap;

    public LRUCache(int limit){
        this.limit = limit;
        this.hashmap = new HashMap<String, Node>();
    }

    public String get(String key){
        Node node = hashmap.get(key);
        if(node==null){
            return null;
        }

        refreshNode(node);
        return node.value;
    }

    public void put(String key, String value){
        Node node  = hashmap.get(key);
        if(node==null){
            //如果key不存在，则插入key-value
            if(hashmap.size()>=limit){
                String oldkey = removeNode(head);
                hashmap.remove(oldkey);
            }
            node = new Node(key,value);
            hashmap.put(key,node);
            addNode(node);
        }else{
            //如果key存在，刷新key-value
            node.value = value;
            refreshNode(node);
        }
    }
    public void remove(String key){
        Node node = hashmap.get(key);
//        if(node!=null){
//            hashmap.remove(key);
//            removeNode(node);
//        }
        //因为hashmap允许key为null
        hashmap.remove(key);
        removeNode(node);
    }

    /**
     * 刷新被访问的节点位置
     * @param node
     */
    private void refreshNode(Node node){
        if(node == end){
            return ;
        }
        //移出节点
        removeNode(node);
        //从新插入节点
        addNode(node);
    }

    /**
     * 删除节点
     * @param node
     * @return
     */
    private String removeNode(Node node){
        if(node==end){
            //移出尾节点
            end = end.pre;
        }else if(node == head){
            head = head.next;
        }else{
            //移出当前节点
            node.pre.next = node.next;
            node.next.pre = node.pre;
        }
        return node.key;
    }

    /**
     * 尾部插入节点
     * @param node
     */
    private void addNode(Node node){
        if(end!=null){
            end.next = node;
            node.pre = end;
            node.next = null;
        }
        end = node;
        if(head==null){
            head=node;
        }

    }

    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(5);
        lruCache.put("001","用户1信息");
        lruCache.put("002","用户2信息");
        lruCache.put("003","用户3信息");
        lruCache.put("004","用户4信息");
        lruCache.put("005","用户5信息");
        lruCache.get("002");
        lruCache.put("004","用户信息更新");
        lruCache.put("006","用户6信息");
        System.out.println(lruCache.get("001"));
        System.out.println(lruCache.get("006"));
    }
}
