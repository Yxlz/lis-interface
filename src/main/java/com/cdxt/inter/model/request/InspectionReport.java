package com.cdxt.inter.model.request;

import com.cdxt.inter.annotation.Path;
import com.cdxt.inter.annotation.Subject;
import com.cdxt.inter.constants.DocConstants;
import com.cdxt.inter.model.request.mults.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Description: 检验报告
 * @Author: tangxiaohui
 * @CreateDate: 2020/6/3 9:55
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Data
public class InspectionReport {

    @Path(path = "effectiveTime", attribute = "value", dateformat = DocConstants.DOC_DATE_FORMATTER)
    private Date documentCreateTime;//文档机器生成时间

    /*文档记录对象(患者)*/
    @Path(path = "recordTarget/patientRole/id[@root='1.2.156.112636.1.2.1.4.1']", attribute = "extension")
    private String personInfoId;
    @Path(path = "recordTarget/patientRole/id[@root='1.2.156.112636.1.1.2.2.1.3']", attribute = "extension")
    private String mjzNo;//门急诊号
    @Path(path = "recordTarget/patientRole/id[@root='1.2.156.112636.1.1.2.2.1.2']", attribute = "extension")
    private String zyNo;//住院号
    @Path(path = "recordTarget/patientRole/id[@root='1.2.156.112636.1.1.2.2.1.24']", attribute = "extension")
    private String reportNo;//检验报告单号
    @Path(path = "recordTarget/patientRole/id[@root='1.2.156.112636.1.1.2.2.1.20']", attribute = "extension")
    private String requestNo;//电子申请单编号
    @Path(path = "recordTarget/patientRole/id[@root='1.2.156.112636.1.1.2.2.1.25']", attribute = "extension")
    private String specimenNo;//检验标本编号
    @Path(path = "recordTarget/patientRole/id[@root='1.2.156.112636.1.1.2.2.1.4']", attribute = "extension")
    private String tjNo;//体检号
    @Path(path = "recordTarget/patientRole/lengthOfStayQuantity", attribute = "value")
    private String timesOfVisit;//就诊次数
    @Path(path = "recordTarget/patientRole/patientType/patienttypeCode", attribute = "code")
    private String patientTypeCode;//患者就诊类别代码1.门诊 2.急诊 3.住院 4.体检 9.其他
    @Path(path = "recordTarget/patientRole/patientType/patienttypeCode", attribute = "displayName")
    private String patientTypeName;
    @Path(path = "recordTarget/patientRole/telecom", attribute = "value")
    private String patientPhone;
    @Path(path = "recordTarget/patientRole/patient/id", attribute = "extension")
    private String patientIdNo;//身份证号
    @Path(path = "recordTarget/patientRole/patient/name")
    private String patientName;//患者姓名
    @Path(path = "recordTarget/patientRole/patient/administrativeGenderCode", attribute = "code")
    private String patientSexCode;//性别代码
    @Path(path = "recordTarget/patientRole/patient/administrativeGenderCode", attribute = "displayName")
    private String patientSexStr;//性别
    @Path(path = "recordTarget/patientRole/patient/birthTime", attribute = "value")
    private String patientBirthday;//出生日期
    @Path(path = "recordTarget/patientRole/patient/age", attribute = "unit")
    private String patientAgeUnit;//年龄单位
    @Path(path = "recordTarget/patientRole/patient/age", attribute = "value")
    private String patientAge;//年龄

    /*检验报告医师（文档创作者）*/
    @Path(path = "author/time", attribute = "value", dateformat = DocConstants.DOC_DATE_FORMATTER)
    private Date timeReport;//检验报告日期  审核时间
    @Path(path = "author/assignedAuthor/id", attribute = "extension")
    private String reporterJobNo;//医师工号
    @Path(path = "author/assignedAuthor/assignedPerson/name")
    private String reporterName;//医师姓名

    /*保管机构*/
    @Path(path = "custodian/assignedCustodian/representedCustodianOrganization/id", attribute = "extension")
    private String custodianOrgCode;//保管机构编码
    @Path(path = "custodian/assignedCustodian/representedCustodianOrganization/name")
    private String custodianOrgName;//保管机构名称

    /*审核医师签名*/
    @Path(path = "legalAuthenticator/time", attribute = "value", dateformat = DocConstants.DOC_DATE_FORMATTER)
    private Date timeOfCheck;//审核时间
    @Path(path = "legalAuthenticator/assignedEntity/id", attribute = "extension")
    private String checkDoctorJobNo;//审核医生工号
    @Path(path = "legalAuthenticator/assignedEntity/assignedPerson/name")
    private String checkDoctorName;//审核医生

