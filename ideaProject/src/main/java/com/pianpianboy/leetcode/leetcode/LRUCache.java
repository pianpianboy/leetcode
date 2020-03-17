package com.pianpianboy.leetcode.leetcode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @ClassName LRU
 * @Description TODO
 * @Author liaomengjie
 * @Date 2020-03-15 21:59
 */
public class LRUCache {

    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();

        ((LinkedList<Integer>) queue).push(1);
        ((LinkedList<Integer>) queue).add(1);


        LRUCache cache = new LRUCache(2);
        cache.put(1,1);
        cache.put(2,2);
        cache.get(1);
        cache.put(3,3);
        cache.get(2);
        cache.put(4,4);
        cache.get(1);
        cache.get(3);
        cache.get(4);
    }

    private int count;
    private int capacity;
    private Node head;
    private Node tail;
    HashMap<Integer,Node> map = new HashMap<Integer,Node>();

    public LRUCache(int capacity){
        this.capacity = capacity;
        this.count = 0;
        this.head = new Node(0,0);
        this.tail = new Node(0,0);

        head.next = tail;
        head.pre = null;
        tail.next = null;
        tail.pre = head;
    }

    public int get(int key){
        Node node = map.get(key);
        if(node==null)return -1;

        removeNode(node);
        addNodeToHead(node);
        return node.value;
    }

    public void put(int key,int value){
        Node node = map.get(key);
        //LRU中存在key这个值，则更新key，value，与get的逻辑很类似
        if(node!=null){
            node.value = value;
            removeNode(node);
            addNodeToHead(node);
        }else{//LRU不存在key这个值，
            Node newNode = new Node(key,value);
            //下面一步很容易遗忘
            addNodeToHead(newNode);
            map.put(key,newNode);
            count++;
            //如果容量超过了capacity需要从链表末尾移出node，同时需要从map中移出
            if(count>capacity) {
                int deleteKey= removeNode(tail.pre);
                //以下两步很重要，容易遗忘
                count--;
                map.remove(deleteKey);
            }
        }
    }


    public int removeNode(Node node){
        Node pre = node.pre;
        Node next = node.next;
        pre.next = next;
        next.pre = pre;
        return node.key;
    }

    public void addNodeToHead(Node node){
        Node  next = head.next;
        node.pre = head;
        head.next = node;
        node.next = next;
        next.pre = node;
    }


    public class Node{
        private Node pre;
        private Node next;
        private int key;
        private int value;
        public Node(int key,int value){
            this.key = key;
            this.value = value;
        }
    }
}
