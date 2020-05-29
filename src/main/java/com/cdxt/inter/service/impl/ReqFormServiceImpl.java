package com.cdxt.inter.service.impl;

import com.cdxt.inter.service.ReqFormService;
import org.springframework.stereotype.Service;

/**
 * @Description: TODO
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/21 14:43
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Service
public class ReqFormServiceImpl implements ReqFormService {
    /**
     * @param xmlData
     * @return: java.lang.String
     * @throws:
     * @author: tangxiaohui
     * @description: 解析平台传递的申请单XML
     * @Param xmlData:
     * @date: 2020/5/21 14:42
     */
    @Override
    public String parseReqFormXmlData(String xmlData) {
        // TODO
        return "webservice调用成功";
    }
}
