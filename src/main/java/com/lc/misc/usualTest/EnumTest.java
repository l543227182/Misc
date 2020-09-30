package com.lc.misc.usualTest;

import java.util.Arrays;

public class EnumTest {
    public static void main(String[] args) {
        Arrays.stream(MateDataEnum.values()).forEach(System.out::println);
    }
}

enum MateDataEnum {
    GROUP_NAME("组名"), AREA_NAME("区名"), GROUP_UNIFIED_ID("区unified_id"), AREA_UNIFIED_ID("组unified_id"), ROLE_NAME("角色名"), REMARK("备注");
    String label;

    MateDataEnum(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "{label:\"" + label + "\",value: \"" + GROUP_NAME.name() + "\"}";
    }
}
