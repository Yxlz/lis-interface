package com.cdxt.inter.util.dom4j;

import com.cdxt.inter.constants.DocConstants;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/27 16:01
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
public class Dom4jBaseUtil {
    /**
     * 斜杠/
     */
    static final String ns = "/";

    /**
     * 双斜杠//
     */
    private static final String nss = "//";

    /**
     * /N:
     */
    private static final String ls = "/" + DocConstants.DOC_NAMESPACE_KEY + ":";

    /**
     * /N:/N:
     */
    private static final String lls = ls + ls;

    /**
     * //N:
     */
    private static final String lss = "//" + DocConstants.DOC_NAMESPACE_KEY
            + ":";

    /**
     * N:
     */
    private static final String ss = DocConstants.DOC_NAMESPACE_KEY + ":";

    /**
     * @return: java.lang.String
     * @description: 获取含有namespace的XPath路径
     * @Param path:  注解上的路径
     * @date: 2020/5/29 0029 22:37
     */
    static String getNamespacePath(String path) {
        if (StringUtils.isBlank(path))
            return "";
        String docpath = path.replace(ns, ls).replace(lls, lss).replace("N:..", "..")
                .replace("N:preceding-sibling", "preceding-sibling");
        if (path.startsWith(ns) || path.startsWith(nss)) {
            return docpath;
        }
        return ss + docpath;
    }

    /**
     * @return:  org.dom4j.Document
     * @description: 将xml字符串转成Document文档
     * @Param xml:  xml字符串
     * @date: 2020/5/29 0029 22:46
     */
    public static Document parseXml2Document(String xml) throws Exception {
        Map<String, String> xmlMap = new HashMap<>();
        xmlMap.put(DocConstants.DOC_NAMESPACE_KEY, DocConstants.DOC_NAMESPACE);
        SAXReader reader = new SAXReader();
        reader.getDocumentFactory().setXPathNamespaceURIs(xmlMap);
        return reader.read(new ByteArrayInputStream(xml.getBytes(DocConstants.DOC_ENCODING)));
    }

    /**
     * @return:  org.dom4j.Document
     * @description: 读取xml文件，并将文件内容转成Document文档
     * @Param relativeFileName:  xml文件在项目中的相对路径
     * @date: 2020/5/31 0031 10:03
     */
    public static Document parseXmlFile2Document(String relativeFileName) throws Exception {
        URL url = Dom4jBaseUtil.class.getClassLoader().getResource(relativeFileName);
        File file = new File(url.getFile());
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        return document;
    }
}
