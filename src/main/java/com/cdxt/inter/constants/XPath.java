package com.cdxt.inter.constants;

/**
 * @ClassName: XPath
 * @Description: xpath常量
 * @Author tangxiaohui
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @DateTime 2020/5/28 17:45
 */
public class XPath {
	/**
	 * 删除节点
	 */
	public static final int DEL_NODE = 0;
	/**
	 * 保留节点
	 */
	public static final int VISIABLE = 1;
	/**
	 * 删除属性
	 */
	public static final int DEL_ATTRIBUTE = 2;
	/**
	 * xsi:schemaLocation
	 */
	public static final String XSI_SCHEMA_LOCATION = "xsi:schemaLocation";
	/**
	 * urn:hl7-org:v3 multicacheschemas
	 */
	public static final String URN_HL7_PATH = "urn:hl7-org:v3 ../multicacheschemas/";
	/**
	 * xsd 文件后缀
	 */
	public static final String XSD_SUFFIX = ".xsd";
	/**
	 * 国家标准xml规范路径
	 */
	public static final String xPATH="/CUST_OUT00004/controlActProcess/subject/message_contents/POOR_IN200901UV";
	/**
	 * 扩展路径
	 */
	public static final String xPATH1="/CUST_OUT00004/controlActProcess/subject/message_contents/EXT_MULT/EXT_INFO/REQUEST";
	/**
	 * 国家标准xml请求xml名称路劲
	 */
	public static final String actionName="/CUST_OUT00004/controlActProcess/subject/action";
}
