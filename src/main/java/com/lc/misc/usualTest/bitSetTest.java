package com.lc.misc.usualTest;

import java.util.BitSet;

public class bitSetTest {
    public static void main(String[] args) {
        BitSet bs = new BitSet();
        bs.set(5);
        bs.set(8);
        bs.set(10);
        int i = bs.nextSetBit(11);
        System.out.println(i);
    }
}
