package com.lc.misc.usualTest;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Calendar;
import java.util.List;

public class ScheduleTaskParamsAnalyszer {

    private BitSet minutes;

    private BitSet hours;

    private BitSet daysOfMonth;

    private BitSet daysOfWeek;

    private BitSet months;

    private int recycleWeek ;

    //从calendar开始寻找下一个匹配cron表达式的时间
    private void doNext(Calendar calendar) {
        //calendar中比当前更高的域是否调整过
        boolean changed = false;
        List<Integer> fields = Arrays.asList(Calendar.MONTH, Calendar.DAY_OF_MONTH,
                Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND);

        //依次调整月，日，时，分，秒
        for (int field : fields) {
            if (changed) {
                calendar.set(field, field == Calendar.DAY_OF_MONTH ? 1 : 0);
            }
            if (!checkField(calendar, field)) {
                changed = true;
                findNext(calendar, field);
            }
        }
    }

    //检查某个域是否匹配cron表达式
    private boolean checkField(Calendar calendar, int field) {
        switch (field) {
            case Calendar.MONTH: {
                int month = calendar.get(Calendar.MONTH);
                return this.months.get(month);
            }
            case Calendar.DAY_OF_MONTH: {
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                return this.daysOfMonth.get(dayOfMonth) && this.daysOfWeek.get(dayOfWeek);
            }
            case Calendar.HOUR_OF_DAY: {
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                return this.hours.get(hour);
            }
            case Calendar.MINUTE: {
                int minute = calendar.get(Calendar.MINUTE);
                return this.minutes.get(minute);
            }
            default:
                return true;
        }
    }

    //调整某个域到下一个匹配值
    private void findNext(Calendar calendar, int field) {
        switch (field) {
            case Calendar.MONTH: {
                if (calendar.get(Calendar.YEAR) > 2099) {
                    throw new IllegalArgumentException("year exceeds 2099!");
                }
                int month = calendar.get(Calendar.MONTH);
                int nextMonth = this.months.nextSetBit(month);
                if (nextMonth == -1) {
                    calendar.add(Calendar.YEAR, 1);
                    calendar.set(Calendar.MONTH, 0);
                    nextMonth = this.months.nextSetBit(0);
                }
                if (nextMonth != month) {
                    calendar.set(Calendar.MONTH, nextMonth);
                }
                break;
            }
            case Calendar.DAY_OF_MONTH: {
                while (!this.daysOfMonth.get(calendar.get(Calendar.DAY_OF_MONTH))
                        || !this.daysOfWeek.get(calendar.get(Calendar.DAY_OF_WEEK) - 1)) {
                    int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                    int nextDayOfMonth = this.daysOfMonth.nextSetBit(calendar.get(Calendar.DAY_OF_MONTH) + 1);
                    if (nextDayOfMonth == -1 || nextDayOfMonth > max) {
                        calendar.add(Calendar.MONTH, 1);
                        findNext(calendar, Calendar.MONTH);
                        calendar.set(Calendar.DAY_OF_MONTH, 1);
                    } else {
                        calendar.set(Calendar.DAY_OF_MONTH, nextDayOfMonth);
                    }
                }
                break;
            }
            case Calendar.HOUR_OF_DAY: {
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int nextHour = this.hours.nextSetBit(hour);
                if (nextHour == -1) {
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    findNext(calendar, Calendar.DAY_OF_MONTH);
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    nextHour = this.hours.nextSetBit(0);
                }
                if (nextHour != hour) {
                    calendar.set(Calendar.HOUR_OF_DAY, nextHour);
                }
                break;
            }
            case Calendar.MINUTE: {
                int minute = calendar.get(Calendar.MINUTE);
                int nextMinute = this.minutes.nextSetBit(minute);
                if (nextMinute == -1) {
                    calendar.add(Calendar.HOUR_OF_DAY, 1);
                    findNext(calendar, Calendar.HOUR_OF_DAY);
                    calendar.set(Calendar.MINUTE, 0);
                    nextMinute = this.minutes.nextSetBit(0);
                }
                if (nextMinute != minute) {
                    calendar.set(Calendar.MINUTE, nextMinute);
                }
                break;
            }
        }
    }


    public static void main(String args[]){
        Calendar instance = Calendar.getInstance();

        int i = instance.get(Calendar.DAY_OF_WEEK);
        System.out.println(i);
        instance.add(Calendar.DAY_OF_WEEK,1);
        i=instance.get(Calendar.DAY_OF_WEEK);
        System.out.println(i);
        instance.add(Calendar.DAY_OF_WEEK,1);
        int i1 = instance.get(Calendar.DAY_OF_WEEK);
        instance.add(Calendar.DAY_OF_WEEK,1);
        i1 = instance.get(Calendar.DAY_OF_WEEK);

        System.out.println(i1);
    }
}
