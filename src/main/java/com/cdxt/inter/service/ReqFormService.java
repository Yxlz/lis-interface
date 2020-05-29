package com.cdxt.inter.service;

/**
 * @Description: 申请单服务
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/21 14:40
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
public interface ReqFormService {

    /**
     * @return:  java.lang.String
     * @throws:
     * @author: tangxiaohui
     * @description: 解析平台传递的申请单XML
     * @Param xmlData:
     * @date: 2020/5/21 14:42
     */
    public String parseReqFormXmlData(String xmlData);
}
