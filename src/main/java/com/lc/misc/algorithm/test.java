package com.lc.misc.algorithm;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author luochao
 * @date 2020/11/29 3:21 下午
 */
public class test<T> {

    static class Node<T> {
        public T value;
        public Node next;

        public Node(T num) {
            this.value = num;
        }

        public Node setNext(Node node) {
            this.next = node;
            return this.next;
        }
    }

    public static void main(String[] args) {
        Node node = new Node(1);

        Node node3 = new Node(3);

        node.setNext(new Node(2)).setNext(node3).setNext(new Node(null)).setNext(new Node(null)).setNext(new Node(5)).setNext(new Node(3)).setNext(new Node(3)).setNext(new Node(3))

                .setNext(node3);

        getTopN(node, 3);

    }

    public static <T> List<T> getTopN(Node<T> header, int n) {

        Map<T, Integer> map = new HashMap<>();
        Map<Node, Boolean> checkCycle = new HashMap<>();
        Node next = header;
        while (Objects.nonNull(next)) {
            if (Objects.nonNull(checkCycle.get(next)) && checkCycle.get(next)) {
                break;
            }
            checkCycle.put(next, true);
            if (Objects.nonNull(next.value)) {
                map.put((T) next.value, map.computeIfAbsent((T) next.value, coutItem -> 0) + 1);
            }
            next = next.next;
        }


        List<T> result = map.entrySet().stream().sorted((a, b) -> {
            if (a.getValue() > b.getValue()) {
                return -1;
            } else if (a.getValue() < b.getValue()) {
                return 1;
            } else {
                return 0;
            }
        }).map(item -> item.getKey()).collect(Collectors.toList());
        if (result.size() > n) {
            return result.subList(0, n);
        } else if (n <= 0) {
            return new ArrayList<>();
        } else {
            return result;
        }
    }
}
