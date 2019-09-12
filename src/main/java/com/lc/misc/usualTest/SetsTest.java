package com.lc.misc.usualTest;

import com.google.common.collect.Sets;

import java.util.HashSet;

public class SetsTest {
    public static void main(String[] args) {
        HashSet<Integer> old = Sets.newHashSet(1, 2, 4, 5);
        HashSet<Integer> newSet = Sets.newHashSet(2, 3, 4, 5, 6, 7);
        Sets.difference(newSet,old).stream().forEach(System.out::println);
    }
}
