package com.cdxt.inter.util.dom4j;

import com.cdxt.inter.annotation.Path;
import com.cdxt.inter.annotation.Subject;
import com.cdxt.inter.constants.DocConstants;
import com.cdxt.inter.constants.XPath;
import com.cdxt.inter.util.BeanPropertyUtils;
import com.cdxt.inter.util.DateUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 将bean转换为hl7_v3协议的xml
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/30 0030 9:06
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
public class Hl7bean2Xml extends Dom4jBaseUtil {

    /**
     * @return:  org.dom4j.Element
     * @throws:  Exception
     * @description: 将对象的属性值转换成对应xml的节点或节点属性值
     * @Param object: 需转换的对象
     * @Param rootElement: 目标xml根节点
     * @Param containNameSpace:  是否包含命名空间
     * @date: 2020/5/30 0030 9:20
     */
    public static Element convertBean(Object object, Element rootElement, Boolean containNameSpace) throws Exception {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        // 根路径
        Path rootPath = clazz.getAnnotation(Path.class);
        String rootXPath = "";
        if (rootPath != null)
            rootXPath = rootPath.path();
        // 设置当前类的属性值
        Hl7bean2Xml.setNodesValue(rootXPath, fields, rootElement, object, containNameSpace);
        // 如果继承了父类
        Type superType = clazz.getGenericSuperclass();
        if (superType != null) {
            // 父类解析
            Field[] superFields = clazz.getSuperclass().getDeclaredFields();
            Hl7bean2Xml.setNodesValue(rootXPath, superFields, rootElement, object, containNameSpace);
        }
        return rootElement;
    }

    /**
     * @return:  void
     * @throws:  Exception
     * @description: 将对象的属性值设置到xml对应节点或节点的属性上
     * @Param rootXPath: 根节点XPath
     * @Param fields: 对象的所有反射属性
     * @Param rootElement: xml根节点
     * @Param object: 对象
     * @Param containNameSpace:是否包含命名空间
     * @date: 2020/5/30 0030 9:25
     */
    private static void setNodesValue(String rootXPath, Field[] fields, Element rootElement, Object object, boolean containNameSpace) throws Exception {
        for (Field f : fields) {
            // 非子节点
            if (f.isAnnotationPresent(Path.class)) {
                Hl7bean2Xml.setSingleNodeValue(rootXPath, f, rootElement, object, containNameSpace);
                continue;
            }
            // 如果是子节点
            if (f.isAnnotationPresent(Subject.class)) {
                Hl7bean2Xml.setSubNodeValue(rootXPath, f, rootElement, object, containNameSpace);
            }
        }
    }

    /**
     * @return:  void
     * @description: 设置一个对象属性值给xml单个节点或者节点属性
     * @Param rootXPath: 根节点XPath
     * @Param field: 对象的一个反射属性
     * @Param rootElement: xml根节点
     * @Param object: 对象
     * @Param containNameSpace:  是否包含命名空间
     * @date: 2020/5/30 0030 9:43
     */
    private static void setSingleNodeValue(String rootXPath, Field field, Element rootElement, Object object, boolean containNameSpace) throws Exception {
        Path path = field.getAnnotation(Path.class);
        // 没有进行XPATH注解的直接不解析
        if (path == null)
            return;
        String fieldValue = BeanUtils.getProperty(object, field.getName());
        String nodeXPath = path.path();
        // 如果都未设置则不解析
        if (!StringUtils.isBlank(rootXPath) && !StringUtils.isBlank(nodeXPath)
                && !nodeXPath.startsWith(Hl7bean2Xml.ns)) {
            nodeXPath = Hl7bean2Xml.ns + nodeXPath;
        }
        String realXPath = rootXPath + nodeXPath;
        if (!containNameSpace) {
            realXPath = Hl7bean2Xml.getNamespacePath(realXPath);
        }
        Element node = (Element) rootElement.selectSingleNode(realXPath);
        // 注解路径不正确的直接跳过
        if (node == null)
            return;
        String nullFlavor = path.nullFlavor();
        if (!StringUtils.isBlank(nullFlavor) && StringUtils.isBlank(fieldValue)) {
            // 删除其它所有节点
            List<Attribute> attributes = node.attributes();
            List<String> attributeNames = new ArrayList<>();
            for (Attribute attr : attributes) {
                attributeNames.add(attr.getName());
            }
            for (String attributeName : attributeNames) {
                node.remove(node.attribute(attributeName));
            }
            // 添加空节点标识
            node.addAttribute("nullFlavor", nullFlavor);
            return;
        }
        String attribute = path.attribute();
        // 不存在值的时候必须设置成空字符串
        if (StringUtils.isBlank(fieldValue)) {
            // 如果设置了为空时不显示该节点 则删除该节点或者属性
            if (!path.textEmptyVisiable() || path.attrEmptyVisiable() == XPath.DEL_NODE) {
                // 删除节点
                Node parentNode = node.getParent();
                node.getParent().remove(node);
                // 递归删除空的节点
                Hl7bean2Xml.delParentEmptyNode((Element) parentNode);
                return;
            } else if (path.attrEmptyVisiable() == XPath.DEL_ATTRIBUTE) {
                // 删除属性
                node.remove(node.attribute(attribute));
                return;
            }
            // 没有设置的直接赋值为空字符串
            fieldValue = "";
        }
        // 时间格式化
        String dateformat = path.dateformat();
        if (!StringUtils.isBlank(fieldValue) && !StringUtils.isBlank(dateformat)) {
            fieldValue = DateUtil.getDateUTCtring(fieldValue, dateformat);
        }
        // 属性设置
        if (!StringUtils.isBlank(attribute)) {
            node.addAttribute(attribute, fieldValue);
            return;
        }
        if (DocConstants.NULL_STRING.equals(fieldValue)) {
            // 节点text设置
            node.setText("");
            return;
        }
        // 节点text设置
        node.setText(fieldValue);
    }

