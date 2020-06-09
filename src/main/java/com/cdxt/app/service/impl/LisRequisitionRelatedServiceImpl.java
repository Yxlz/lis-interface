package com.cdxt.app.service.impl;

import com.cdxt.app.constants.DocConstants;
import com.cdxt.app.dao.chongqing.LisExchangePatientInfoMapper;
import com.cdxt.app.dao.chongqing.LisExchangePatientItemMapper;
import com.cdxt.app.entity.LisExchangePatientInfo;
import com.cdxt.app.entity.LisExchangePatientItem;
import com.cdxt.app.model.*;
import com.cdxt.app.model.response.CheckResult;
import com.cdxt.app.model.request.InspecRequisitionInfo;
import com.cdxt.app.model.request.mults.RequisitionItemInfo;
import com.cdxt.app.model.response.InspecReqResponse;
import com.cdxt.app.service.LisRequisitionRelatedService;
import com.cdxt.app.util.DateUtil;
import com.cdxt.app.util.UUIDGenerator;
import com.cdxt.app.util.dom4j.Hl7bean2Xml;
import com.cdxt.app.util.dom4j.Hl7xml2Bean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Description: 检验申请单相关服务接口实现类（主要重庆渝北区嘉禾平台开单子）
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/30 0030 13:26
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Slf4j
@Service
public class LisRequisitionRelatedServiceImpl implements LisRequisitionRelatedService {

    @Resource
    private LisExchangePatientInfoMapper lisExchangePatientInfoMapper;
    @Resource
    private LisExchangePatientItemMapper lisExchangePatientItemMapper;

