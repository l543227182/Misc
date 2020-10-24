package com.lc.misc.usualTest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * TODO
 *
 * @author luochao
 * @since 2020-09-30 14:26
 */
public class provinceNameTest {
    private static final Set<String> mainlandZoneNameSet = new HashSet<>();
    static {
                mainlandZoneNameSet.add("黑吉片区");
                mainlandZoneNameSet.add("山西省公司");
                mainlandZoneNameSet.add("浙江省公司");
                mainlandZoneNameSet.add("桂琼省公司");
                mainlandZoneNameSet.add("重庆省公司");
                mainlandZoneNameSet.add("福建省公司");
                mainlandZoneNameSet.add("内蒙省公司");
                mainlandZoneNameSet.add("湖南省公司");
                mainlandZoneNameSet.add("黑吉省公司");
                mainlandZoneNameSet.add("山东省公司");
                mainlandZoneNameSet.add("四川省公司");
                mainlandZoneNameSet.add("湖北省公司");
                mainlandZoneNameSet.add("云南省公司");
                mainlandZoneNameSet.add("河南省公司");
                mainlandZoneNameSet.add("浙江片区");
                mainlandZoneNameSet.add("京津冀大区");
                mainlandZoneNameSet.add("北京省公司");
                mainlandZoneNameSet.add("琼桂片区");
                mainlandZoneNameSet.add("云贵片区");
                mainlandZoneNameSet.add("新甘青宁藏片区");
                mainlandZoneNameSet.add("陕西片区");
                mainlandZoneNameSet.add("内蒙片区");
                mainlandZoneNameSet.add("津晋片区");
                mainlandZoneNameSet.add("贵州省公司");
                mainlandZoneNameSet.add("江苏省公司");
                mainlandZoneNameSet.add("上海省公司");
                mainlandZoneNameSet.add("江西省公司");
                mainlandZoneNameSet.add("天津省公司");
                mainlandZoneNameSet.add("陕甘片区");
                mainlandZoneNameSet.add("西北大区");
                mainlandZoneNameSet.add("广东省公司");
                mainlandZoneNameSet.add("安徽省公司");
                mainlandZoneNameSet.add("新宁青片区");
                mainlandZoneNameSet.add("河北省公司");
                mainlandZoneNameSet.add("辽宁省公司");

    }
    static String names = "[{" +
            "\"label\": \"黑吉片区\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 37802" +
            "}, {" +
            "\"label\": \"山西省公司\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 33494" +
            "}, {" +
            "\"label\": \"浙江省公司\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 44050" +
            "}, {" +
            "\"label\": \"桂琼省公司\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 28804" +
            "}, {" +
            "\"label\": \"重庆省公司\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 48103" +
            "}, {" +
            "\"label\": \"福建省公司\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 43433" +
            "}, {" +
            "\"label\": \"内蒙省公司\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 52954" +
            "}, {" +
            "\"label\": \"湖南省公司\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 50736" +
            "}, {" +
            "\"label\": \"黑吉省公司\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 36725" +
            "}, {" +
            "\"label\": \"山东省公司\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 38757" +
            "}, {" +
            "\"label\": \"四川省公司\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 55341" +
            "}, {" +
            "\"label\": \"湖北省公司\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 51433" +
            "}, {" +
            "\"label\": \"云南省公司\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 52002" +
            "}, {" +
            "\"label\": \"河南省公司\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 52068" +
            "}, {" +
            "\"label\": \"浙江片区\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 34193" +
            "}, {" +
            "\"label\": \"京津冀大区\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 65669" +
            "}, {" +
            "\"label\": \"北京省公司\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 52619" +
            "}, {" +
            "\"label\": \"琼桂片区\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 46050" +
            "}, {" +
            "\"label\": \"云贵片区\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 44517" +
            "}, {" +
            "\"label\": \"新甘青宁藏片区\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 33789" +
            "}, {" +
            "\"label\": \"陕西片区\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 44727" +
            "}, {" +
            "\"label\": \"内蒙片区\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 31721" +
            "}, {" +
            "\"label\": \"津晋片区\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 52900" +
            "}, {" +
            "\"label\": \"贵州省公司\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 47791" +
            "}, {" +
            "\"label\": \"江苏省公司\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 48662" +
            "}, {" +
            "\"label\": \"上海省公司\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 42022" +
            "}, {" +
            "\"label\": \"江西省公司\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 31605" +
            "}, {" +
            "\"label\": \"天津省公司\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 55751" +
            "}, {" +
            "\"label\": \"陕甘片区\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 45412" +
            "}, {" +
            "\"label\": \"西北大区\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 67340" +
            "}, {" +
            "\"label\": \"广东省公司\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 44201" +
            "}, {" +
            "\"label\": \"安徽省公司\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 30416" +
            "}, {" +
            "\"label\": \"新宁青片区\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 43908" +
            "}, {" +
            "\"label\": \"河北省公司\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 47967" +
            "}, {" +
            "\"label\": \"辽宁省公司\"," +
            "\"type\": \"MANAGE_AREA\"," +
            "" +
            "\"value\": 35646" +
            "}]";
    public static void main(String[] args) {
        JSONArray objects = JSONObject.parseArray(names);
        List<JSONObject> jsonObjects = objects.toJavaList(JSONObject.class);
        jsonObjects.forEach(item->{
            System.out.println(item.get("label"));
        });
    }
}
