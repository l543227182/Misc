package com.lc.misc.main.LRU;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by luochao.byron on 2017/9/25.
 */
public class LRUcache<K, V> implements Iterator {
    @Override
    public boolean hasNext() {
        return pointer.pre != header;
    }

    @Override
    public V next() {
        Node pre = pointer.pre;
        pointer = pointer.pre;
        return pre.value;
    }

    public void resetPointer() throws Exception {
        if (pointer.next != tail) {
            throw new Exception("遍历中");
        } else {
            pointer = tail;
        }
    }

    private Node pointer;

    class Node {
        private V value;
        private K key;
        private int count;
        private Node next;
        private Node pre;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public Node() {

        }
    }

    private Node header;
    private Node tail;
    private HashMap<K, Node> map;
    private int capacity;

    public LRUcache(int capacity) {
        this.capacity = capacity;
        map = new HashMap(capacity);
        header = new Node();
        tail = new Node();
        header.next = header.pre = tail;
        tail.pre = tail.next = header;
        pointer = tail;
    }

    public LRUcache() {
        this(16);
    }

    public void put(K key, V value) {
        assert key == null : "元素不能为空";
        if (map.containsKey(key)) {
            map.get(key).value = value;
            return;
        }
        Node newNode = new Node(key, value);
        if (map.size() == capacity) {
            header.next = header.next.next;
            header.next.pre = header;
        }
        map.put(key, newNode);
        addTailNode(newNode);
    }

    void addTailNode(Node currentNode) {
        currentNode.pre = tail.pre;
        tail.pre.next = currentNode;
        tail.pre = currentNode;
        currentNode.next = tail;
        tail.next = header;
    }

    public V get(K key) {
        assert key != null;
        Node node = map.get(key);
        if (node == null) {
            return null;
        }
        // del node
        node.pre.next = node.next;
        node.next.pre = node.pre;

        // LRU
        addTailNode(node);
        return node.value;
    }
}