package com.lc.misc.usualTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class flatMapTest {
    public static void main(String[] args) {
        List<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(1, 2, 3, 4));
        list.add(Arrays.asList(11, 22, 33, 44));
        list.add(Arrays.asList(111, 222, 333, 444));
        list.add(Arrays.asList(1111, 2222, 3333, 4444));
        list.add(Arrays.asList(11111, 22222, 33333, 44444));
        list.add(Arrays.asList(111111, 222222, 333333, 44444));

        System.out.println(list.stream().flatMap(item -> item.stream()).collect(Collectors.toList()));
    }
}
