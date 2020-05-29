package com.cdxt.inter.service.impl;

import com.cdxt.inter.service.LisSpecimenRelatedService;
import com.cdxt.inter.webservice.params.LisRequestionXml;
import org.springframework.stereotype.Service;

/**
 * @Description: TODO
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/26 17:36
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Service
public class LisSpecimenRelatedServiceImpl implements LisSpecimenRelatedService {
    /**
     * @return: java.lang.String
     * @author: tangxiaohui
     * @description: 发送检验报告
     * @Param message:包含实验号，设备，录入日期，条码，医院id
     * @date: 2020/5/26 17:34
     */
    @Override
    public String sendInspectionReport(LisRequestionXml message) {
        return "发送检验报告";
    }

    /**
     * @return: java.lang.String
     * @author: tangxiaohui
     * @description: 标本接收
     * @Param message:  包含实验号，设备，录入日期，条码，医院id
     * @date: 2020/5/27 14:37
     */
    @Override
    public String sampleReceive(LisRequestionXml message) {
        return null;
    }

    /**
     * @return: java.lang.String
     * @throws:
     * @author: tangxiaohui
     * @description: 标本拒收
     * @Param message:  包含实验号，设备，录入日期，条码，医院id
     * @date: 2020/5/27 14:39
     */
    @Override
    public String sampleRefuse(LisRequestionXml message) {
        return null;
    }

    /**
     * @return: java.lang.String
     * @throws:
     * @author: tangxiaohui
     * @description: 更新检验状态
     * @Param message:  包含实验号，设备，录入日期，条码，医院id
     * @date: 2020/5/27 14:40
     */
    @Override
    public String updateInspectionState(LisRequestionXml message) {
        return null;
    }
}