    /*检验技师签名*/
    @Path(path = "authenticator/time", attribute = "value", dateformat = DocConstants.DOC_DATE_FORMATTER)
    private Date timeOfInspec;//检验时间
    @Path(path = "authenticator/assignedEntity/id", attribute = "extension")
    private String inspecDoctorJobNo;//检验医生工号
    @Path(path = "authenticator/assignedEntity/assignedPerson/name")
    private String inspecDoctorName;//检验医生

    /*检验申请机构及科室*/
    @Path(path = "participant/time", attribute = "value", dateformat = DocConstants.DOC_DATE_FORMATTER)
    private Date timeOfReq;//申请时间
    @Path(path = "participant/associatedEntity/scopingOrganization/id", attribute = "extension")
    private String reqDeptCode;//申请科室编码
    @Path(path = "participant/associatedEntity/scopingOrganization/name")
    private String reqDeptName;//申请科室
    @Path(path = "participant/associatedEntity/scopingOrganization/asOrganizationPartOf/wholeOrganization/id", attribute = "extension")
    private String reqHospitalCode;//申请医院编码
    @Path(path = "participant/associatedEntity/scopingOrganization/asOrganizationPartOf/wholeOrganization/name")
    private String reqHospitalName;//申请医院

    /*医嘱从属信息*/
    @Subject(path = "inFulfillmentOf", mult = true)
    private List<InFulfillmentOf> orderItems;

    @Path(path = "documentationOf/serviceEvent/effectiveTime", attribute = "value", dateformat = DocConstants.DOC_DATE_FORMATTER)
    private Date timeOfInspecExcute;//检验执行时间
    @Path(path = "documentationOf/serviceEvent/performer/assignedEntity/id", attribute = "extension")
    private String inspecExcuteDoctorJobNo;//检验执行医生工号
    @Path(path = "documentationOf/serviceEvent/performer/assignedEntity/assignedPerson/name")
    private String inspecExcuteDoctorName;//检验执行医生

    /*病床号、病房、病区、科室和医院的关联*/
    @Path(path = "componentOf/encompassingEncounter/location/healthCareFacility/serviceProviderOrganization/asOrganizationPartOf/wholeOrganization/id", attribute = "extension")
    private String bedNo;//DE01.00.026.00	病床号
    @Path(path = "componentOf/encompassingEncounter/location/healthCareFacility/serviceProviderOrganization/asOrganizationPartOf/wholeOrganization/asOrganizationPartOf/wholeOrganization/id", attribute = "extension")
    private String patientWardNo;//DE01.00.026.00	病房号
    @Path(path = "componentOf/encompassingEncounter/location/healthCareFacility/serviceProviderOrganization/asOrganizationPartOf/wholeOrganization/asOrganizationPartOf/wholeOrganization/asOrganizationPartOf/wholeOrganization/id", attribute = "extension")
    private String patientWardNoCode;//DE01.00.026.00	病区编码
    @Path(path = "componentOf/encompassingEncounter/location/healthCareFacility/serviceProviderOrganization/asOrganizationPartOf/wholeOrganization/asOrganizationPartOf/wholeOrganization/asOrganizationPartOf/wholeOrganization/name")
    private String patientWardName;//DE01.00.026.00	病区名称
    @Path(path = "componentOf/encompassingEncounter/location/healthCareFacility/serviceProviderOrganization/asOrganizationPartOf/wholeOrganization/asOrganizationPartOf/wholeOrganization/asOrganizationPartOf/wholeOrganization/asOrganizationPartOf/wholeOrganization/id", attribute = "extension")
    private String deptCode;//DE01.00.026.00	科室编码
    @Path(path = "componentOf/encompassingEncounter/location/healthCareFacility/serviceProviderOrganization/asOrganizationPartOf/wholeOrganization/asOrganizationPartOf/wholeOrganization/asOrganizationPartOf/wholeOrganization/asOrganizationPartOf/wholeOrganization/name")
    private String deptName;//DE01.00.026.00	科室名称
    @Path(path = "componentOf/encompassingEncounter/location/healthCareFacility/serviceProviderOrganization/asOrganizationPartOf/wholeOrganization/asOrganizationPartOf/wholeOrganization/asOrganizationPartOf/wholeOrganization/asOrganizationPartOf/wholeOrganization/name/asOrganizationPartOf/wholeOrganization/name")
    private String hospitalName;//DE01.00.026.00	医院名称


    /*---------------------------------------文档体Body------------------------------------------*/
    /*诊断记录章节*/
    @Path(path = "component/structuredBody/component/section/code[@code='29548-5']/../text")
    private String diagnosisText;//诊断文本，诊断描述、内容
    @Path(path = "component/structuredBody/component/section/code[@code='29548-5']/../entry/observation/effectiveTime", attribute = "value", dateformat = DocConstants.DOC_DATE_FORMATTER)
    private Date diagnosisTime;//诊断日期
    @Path(path = "component/structuredBody/component/section/code[@code='29548-5']/../entry/observation/value", attribute = "code")
    private String diagnosisCode;//诊断代码
    @Path(path = "component/structuredBody/component/section/code[@code='29548-5']/../entry/observation/value", attribute = "displayName")
    private String  diagnosisName;//诊断名称
    @Path(path = "component/structuredBody/component/section/code[@code='29548-5']/../entry/observation/performer/assignedEntity/representedOrganization/name")
    private String  diagnosisOrg;//诊断机构

