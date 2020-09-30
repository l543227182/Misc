package com.lc.misc.spring;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.LocalDateTime;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println(positiveNum());
    }
    public static Long positiveNum() {
        Random random = new Random();
        long l = random.nextInt(10000);
        return l > 0 ? l : -l;
    }
    public static void test2() {
        // 上个月的最后一天
        LocalDateTime lastDayOfPreviousMonth = LocalDateTime.now().minusMonths(1).dayOfMonth().withMaximumValue()
                .withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
        // 上个月的第一天
        LocalDateTime statDayOfPreviousMonth = LocalDateTime.now().minusMonths(1).dayOfMonth().withMinimumValue()
                .withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);
        long bizDateStart = Long.parseLong(DateFormatUtils.format(statDayOfPreviousMonth.toDate(), "yyyyMMdd"));
        long bizDateEnd = Long.parseLong(DateFormatUtils.format(lastDayOfPreviousMonth.toDate(), "yyyyMMdd"));
        System.out.println(bizDateStart);
        System.out.println(bizDateEnd);
    }

    public static void test1() {
        class CapacityNode {
            public Long value;
            private CapacityNodeType type;

            public CapacityNode(Long value, CapacityNodeType type) {
                this.value = value;
                this.type = type;
            }
        }
        List<CapacityNode> nodeList = Lists.newArrayList();
        nodeList.add(new CapacityNode(1L, CapacityNodeType.UNLOAD));
        nodeList.add(new CapacityNode(8L, CapacityNodeType.LOAD));
        nodeList.add(new CapacityNode(1L, CapacityNodeType.EQUIPMENT));

        Optional<CapacityNode> max = nodeList.stream().filter(item -> Objects.nonNull(item.value)).max(Comparator.comparing(a -> a.value));

        // 相等的时候 优先级 设备》卸车》装车
        Optional<CapacityNode> min = nodeList.stream().min((a, b) -> {
            if (a.value.equals(b.value)) {
                if (a.type.equals(CapacityNodeType.EQUIPMENT)) {
                    return -1;
                } else if (a.type.equals(CapacityNodeType.UNLOAD) && b.type.equals(CapacityNodeType.LOAD)) {
                    return -1;
                } else {
                    return 1;
                }
            } else {
                if (a.value.compareTo(b.value) > 0) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        System.out.println("max node: " + max.get().type);
        System.out.println("min node: " + min.get().type);
    }

    enum CapacityNodeType {

        /**
         * 卸车
         */
        UNLOAD,
        /**
         * 装车
         */
        LOAD,

        /**
         * 设备折算
         */
        EQUIPMENT,

        /**
         * 所有
         */
        ALL
    }
}
