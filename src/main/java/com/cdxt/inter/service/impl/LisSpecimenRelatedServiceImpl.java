package com.cdxt.inter.service.impl;

import com.cdxt.inter.constants.DocConstants;
import com.cdxt.inter.model.XmlMessage;
import com.cdxt.inter.model.docbody.InspectionState;
import com.cdxt.inter.service.LisSpecimenRelatedService;
import com.cdxt.inter.util.DateUtil;
import com.cdxt.inter.util.UUIDGenerator;
import com.cdxt.inter.util.dom4j.Hl7bean2Xml;
import com.cdxt.inter.webservice.params.LisRequestionXml;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description: TODO
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/26 17:36
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Slf4j
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
    public String updateInspectionState(LisRequestionXml requestionXml) {
        XmlMessage<InspectionState> message = new XmlMessage<>();
        message.setMessageId(UUIDGenerator.getUUID());
        message.setCreationTime(new Date());
        InspectionState inspecState = new InspectionState();
        inspecState.setBarcode("12121212121");
        inspecState.setHealthCareCardNo("123123123");
        inspecState.setIdNumber("513029199009244594");
        inspecState.setOperationTime(DateUtil.formatDate(new Date(), DocConstants.DOC_DATE_FORMATTER));
        inspecState.setOperatorDeptCode("9527");
        inspecState.setOperatorDeptName("医学检验科");
        inspecState.setOperatorJobNumber("9528");
        inspecState.setOperatorName("小灰灰");
        inspecState.setPatientBirthday("19900924");
        inspecState.setPatientName("张三");
        inspecState.setPatientSexCode("1");
        inspecState.setPatientSexStr("男");
        inspecState.setPersonInfoId("9999999999999999");
        inspecState.setReqDescription("不举");
        inspecState.setReqNo("7410852963");
        inspecState.setSampleCode("2222");
        inspecState.setSampleName("尿液");
        inspecState.setSampleStateCode("4");
        inspecState.setSampleStateStr("标本接收");
        try {
            Document doc = Hl7bean2Xml.parseXmlFile2Document("hl7v3/InspectionState.xml");
            Element element = Hl7bean2Xml.convertBean(message, doc.getRootElement(), true);
            log.info(element.asXML());
            return element.asXML();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