    /*采样预约章节*/
    @Path(path = "component/structuredBody/component/section/entry/act/code[@displayName='预约信息']/../effectiveTime", attribute = "value", dateformat = DocConstants.DOC_DATE_FORMATTER)
    private Date  preOrderTime;//预约时间
    @Path(path = "component/structuredBody/component/section/entry/act/code[@displayName='预约信息']/../performer/assignedEntity/id", attribute = "extension")
    private String   preOrderPersonJobNo;//预约人工号
    @Path(path = "component/structuredBody/component/section/entry/act/code[@displayName='预约信息']/../performer/assignedEntity/assignedPerson/name")
    private String   preOrderPersonName;//预约人姓名
    @Path(path = "component/structuredBody/component/section/entry/act/code[@displayName='预约信息']/../entryRelationship/act/effectiveTime", attribute = "value", dateformat = DocConstants.DOC_DATE_FORMATTER)
    private Date  preCollectTime;//拟采样时间
    @Path(path = "component/structuredBody/component/section/entry/act/code[@displayName='预约信息']/../entryRelationship/act/performer/assignedEntity/id", attribute = "extension")
    private String   preCollectorJobNo;//拟采样人工号
    @Path(path = "component/structuredBody/component/section/entry/act/code[@displayName='预约信息']/../entryRelationship/act/performer/assignedEntity/assignedPerson/name")
    private String   preCollectorName;//拟采样人姓名

