package com.cdxt.inter.util.dom4j;

import com.cdxt.inter.annotation.Path;
import com.cdxt.inter.annotation.Subject;
import com.cdxt.inter.constants.DocConstants;
import com.cdxt.inter.util.DateUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 将hl7_v3协议的xml转换为bean
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/29 0029 22:52
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
public class Hl7xml2Bean extends Dom4jBaseUtil {

    /**
     * @return: java.lang.Object
     * @throws: Exception
     * @description: 将XML数据转换成bean
     * @Param rootElement: document的根节点
     * @Param clazz: 目标对象的class
     * @Param containNameSpace:  是否带命名空间
     * @date: 2020/5/29 0029 23:07
     */
    public static Object parseXml(Element rootElement, Class<?> clazz, Boolean containNameSpace) throws Exception {
        Object bean = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        Hl7xml2Bean.setFieldsValue(rootElement, bean, fields, containNameSpace);
        // 如果继承了父类
        Type superType = clazz.getGenericSuperclass();
        if (superType != null) {
            // 父类解析
            Field[] superFields = clazz.getSuperclass().getDeclaredFields();
            Hl7xml2Bean.setFieldsValue(rootElement, bean, superFields, containNameSpace);
        }
        return bean;
    }

    /**
     * @return:  java.util.List<java.lang.Object>
     * @throws:
     * @description: 解析文档体  controlActProcess标签
     * @Param rootElement: document的根节点
     * @Param clazz:  文档体目标对象
     * @date: 2020/5/30 0030 10:49
     */
    public static Object parseXml_controlActProcess(Element rootElement, Class<?> clazz) throws Exception{
        Element body = (Element) rootElement.selectSingleNode(Hl7xml2Bean.getNamespacePath("//" + DocConstants.MESSAGE_ACT_PROCESS));
        // 获取正文的节点
        Path path = clazz.getAnnotation(Path.class);
        if (path != null) {
            String subRoot = path.path();
            Element subNode = (Element) body.selectSingleNode(Hl7xml2Bean.getNamespacePath(subRoot));
            return Hl7xml2Bean.parseXml(subNode, clazz, false);
        }
        return null;
//        List<Object> lists = new ArrayList<>();
//        if (path != null) {
//            String subRoot = path.path();
//            // 消息体正文解析
//            List<Element> subjects = body.selectNodes(Hl7xml2Bean.getNamespacePath(subRoot));
//            for (Element subject : subjects) {
//                Object obj = Hl7xml2Bean.parseXml(subject, clazz, false);
//                lists.add(obj);
//            }
//        }
//        return lists;
    }

    /**
     * @return: java.lang.Object
     * @throws: Exception
     * @description: 给属性设置值 获取属性注解的路径 用XPth解析XML
     * @Param element: document的根节点
     * @Param bean:   目标对象
     * @Param fields: 目标对象的所有反射属性
     * @Param containNameSpace:  是否带命名空间
     * @date: 2020/5/29 0029 23:13
     */
    private static Object setFieldsValue(Element rootElement, Object bean, Field[] fields, boolean containNameSpace) throws Exception {
        for (Field f : fields) {
            if (f.isAnnotationPresent(Path.class)) {
                Hl7xml2Bean.parseProperty(f, rootElement, bean, containNameSpace);
                continue;
            }
            // 解析子对象
            if (f.isAnnotationPresent(Subject.class)) {
                Subject subject = f.getAnnotation(Subject.class);
                String node = subject.path();
                if (!containNameSpace) {
                    node = Hl7xml2Bean.getNamespacePath(node);
                }
                List<Element> nodes = (List<Element>) rootElement.selectNodes(node);
                if (nodes == null || nodes.size() == 0)
                    continue;
                // 获取类型
                Class<?> subClazz = Hl7xml2Bean.getClassType(f);
                // 解析子对象
                List<?> lists = Hl7xml2Bean.parseSubjectBean(nodes, subClazz, containNameSpace);
                // 是否存在多个
                boolean mult = subject.mult();
                if (mult) {
                    // 直接取节点的text值
                    BeanUtils.setProperty(bean, f.getName(), lists);
                    continue;
                }
                // 直接取节点的text值
                BeanUtils.setProperty(bean, f.getName(), lists.get(0));
            }
        }
        return bean;
    }

    /**
     * @return: void
     * @throws: Exception
     * @description: 解析单个属性 并赋值
     * @Param field: 目标对象的一个属性
     * @Param rootElement: document的根节点
     * @Param bean: 目标对象
     * @Param containNameSpace:  是否带命名空间
     * @date: 2020/5/29 0029 23:23
     */
    private static void parseProperty(Field field, Element rootElement, Object bean, boolean containNameSpace) throws Exception {
        Path path = field.getAnnotation(Path.class);
        String xPath = path.path();
        if (!containNameSpace) {
            xPath = Hl7xml2Bean.getNamespacePath(xPath);
        }
        Element node = (Element) rootElement.selectSingleNode(xPath);
        if (node == null)
            return;
        // 属性值
        String attribute = path.attribute();
        // 时间格式化
        String dateformat = path.dateformat();
        String value = "";
        // 取属性值
        if (!StringUtils.isBlank(attribute)) {
            value = node.attributeValue(attribute);
        } else {
            value = node.getText();
        }
        // 时间格式化
        if (!StringUtils.isBlank(dateformat)) {
            if (!StringUtils.isBlank(value)) {
                BeanUtils.setProperty(bean, field.getName(), DateUtil.parseUTCDate(value, dateformat));
            }
            return;
        }
        // 直接取节点的text值
        BeanUtils.setProperty(bean, field.getName(), value);
    }

    /**
     * @return: java.lang.Class<?>
     * @description: 获取一个属性的类型
     * @Param field:
     * @date: 2020/5/29 0029 23:33
     */
    private static Class<?> getClassType(Field field) {
        Class fieldClazz = field.getType();
        if (fieldClazz.isAssignableFrom(List.class)) {
            Type fc = field.getGenericType();
            if (fc instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) fc;
                fieldClazz = (Class) pt.getActualTypeArguments()[0];
            }
        }
        return fieldClazz;
    }

    /**
     * @return: java.util.List<?>
     * @throws: Exception
     * @description: 解析子节点信息
     * @Param nodes:
     * @Param clazz:
     * @Param containNameSpace:
     * @date: 2020/5/29 0029 23:37
     */
    private static List<?> parseSubjectBean(List<Element> nodes, Class<?> clazz, boolean containNameSpace) throws Exception {
        List lists = new ArrayList();
        Field[] fields = clazz.getDeclaredFields();
        for (Element el : nodes) {
            Object bean = clazz.newInstance();
            for (Field f : fields) {
                if (f.isAnnotationPresent(Path.class)) {
                    Hl7xml2Bean.parseProperty(f, el, bean, containNameSpace);
                } else if (f.isAnnotationPresent(Subject.class)) {
                    Hl7xml2Bean.setFieldsValue(el, bean, fields, containNameSpace);
                }
            }
            lists.add(bean);
        }
        return lists;
    }
}
