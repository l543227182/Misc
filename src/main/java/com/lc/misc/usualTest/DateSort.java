package com.lc.misc.usualTest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DateSort {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String specifyTime = "2010-06-17 10:32:43,2018-09-05 10:37:37,2019-06-12 10:37:40,2018-10-06 10:37:41,2018-12-26 10:37:48";
        Arrays.stream(specifyTime.split(",")).map(item -> {
            try {
                return sdf.parse(item);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }).sorted((a,b)->{
            return a.compareTo(b);
        }).map(sdf::format).forEach(System.out::println);
    }
}
