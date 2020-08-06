package com.cdxt.app.service.chongqing;

import com.cdxt.app.model.request.LisRequestionXml;

/**
 * @Description: 标本相关服务
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/26 17:28
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
public interface LisSpecimenRelatedService {

    /**
     * @return:  java.lang.String
     * @author: tangxiaohui
     * @description: 发送检验报告
     * @Param message:  包含实验号，设备，录入日期，条码，医院id
     * @date: 2020/5/26 17:34
     */
    public String sendInspectionReport(LisRequestionXml message) throws Exception;

    /**
     * @return:  java.lang.String
     * @author: tangxiaohui
     * @description: 标本接收
     * @Param message:  包含实验号，设备，录入日期，条码，医院id
     * @date: 2020/5/27 14:37
     */
    public String sampleReceive(LisRequestionXml message) throws Exception;

    /**
     * @return:  java.lang.String
     * @throws:
     * @author: tangxiaohui
     * @description: 标本拒收
     * @Param message:  包含实验号，设备，录入日期，条码，医院id
     * @date: 2020/5/27 14:39
     */
    public String sampleRefuse(LisRequestionXml message) throws Exception;

    /**
     * @return:  java.lang.String
     * @throws:
     * @author: tangxiaohui
     * @description: 更新检验状态
     * @Param message:  包含实验号，设备，录入日期，条码，医院id
     * @date: 2020/5/27 14:40
     */
    public String updateInspectionState(LisRequestionXml message) throws Exception;
}
