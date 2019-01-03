package com.lc.misc.usualTest;

import com.lc.misc.utils.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class flatMapTest {
    public static void main(String[] args) {
        List<Map<String,String>> list = new ArrayList<>();
        list.add(Maps.of("a","1","name","a1"));
        list.add(Maps.of("b","2","name","b2"));
        list.add(Maps.of("c","3","name","c3"));
        list.add(Maps.of("d","4","name","d4"));
        list.add(Maps.of("e","5","name","e5"));
        list.add(Maps.of("f","6","name","f6"));

        list.stream().peek(System.out::println).collect(Collectors.toList());
    }
}
