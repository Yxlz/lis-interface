package com.cdxt.inter;

import com.cdxt.inter.constants.DocConstants;
import com.cdxt.inter.model.InspecReqResponse;
import com.cdxt.inter.model.XmlMessage;
import com.cdxt.inter.model.docbody.InspectionState;
import com.cdxt.inter.util.DateUtil;
import com.cdxt.inter.util.UUIDGenerator;
import com.cdxt.inter.util.dom4j.Hl7bean2Xml;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.Date;
import java.util.UUID;

/**
 * @Description: TODO
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/30 0030 13:05
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
public class XmlParseTest {
    public static void main(String[] args) {
//        XmlMessage<InspectionState> message = new XmlMessage<>();
//        message.setMessageId(UUIDGenerator.getUUID());
//        message.setCreationTime(new Date());
//        InspectionState inspecState = new InspectionState();
//        inspecState.setBarcode("12121212121");
//        inspecState.setHealthCareCardNo("123123123");
//        inspecState.setIdNumber("513029199009244594");
//        inspecState.setOperationTime(DateUtil.formatDate(new Date(), DocConstants.DOC_DATE_FORMATTER));
//        inspecState.setOperatorDeptCode("9527");
//        inspecState.setOperatorDeptName("医学检验科");
//        inspecState.setOperatorJobNumber("9528");
//        inspecState.setOperatorName("小灰灰");
//        inspecState.setPatientBirthday("19900924");
//        inspecState.setPatientName("张三");
//        inspecState.setPatientSexCode("1");
//        inspecState.setPatientSexStr("男");
//        inspecState.setPersonInfoId("9999999999999999");
//        inspecState.setReqDescription("不举");
//        inspecState.setReqNo("7410852963");
//        inspecState.setSampleCode("2222");
//        inspecState.setSampleName("尿液");
//        inspecState.setSampleStateCode("4");
//        inspecState.setSampleStateStr("标本接收");
//        try {
//            Document doc = Hl7bean2Xml.parseXmlFile2Document("hl7v3/InspectionState.xml");
//            Element element = Hl7bean2Xml.convertBean(message, doc.getRootElement(), false);
//            System.out.println(element.asXML());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        InspecReqResponse response = new InspecReqResponse();
        response.setMessageId(UUID.randomUUID().toString());
        response.setCreationTime(new Date());
        response.setServiceCode("JH1111");
        response.setProcessingCode(DocConstants.DOC_PROCESSING_CODE);
        response.setProcessingModeCode("T");
        response.setAcceptAckCode(DocConstants.DOC_ASK_MODE);
        response.setReceiverId("LIS");
        response.setSenderId("EMR");
        response.setSourceId(UUID.randomUUID().toString());
        response.setResponseCode("AA");
        response.setResponseMessage("sucess");
        Document doc = null;
        try {
            doc = Hl7bean2Xml.parseXmlFile2Document("hl7v3/InspecReqResponse.xml");
            System.out.println(Hl7bean2Xml.convertBean(response, doc.getRootElement(), false).asXML());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
