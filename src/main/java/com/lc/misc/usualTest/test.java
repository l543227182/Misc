package com.lc.misc.usualTest;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test {

    public static void main(String args[]){
        testTransferMap();
    }
    static class TrieNode{
        private boolean end = false;
        private String key;
        private String value;
        private Map<String, TrieNode> subNodes = new HashMap<>();
    }

    public static <T> T  parseJsonString(String str,Class clazz){
        ObjectMapper objectMapper=new ObjectMapper();
        try {
            Object o = objectMapper.readValue(str, clazz);
            return (T) o;
        } catch (IOException e) {
        }
        return  null;
    }

    public static String objectToString(Object o){

        try {
            ObjectMapper objectMapper=new ObjectMapper();
            StringWriter str=new StringWriter();
            objectMapper.writeValue(str, o);
            return str.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void testTransferMap(){

        String sourceStr = "{\"channels\":\"[{\\\"channel\\\":\\\"1\\\",\\\"users\\\":\\\"fanghua02\\\"},{\\\"channel\\\":\\\"2\\\",\\\"users\\\":\\\"18101807870\\\"},{\\\"channel\\\":\\\"3\\\",\\\"users\\\":\\\"fanghua02@shandagames.com\\\"},{\\\"channel\\\":\\\"5\\\",\\\"users\\\":\\\"fanghua\\\"}]\",\"message\":\"test\"}";
        Map<String,String> targetMap = new HashMap<String,String>();
        Map<String,String> o = (Map<String,String>) parseJsonString(sourceStr, Map.class);
        System.out.println(o);
        List<TrieNode> list = new ArrayList<>();

        for(Map.Entry<String,String> item:o.entrySet()){
            TrieNode TrieNode = new TrieNode();
            TrieNode.key=item.getKey();
            TrieNode.value=item.getValue();
            list.add(TrieNode);
            recursionMap(TrieNode,item.getValue(),Map.class);
        }

        list.stream().forEach(item -> {
            getResult(item, new String());
        });
        System.out.println(result);
    }
    static  List<String> result = new ArrayList<>();
    public static void getResult(TrieNode node,String str){
        if(node == null)
            return;
        if(!node.key.startsWith("@index"))
                str += node.key;
        Map<String, TrieNode> subNodes = node.subNodes;
        if(subNodes.size() == 0){
            result.add(str + "=" +node.value);
        }
        for(Map.Entry<String, TrieNode> item:subNodes.entrySet()){
            String nextStr = new String(str);
            if(!node.key.startsWith("@index")) nextStr +=".";
            getResult(item.getValue(),nextStr);
        }
    }
    public static void recursionMap(TrieNode father,String source,Class clazz){
        Object o = parseJsonString(source, clazz);
        if(o == null){
            o=parseJsonString(source, List.class);
            if (o==null) {
                return;
            }
        }
        if(o instanceof List){
            final int[] i = {0};
            ((List) o).stream().forEach(item->{

                if(item instanceof  Map){
                    TrieNode trieNode = new TrieNode();
                    trieNode.key = "@index"+(i[0]++) ;
                    trieNode.value = objectToString(item);
                    father.subNodes.put(trieNode.key,trieNode);
                    recursionMap(trieNode,objectToString(item),Map.class);
                }
                if(item instanceof  List){
                    recursionMap(father,objectToString(item),List.class);
                }
                if(item instanceof String){

                }
            });
        }
        if(o instanceof Map){
            for(Map.Entry<String,Object> item:((Map<String,Object>)o).entrySet()){
                TrieNode trieNode = new TrieNode();
                trieNode.key = item.getKey();
                trieNode.value = objectToString(item.getValue());
                father.subNodes.put(trieNode.key,trieNode);
                Class aClass = item.getValue().getClass();
                recursionMap(trieNode,trieNode.value,aClass);
            }
        }

    }
}
