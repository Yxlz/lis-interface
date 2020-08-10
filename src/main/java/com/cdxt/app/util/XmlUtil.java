package com.cdxt.app.util;

import com.cdxt.app.model.request.LisRequestionXml;
import com.ctc.wstx.exc.WstxUnexpectedCharException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: tangxiaohui
 * @description: xml bean互转
 * @date: 2020/8/10 15:35
 */
public class XmlUtil {
    /**
     * 将javabean 转换成xml字符串
     *
     * @param bean
     * @param capitalize 首字母大写
     * @return
     */
    public static String convertBeanToXml(Object bean, boolean capitalize) {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            String xml = xmlMapper.writeValueAsString(bean);
            if (capitalize) {
                return xmlStrFirstSpelling(xml, true);
            }
            return xml;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 转换xml字符串节点的首字母大小写
     *
     * @param xml
     * @param capitalize 是否大写
     * @return
     */
    public static String xmlStrFirstSpelling(String xml, boolean capitalize) {
        Set<String> ns = new HashSet<>();
        Pattern ptn = Pattern.compile("<(/?)(.+?)(/?)>");
        Matcher mer = ptn.matcher(xml);
        while (mer.find()) {
            ns.add(mer.group(2));
        }
        for (String n : ns) {
            xml = xml.replaceAll("\\b" + n + "\\b",
                    capitalize ? StringUtils.capitalize(n) : StringUtils.uncapitalize(n));// 全字替换
        }
        return xml;
    }

    /**
     * 将 xml 转换成对应的 bean
     *
     * @param clazz
     * @param xml
     * @return
     */
    public static <T> T convertXmlToBean(Class<T> clazz, String xml) {
        xml = xmlStrFirstSpelling(xml, false);

        XmlMapper xmlMapper = new XmlMapper();
        try {
            return xmlMapper.readValue(xml, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
