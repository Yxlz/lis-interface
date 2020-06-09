package com.cdxt.app.util;

import com.cdxt.app.annotation.Copier;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;




/**
 * @Description: 实体辅用工具类
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/27 17:31
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
public class BeanPropertyUtils {
	/**
	 * @return:  java.util.Map<java.lang.String,java.lang.Object>
	 * @throws:  Exception
	 * @description: 将实体bean转成Map
	 * @Param bean:
	 * @date: 2020/5/30 0030 11:11
	 */
	public static Map<String, Object> beanToMap(Object bean) throws Exception {
		Map<String, Object> returnMap = new HashMap<>();
		BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}
		return returnMap;
	}
    /**
     * @return:  void
     * @description: map转bean
     * @Param map:
     * @Param bean:
     * @date: 2020/5/30 0030 11:12
     */
    public static void mapToBean(Map<String, Object> map, final Object bean) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                //判断是否有符合的mapkey
                Object value = map.get(propertyName.toLowerCase());
                if (!org.springframework.util.StringUtils.isEmpty(value)) {
                    String getMethodName = BeanPropertyUtils.getGetMethodNameByPropName(propertyName);
                    Method getMethod =  BeanPropertyUtils.getGetMethodByProperty(bean.getClass(), getMethodName);

                    String setMethodName = BeanPropertyUtils.getSetMethodNameByPropName(propertyName);
                    Method setMethod =  BeanPropertyUtils.getSetMethodByProperty(bean.getClass(), setMethodName, getMethod.getReturnType());

                    if (!org.springframework.util.StringUtils.isEmpty(setMethod)) {
                        setMethod.invoke(bean, value);
                    }
                }
            }
        }
    }
	
	/**
	 * @return:  void
	 * @description: 属性复制（只复制非空字段）
	 * @Param dest:
	 * @Param src:
	 * @date: 2020/5/30 0030 11:13
	 */
	public static void copyProperties(Object dest, Object src){
		BeanUtils.copyProperties(src, dest, getNullPropertyNames(src));
	}
	
	/**
	 * @return:  java.lang.String[]
	 * @description: 获取空字段的属性名称
	 * @Param source:
	 * @date: 2020/5/30 0030 11:19
	 */
	public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for(PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null||(srcValue instanceof String 
            		&& StringUtils.isBlank(srcValue.toString())))
            	emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
	
	/**
	 * @return:  java.lang.String
	 * @description: 获取属性的get方法
	 * @Param propName:
	 * @date: 2020/5/30 0030 11:14
	 */
	public static String getGetMethodNameByPropName(String propName){
		return "get"+String.valueOf(propName.charAt(0)).toUpperCase()+propName.substring(1);
	}
	
	/**
	 * @return:  java.lang.String
	 * @description: 获取属性的set方法
	 * @Param propName:
	 * @date: 2020/5/30 0030 11:15
	 */
	public static String getSetMethodNameByPropName(String propName){
		return "set"+String.valueOf(propName.charAt(0)).toUpperCase()+propName.substring(1);
	}
	
	/**
	 * @return:  java.lang.String
	 * @description: 通过set方法名获取对应的get方法
	 * @Param setName:
	 * @date: 2020/5/30 0030 11:15
	 */
	public static String getGetMethodNameBySetName(String setName){
		return "g"+setName.substring(1);
	}
	
	/**
	 * @return:  java.lang.String
	 * @description: 通过get方法名获取对应的set方法
	 * @Param getName:
	 * @date: 2020/5/30 0030 11:15
	 */
	public static String getSetMethodNameByGetName(String getName){
		return "s"+getName.substring(1);
	}
	
	/**
	 * @description: 属性复制
	 * @Param source:
	 * @Param dest:
	 * @date: 2020/5/30 0030 11:16
	 */
	public static void copyBeanProperties(Object source, Object dest) throws Exception{
		Class<?> sourceClazz = source.getClass();
		Class<?> destClazz = dest.getClass();
		Field[] fields = destClazz.getDeclaredFields();
		for(Field field:fields){
			String getMethodName = BeanPropertyUtils.getGetMethodNameByPropName(field.getName());
			String setMethodName = BeanPropertyUtils.getSetMethodNameByPropName(field.getName());
			//要复制的当前属性对应的get方法
			Method getMethod = BeanPropertyUtils.getGetMethodByProperty(sourceClazz, getMethodName);
			if(getMethod==null) continue;
			
			//要复制的属性值
			Object obj = getMethod.invoke(source);
			if(obj==null) continue;
			
			Copier copier = field.getAnnotation(Copier.class);
			//普通对象复制
			if(copier==null){
				//目标属性所对应的set方法
				Method setMethod =  BeanPropertyUtils.getSetMethodByProperty(destClazz, setMethodName, getMethod.getReturnType());
				if(setMethod==null) continue;
				setMethod.invoke(dest, obj);
			}else{//特殊对象复制
				Class<?> parameterType = copier.parameterType();
				Class<?> genericType = copier.genericType();
				//目标属性所对应的set方法
				Method setMethod =  BeanPropertyUtils.getSetMethodByProperty(destClazz, setMethodName, parameterType);
				if(setMethod==null) continue;
				//如果不是集合属性
				if(genericType==Object.class){
					Object subObj = parameterType.newInstance();
					BeanPropertyUtils.copyBeanProperties(obj, subObj);
					setMethod.invoke(dest, subObj);
					continue;
				}
				//集合属性复制
				if(parameterType == List.class){
					Object subObj = genericType.newInstance();
					Collection<Object> lists = (Collection<Object>) obj;
					Collection<Object> destLists = new ArrayList<Object>();
					for(Object srcObj :lists){
						BeanPropertyUtils.copyBeanProperties(srcObj, subObj);
						destLists.add(subObj);
					}
					//设置数据
					setMethod.invoke(dest, destLists);
				}
			}
		}
	}
	
	/**
	 * @return:  java.lang.reflect.Method
	 * @description: 根据方法名获取类型的get方法
	 * @Param clazz:
	 * @Param propertyName:
	 * @date: 2020/5/30 0030 11:16
	 */
	public static Method getGetMethodByProperty(Class<?> clazz, String propertyName){
		try {
			return clazz.getDeclaredMethod(propertyName);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @return:  java.lang.reflect.Method
	 * @description: 根据方法名获取类型的set方法
	 * @Param clazz:
	 * @Param propertyName:
	 * @Param rtnClazz:
	 * @date: 2020/5/30 0030 11:17
	 */
	public static Method getSetMethodByProperty(Class<?> clazz, String propertyName, Class<?> rtnClazz){
		try {
			return clazz.getDeclaredMethod(propertyName, rtnClazz);
		} catch (Exception e) {
			return null;
		}
	}
}
