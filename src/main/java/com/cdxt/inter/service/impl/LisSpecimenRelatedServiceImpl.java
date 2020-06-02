package com.cdxt.inter.service.impl;

import com.cdxt.inter.constants.DocConstants;
import com.cdxt.inter.model.request.InspectionState;
import com.cdxt.inter.model.request.SampleReceive;
import com.cdxt.inter.model.request.mults.DoctorAdvice;
import com.cdxt.inter.service.LisSpecimenRelatedService;
import com.cdxt.inter.util.DateUtil;
import com.cdxt.inter.util.UUIDGenerator;
import com.cdxt.inter.util.dom4j.Hl7bean2Xml;
import com.cdxt.inter.model.request.LisRequestionXml;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 标本相关接口实现
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
    public String sampleReceive(LisRequestionXml message) throws Exception {
        SampleReceive sr = new SampleReceive();
        sr.setMessageId(UUIDGenerator.getUUID());
        sr.setCreationTime(new Date());
        sr.setServiceCode("JH0411");
        sr.setProcessingCode(DocConstants.DOC_PROCESSING_CODE);
        sr.setProcessingModeCode("T");
        sr.setAcceptAckCode(DocConstants.DOC_ASK_MODE);
        sr.setReceiverId("EMR");
        sr.setSenderId("LIS");
        sr.setInspecNameCode("12");
        sr.setInspecName("生化类");
        sr.setOrderDeptCode("21");
        sr.setOrderDeptName("开单科室1");
        sr.setOrderDoctorCode("999");
        sr.setOrderDoctorName("医生李四");
        sr.setRegionId("1");
        List<DoctorAdvice> advices = new ArrayList<>();
        DoctorAdvice advice = new DoctorAdvice();
        advice.setAttentionCode("22");
        advice.setAttentionName("空腹");
        advice.setBarcode("1234567890");
        advice.setCollectorCode("321");
        advice.setCollectorName("采集人王五");
        advice.setExcuteSectionCode("666");
        advice.setExcuteSectionName("检验医学科");
        advice.setOrderNo("12345678");
        advice.setPriority("21");
        advices.add(advice);
        advices.add(advice);
        sr.setDocAdvices(advices);
        Document doc = Hl7bean2Xml.parseXmlFile2Document("hl7v3/SampleReceive.xml");
        return Hl7bean2Xml.convertBean(sr, doc.getRootElement(), false).asXML();
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
    public String updateInspectionState(LisRequestionXml requestionXml) throws Exception {
        InspectionState inspecState = new InspectionState();
        inspecState.setMessageId(UUIDGenerator.getUUID());
        inspecState.setCreationTime(new Date());
        inspecState.setServiceCode("JH0411");
        inspecState.setProcessingCode(DocConstants.DOC_PROCESSING_CODE);
        inspecState.setProcessingModeCode("T");
        inspecState.setAcceptAckCode(DocConstants.DOC_ASK_MODE);
        inspecState.setReceiverId("EMR");
        inspecState.setSenderId("LIS");
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
        Document doc = Hl7bean2Xml.parseXmlFile2Document("hl7v3/InspectionState.xml");
        return Hl7bean2Xml.convertBean(inspecState, doc.getRootElement(), false).asXML();
    }
}
