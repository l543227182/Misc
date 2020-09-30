package com.lc.misc.rpc;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtils2 {

    public static void main(String args[]) {
        String sourceStr = "{\"action\":\"1\",\"role_params\":\"{\\\"webServer_vars\\\":\\\"aaa\\\",\\\"webServer_timeout\\\":\\\"60\\\",\\\"webServer_timeout_policy\\\":\\\"1\\\",\\\"wp123_vars\\\":\\\"aaa\\\",\\\"wp123_timeout\\\":\\\"60\\\",\\\"wp123_timeout_policy\\\":\\\"1\\\"}\",\"role_names\":\"webServer,wp123\",\"group_nums\":\"3-96090\",\"group_ids\":\"83663\"}";

        testTransferMap(sourceStr);
    }

    static class TrieNode {
        //  private boolean end = false;
        private String key;
        private String value;
        private Map<String, TrieNode> subNodes = new HashMap<>();
    }

    public static <T> T parseJsonString(String str, Class clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Object o = objectMapper.readValue(str, clazz);
            return (T) o;
        } catch (IOException e) {

        }
        return null;
    }

    public static String objectToString(Object o) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            StringWriter str = new StringWriter();
            objectMapper.writeValue(str, o);
            return str.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String testTransferMap(String sourceStr) {

        Map<String, String> targetMap = new HashMap<String, String>();
        Map<String, Object> o = (Map<String, Object>) parseJsonString(sourceStr, Map.class);
        //System.out.println(o);
        List<TrieNode> list = new ArrayList<>();

        for (Map.Entry<String, Object> item : o.entrySet()) {
            TrieNode TrieNode = new TrieNode();
            TrieNode.key = item.getKey();
            TrieNode.value = item.getValue().toString();
            list.add(TrieNode);
            recursionMap(TrieNode, item.getValue().toString(), Map.class);
        }

        list.stream().forEach(item -> {
            getResult(item, new String(), targetMap);
        });
        //System.out.println(result);
        System.out.println(targetMap);
        return objectToString(targetMap);
    }

    static List<String> result = new ArrayList<>();

    public static void getResult(TrieNode node, String str, Map<String, String> targetMap) {
        if (node == null)
            return;
        if (!node.key.startsWith("@index")) {

            str += node.key;
        }
        Map<String, TrieNode> subNodes = node.subNodes;
        if (subNodes.size() == 0) {
            result.add(str + "=" + node.value);
            str = str.replace("..", ".");
            if (targetMap.containsKey(str)) {
                String aString = targetMap.get(str) + "," + node.value;
                targetMap.put(str, aString);
            } else {

                targetMap.put(str, node.value);

            }

        }
        for (Map.Entry<String, TrieNode> item : subNodes.entrySet()) {
            if (!node.key.startsWith("@index")) {
                str = str + ".";
            }
            getResult(item.getValue(), new String(str.replace("..", ".")), targetMap);
        }
    }

    public static void recursionMap(TrieNode father, String source, Class clazz) {
        Object o = parseJsonString(source, clazz);
        if (o == null) {
            o = parseJsonString(source, List.class);
            if (o == null) {
                return;

            }
        }
        if (o instanceof List) {
            final int[] i = {0};
            ((List) o).stream().forEach(item -> {
                if (item instanceof Map) {
                    TrieNode trieNode = new TrieNode();
                    trieNode.key = "@index" + (i[0]++);
                    trieNode.value = objectToString(item);
                    father.subNodes.put(trieNode.key, trieNode);
                    recursionMap(trieNode, objectToString(item), Map.class);
                }
                if (item instanceof List) {
                    recursionMap(father, objectToString(item), List.class);
                }
                if (item instanceof String) {

                }
            });
        }
        if (o instanceof Map) {
            for (Map.Entry<String, Object> item : ((Map<String, Object>) o).entrySet()) {
                TrieNode trieNode = new TrieNode();
                trieNode.key = item.getKey();
                if (item.getValue().getClass() == String.class) {

                    trieNode.value = item.getValue().toString();
                } else {
                    trieNode.value = objectToString(item.getValue());
                }
                father.subNodes.put(trieNode.key, trieNode);
                Class aClass = item.getValue().getClass();
                recursionMap(trieNode, trieNode.value, aClass);
            }
        }

    }
}

