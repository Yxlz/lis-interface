package com.cdxt.inter.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

/**
 * @Description: 利用XStream，xml和bean互相转换
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/27 16:33
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
public class XStreamXmlUtil {

    public static <T> T fromXml2Bean(Class<T> clazz, String xml) {
        //创建解析XML对象
        XStream xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("_-", "_")));
        XStream.setupDefaultSecurity(xStream);
        xStream.allowTypesByWildcard(new String[]{"com.cdxt.inter.model.request.**"});
        //处理注解
        xStream.processAnnotations(clazz);
        //将XML字符串转为bean对象
        T t = (T)xStream.fromXML(xml);
        return t;
    }

    public static String fromBean2Xml(Object obj) {
        XStream xStream = new XStream(new DomDriver());
        xStream.processAnnotations(obj.getClass());
        return xStream.toXML(obj);
    }

}
