package com.lc.misc.algorithm;


import java.util.*;
import java.util.stream.Collectors;

class Solution2 {
    public static int[] relativeSortArray(int[] arr1, int[] arr2) {
        List<Integer> arr1List = Arrays.stream(arr1).boxed().collect(Collectors.toList());
        List<Integer> arr2List = Arrays.stream(arr2).boxed().collect(Collectors.toList());

        Map<Integer, List<Integer>> count = new HashMap<Integer, List<Integer>>();

        List<Integer> notIn = new ArrayList<Integer>();
        arr1List.forEach(item -> {
            if (arr2List.contains(item)) {
                count.computeIfAbsent(item, coutItem -> new ArrayList<>()).add(item);
            } else {
                notIn.add(item);
            }
        });
        List<Integer> result = new ArrayList<Integer>();
        arr2List.forEach(item -> {
            result.addAll(count.get(item));
        });

        notIn.sort(Integer::compareTo);

        result.addAll(notIn);
        return Arrays.stream(result.toArray(new Integer[1])).mapToInt(item -> item).toArray();
    }

    public static void main(String[] args) {
        int[] ints = relativeSortArray(new int[]{2, 3, 1, 3, 2, 4, 6, 7, 9, 2, 19}, new int[]{2, 1, 4, 3, 9, 6});
        System.out.println(ints);
    }
}