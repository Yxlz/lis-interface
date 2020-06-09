package com.cdxt.app.service.impl;

import app.entity.LisInspecGeneralInfo;
import app.entity.LisInspecResultIntraday;
import app.entity.add.LisMicroCultureResult;
import app.entity.add.LisMicroDurgResult;
import app.entity.add.LisMicroMicroResult;
import app.entity.add.LisMicroSmearResult;
import app.manager.api.LisService;
import com.cdxt.app.constants.DocConstants;
import com.cdxt.app.dao.chongqing.VLisReportInfoMapper;
import com.cdxt.app.entity.VLisReportInfo;
import com.cdxt.app.model.request.*;
import com.cdxt.app.model.request.mults.*;
import com.cdxt.app.service.LisSpecimenRelatedService;
import com.cdxt.app.util.DateUtil;
import com.cdxt.app.util.SendReportUtil;
import com.cdxt.app.util.UUIDGenerator;
import com.cdxt.app.util.dom4j.Hl7bean2Xml;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.dom4j.Document;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Resource
    private VLisReportInfoMapper vLisReportInfoMapper;

    @Resource
    private LisService lisService;

    /**
     * @return: java.lang.String
     * @author: tangxiaohui
     * @description: 发送检验报告
     * @Param message:包含实验号，设备，录入日期，条码，医院id
     * @date: 2020/5/26 17:34
     */
    @Override
    public String sendInspectionReport(LisRequestionXml message) throws Exception {
        VLisReportInfo vLisReportInfo = vLisReportInfoMapper.selectByParam(message.getBarCode(), message.getInspecNo(), message.getInputDate());
        if(vLisReportInfo == null){
            log.error("数据库没查询到此数据:{}",message);
            return "";
        }
        InspectionReport inspectionReport = new InspectionReport();
        //注册一个日期空值转换器
        ConvertUtils.register(new DateConverter(null), java.util.Date.class);
        BeanUtils.copyProperties(inspectionReport, vLisReportInfo);
        inspectionReport.setDocumentCreateTime(new Date());

        Object obj = lisService.getLisGeneralInfos(message.getDevCode(), message.getInputDate(), message.getInspecNo());
        /*医嘱从属信息*/
        List<InFulfillmentOf> inFulfillmentOfs = new ArrayList<>();
        /*检验执行*/
        List<InspecItemExcute> inspecItemExcutes = new ArrayList<>();
        /*检验结果项*/
        List<InspecResult> inspecResults = new ArrayList<>();
        /*微生物结果项  个人理解为涂片培养结果*/
        List<InspecMicrobialResult> inspecMicrobialResults = new ArrayList<>();
        /*细菌培养结果项*/
        List<InspecMicroCultureResult> inspecMicroCultureResults = new ArrayList<>();
        /*药敏抗生素结果项*/
        List<InspecMicroDrugResult> inspecMicroDrugResults = new ArrayList<>();
        if(obj instanceof LisInspecGeneralInfo){
            LisInspecGeneralInfo igi = (LisInspecGeneralInfo) obj;
            String[] itemNames = igi.getRequestItemName().split(",");
            String[] itemCodes = igi.getRequestItemNo().split(",");
            for (int i = 0; i < itemCodes.length; i++) {
                InFulfillmentOf inFulfillmentOf = new InFulfillmentOf();
                inFulfillmentOf.setOrderNo(vLisReportInfo.getRequestNo());
                inFulfillmentOf.setOrderItemCode(itemCodes[i]);
                inFulfillmentOf.setOrderItemName(itemNames[i]);
                inFulfillmentOfs.add(inFulfillmentOf);

                InspecItemExcute inspecItemExcute = new InspecItemExcute();
                inspecItemExcute.setCheckTime(vLisReportInfo.getTimeOfCheck());
                inspecItemExcute.setInspecItemCode(itemCodes[i]);
                inspecItemExcute.setInspecItemName(itemNames[i]);
                inspecItemExcute.setDevCode(igi.getDevCode().getDevCode());
                inspecItemExcute.setDevName(igi.getDevCode().getDevName());
                inspecItemExcute.setInspecDoctorJobNo(vLisReportInfo.getInspecExcuteDoctorJobNo());
                inspecItemExcute.setInspecDoctorName(vLisReportInfo.getInspecExcuteDoctorName());
                inspecItemExcutes.add(inspecItemExcute);
            }

            if(igi.getDevCode().getDevType().equals("1")){/*微生物*/
                /*涂片结果*/
                List<LisMicroSmearResult> smearList = lisService.getLisMicroSmearResultList(igi.getId());
                for (LisMicroSmearResult lmsr : smearList) {
                    InspecMicrobialResult inspecMicrobialResult = new InspecMicrobialResult();
                    inspecMicrobialResult.setSmearCode(lmsr.getSmearCode());
                    inspecMicrobialResult.setSmearName(lmsr.getSmeraContent());
                    inspecMicrobialResult.setReportTime(lmsr.getValueTime());
                    inspecMicrobialResult.setResult(SendReportUtil.transferMeaning(lmsr.getInspecValue()));
                    inspecMicrobialResults.add(inspecMicrobialResult);
                }
                /*细菌培养结果*/
                List<LisMicroCultureResult> cultureList = lisService.getLisMicroCultureResults(igi.getId());
                for (LisMicroCultureResult lmcr : cultureList) {
                    InspecMicroCultureResult inspecMicroCultureResult = new InspecMicroCultureResult();
                    inspecMicroCultureResult.setMicroCode(lmcr.getCultureCode());
                    inspecMicroCultureResult.setMicroName(lmcr.getResult());
                    inspecMicroCultureResult.setReportTime(lmcr.getValueTime());
                    inspecMicroCultureResult.setResult(lmcr.getType());
                    inspecMicroCultureResults.add(inspecMicroCultureResult);
                }
                /*药敏抗生素结果 */
                List<LisMicroMicroResult> lmmrs = lisService.getLisMicroMicroResultList(igi.getId());
                for (LisMicroMicroResult lmmr : lmmrs) {
                    InspecMicroCultureResult inspecMicroCultureResult = new InspecMicroCultureResult();
                    inspecMicroCultureResult.setMicroCode(lmmr.getMicroName());
                    inspecMicroCultureResult.setMicroName(lmmr.getMicroNameCn());
                    inspecMicroCultureResult.setReportTime(lmmr.getValueTime());
                    inspecMicroCultureResult.setResult("阳性");
                    inspecMicroCultureResults.add(inspecMicroCultureResult);

                    List<LisMicroDurgResult> lmdrs = lisService.getLisMicroDurgResultList(igi.getInspecNo(),igi.getDevCode().getDevCode(),igi.getId());
                    for (LisMicroDurgResult lmdr : lmdrs) {
                        InspecMicroDrugResult inspecMicroDrugResult = new InspecMicroDrugResult();
                        inspecMicroDrugResult.setItemCode(lmdr.getDurgCode());
                        inspecMicroDrugResult.setItemName(lmdr.getDurgName());
                        inspecMicroDrugResult.setResult(lmdr.getResult());
                        inspecMicroDrugResult.setReportTime(igi.getTimeReport());
                        inspecMicroDrugResult.setResultValue(lmdr.getExpertValue());
                        inspecMicroDrugResult.setReferenceRange(SendReportUtil.transferMeaning(lmdr.getMic()));
                        inspecMicroDrugResults.add(inspecMicroDrugResult);
                    }
                }
            }else{/*常规结果*/
                List<LisInspecResultIntraday> results = lisService.getOnlyLisInspecResultIntradays(igi.getDevCode().getId(), message.getInspecNo(), message.getInputDate());
                for (LisInspecResultIntraday liri : results) {
                    InspecResult inspecResult = new InspecResult();
                    inspecResult.setItemCode(liri.getItemName());
                    inspecResult.setItemName(liri.getItemNameCn());
                    inspecResult.setResult(liri.getHighLow());
                    inspecResult.setReportTime(liri.getValueTime());
                    boolean inspecValueIsNumber = SendReportUtil.inspecValueIsNumber(liri.getInspecValue());
                    if(inspecValueIsNumber){
                        inspecResult.setResultType("PQ");//定量
                    }else{
                        inspecResult.setResultType("ST");//定性
                    }
                    inspecResult.setResultValue(SendReportUtil.transferMeaning(liri.getInspecValue()));
                    inspecResult.setReferenceRange(SendReportUtil.transferMeaning(liri.getHighLowGap()));
                    inspecResults.add(inspecResult);
                }
            }
        }
        inspectionReport.setOrderItems(inFulfillmentOfs);
        inspectionReport.setInspecItemExcutes(inspecItemExcutes);
        inspectionReport.setInspecResults(inspecResults);
        inspectionReport.setInspecMicrobialResults(inspecMicrobialResults);
        inspectionReport.setInspecMicroCultureResults(inspecMicroCultureResults);
        inspectionReport.setInspecMicroDrugResults(inspecMicroDrugResults);

        Document doc = Hl7bean2Xml.parseXmlFile2Document("hl7v3/InspectionReport.xml");
        return Hl7bean2Xml.convertBean(inspectionReport, doc.getRootElement(), false).asXML();
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
    public String sampleRefuse(LisRequestionXml message) throws Exception {
        SampleRefuse sr = new SampleRefuse();
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
        sr.setRegionId("2");
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
        Document doc = Hl7bean2Xml.parseXmlFile2Document("hl7v3/SampleRefuse.xml");
        return Hl7bean2Xml.convertBean(sr, doc.getRootElement(), false).asXML();
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