    /**
     * @return:  void
     * @description: 设置子节点的值
     * @Param rootXPath: 根节点XPath
     * @Param field: 对象的一个反射子对象
     * @Param rootElement: xml根节点
     * @Param object: 对象
     * @Param containNameSpace:  是否包含命名空间
     * @date: 2020/5/30 0030 9:57
     */
    private static void setSubNodeValue(String rootXPath, Field field, Element rootElement, Object object, boolean containNameSpace) throws Exception {
        Subject subject = field.getAnnotation(Subject.class);
        // 未进行注解的直接跳过
        if (subject == null || StringUtils.isBlank(subject.path()))
            return;
        String nodeXPath = subject.path();
        // 如果都未设置则不解析
        if (!StringUtils.isBlank(rootXPath) && !StringUtils.isBlank(nodeXPath)
                && !nodeXPath.startsWith(Hl7bean2Xml.ns)) {
            nodeXPath = Hl7bean2Xml.ns + nodeXPath;
        }
        String realXPath = rootXPath + nodeXPath;
        if (!containNameSpace) {
            realXPath = Hl7bean2Xml.getNamespacePath(realXPath);
        }
        Element subNode = (Element) rootElement.selectSingleNode(realXPath);
        // 当前对象类型
        Class<?> clazz = object.getClass();
        // 获取当前属性的get方法
        Method method = clazz.getMethod(BeanPropertyUtils.getGetMethodNameByPropName(field.getName()));
        // 目标节点的父节点
        Element parentNode = subNode.getParent();
        List<Element> elements = parentNode.content();
        // 目标子节点的位置
        int subNodeIndex = parentNode.indexOf(subNode);
        // 是否存在多个
        boolean mult = subject.mult();
        // 非集合属性
        if (!mult) {
            Object obj = method.invoke(object);
            if (obj == null) {
                parentNode.remove(subNode);
                return;
            }
            // 获取当前属性实体类型
            Class<?> subClazz = obj.getClass();
            Field[] fields = subClazz.getDeclaredFields();
            for (Field f : fields) {
                if (f.isAnnotationPresent(Path.class)) {
                    Hl7bean2Xml.setSingleNodeValue("", f, subNode, obj, containNameSpace);
                } else if (field.isAnnotationPresent(Subject.class)) {
                    Hl7bean2Xml.setSubNodeValue("", f, subNode, obj, containNameSpace);
                }
            }

            return;
        }
        // 集合属性
        Object subData = method.invoke(object);
        if (subData == null) {
            parentNode.remove(subNode);
            return;
        }
        Object[] subDatas = ((Collection) subData).toArray();
        // 获取当前属性实体类型
        Class<?> subClazz = null;
        for (int i = 0; i < subDatas.length; i++) {
            if (subClazz == null) {
                subClazz = subDatas[0].getClass();
            }
            Field[] fields = subClazz.getDeclaredFields();
            Element newNode = (Element) subNode.clone();
            for (Field f : fields) {
                if (f.isAnnotationPresent(Path.class)) {
                    Hl7bean2Xml.setSingleNodeValue("", f, newNode, subDatas[i], containNameSpace);
                } else if (f.isAnnotationPresent(Subject.class)) {
                    Hl7bean2Xml.setSubNodeValue("", f, newNode, subDatas[i], containNameSpace);
                }
            }
            elements.add(subNodeIndex + i, newNode);
        }
        // 将子节点添加到文档中
        parentNode.setContent(elements);
        // 删除模板子节点
        parentNode.remove(subNode);
    }

    /**
     * @return:  void
     * @description: 递归删除无子节点的节点
     * @Param node:  
     * @date: 2020/5/30 0030 9:47
     */
    private static void delParentEmptyNode(Element node) {
        Element parentNode = node.getParent();
        if (parentNode == null || node.getName().equals(DocConstants.MESSAGE_ACT_PROCESS))
            return;
        if (node.elements().size() == 0) {
            parentNode.remove(node);
        } else {
            Hl7bean2Xml.delParentEmptyNode(parentNode);
        }
    }
}
