package com.lc.misc.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @author luochao
 * @date 2020-06-18 11:08
 */
public class HWTest {
    static class P {
        public int height;
        public int weight;
        public int num;

        public P(Integer height, Integer weight, Integer num) {
            this.height = height;
            this.weight = weight;
            this.num = num;
        }

        @Override
        public String toString() {
            return height + " - " + weight + " - " + num;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        List<Integer> heights = new ArrayList<>();
        List<Integer> weights = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            heights.add(scanner.nextInt());
        }
        for (int i = 0; i < n; i++) {
            weights.add(scanner.nextInt());
        }

        List<P> pList = new ArrayList();
        for (int i = 0; i < n; i++) {
            pList.add(new P(heights.get(i), weights.get(i), i + 1));
        }
        List<Integer> soredPList = pList.stream().sorted((a, b) -> {
            if (a.height > b.height) {
                return 1;
            } else if (a.height == b.height) {
                if (a.weight > b.weight) {
                    return 1;
                } else if (a.weight == b.weight) {
                    return 0;
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
        }).map(item -> item.num).collect(Collectors.toList());
        for (int i = 0; i < n; i++) {
            System.out.print(soredPList.get(i) + " ");
        }
    }
}