    /**
     * @return: java.lang.String
     * @throws: Exception
     * @description: 保存或更新申请单
     * @Param reqXml:
     * @date: 2020/5/30 0030 13:24
     */
    @Override
    public String saveOrUpdateRequisition(String reqXml) {
        try {
            /*解析XML  开始---------------------------------------*/
            Document doc = Hl7xml2Bean.parseXml2Document(reqXml);
            XmlMessage<InspecRequisitionInfo> mainInfo = (XmlMessage<InspecRequisitionInfo>) Hl7xml2Bean.parseXml(doc.getRootElement(), XmlMessage.class, false);
            InspecRequisitionInfo reqInfo = (InspecRequisitionInfo) Hl7xml2Bean.parseXml_controlActProcess(doc.getRootElement(), InspecRequisitionInfo.class);
            mainInfo.setSubject(reqInfo);
            /*解析XML  结束---------------------------------------*/

            assert reqInfo != null;
            String barcode = reqInfo.getBarCode();//条码号
            String serviceCode = mainInfo.getServiceCode();//服务事件
            LisExchangePatientInfo patientInfo = lisExchangePatientInfoMapper.selectByPatientId(barcode);
            if (serviceCode.equals("JH0206")) {// 新增更新申請單
                CheckResult result = checkExchangePatientInfo(reqInfo);
                if (patientInfo != null) { //更新
                    if (result.isFlag()) {
                        saveOrUpdateExchangePatientInfo(mainInfo, patientInfo);
                    }
                } else {// 新增
                    if (result.isFlag()) {
                        saveOrUpdateExchangePatientInfo(mainInfo, null);
                    }
                }
                return responseXml(mainInfo, result);
            } else if (serviceCode.equals("JH0209")) {//取消申请单
                if (patientInfo != null) {
                    lisExchangePatientInfoMapper.cancelByPatientId(patientInfo.getPatientId());
                    return responseXml(mainInfo, new CheckResult(DocConstants.CMD_EXE_SUCCESS, "取消成功"));
                } else {
                    return responseXml(mainInfo, new CheckResult(DocConstants.CMD_EXE_FAIL, "取消成功,LIS中沒有查询到此申请单"));
                }
            } else {
                return responseXml(mainInfo, new CheckResult(DocConstants.CMD_EXE_FAIL, "无此服务接口"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return DocConstants.CMD_FAIL_XML;
        }
    }

    /**
     * @return: void
     * @description: 保存更新检验申请
     * @Param mainInfo:
     * @Param patientInfo:
     * @date: 2020/5/30 0030 15:49
     */
    private void saveOrUpdateExchangePatientInfo(XmlMessage<InspecRequisitionInfo> mainInfo, LisExchangePatientInfo patient) {
        InspecRequisitionInfo info = mainInfo.getSubject();
        List<RequisitionItemInfo> itemInfos = info.getItems();
        LisExchangePatientItem item = new LisExchangePatientItem();
        boolean isInsert = false;
        if (patient == null) {
            patient = new LisExchangePatientInfo();
            //获取id
            patient.setId(UUIDGenerator.getUUID());
            isInsert = true;
        }
        //地址
        patient.setAddress(info.getAddress() == null ? "" : info.getAddress());
        //床号编码
        patient.setBedCode(info.getBedName() == null ? "" : info.getBedName());
        //床号
        patient.setBedNumber(info.getBedName() == null ? "" : info.getBedName());
        //病房编码
        patient.setRoomCode(info.getInpatientWardRoomCode() == null ? "" : info.getInpatientWardRoomCode());
        //病房号
        patient.setRoomNumber(info.getInpatientWardRoomName() == null ? "" : info.getInpatientWardRoomName());
        //科室编码
        patient.setDeptId(info.getDepartmentCode() == null ? "" : info.getDepartmentCode());
        //科室名称
        patient.setDeptName(info.getDepartmentName() == null ? "" : info.getDepartmentName());
        //病区编码
        patient.setPatientWardCode(info.getInpatientWardCOde() == null ? "" : info.getInpatientWardCOde());
        //病区名称
        patient.setPatientWardName(info.getInpatientWardName() == null ? "" : info.getInpatientWardName());
        //申请机构id
        patient.setHospitalId(info.getHospitalCode());
        //申请机构名称
        patient.setHospitalName(info.getHospitalName());
        // 条码号
        patient.setPatientId(info.getBarCode());
        // patientId号类
        patient.setPatientIdType("1");//嘉和平台条码
        // 病人类型 <!--就诊类别代码1.门诊 2.急诊 3.住院 4.体检 9.其他-->
        String pType;
        switch (info.getClinicTypeCode()) {
            case "1":
                pType = "0";
                break;
            case "2":
                pType = "3";
                break;
            case "3":
                pType = "1";
                break;
            case "4":
                pType = "2";
                break;
            default:
                pType = "8";
                break;
        }
        patient.setPatientType(pType);
        // 病人姓名
        patient.setPatientName(info.getPatientName());
        // 患者年龄
        patient.setPatientAge(info.getPatientAge());
        // 年龄单位
        patient.setPatientAgeUnit(info.getPatientAgeUnit());
        // 病人性别
        patient.setPatientSex(info.getPatientSexCode());
        // 申请医生姓名
        patient.setDoctorName(info.getOrderDoctorName() == null ? "" : info.getOrderDoctorName());
        patient.setDoctorNameId(info.getOrderDoctorCode() == null ? "" : info.getOrderDoctorCode());
        //开单科室名称
        patient.setSectionName(info.getOrderDeptName());
        //开单科室编码
        patient.setSectionNameId(info.getOrderDeptCode());
        //诊断内容
        patient.setpDiagnosis(info.getDiagName() == null ? "" : info.getDiagName());
        //诊断内容编码
        patient.setpDiagnosisId(info.getDiagCode() == null ? "" : info.getDiagCode());
        //诊断类别
        patient.setDiagnosisType(info.getDiagTypeName() == null ? "" : info.getDiagTypeName());
        //诊断类别编码
        patient.setDiagnosisTypeId(info.getDiagTypeCode() == null ? "" : info.getDiagTypeCode());
        //申请时间
        patient.setReqDate(DateUtil.parseDate(info.getOrderTime(), "yyyyMMddHHmmss"));
        //病人号
        //门急诊号
        String patientMzCode = info.getOpcRegistrationNo();
        //住院号
        String patientZyCode = info.getIpiRegistrationNO();
        //体检号
        String patientTjCode = info.getTjRegistrationNO();
        if (StringUtils.isNotBlank(patientMzCode)) {
            patient.setPatientCode(patientMzCode);// 门诊
        } else if (StringUtils.isNotBlank(patientZyCode)) {
            patient.setPatientCode(patientZyCode);// 住院
        } else if (StringUtils.isNotBlank(patientTjCode)) {
            patient.setPatientCode(patientTjCode);// 体检
        }
        // 病人唯一号
        patient.setPersonInfoId(info.getPersonInfoId() == null ? "" : info.getPersonInfoId());
        // 创建时间
        patient.setCreateDate(DateUtil.parseDate(info.getOrderTime(), "yyyyMMddHHmmss"));
        // 身份证号
        patient.setIdCardNum(info.getPersonIDCard() == null ? "" : info.getPersonIDCard());
        // 联系号码
        patient.setPhoneNum(info.getTelephone() == null ? "" : info.getTelephone());
        // 生日
        patient.setBirdthday(info.getBirthday() == null ? "" : info.getBirthday());
        //健康卡号
        patient.setHealthCardNumber(info.getMedicalInsuranceCard() == null ? "" : info.getMedicalInsuranceCard());
        //就诊次数
        patient.setVisitNumber(info.getTimesOfVisit() == null ? "" : info.getTimesOfVisit());

        // 申请单号
        patient.setRequestNo(info.getReqNo());
        // 申请单类型  就诊类别代码1.门诊 2.急诊 3.住院 9.其他
        patient.setRequestCode(info.getClinicTypeCode() == null ? "" : info.getClinicTypeCode());
        // 申请单内容
        patient.setRequestText(info.getReqDescription() == null ? "" : info.getReqDescription());
        // 申请单计划开始日期时间
        patient.setEffectiveLow(info.getEffectiveTimeStarting() == null ? "" : info.getEffectiveTimeStarting());
        // 申请单计划结束日期时间
        patient.setEffectiveHigh(info.getEffectiveTimeLimit() == null ? "" : info.getEffectiveTimeLimit());
        // 申请单状态
        patient.setRequestStatus(info.getReqState() == null ? "" : info.getReqState());

        //标本名称
        patient.setSampleName(info.getSpecimenName() == null ? "" : info.getSpecimenName());
        //标本代码
        patient.setSampleCode(info.getSpecimenCode() == null ? "" : info.getSpecimenCode());
        //检验类型
        patient.setInspecName(info.getInspectionName());
        //检验类型代码
        patient.setInspecNameCode(info.getInspectionCode());
        //优先级别代码
        patient.setPrioritycode(info.getPriorityLevelCode());
        //优先级别
        patient.setPriorityname(info.getPriorityLevelName());
        if (info.getPriorityLevelCode().equals("20") || info.getPriorityLevelName().contains("急")) {
            // 是否急查(1：是；0：否)
            patient.setPatientJzbs("1");
        } else {
            patient.setPatientJzbs("0");
        }
        //采样方法
        patient.setSampleCollectMethod(info.getSamplingMethodName());
        //采样方法代码
        patient.setSampleCollectMethodCode(info.getSamplingMethodCode());
        //采样部位
        patient.setSampleCollectPart(info.getSamplingPartName());
        //采样部位代码
        patient.setSampleCollectPartCode(info.getSamplingPartCode());
        //医嘱执行频率
        patient.setMedicalOrderFrequency(info.getMedicalOrderName());
        //医嘱执行频率代码
        patient.setMedicalOrderFrequencyCode(info.getMedicalOrderCode());
        //创建数据保存时间
        patient.setInsertTime(DateUtil.getNowDate());
        //执行科室
        patient.setExecuteSection("检验科");
        //申请单作废标志
        patient.setCancelled("0");
        if (isInsert) {
            lisExchangePatientInfoMapper.insertSelective(patient);
        } else {
            lisExchangePatientInfoMapper.updateByPrimaryKey(patient);
            lisExchangePatientItemMapper.deleteByPatientId(patient.getId());
        }
        patient.setTotalCharge(insertPatientItems(patient, info, itemInfos, item));
        lisExchangePatientInfoMapper.updateByPrimaryKey(patient);
    }

    /**
     * @return: java.math.BigDecimal
     * @description: 保存申请项目 计算总价格
     * @Param patient:
     * @Param info:
     * @Param items:
     * @Param item:
     * @date: 2020/5/30 0030 17:13
     */
    private BigDecimal insertPatientItems(LisExchangePatientInfo patient, InspecRequisitionInfo info, List<RequisitionItemInfo> items, LisExchangePatientItem item) {
        BigDecimal tbd = new BigDecimal("0.00");
        // 检验项目信息 循环
        for (RequisitionItemInfo requisitionItemInfo : items) {
            //获取id
            item.setId(UUIDGenerator.getUUID());
            //申请单号
            item.setReqOrderNo(info.getReqNo() == null ? "" : info.getReqNo());
            //项目编码
            item.setReqItemNo(requisitionItemInfo.getLabCode() == null ? "" : requisitionItemInfo.getLabCode());
            //项目英文名称
            item.setReqItemName(requisitionItemInfo.getLabName() == null ? "" : requisitionItemInfo.getLabName());
            //项目中文名称
            item.setReqItemCname(requisitionItemInfo.getLabName() == null ? "" : requisitionItemInfo.getLabName());
            // REQ_ITEM_FEE;//项目价格
            BigDecimal bd = new BigDecimal(requisitionItemInfo.getCost() == null ? "0" : requisitionItemInfo.getCost());
            tbd = tbd.add(bd);
            //设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)
            bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
            item.setReqItemFee(bd);
            //项目类型
            item.setItemType("0");
            //病人ID
            item.setPatientId(patient.getId());
            //执行科室
            item.setExecuteSection(requisitionItemInfo.getExecDeptName());
            //执行科室ID
            item.setExecuteSectionId(requisitionItemInfo.getExecDeptId());
            //医嘱ID
            item.setOrderId(requisitionItemInfo.getOrdersId());
            // 检验方法名称
            item.setMethodName(requisitionItemInfo.getMethodName() == null ? "" : requisitionItemInfo.getMethodName());
            // 检验方法名称编码
            item.setMethodeCode(requisitionItemInfo.getMethodCode() == null ? "" : requisitionItemInfo.getMethodCode());
            lisExchangePatientItemMapper.insert(item);
        }
        return tbd.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * @return: com.cdxt.inter.model.response.CheckResult
     * @description: 校验申请单非空信息
     * @Param requisitionInfo:
     * @date: 2020/5/30 0030 14:19
     */
    private CheckResult checkExchangePatientInfo(InspecRequisitionInfo requisitionInfo) {
        if (StringUtils.isBlank(requisitionInfo.getHospitalCode())) {
            return new CheckResult(false, DocConstants.CMD_EXE_FAIL, "申请机构ID为空");
        }
        if (StringUtils.isBlank(requisitionInfo.getHospitalName())) {
            return new CheckResult(false, DocConstants.CMD_EXE_FAIL, "申请机构为空");
        }
        if (StringUtils.isBlank(requisitionInfo.getClinicTypeCode())) {
            return new CheckResult(false, DocConstants.CMD_EXE_FAIL, "病人类型（门诊或住院或体检等）为空");
        }
        if (StringUtils.isBlank(requisitionInfo.getPatientName())) {
            return new CheckResult(false, DocConstants.CMD_EXE_FAIL, "病人姓名为空");
        }
        if (StringUtils.isBlank(requisitionInfo.getOpcRegistrationNo()) && StringUtils.isBlank(requisitionInfo.getIpiRegistrationNO()) && StringUtils.isBlank(requisitionInfo.getTjRegistrationNO())) {
            return new CheckResult(false, DocConstants.CMD_EXE_FAIL, "住院号，门诊号，体检号都为空");
        }
        if (StringUtils.isBlank(requisitionInfo.getOrderDeptName())) {
            return new CheckResult(false, DocConstants.CMD_EXE_FAIL, "申请科室为空");
        }
        if (StringUtils.isBlank(requisitionInfo.getOrderDeptCode())) {
            return new CheckResult(false, DocConstants.CMD_EXE_FAIL, "申请科室ID为空");
        }
        if (StringUtils.isBlank(requisitionInfo.getOrderTime())) {
            return new CheckResult(false, DocConstants.CMD_EXE_FAIL, "申请时间为空");
        }
        if (StringUtils.isBlank(requisitionInfo.getBarCode())) {
            return new CheckResult(false, DocConstants.CMD_EXE_FAIL, "标本ID（条码号）为空");
        }
        if (StringUtils.isBlank(requisitionInfo.getReqNo())) {
            return new CheckResult(false, DocConstants.CMD_EXE_FAIL, "申请单号为空");
        }
        if (StringUtils.isBlank(requisitionInfo.getPersonInfoId())) {
            return new CheckResult(false, DocConstants.CMD_EXE_FAIL, "病人唯一ID为空");
        }
        return new CheckResult(true, DocConstants.CMD_EXE_SUCCESS, "成功");
    }

    /**
     * @return: java.lang.String
     * @description: 接口返回
     * @Param mainInfo:解析xml的申請單信息
     * @Param CheckResult:结果信息
     * @date: 2020/5/30 0030 17:54
     */
    private String responseXml(XmlMessage<InspecRequisitionInfo> mainInfo, CheckResult result) throws Exception {
        InspecReqResponse response = new InspecReqResponse();
        response.setMessageId(UUID.randomUUID().toString());
        response.setCreationTime(new Date());
        response.setServiceCode(mainInfo.getServiceCode());
        response.setProcessingCode(mainInfo.getProcessingCode());
        response.setProcessingModeCode(mainInfo.getProcessingModeCode());
        response.setAcceptAckCode(mainInfo.getAcceptAckCode());
        response.setReceiverId(mainInfo.getReceiverId());
        response.setSenderId(mainInfo.getSenderId());
        response.setSourceId(mainInfo.getMessageId());
        response.setResponseCode(result.getResultCode());
        response.setResponseMessage(result.getResultMsg());
        Document doc = Hl7bean2Xml.parseXmlFile2Document("hl7v3/InspecReqResponse.xml");
        return Hl7bean2Xml.convertBean(response, doc.getRootElement(), false).asXML();
    }
}
