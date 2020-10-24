package com.lc.misc.algorithm;

import java.util.*;

/**
 * @author luochao
 * @date 2020-06-18 11:08
 */
public class HWTest2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        Set<Integer> numSet = new HashSet();
        for(int i=0;i<m;i++) {
            numSet.add(scanner.nextInt());
        }
        int n = scanner.nextInt();
        PriorityQueue<Integer> maxQueues = new PriorityQueue<Integer>((a,b)-> a>b?1:-1);
        PriorityQueue<Integer> minQueues = new PriorityQueue<Integer>((a,b)-> a<b?1:-1);

        numSet.stream().forEach(item->{
                maxQueues.add(item);
                minQueues.add(item);
        });
        List<Integer> max = new ArrayList<>();
        List<Integer> min = new ArrayList<>();
        for(int i=0;i<n;i++) {
                if(i <maxQueues.size()) {
                    max.add(maxQueues.poll());
                    min.add(minQueues.poll());
                }

        }

        Set<Integer> allSet = new HashSet<>(max);
        allSet.addAll(min);
        if(allSet.size() != 2*n) {
            System.out.println(-1);
            return;
        }

        Integer sum = 0;
        for(int i=0;i<max.size();i++) {
            sum +=max.get(i);

        }for(int i=0;i<min.size();i++) {
            sum +=min.get(i);

        }
        System.out.println(sum);

    }
}
