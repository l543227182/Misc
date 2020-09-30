package com.lc.misc.usualTest;

import com.alibaba.fastjson.JSONObject;
import com.google.common.io.Files;
import com.lc.misc.usualTest.extendsData.datamodel.DaemonBeatData;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Objects;

public class JsonTransfer {
    public static void main(String[] args) throws IOException {
        String string = Files.toString(new File("D:\\javaworkspace\\Misc\\src\\main\\java\\com\\lc\\misc\\usualTest\\extendsData\\data4.json"), Charset.defaultCharset());
        DaemonBeatData daemonBeatData = JSONObject.parseObject(string, DaemonBeatData.class);
        Map<String, String> influxFields =
                daemonBeatData.getInfluxFields();
        Map<String, String> influxTags = daemonBeatData.getInfluxTags();
        System.out.println(influxFields);
        influxFields.entrySet().stream().forEach(item -> {
            if (Objects.isNull(item.getKey())) {
                throw new RuntimeException("field key is null, key :" + item.getKey());
            }
            if (Objects.isNull(item.getValue())) {
                throw new RuntimeException("field value is null, value :" + item.getValue());
            }
        });
        influxTags.entrySet().stream().forEach(item -> {
            if (Objects.isNull(item.getKey())) {
                throw new RuntimeException("tag key is null, key :" + item.getKey());
            }
            if (Objects.isNull(item.getValue())) {
                throw new RuntimeException("tag value is null, value :" + item.getValue());
            }
        });
    }
}
