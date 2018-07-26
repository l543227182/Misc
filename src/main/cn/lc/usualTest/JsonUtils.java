
    /**    
    * @Title: JsonUtils.java  
    * @Package com.cloud.apigateway.sdk.demo  
    * @Description: TODO(用一句话描述该文件做什么)  
    * @author zhaoqing.frank  
    * @date 2018年7月16日  
    * @version V1.0    
    */

    package main.cn.lc.usualTest;



import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtils {

/*    public static void main(String args[]){
        String sourceStr = "{\"assign_type\":\"1\",\"ui_custom_params\":\"{\\\"destVar\\\":[{\\\"device_infos\\\":[{\\\"public_ips\\\":\\\"115.182.3.74\\\",\\\"private_ips\\\":\\\"10.168.3.74\\\",\\\"group_names\\\":\\\"技术测试区-双线体验,技术测试区-双线体验\\\",\\\"device_id\\\":\\\"dmp:40647\\\"}],\\\"scope_values\\\":\\\"dmp:40647\\\",\\\"group_infos\\\":[],\\\"value\\\":\\\"jjj\\\",\\\"name\\\":\\\"jjj\\\",\\\"scope\\\":\\\"4\\\",\\\"area_infos\\\":[],\\\"remark\\\":\\\"\\\",\\\"referenced_job_ids\\\":[],\\\"id\\\":\\\"2948\\\"}]}\",\"dest_var_id\":\"2948\",\"var_value\":\"121321\"}";

        testTransferMap(sourceStr);
    }*/
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
    public static String testTransferMap(String sourceStr){

        Map<String,String> targetMap = new HashMap<String,String>();
        Map<String,Object> o = (Map<String,Object>) parseJsonString(sourceStr, Map.class);
        //System.out.println(o);
        List<TrieNode> list = new ArrayList<>();

        for(Map.Entry<String,Object> item:o.entrySet()){
            TrieNode TrieNode = new TrieNode();
            TrieNode.key=item.getKey();
            TrieNode.value=item.getValue().toString();
            list.add(TrieNode);
            recursionMap(TrieNode,item.getValue().toString(),Map.class);
        }

        list.stream().forEach(item->{
            getResult(item,new String(),targetMap);
        });
       // System.out.println(result);
       return objectToString(targetMap);
    }
   // static  List<String> result = new ArrayList<>();
    public static void getResult(TrieNode node,String str,Map<String, String> targetMap){
        if(node == null)
            return;
        str += node.key;
        Map<String, TrieNode> subNodes = node.subNodes;
        if(subNodes.size() == 0){
           // result.add(str + "=" +node.value);
            targetMap.put(str, node.value);
        }
        for(Map.Entry<String, TrieNode> item:subNodes.entrySet()){
            getResult(item.getValue(),new String(str + "."),targetMap);
        }
    }
    public static void recursionMap(TrieNode father,String source,Class clazz){
        Object o = parseJsonString(source, clazz);
        if(o == null){
            return;
        }
        if(o instanceof List){
            ((List) o).stream().forEach(item->{
                if(item instanceof  Map){
                    recursionMap(father,objectToString(item),Map.class);
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