    /*实验室检查专业章节*/
    @Path(path = "component/structuredBody/component/section/templateId[@root='1.3.6.1.4.1.19376.1.3.3.2.1']/../code", attribute = "code")
    private String   inspecCode;//检验类别
    @Path(path = "component/structuredBody/component/section/templateId[@root='1.3.6.1.4.1.19376.1.3.3.2.1']/../code", attribute = "displayName")
    private String   inspecName;//检验类别
    @Path(path = "component/structuredBody/component/section/templateId[@root='1.3.6.1.4.1.19376.1.3.3.2.1']/../title")
    private String   inspecTitle;//检验标题，如：肝功+肾功
        /*样本信息*/
    @Path(path = "component/structuredBody/component/section/templateId[@root='1.3.6.1.4.1.19376.1.3.3.2.1']/../entry/act/specimen/specimenRole/id", attribute = "extension")
    private String   barcode;//样本编号/条码
    @Path(path = "component/structuredBody/component/section/templateId[@root='1.3.6.1.4.1.19376.1.3.3.2.1']/../entry/act/specimen/specimenRole/specimenPlayingEntity/code", attribute = "code")
    private String   sampleCode;//样本编码
    @Path(path = "component/structuredBody/component/section/templateId[@root='1.3.6.1.4.1.19376.1.3.3.2.1']/../entry/act/specimen/specimenRole/specimenPlayingEntity/code", attribute = "displayName")
    private String   sampleName;//样本编码
        /*样本采集条目*/
    @Path(path = "component/structuredBody/component/section/templateId[@root='1.3.6.1.4.1.19376.1.3.3.2.1']/../entry/act/entryRelationship/act/code[@displayName='Specimen Collection']/../effectiveTime", attribute = "value")
    private String   sampleCollectTime;//样本采集时间 20071108000000.0000-0500
    @Path(path = "component/structuredBody/component/section/templateId[@root='1.3.6.1.4.1.19376.1.3.3.2.1']/../entry/act/entryRelationship/act/code[@displayName='Specimen Collection']/../specimen/specimenRole/id", attribute = "extension")
    private String   barcode1;//样本编号/条码
    @Path(path = "component/structuredBody/component/section/templateId[@root='1.3.6.1.4.1.19376.1.3.3.2.1']/../entry/act/entryRelationship/act/code[@displayName='Specimen Collection']/../performer/assignedEntity/id", attribute = "extension")
    private String   sampleCollectorJobNo;//采样人工号
    @Path(path = "component/structuredBody/component/section/templateId[@root='1.3.6.1.4.1.19376.1.3.3.2.1']/../entry/act/entryRelationship/act/code[@displayName='Specimen Collection']/../performer/assignedEntity/assignedPerson/name")
    private String   sampleCollectorName;//采样人姓名
    @Path(path = "component/structuredBody/component/section/templateId[@root='1.3.6.1.4.1.19376.1.3.3.2.1']/../entry/act/entryRelationship/act/code[@displayName='Specimen Collection']/../entryRelationship/procedure/methodCode", attribute = "code")
    private String   sampleCollectMethodCode;//采样方法代码
    @Path(path = "component/structuredBody/component/section/templateId[@root='1.3.6.1.4.1.19376.1.3.3.2.1']/../entry/act/entryRelationship/act/code[@displayName='Specimen Collection']/../entryRelationship/procedure/methodCode", attribute = "displayName")
    private String   sampleCollectMethodName;//采样方法
    @Path(path = "component/structuredBody/component/section/templateId[@root='1.3.6.1.4.1.19376.1.3.3.2.1']/../entry/act/entryRelationship/act/code[@displayName='Specimen Collection']/../entryRelationship/procedure/targetSiteCode", attribute = "code")
    private String   sampleCollectPartCode;//采样部位代码
    @Path(path = "component/structuredBody/component/section/templateId[@root='1.3.6.1.4.1.19376.1.3.3.2.1']/../entry/act/entryRelationship/act/code[@displayName='Specimen Collection']/../entryRelationship/procedure/targetSiteCode", attribute = "displayName")
    private String   sampleCollectPartName;//采样部位名称
    @Path(path = "component/structuredBody/component/section/templateId[@root='1.3.6.1.4.1.19376.1.3.3.2.1']/../entry/act/entryRelationship/act/code[@displayName='Specimen Collection']/../entryRelationship/procedure/specimen/specimenRole/id", attribute = "extension")
    private String   barcode2;//样本编号/条码
        /*样本接收条目*/
    @Path(path = "component/structuredBody/component/section/templateId[@root='1.3.6.1.4.1.19376.1.3.3.2.1']/../entry/act/entryRelationship/act/code[@displayName='Specimen Receive']/../effectiveTime/low", attribute = "value", dateformat = DocConstants.DOC_DATE_FORMATTER)
    private String   sampleReqTime;//标本送检时间
    @Path(path = "component/structuredBody/component/section/templateId[@root='1.3.6.1.4.1.19376.1.3.3.2.1']/../entry/act/entryRelationship/act/code[@displayName='Specimen Receive']/../effectiveTime/high", attribute = "value", dateformat = DocConstants.DOC_DATE_FORMATTER)
    private String   sampleReceiveTime;//标本接收时间
    @Path(path = "component/structuredBody/component/section/templateId[@root='1.3.6.1.4.1.19376.1.3.3.2.1']/../entry/act/entryRelationship/act/code[@displayName='Specimen Receive']/../specimen/specimenRole/id", attribute = "extension")
    private String   barcode3;//样本编号/条码
    @Path(path = "component/structuredBody/component/section/templateId[@root='1.3.6.1.4.1.19376.1.3.3.2.1']/../entry/act/entryRelationship/act/code[@displayName='Specimen Receive']/../performer/assignedEntity/id", attribute = "extension")
    private String   sampleReceiverJobNo;//样本接收人工号
    @Path(path = "component/structuredBody/component/section/templateId[@root='1.3.6.1.4.1.19376.1.3.3.2.1']/../entry/act/entryRelationship/act/code[@displayName='Specimen Receive']/../performer/assignedEntity/assignedPerson/name")
    private String   sampleReceiverName;//样本接收人姓名

        /*检验执行*/
    @Subject(path = "component/structuredBody/component/section/templateId[@root='1.3.6.1.4.1.19376.1.3.3.2.1']/../entry/act/entryRelationship/act/code[@codeSystem='1.2.156.112636.1.1.2.1.4.9']/../..", mult = true)
    private List<InspecItemExcute> inspecItemExcutes;

    /*检验报告章节*/
    @Path(path = "component/structuredBody/component/section/entry/organizer/id", attribute = "extension")
    private String reportNo1;//报告号

    @Subject(path = "component/structuredBody/component/section/entry/organizer/component/observation/code[@codeSystemName='检验结果项代码表']/../..", mult = true)
    private List<InspecResult> inspecResults;//检验结果项

    @Subject(path = "component/structuredBody/component/section/entry/organizer/component/observation/code[@codeSystemName='微生物代码表']/../..", mult = true)
    private List<InspecMicrobialResult> inspecMicrobialResults;//微生物结果项  个人理解为涂片培养结果

    @Subject(path = "component/structuredBody/component/section/entry/organizer/component/observation/code[@codeSystemName='细菌培养代码表']/../..", mult = true)
    private List<InspecMicroCultureResult> inspecMicroCultureResults;//细菌培养结果项

    @Subject(path = "component/structuredBody/component/section/entry/organizer/component/observation/code[@codeSystemName='药敏抗生素代码表']/../..", mult = true)
    private List<InspecMicroDrugResult> inspecMicroDrugResults;//药敏抗生素结果项
}
