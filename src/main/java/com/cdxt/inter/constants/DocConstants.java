package com.cdxt.inter.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: DocConstants
 * @Description: 文档常量类
 * @Author tangxiaohui
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @DateTime 2020/5/28 17:46
 */
public class DocConstants {

	/**
	 * XML文档默认的编码
	 */
	public static final String DOC_ENCODING = "UTF-8";

	/**
	 * 文档的命名空间
	 */
	public static final String DOC_NAMESPACE = "urn:hl7-org:v3";

	/**
	 * 文档的命名空间别名
	 */
	public static final String DOC_NAMESPACE_KEY = "N";

	/**
	 * null字符串 用于节点值得特殊处理
	 */
	public static final String NULL_STRING = "_null";

	/**
	 * 文档时间默认格式
	 */
	public static final String DOC_DATE_FORMATTER = "yyyyMMddHHmmss";

	/**
	 * 服务调用处理失败代码
	 */
	public static final String CMD_EXE_FAIL = "AE";

	/**
	 * 服务调用处理成功代码
	 */
	public static final String CMD_EXE_SUCCESS = "AA";

	/**
	 * 文档处理代码。标识此消息是否是产品、训练、调试系统的一部分。D：调试；P：产品；T：训练
	 */
	public static final String DOC_PROCESSING_CODE = "P";

	/**
	 * 接收确认类型 AL：总是确认；NE：从不确认；ER：仅在错误/或拒绝时确认；SU：仅在成功完成时确认
	 */
	public static final String DOC_ASK_MODE = "AL";

	/**
	 * 文档体标识
	 */
	public static final String MESSAGE_ACT_PROCESS = "controlActProcess";

	/**
	 * 出错时返回的XML
	 */
	public static final String CMD_FAIL_XML = "<LIS>服务调用返回时发生异常！请重试！</LIS>";

}
