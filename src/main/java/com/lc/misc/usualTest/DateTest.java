package com.lc.misc.usualTest;

import com.google.common.collect.Maps;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Map;

public class DateTest {
    public static void main(String[] args) {
        int a = 4;
        calcDuration();
    }

    static void calcDuration() {
        DateTime pointNext = new DateTime().minusDays(3);
        DateTime end = new DateTime();
        Map<DateTime, Integer> log = Maps.newHashMap();
        Integer count = 0;
        while (pointNext.getMillis() <= end.getMillis()) {
            if (pointNext.getDayOfWeek() == 6 || pointNext.getDayOfWeek() == 7) {
                if (count != 0) {
                    log.put(new DateTime(pointNext), count);
                }
                count = 0;
                pointNext = pointNext.plusDays(1);
                continue;
            }
            count++;
            pointNext = pointNext.plusDays(1);
        }
        if (count != 0) {
            log.put(new DateTime(pointNext), count);
        }
        log.keySet().stream().forEach(item -> {
            System.out.println(log.get(item));
        });
        System.out.println(log);
    }

    private static void JodaTest() {
        DateTime time = new DateTime();
        Days days = Days.daysBetween(time, time.plusDays(1));
        System.out.println(days.getDays());
        System.out.println(time.plusDays(2).getDayOfWeek());
    }

    private static void sort() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String specifyTime = "2010-06-17 10:32:43,2018-09-05 10:37:37,2019-06-12 10:37:40,2018-10-06 10:37:41,2018-12-26 10:37:48";
        Arrays.stream(specifyTime.split(",")).map(item -> {
            try {
                return sdf.parse(item);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }).sorted((a, b) -> {
            return a.compareTo(b);
        }).map(sdf::format).forEach(System.out::println);
    }
}
