package com.lc.misc.usualTest.extendsData;

import java.util.List;

public class GopsGroupDto {

    Long id;

    String name;

    List<GopsGroupDto> subGopsGroups;

    boolean virtualNode;

    List<Object> monitorObjects;

    boolean isLeaf;

    Long fatherId;

    List<String> fatherNames;

}