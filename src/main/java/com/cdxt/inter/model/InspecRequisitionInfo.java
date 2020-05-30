package com.cdxt.inter.model;

import com.cdxt.inter.annotation.Path;
import com.cdxt.inter.annotation.Subject;
import lombok.Data;

import java.util.List;

/**
 * @Description: 检验申请信息
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/30 0030 11:38
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Data
@Path(path = "//controlActProcess")
public class InspecRequisitionInfo {
    @Path(path = "subject/observationRequest/id/item", attribute = "extension")
    private String reqNo;// 电子申请单编号

    @Path(path = "subject/observationRequest/text", attribute = "value")
    private String reqDescription;// 申请单描述

    @Path(path = "subject/observationRequest/statusCode", attribute = "code")
    private String reqState;// 申请单状态

    @Path(path = "subject/observationRequest/effectiveTime/low", attribute = "value")
    private String effectiveTimeStarting;// 申请单有效日期时间 起

    @Path(path = "subject/observationRequest/effectiveTime/high", attribute = "value")
    private String effectiveTimeLimit;// 申请单有效日期时间 止

    @Path(path = "subject/observationRequest/lengthOfStayQuantity", attribute = "value")
    private String timesOfVisit;// 就诊次数

    @Path(path = "subject/observationRequest/priorityCode", attribute = "code")
    private String priorityLevelCode;// 优先级别

    @Path(path = "subject/observationRequest/priorityCode/displayName", attribute = "value")
    private String priorityLevelName;// 优先级别 名称

    @Path(path = "subject/observationRequest/specimen/specimen/id", attribute = "extension")
    private String barCode;// 标本条码

    @Path(path = "subject/observationRequest/specimen/specimen/code", attribute = "code")
    private String specimenCode;// 标本代码

    @Path(path = "subject/observationRequest/specimen/specimen/code/displayName", attribute = "value")
    private String specimenName;// 标本名

    @Path(path = "subject/observationRequest/specimen/specimen/code1", attribute = "code")
    private String inspectionCode;// 检验类别代码

    @Path(path = "subject/observationRequest/specimen/specimen/code1/displayName", attribute = "value")
    private String inspectionName;// 检验类别名称

    @Path(path = "subject/observationRequest/specimen/specimen/code2", attribute = "code")
    private String samplingMethodCode;// 采样方法代码

    @Path(path = "subject/observationRequest/specimen/specimen/code2/displayName", attribute = "value")
    private String samplingMethodName;// 采样方法名称

    @Path(path = "subject/observationRequest/specimen/specimen/code3", attribute = "code")
    private String samplingPartCode;// 采样部位代码

    @Path(path = "subject/observationRequest/specimen/specimen/code3/displayName", attribute = "value")
    private String samplingPartName;// 采样部位名称

    @Path(path = "subject/observationRequest/specimen/specimen/code4", attribute = "code")
    private String medicalOrderCode;// 医嘱执行频率代码

    @Path(path = "subject/observationRequest/specimen/specimen/code4/displayName", attribute = "value")
    private String medicalOrderName;// 医嘱执行频率名称

    @Path(path = "subject/observationRequest/author/time", attribute = "value")
    private String orderTime;// 开单时间

    @Path(path = "subject/observationRequest/author/signatureText", attribute = "value")
    private String orderDoctorSign;// 开单医生签名

    @Path(path = "subject/observationRequest/author/assignedEntity/id/item ", attribute = "extension")
    private String orderDoctorCode;// 开单医生编码

    @Path(path = "subject/observationRequest/author/assignedEntity/assignedPerson/name/item/part", attribute = "value")
    private String orderDoctorName;// 开单医生姓名

    @Path(path = "subject/observationRequest/author/assignedEntity/providerOrganization/id/item ", attribute = "extension")
    private String hospitalCode;// 医院编码

    @Path(path = "subject/observationRequest/author/assignedEntity/providerOrganization/name/item/part", attribute = "value")
    private String hospitalName;// 医院名称

    @Path(path = "subject/observationRequest/author/assignedEntity/representedOrganization/id/item", attribute = "extension")
    private String orderDeptCode;// 申请科室编码

    @Path(path = "subject/observationRequest/author/assignedEntity/representedOrganization/name/item/part", attribute = "value")
    private String orderDeptName;// 申请科室名称

    @Path(path = "subject/observationRequest/verifier/time", attribute = "value"/*, dateformat = "yyyyMMddHHmmss"*/)
    private String auditTime;// 审核日期

    @Path(path = "subject/observationRequest/verifier/assignedEntity/id/item", attribute = "extension")
    private String auditorCode;// 审核者编码

    @Path(path = "subject/observationRequest/verifier/assignedEntity/assignedPerson/name/item/part", attribute = "value")
    private String auditorName;// 审核者姓名

    @Subject(path = "subject/observationRequest/component2", mult = true)
    private List<RequisitionItemInfo> items;// 检验项目明细

    @Path(path = "subject/observationRequest/subjectOf6/annotation/text", attribute = "value")
    private String mattersNeedAttention;// 注意事项内容

    @Path(path = "subject/observationRequest/componentOf1/encounter/id/item[@root='2.16.156.10011.1.11']", attribute = "extension")
    private String opcRegistrationNo;// 门急诊号

    @Path(path = "subject/observationRequest/componentOf1/encounter/id/item[@root='2.16.156.10011.1.12']", attribute = "extension")
    private String ipiRegistrationNO;// 住院号

    @Path(path = "subject/observationRequest/componentOf1/encounter/id/id", attribute = "extension")
    private String tjRegistrationNO;// 体检号

    @Path(path = "subject/observationRequest/componentOf1/encounter/code", attribute = "code")
    private String clinicTypeCode;// 就诊类型

    @Path(path = "subject/observationRequest/componentOf1/encounter/code/displayName", attribute = "value")
    private String clinicTypeName;// 就诊类型名称

    @Path(path = "subject/observationRequest/componentOf1/encounter/subject/patient/id/item", attribute = "extension")
    private String personInfoId;// 患者ID

    @Path(path = "subject/observationRequest/componentOf1/encounter/subject/patient/patientPerson/id/item[@root='2.16.156.10011.1.3']", attribute = "extension")
    private String personIDCard;// 身份证

    @Path(path = "subject/observationRequest/componentOf1/encounter/subject/patient/patientPerson/id/item[@root='2.16.156.10011.1.15']", attribute = "extension")
    private String medicalInsuranceCard;// 医保卡

    @Path(path = "subject/observationRequest/componentOf1/encounter/subject/patient/patientPerson/name/item/part", attribute = "value")
    private String patientName;// 患者姓名

    @Path(path = "subject/observationRequest/componentOf1/encounter/subject/patient/patientPerson/telecom/item", attribute = "value")
    private String telephone;// 联系电话号

    @Path(path = "subject/observationRequest/componentOf1/encounter/subject/patient/patientPerson/administrativeGenderCode", attribute = "code")
    private String patientSexCode;// 性别代码

    @Path(path = "subject/observationRequest/componentOf1/encounter/subject/patient/patientPerson/birthTime", attribute = "value")
    private String birthday;// 出生日期

    @Path(path = "subject/observationRequest/componentOf1/encounter/subject/patient/patientPerson/birthTime/originalText", attribute = "value")
    private String patientAge;// 年龄

    @Path(path = "subject/observationRequest/componentOf1/encounter/subject/patient/patientPerson/birthTime/originalText", attribute = "unit")
    private String patientAgeUnit;// 年龄单位

    @Path(path = "subject/observationRequest/componentOf1/encounter/subject/patient/patientPerson/addr/item/part", attribute = "value")
    private String address;// 住址

    @Path(path = "subject/observationRequest/componentOf1/encounter/location/serviceDeliveryLocation/location/id/item", attribute = "extension")
    private String bedCode;// 病床编码

    @Path(path = "subject/observationRequest/componentOf1/encounter/location/serviceDeliveryLocation/location/name/item/part", attribute = "value")
    private String bedName;// 病床名

    @Path(path = "subject/observationRequest/componentOf1/encounter/location/serviceDeliveryLocation/location/asLocatedEntityPartOf/location/id/item", attribute = "extension")
    private String inpatientWardRoomCode;// 病房编码

    @Path(path = "subject/observationRequest/componentOf1/encounter/location/serviceDeliveryLocation/location/asLocatedEntityPartOf/location/name/item/part", attribute = "value")
    private String inpatientWardRoomName;// 病房名

    @Path(path = "subject/observationRequest/componentOf1/encounter/location/serviceDeliveryLocation/serviceProviderOrganization/id/item", attribute = "extension")
    private String departmentCode;// 科室编码

    @Path(path = "subject/observationRequest/componentOf1/encounter/location/serviceDeliveryLocation/serviceProviderOrganization/name/item/part", attribute = "value")
    private String departmentName;// 科室名称

    @Path(path = "subject/observationRequest/componentOf1/encounter/location/serviceDeliveryLocation/serviceProviderOrganization/asOrganizationPartOf/wholeOrganization/id/item", attribute = "extension")
    private String inpatientWardCOde;// 病区编码

    @Path(path = "subject/observationRequest/componentOf1/encounter/location/serviceDeliveryLocation/serviceProviderOrganization/asOrganizationPartOf/wholeOrganization/name/item/part", attribute = "value")
    private String inpatientWardName;// 病区名称

    @Path(path = "subject/observationRequest/componentOf1/encounter/pertinentInformation1/observationDx/code", attribute = "code")
    private String diagTypeCode;// 诊断类别编码

    @Path(path = "subject/observationRequest/componentOf1/encounter/pertinentInformation1/observationDx/code/displayName", attribute = "value")
    private String diagTypeName;// 诊断类别名称

    @Path(path = "subject/observationRequest/componentOf1/encounter/pertinentInformation1/observationDx/effectiveTime/any", attribute = "value"/*, dateformat = "yyyyMMdd"*/)
    private String diagTime;// 诊断时间

    @Path(path = "subject/observationRequest/componentOf1/encounter/pertinentInformation1/observationDx/value", attribute = "code")
    private String diagCode;// 诊断编码

    @Path(path = "subject/observationRequest/componentOf1/encounter/pertinentInformation1/observationDx/value/displayName", attribute = "value")
    private String diagName;// 诊断编码
}
