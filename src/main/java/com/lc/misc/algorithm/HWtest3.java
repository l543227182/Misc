package com.lc.misc.algorithm;

/**
 * @author luochao
 * @date 2020-06-18 12:47
 */
public class HWtest3 {
    public static void main(String[] args) {

        String a = "1";
        String b = "2";

        swap(a,b);

        System.out.println(a);
        System.out.println(b);
    }

    public static void swap(String a ,String b) {
        String temp = a;
        a = b;
        b = temp;
    }
}
