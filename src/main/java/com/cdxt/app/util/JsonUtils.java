package com.cdxt.app.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class JsonUtils {
    private static ObjectMapper mapper = new ObjectMapper();
    //将json的key值转成小写
    private static ObjectMapper lowerCaseMapper = new ObjectMapper();
	/**
	 * 将对象转换成json字符串
	 * 
	 * @return
	 * @throws JsonProcessingException 
	 */
	public static String parseToJson(Object obj) throws JsonProcessingException{
		if (obj==null)
			return null;
		return mapper.writeValueAsString(obj);
	}
	
	/**
	* @Title: parseToLowerCaseKeyJson 
	* @Description: 转换JSON字符串时 将对象的key值转成小写
	* @最后修改人：hezheng
	* @最后修改时间：2017-4-26 下午3:38:51
	* @param obj
	* @return
	* @throws JsonProcessingException 对方法的参数进行描述
	* @return String 返回类型
	* @throws
	*/
	public static String parseToLowerCaseKeyJson(Object obj) throws JsonProcessingException{
		lowerCaseMapper.setPropertyNamingStrategy(new PropertyNamingStrategy() {  
		    // 反序列化时调用  
		    @Override  
		    public String nameForSetterMethod(MapperConfig<?> config,  
		            AnnotatedMethod method, String defaultName) {  
		        return method.getName().toLowerCase();  
		    }  
		    // 序列化时调用  
		    @Override  
		    public String nameForGetterMethod(MapperConfig<?> config,  
		            AnnotatedMethod method, String defaultName) {
		    	String name = method.getName().toLowerCase();
		    	if(method.getName().startsWith("get")){
		    		name = name.substring(3);
		    	}else if(method.getName().startsWith("is")){
		    		name = name.substring(2);
		    	}
		        return name;
		    }  
		}); 
		return lowerCaseMapper.writeValueAsString(obj);
	}
	
	/**
	 * 将json字符串转换成集合对象
	 * 
	 * @param jsonStr 需要进行解析的json字符串
	 * @param tf  解析目标类型
	 * @return 解析结果
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public static Object parseToObject(String jsonStr, TypeReference tf) throws JsonParseException, JsonMappingException, IOException{
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper.readValue(jsonStr, tf);
	}
	
	/**
	 * 将json字符串转换成对象
	 * 
	 * @param jsonStr 需要进行解析的json字符串
	 * @param clazz  解析目标类型
	 * @return 解析结果
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static <R> R parseToObject(String jsonStr, Class<R> clazz) throws JsonParseException, JsonMappingException, IOException{
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		return mapper.readValue(jsonStr, clazz);
	}
}
