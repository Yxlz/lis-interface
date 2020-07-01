package com.cdxt.app.service.impl;

import app.entity.LisInspecDevInfo;
import app.entity.LisInspecGeneralInfo;
import app.entity.LisInspecResultIntraday;
import app.entity.add.LisMicroCultureResult;
import app.entity.add.LisMicroDurgResult;
import app.entity.add.LisMicroMicroResult;
import app.entity.add.LisMicroSmearResult;
import app.manager.api.LisService;
import com.cdxt.app.constants.DocConstants;
import com.cdxt.app.constants.SampleInfoConstants;
import com.cdxt.app.dao.chongqing.VLisDoctorAdviceMapper;
import com.cdxt.app.dao.chongqing.VLisInspecStateMapper;
import com.cdxt.app.dao.chongqing.VLisReportInfoMapper;
import com.cdxt.app.dao.chongqing.VLisSampleInfoMapper;
import com.cdxt.app.entity.VLisDoctorAdvice;
import com.cdxt.app.entity.VLisInspecState;
import com.cdxt.app.entity.VLisReportInfo;
import com.cdxt.app.entity.VLisSampleInfo;
import com.cdxt.app.model.request.*;
import com.cdxt.app.model.request.mults.*;
import com.cdxt.app.service.LisSpecimenRelatedService;
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
import java.text.SimpleDateFormat;
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
    private VLisInspecStateMapper vLisInspecStateMapper;

    @Resource
    private VLisSampleInfoMapper vLisSampleInfoMapper;

    @Resource
    private VLisDoctorAdviceMapper vLisDoctorAdviceMapper;

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
        /*LIS中审核走表的话message中devCode是设备代码，走视图则为设备ID了*/
        List<LisInspecDevInfo> devInfos = lisService.getInspecDevInfoByDevCode(message.getDevCode());
        VLisReportInfo vLisReportInfo;
        if (devInfos != null && devInfos.size() > 0){
            vLisReportInfo = vLisReportInfoMapper.selectByParam(message.getBarCode(), message.getInspecNo(), message.getInputDate(), devInfos.get(0).getId());
        } else {
            vLisReportInfo = vLisReportInfoMapper.selectByParam(message.getBarCode(), message.getInspecNo(), message.getInputDate(), message.getDevCode());
        }
        if (vLisReportInfo == null) {
            log.error("数据库没查询到此数据:{}", message);
            return "";
        }

        InspectionReport inspectionReport = new InspectionReport();
        //注册一个日期空值转换器
        ConvertUtils.register(new DateConverter(null), java.util.Date.class);
        BeanUtils.copyProperties(inspectionReport, vLisReportInfo);
        inspectionReport.setDocumentCreateTime(new Date());
        inspectionReport.setReportId(vLisReportInfo.getId());//用于区分同一个条码多次上机  标本表lis_inspec_general_info表id

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
        if (obj instanceof LisInspecGeneralInfo) {
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

            if (igi.getDevCode().getDevType().equals("1")) {/*微生物*/
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

                    List<LisMicroDurgResult> lmdrs = lisService.getLisMicroDurgResultList(igi.getInspecNo(), igi.getDevCode().getDevCode(), igi.getId());
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
            } else {/*常规结果*/
                List<LisInspecResultIntraday> results = lisService.getOnlyLisInspecResultIntradays(igi.getDevCode().getId(), message.getInspecNo(), message.getInputDate());
                for (LisInspecResultIntraday liri : results) {
                    InspecResult inspecResult = new InspecResult();
                    inspecResult.setItemCode(liri.getItemName());
                    inspecResult.setItemName(liri.getItemNameCn());
                    inspecResult.setResult(liri.getHighLow());
                    inspecResult.setReportTime(liri.getValueTime());
                    boolean inspecValueIsNumber = SendReportUtil.inspecValueIsNumber(liri.getInspecValue());
                    if (inspecValueIsNumber) {
                        inspecResult.setResultType("PQ");//定量
                    } else {
                        inspecResult.setResultType("ST");//定性
                    }
                    inspecResult.setResultValue(SendReportUtil.transferMeaning(liri.getInspecValue()));
                    inspecResult.setReferenceRange(SendReportUtil.transferMeaning(liri.getHighLowGap()));
                    inspecResult.setResultUnit(liri.getUnit());
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
        VLisSampleInfo vLisSampleInfo = vLisSampleInfoMapper.selectByBarcodeAndType(message.getBarCode(), SampleInfoConstants.SAMPLE_RECEIVE);
        if (vLisSampleInfo == null) {
            log.error("数据库没查询到此拒收数据:{}", message);
            return "";
        }
        SampleReceive sampleReceive = new SampleReceive();
        //注册一个日期空值转换器
        ConvertUtils.register(new DateConverter(null), java.util.Date.class);
        BeanUtils.copyProperties(sampleReceive, vLisSampleInfo);

        List<VLisDoctorAdvice> adviceList = vLisDoctorAdviceMapper.selectByPatientIdAndType(vLisSampleInfo.getPatientId(), SampleInfoConstants.SAMPLE_RECEIVE);
        List<DoctorAdvice> advices = new ArrayList<>();
        if (adviceList == null) {
            log.error("数据库没查询到医嘱信息:{}", vLisSampleInfo);
        } else {
            for (VLisDoctorAdvice vadvice : adviceList) {
                DoctorAdvice advice = new DoctorAdvice();
                BeanUtils.copyProperties(advice, vadvice);
                advices.add(advice);
            }
        }
        sampleReceive.setDocAdvices(advices);
        sampleReceive.setMessageId(UUIDGenerator.getUUID());
        sampleReceive.setCreationTime(new Date());
        sampleReceive.setServiceCode("JH0413");
        sampleReceive.setProcessingCode(DocConstants.DOC_PROCESSING_CODE);
        sampleReceive.setProcessingModeCode("T");
        sampleReceive.setAcceptAckCode(DocConstants.DOC_ASK_MODE);
        sampleReceive.setReceiverId("EMR");
        sampleReceive.setSenderId("LIS");
        Document doc = Hl7bean2Xml.parseXmlFile2Document("hl7v3/SampleReceive.xml");
        return Hl7bean2Xml.convertBean(sampleReceive, doc.getRootElement(), false).asXML();
    }

    /**
     * @return: java.lang.String
     * @author: tangxiaohui
     * @description: 标本拒收
     * @Param message:  包含实验号，设备，录入日期，条码，医院id
     * @date: 2020/5/27 14:39
     */
    @Override
    public String sampleRefuse(LisRequestionXml message) throws Exception {
        VLisSampleInfo vLisSampleInfo = vLisSampleInfoMapper.selectByBarcodeAndType(message.getBarCode(), SampleInfoConstants.SAMPLE_REFUSE);
        if (vLisSampleInfo == null) {
            log.error("数据库没查询到此拒收数据:{}", message);
            return "";
        }
        SampleRefuse sampleRefuse = new SampleRefuse();
        //注册一个日期空值转换器
        ConvertUtils.register(new DateConverter(null), java.util.Date.class);
        BeanUtils.copyProperties(sampleRefuse, vLisSampleInfo);

        List<VLisDoctorAdvice> adviceList = vLisDoctorAdviceMapper.selectByPatientIdAndType(vLisSampleInfo.getPatientId(), SampleInfoConstants.SAMPLE_REFUSE);
        List<DoctorAdvice> advices = new ArrayList<>();
        if (adviceList == null) {
            log.error("数据库没查询到医嘱信息:{}", vLisSampleInfo);
        } else {
            for (VLisDoctorAdvice vadvice : adviceList) {
                DoctorAdvice advice = new DoctorAdvice();
                BeanUtils.copyProperties(advice, vadvice);
                advices.add(advice);
            }
        }
        sampleRefuse.setDocAdvices(advices);
        sampleRefuse.setMessageId(UUIDGenerator.getUUID());
        sampleRefuse.setCreationTime(new Date());
        sampleRefuse.setServiceCode("JH0414");
        sampleRefuse.setProcessingCode(DocConstants.DOC_PROCESSING_CODE);
        sampleRefuse.setProcessingModeCode("T");
        sampleRefuse.setAcceptAckCode(DocConstants.DOC_ASK_MODE);
        sampleRefuse.setReceiverId("EMR");
        sampleRefuse.setSenderId("LIS");
        Document doc = Hl7bean2Xml.parseXmlFile2Document("hl7v3/SampleRefuse.xml");
        return Hl7bean2Xml.convertBean(sampleRefuse, doc.getRootElement(), false).asXML();
    }

    /**
     * @return: java.lang.String
     * @author: tangxiaohui
     * @description: 更新检验状态
     * @Param message:  包含实验号，设备，录入日期，条码，医院id
     * @date: 2020/5/27 14:40
     */
    @Override
    public String updateInspectionState(LisRequestionXml requestionXml) throws Exception {
        List<VLisInspecState> vLisInspecStateList = vLisInspecStateMapper.selectByBarcode(requestionXml.getBarCode());
        if (vLisInspecStateList == null || vLisInspecStateList.size() == 0) {
            log.error("数据库没查询到此数据:{}", requestionXml);
            return "";
        }
        ListSort(vLisInspecStateList);
        InspectionState inspecState = new InspectionState();
        //注册一个日期空值转换器
        ConvertUtils.register(new DateConverter(null), java.util.Date.class);
        BeanUtils.copyProperties(inspecState, vLisInspecStateList.get(0));
        inspecState.setMessageId(UUIDGenerator.getUUID());
        inspecState.setCreationTime(new Date());
        inspecState.setServiceCode("JH0411");
        inspecState.setProcessingCode(DocConstants.DOC_PROCESSING_CODE);
        inspecState.setProcessingModeCode("T");
        inspecState.setAcceptAckCode(DocConstants.DOC_ASK_MODE);
        inspecState.setReceiverId("EMR");
        inspecState.setSenderId("LIS");
        Document doc = Hl7bean2Xml.parseXmlFile2Document("hl7v3/InspectionState.xml");
        return Hl7bean2Xml.convertBean(inspecState, doc.getRootElement(), false).asXML();
    }

    /**
     * @return: void
     * @author: tangxiaohui
     * @description: 根据日期排序
     * @Param list:
     * @date: 2020/6/23 11:50
     */
    private static void ListSort(List<VLisInspecState> list) {
        list.sort((o1, o2) -> {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                // format.format(o1.getTime()) 表示 date转string类型 如果是string类型就不要转换了
                Date dt1 = format.parse(format.format(o1.getOperationTime()));
                Date dt2 = format.parse(format.format(o2.getOperationTime()));
                // 这是由大向小排序   如果要由小向大转换比较符号就可以
                return Long.compare(dt2.getTime(), dt1.getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        });
    }
}
