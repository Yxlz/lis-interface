package com.cdxt.app.model.request;

import com.cdxt.app.annotation.Path;
import com.cdxt.app.constants.DocConstants;
import com.cdxt.app.model.XpathCommon;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @Description: 检验状态文档body
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/31 0031 13:39
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InspectionState extends XpathCommon {
    @Path(path = "controlActProcess/subject/observationReport/id/item", attribute = "extension")
    private String reqNo;// 电子申请单编号

    @Path(path = "controlActProcess/subject/observationReport/text", attribute = "value")
    private String reqDescription;// 申请单描述

    @Path(path = "controlActProcess/subject/observationReport/specimen/specimen/id", attribute = "extension")
    private String barcode;

    @Path(path = "controlActProcess/subject/observationReport/specimen/specimen/code", attribute = "code")
    private String sampleCode;//标本类别代码

    @Path(path = "controlActProcess/subject/observationReport/specimen/specimen/code/displayName", attribute = "value")
    private String sampleName;//标本类别名称

    @Path(path = "controlActProcess/subject/observationReport/specimen/specimen/subjectOf1/specimenProcessStep/verifier/time", attribute = "value", dateformat = DocConstants.DOC_DATE_FORMATTER)
    private Date operationTime;//操作时间

    @Path(path = "controlActProcess/subject/observationReport/specimen/specimen/subjectOf1/specimenProcessStep/verifier/modeCode", attribute = "code")
    private String sampleStateCode;//标本状态代码

    @Path(path = "controlActProcess/subject/observationReport/specimen/specimen/subjectOf1/specimenProcessStep/verifier/modeCode/displayName", attribute = "value")
    private String sampleStateStr;//标本状态

    @Path(path = "controlActProcess/subject/observationReport/specimen/specimen/subjectOf1/specimenProcessStep/verifier/modeCode/originalText", attribute = "value")
    private String sampleRefuseReason;//拒收原因描述

    @Path(path = "controlActProcess/subject/observationReport/specimen/specimen/subjectOf1/specimenProcessStep/verifier/assignedEntity/id/item", attribute = "extension")
    private String operatorJobNumber;//操作者工号

    @Path(path = "controlActProcess/subject/observationReport/specimen/specimen/subjectOf1/specimenProcessStep/verifier/assignedEntity/assignedPerson/name/item/part", attribute = "value")
    private String operatorName;//操作者姓名

    @Path(path = "controlActProcess/subject/observationReport/specimen/specimen/subjectOf1/specimenProcessStep/verifier/assignedEntity/representedOrganization/id/item", attribute = "extension")
    private String operatorDeptCode;//操作科室编码

    @Path(path = "controlActProcess/subject/observationReport/specimen/specimen/subjectOf1/specimenProcessStep/verifier/assignedEntity/representedOrganization/name/item/part", attribute = "value")
    private String operatorDeptName;//操作科室名称

    @Path(path = "controlActProcess/subject/observationReport/recordTarget/patient/id/item", attribute = "extension")
    private String personInfoId;//患者编号

    @Path(path = "controlActProcess/subject/observationReport/recordTarget/patient/patientPerson/id/item[@root='2.16.156.10011.1.3']", attribute = "extension")
    private String idNumber;//患者身份证号

    @Path(path = "controlActProcess/subject/observationReport/recordTarget/patient/patientPerson/id/item[@root='2.16.156.10011.1.15']", attribute = "extension")
    private String healthCareCardNo;//患者医保卡号

    @Path(path = "controlActProcess/subject/observationReport/recordTarget/patient/patientPerson/name/item/part", attribute = "value")
    private String patientName;//患者姓名

    @Path(path = "controlActProcess/subject/observationReport/recordTarget/patient/patientPerson/administrativeGenderCode", attribute = "code")
    private String patientSexCode;//患者性别代码

    @Path(path = "controlActProcess/subject/observationReport/recordTarget/patient/patientPerson/administrativeGenderCode/displayName", attribute = "value")
    private String patientSexStr;//患者性别

    @Path(path = "controlActProcess/subject/observationReport/recordTarget/patient/patientPerson/birthTime", attribute = "value")
    private String patientBirthday;//患者生日
}
