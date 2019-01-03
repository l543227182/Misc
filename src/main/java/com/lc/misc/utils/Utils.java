package com.lc.misc.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    static Logger log = LoggerFactory.getLogger(Utils.class);
    
    public static final ObjectMapper mapper = new ObjectMapper();
    
    /** 运行环境 */
    private static final Map<String,String> PROFILES = new HashMap<>();
    
    static{
    	PROFILES.put("dev", "开发环境");
    	PROFILES.put("devtest", "开发测试环境");
    	PROFILES.put("test", "测试环境");
    	PROFILES.put("vrf", "验证环境");
    	PROFILES.put("prd", "生产环境");
    }
    
    public static String anyToString(Object v) {
        return anyToString(v, null);
    }

    public static String anyToString(Object v, String defaultValue) {
        if (v == null) {
            return defaultValue;
        }
        if ((v instanceof String)) {
            return (String) v;
        }
        return v.toString();
    }

    public static long anyToLong(Object v){
        if (v == null) {
            return 0;
        }
        if ((v instanceof Integer)) {
            return ((Integer) v).longValue();
        }
        if ((v instanceof Long)) {
            return ((Long) v).longValue();
        }
        if ((v instanceof Number)) {
            return ((Number) v).longValue();
        }
        return Long.parseLong(v.toString());
    }
    
    
    public static int anyToInt(Object v) {
        if (v == null) {
            return 0;
        }
        if ((v instanceof Integer)) {
            return ((Integer) v).intValue();
        }
        if ((v instanceof Long)) {
            return ((Long) v).intValue();
        }
        if ((v instanceof Number)) {
            return ((Number) v).intValue();
        }
        return Integer.parseInt(v.toString());
    }

    public static String[] anyToStringArray(Object v) {
        String strValue = "";
        if (v == null) {
            return null;
        }
        if ((v instanceof String)) {
            strValue = (String) v;
        }
        strValue = v.toString();
        return strValue.split(",");
    }

    public static int[] anyToIntArray(Object v) {
        String str = anyToString(v, "");
        if (str.isEmpty()) {
            return null;
        }
        return Arrays.stream(str.split(",")).mapToInt(Integer::valueOf).toArray();
    }

    @SuppressWarnings("unused")
    public static boolean isInteger(String val) {
        try {
            int iVal = Integer.parseInt(val);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String implode(Object[] arr) {
        return StringUtils.join(arr, ',');
    }

    public static String implode(String[] arr, String delim) {
        return StringUtils.join(arr,delim);
    }
    public static String implode(List<String> arr, String delim) {
        if(arr == null || arr.size() == 0) return  "";
        return StringUtils.join(arr.toArray(new String[0]),delim);
    }
    public static String implode(Iterable<?> iterable) {
        return StringUtils.join(iterable, ',');
    }

    public static String sortThenImplode(String[] values) {
        Arrays.sort(values);
        return implode(values);
    }

    public static int getIpType(String ip) {
        try {
            return InetAddress.getByName(ip).isSiteLocalAddress() ? 1 : 2;
        } catch(Exception e) {
            return 2;
        }
    }

    public static boolean isValidTime(String start_time, String end_time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = sdf.parse(start_time);
            Date end = sdf.parse(end_time);
            return start.getTime() <= end.getTime() && end.getTime() > new Date().getTime();
        } catch(Exception e) {
            return false;
        }
    }

    public static boolean isValidTime(String start_time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.parse(start_time);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public static Date dateStringToDate(String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(time);
    }

    public static String longToDateString(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(time);
    }
    
    
    public static String longToDateStringMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(time);
    }
    public static String longToDateString(long microseconds, String pDateTimeFormater) {
        if (StringUtils.isEmpty(pDateTimeFormater)) {
            pDateTimeFormater = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pDateTimeFormater);
        Date date = new Date(microseconds);
        return simpleDateFormat.format(date);
    }
    public static int getRandomInteger(int number) {
    	return new Random().nextInt(number);
    	
    }
    public static String addTime(String orginTime,int delay_time) throws ParseException {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date= sdf.parse(orginTime);
        delay_time=Math.abs(getRandomInteger(delay_time));
        long time=date.getTime()+delay_time*1000;
    	return longToDateString(time);
    }

    public static String appendSlash(String path) {
        if ( !path.endsWith("/") && !path.endsWith("\\") ) {
            return path + "/";
        }
        return path;
    }
    public static String removeSlash(String path) {
        if (path.startsWith("/") || path.startsWith("\\") ) {
            return path.substring(1);
        }
        return path;
    }
    
    public static String removeTimestamp(String s) {
    	return s;		//本机上传，不在将文件名改名
    }
    
    // 将文件路径解析成目录和文件名
    public static String[] splitPath(String path) {
        try {
            Pattern p = Pattern.compile("[^/\\\\]+$");
            Matcher m = p.matcher(path);
            if (m.find()) {
                String fileName = m.group(0);
                String filePath = path.replaceAll("[^/\\\\]+$", "");
                return new String[] { fileName, filePath };
            }
        } catch (Exception ex) {
            log.error("exception pvFilePath="+path+",e="+ex.getMessage(),ex);
        }
        return new String[] { path, "" };
    }

    public static String toJsonString(Object bizParameter) {
        try {
            return mapper.writeValueAsString(bizParameter);
        } catch (Exception ex) {
            log.error("exception e="+ex.getMessage(),ex);
            return "";
        }
    }
    
    /**
     * 将object转换为json字符串
     * 
     * @param bizParameter 对象
     * @return 
     * @throws Exception 转换失败
     */
    public static String toJson(Object bizParameter) throws Exception {
        return mapper.writeValueAsString(bizParameter);
    }

    public static <T> T parseJsonString(String jsonString, Class<T> c) {
        try {
            return mapper.readValue(jsonString, c);
        } catch (Exception ex) {
            log.error("exception e=" + ex.getMessage(), ex);
            return null;
        }
    }
    
    /**
     * 解析json
     *
     * @param json
     * @param clazz 对象类型
     * @return
     * @throws Exception json解析出错时抛出异常
     */
    public static <T> Object readJson(String json, Class<T> clazz) throws Exception {
        return mapper.readValue(json, clazz);
    }

    @SuppressWarnings("unchecked")
    public static boolean checkJsonMapFields(String jsonString, String[] keyFields, String roleName) {
        Map<String, Object> parameterMap = (Map<String, Object>) parseJsonString(jsonString, Map.class);
        if (parameterMap != null && parameterMap.size() > 0) {
            for (String keyField : keyFields) {
                if (!parameterMap.containsKey(String.format("%1$s_%2$s", roleName, keyField))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    @SuppressWarnings("unchecked")
    public static boolean checkJsonArrayFields(String jsonString, String... keyFields) {
        List<Map<String, Object>> parameterList = (List<Map<String, Object>>) parseJsonString(jsonString, List.class);
        if (parameterList != null && parameterList.size() > 0) {
            for (Map<String, Object> rowMap : parameterList) {
                for (String keyField : keyFields) {
                    if (!rowMap.containsKey(keyField)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public static int getPropertyIntValue(Object pvBean, String pvPropertyName) {
        return getPropertyIntValue(pvBean, pvPropertyName, 0);
    }

    @SuppressWarnings("rawtypes")
    public static int getPropertyIntValue(Object pvBean, String pvPropertyName, int defaultValue) {
        try {
            Class c = pvBean.getClass();
            Field[] fs = c.getDeclaredFields();
            for (int i = 0; i < fs.length; i++) {
                Field f = fs[i];
                String fieldName = f.getName();
                String fTypeName = f.getGenericType().getTypeName();
                f.setAccessible(true);
                if (fieldName == pvPropertyName) {
                    switch (fTypeName) {                                
                    case "java.lang.String":
                        return anyToInt(f.get(pvBean));
                    case "int":
                        return f.getInt(pvBean);
                    case "short":
                        return (int) f.getShort(pvBean);
                    case "long":
                        return (int) f.getLong(pvBean);
                    default:
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            log.error("exception e="+ex.getMessage(),ex);
        }
        return defaultValue;
    }

    @SuppressWarnings("rawtypes")
    public static String getPropertyStringValue(Object pvBean, String pvPropertyName, String defaultValue) {
        try {
            Class c = pvBean.getClass();
            Field[] fs = c.getDeclaredFields();
            for (int i = 0; i < fs.length; i++) {
                Field f = fs[i];
                String fieldName = f.getName();
                String fTypeName = f.getGenericType().getTypeName();
                f.setAccessible(true);
                if (fieldName == pvPropertyName) {
                    switch (fTypeName) {
                    case "java.lang.String":
                        return anyToString(f.get(pvBean), defaultValue);
                    case "int":
                        return Integer.toString(f.getInt(pvBean));
                    case "short":
                        return Short.toString(f.getShort(pvBean));
                    case "long":
                        return Long.toString(f.getLong(pvBean));
                    default:
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            log.error("exception e="+ex.getMessage(),ex);
        }
        return defaultValue;
    }

    @SuppressWarnings("rawtypes")
    public static void setPropertyFromResultSet(Object pvBean, ResultSet rs) {
        Class c = pvBean.getClass();
        Field[] fs = c.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            String fieldName = f.getName();
            String fTypeName = f.getGenericType().getTypeName();
            f.setAccessible(true);
    
            switch (fTypeName) {
            case "java.lang.String":
                try {
                    f.set(pvBean, rs.getString(fieldName));
                } catch (Exception ex) {
                    log.error("exception e="+ex.getMessage(),ex);
                }
                break;
            case "int":
            case "long":
                try {
                    f.setInt(pvBean, rs.getInt(fieldName));
                } catch (Exception ex) {
                    log.error("exception e="+ex.getMessage(),ex);
                }
                break;
            case "java.util.Date":
                try {
                    f.set(pvBean, rs.getDate(fieldName));
                } catch (Exception ex) {
                    log.error("exception e="+ex.getMessage(),ex);
                }
                break;
            }
        }
    }
    
    @SuppressWarnings("rawtypes")
	public static void setObjectPropertyFromMap(Object pvBean,Class pvBeanClass,Map<String, Object> pvMap){
        Field[] fs = pvBeanClass.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            String fieldName = f.getName();
            if(pvMap.containsKey(fieldName)){
                String fTypeName = f.getGenericType().getTypeName();
                f.setAccessible(true);
                switch (fTypeName) {
                case "java.lang.String":
                    try {
                        f.set(pvBean,  anyToString(pvMap.get(fieldName)));
                    } catch (Exception ex) {
                        log.error("exception e="+ex.getMessage(),ex);
                    }                	
                	break;
                case "int":
                case "long":
                    try {
                        f.setInt(pvBean, anyToInt(pvMap.get(fieldName)));
                    } catch (Exception ex) {
                        log.error("exception e="+ex.getMessage(),ex);
                    }
                    break;
                default:
                    Object v = pvMap.get(fieldName);
                    if (v == null) {
                        v = "";
                    }
                    try {
                    	f.set(pvBean, v);
                    } catch (Exception ex) {
                        log.error("exception e="+ex.getMessage(),ex);
                    }
                    break;
                }
            }
        }
    }

    public static void setPropertyFromResultSet(Map<String, Object> pvMap, ResultSet rs) {
        try {
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int coumnsCount = rsMetaData.getColumnCount();
            for (int i = 1; i <= coumnsCount; i++) {
                String fieldName = rsMetaData.getColumnName(i);
                String fTypeName = rsMetaData.getColumnClassName(i);
                switch (fTypeName) {
                case "java.lang.String":
                    pvMap.put(fieldName, anyToString(rs.getString(fieldName), ""));
                    break;
                case "int":
                case "long":
                    pvMap.put(fieldName, new Integer(anyToInt(rs.getInt(fieldName))));
                    break;
                case "java.util.Date":
                    pvMap.put(fieldName, rs.getDate(fieldName));
                    break;
                default:
                    Object v = rs.getObject(fieldName);
                    if (v == null) {
                        v = "";
                    }
                    pvMap.put(fieldName, v);
                    break;
                }
            }
        } catch (SQLException ex) {
            log.error("exception e="+ex.getMessage(),ex);
        }
    }

    @SuppressWarnings("rawtypes")
    public static HashMap<String, Object> setResponseMapFromDBModel(Object dbModel, Set<String> skipFields, Map<String, String> fieldsMap) {
        HashMap<String, Object> body = new HashMap<String, Object>();
        Class c = dbModel.getClass();
        Field[] fs = c.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            String fieldName = f.getName();
            String fTypeName = f.getGenericType().getTypeName();
            f.setAccessible(true);
            if (skipFields.contains(fieldName)) {
                continue;
            }
            String outputFiledName = (fieldsMap == null ? fieldName : (fieldsMap.containsKey(fieldName) ? fieldsMap.get(fieldName) : fieldName));
            try {
    
                switch (fTypeName) {
                case "java.lang.String":
                    body.put(outputFiledName, anyToString(f.get(dbModel), ""));
                    break;
                case "int":
                case "long":
                    body.put(outputFiledName, anyToInt(f.get(dbModel)));
                    break;
                default:
                    body.put(outputFiledName, f.get(dbModel));
                    break;
                }
            } catch (Exception ex) {
                log.error("exception e="+ex.getMessage(),ex);
            }
        }
        return body;
    }

    public static <T> T setProps(T obj, Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return obj;
        }
        for (Field field : obj.getClass().getFields()) {
            Object value = map.get(field.getName());
            if (value == null) {
                continue;
            }
            try {
                field.set(obj, value);
            } catch (ReflectiveOperationException ex) {
                log.error("exception e=" + ex.getMessage(), ex);
            }
        }
        return obj;
    }

    @SuppressWarnings("rawtypes")
	public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) {
        T obj = null;
        try {
            obj = clazz.newInstance();
        } catch (ReflectiveOperationException ex) {
            log.error("exception e=" + ex.getMessage(), ex);
            return null;
        }
        return setProps(obj, map);
    }
    
    public static long dateStringToLong(String start_time) throws ParseException {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    //   再是Date转换成毫秒数
        return sdf.parse(start_time).getTime();
    }
    
    public static String dateSecondToDateMinute(String time) throws ParseException {
    	if (StringUtils.isEmpty(time) ) {
			return null;
		}
    	long timeStamp=dateStringToLong(time);
    	return longToDateString(timeStamp,"yyyy-MM-dd HH:mm");
    }
    
    public static byte[] intToByte4(int i) {  
        byte[] targets = new byte[4];  
        targets[3] = (byte) (i & 0xFF);  
        targets[2] = (byte) (i >> 8 & 0xFF);  
        targets[1] = (byte) (i >> 16 & 0xFF);  
        targets[0] = (byte) (i >> 24 & 0xFF);  
        return targets;  
    }
    public static Date convertDate(Object pvDate)
    {
    	if(pvDate instanceof Date){
    		return (Date)pvDate;
    	}
    	String strDate="";
    	if(pvDate instanceof String){
    		strDate=(String) pvDate;
    	}else{
    		strDate=pvDate.toString();
    	}
    	SimpleDateFormat simpleDataFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	try {
    		return simpleDataFormat.parse(strDate);
    	} catch (ParseException e) {
    		return null;
    	}
    }    
    public static int verToInter(String version) {
    	String []aInt=version.split("[.]");
    	int verInt=0;
    	for(int i=0;i<=aInt.length-1;i++) {
    		verInt=Integer.parseInt(aInt[i])+verInt*10;
    	}
    	return verInt;
    	
    }
    public static String replaceOddPathChar(String strPath)
    {
    	while(strPath.indexOf("//")>=0){
    		strPath=strPath.replace("//", "/");
    	}
    	while(strPath.indexOf("\\\\")>=0){
    		strPath=strPath.replace("\\\\", "\\");
    	}
    	return strPath;
    }
    
    /**
     * 当前运行环境名称
     * 
     * @return
     */
    public static String getProfileName(){
    	
    	String profile = System.getProperty("javaapp.profile");
    	
        if (profile == null)
            profile = System.getenv("JAVAAPP_PROFILE");
    	
        if(profile == null) profile = "default";
        
        String profileName = PROFILES.get(profile);
        
        return profileName == null ? profile : profileName;
    }

    /**
     * 判断两个对象是否相同
     * 
     * @param obj1
     * @param obj2
     * @return
     */
	public static boolean isEquals(Object obj1, Object obj2) {
		
		if(obj1 == null && obj2 == null) return true;
		if(obj1 == null || obj2 == null) return false;
		
		return obj1.equals(obj2);
	}
	
	/**
     * 转换为字符串数组，兼容List
     * 
     * @param object
     * @return
     */
    @SuppressWarnings("unchecked")
	public static String[] stringArray(Object object) {
    	
    	if(object == null) return null;
    	
    	if(object instanceof List){
    		List<String> list = (List<String>)object;
    		return list.toArray(new String[]{});
    	}
    	
		return (String[])object;
	}
    
}