package com.lc.misc.usualTest;

import com.google.common.collect.Sets;

import java.util.HashSet;

public class streamTesk {
    public static void main(String[] args) {
        HashSet<Integer> a = Sets.newHashSet(1, 2, 3, 4, 5, 6);
        HashSet<Integer> b = Sets.newHashSet(3, 4, 5, 6,7);
        Sets.SetView<Integer> diff = Sets.difference(a, b);
        System.out.println(diff);
    }
}
