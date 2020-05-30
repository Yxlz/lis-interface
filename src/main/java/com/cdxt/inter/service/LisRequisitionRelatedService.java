package com.cdxt.inter.service;

/**
 * @Description: 申请单接口
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/30 0030 13:17
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
public interface LisRequisitionRelatedService {

    /**
     * @return:  java.lang.String
     * @throws:  Exception
     * @description: 保存或更新申请单
     * @Param reqXml:
     * @date: 2020/5/30 0030 13:24
     */
    public String saveOrUpdateRequisition(String reqXml);
}
