package com.lc.misc.usualTest;

import org.joda.time.DateTime;

public class JodaTimeTest {
    public static void main(String[] args) {
        DateTime dt = new DateTime();
        System.out.println(dt.toString(" MM/dd/yyyy ") + " - 1");
        DateTime minus = dt.minusDays(1);
        System.out.println(minus.toString(" MM/dd/yyyy "));
        System.out.println(dt.toString(" MM/dd/yyyy "));
    }
}
