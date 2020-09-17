package com.cdxt.app.model.request;

import com.cdxt.app.annotation.Path;
import com.cdxt.app.annotation.Subject;
import com.cdxt.app.constants.DocConstants;
import com.cdxt.app.model.request.mults.ReqItemInfoDaAn;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: InspecReqInfoDaAn
 * @Description: 自贡大安区人民医院 检验申请单（体检）
 * @Author tangxiaohui
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @DateTime 2020/9/16 9:14
 */
@Data
@Path(path = "//controlActProcess")
public class InspecReqInfoDaAn {
    @Path(path = "code", attribute = "code")
    private String serviceType;// 消息交互类型 @code: 新增 :new 删除：delete 修改：update
    @Path(path = "subject/placerGroup/subject/patient/id/item[@root='1.2.156.112698.1.1.2.1.5']", attribute = "extension")
    private String patientType;// 域ID 门诊: 01 住院：02 体检：03
    @Path(path = "subject/placerGroup/subject/patient/id/item[@root='1.2.156.112698.1.1.2.1.6']", attribute = "extension")
    private String personInfoId;// 患者ID 域ID=01门诊时为PATIENT_ID 域ID=02住院时为PATIENT_ID 域ID=03体检时为体检档案号
    @Path(path = "subject/placerGroup/subject/patient/id/item[@root='1.2.156.112698.1.1.2.1.21']", attribute = "extension")
    private String patientCode;// 就诊号 pcode
    @Path(path = "subject/placerGroup/subject/patient/addr/item/part[@type='BNR']", attribute = "code")
    private String patientWardCode;// 病区编码
    @Path(path = "subject/placerGroup/subject/patient/addr/item/part[@type='BNR']", attribute = "value")
    private String patientWardName;// 病区名称
    @Path(path = "subject/placerGroup/subject/patient/addr/item/part[@type='CAR']", attribute = "value")
    private String bedNumber;// 床号
    @Path(path = "subject/placerGroup/subject/patient/patientPerson/id/item", attribute = "extension")
    private String idCardNum;// 病人身份证号
    @Path(path = "subject/placerGroup/subject/patient/patientPerson/name/item/part", attribute = "value")
    private String patientName;// 病人姓名
    @Path(path = "subject/placerGroup/subject/patient/patientPerson/telecom/item[@use='EC']", attribute = "value")
    private String patientPhone;// 电话
    @Path(path = "subject/placerGroup/subject/patient/patientPerson/telecom/item[@use='MC']", attribute = "value")
    private String phoneNum;// 移动电话
    @Path(path = "subject/placerGroup/subject/patient/patientPerson/administrativeGenderCode", attribute = "code")
    private String patientSexCode;// 性别代码
    @Path(path = "subject/placerGroup/subject/patient/patientPerson/administrativeGenderCode/displayName", attribute = "value")
    private String patientSex;// 性别名称
    @Path(path = "subject/placerGroup/subject/patient/patientPerson/birthTime", attribute = "value")
    private String birdthday;// 患者出生日期
    @Path(path = "subject/placerGroup/subject/patient/patientPerson/birthTime/originalText", attribute = "value")
    private String patientAge;// 患者年龄
    @Path(path = "subject/placerGroup/subject/patient/patientPerson/addr/item/part[@type='AL']", attribute = "value")
    private String address;// 患者地址
    @Path(path = "subject/placerGroup/subject/patient/providerOrganization/id/item", attribute = "extension")
    private String sectionNameId;// 患者科室编码
    @Path(path = "subject/placerGroup/subject/patient/providerOrganization/name/item/part", attribute = "value")
    private String sectionName;// 患者科室名称
    @Path(path = "subject/placerGroup/subject/patient/providerOrganization/asOrganizationPartOf/wholeOrganization/id/item", attribute = "extension")
    private String hospitalId;// 患者机构编码
    @Path(path = "subject/placerGroup/subject/patient/providerOrganization/asOrganizationPartOf/wholeOrganization/name/item/part", attribute = "value")
    private String hospitalName;// 患者机构名称

    @Path(path = "subject/placerGroup/author/time", attribute = "value", dateformat = DocConstants.DOC_DATE_FORMATTER)
    private Date reqDate;// 开单时间
    @Path(path = "subject/placerGroup/author/assignedEntity/id/item", attribute = "extension")
    private String doctorNameId;// 开单医生编码
    @Path(path = "subject/placerGroup/author/assignedEntity/assignedPerson/name/item/part", attribute = "value")
    private String doctorName;// 开单医生名称
    @Path(path = "subject/placerGroup/author/assignedEntity/representedOrganization/id/item", attribute = "extension")
    private String reqDeptCode;// 开单科室编码
    @Path(path = "subject/placerGroup/author/assignedEntity/representedOrganization/name/item/part", attribute = "value")
    private String reqDeptName;// 开单科室名称

    /*申请单信息Begin*/
    @Path(path = "subject/placerGroup/component2/observationRequest/id/item[@root='1.2.156.112698.1.1.2.1.20']", attribute = "extension")
    private String requestNo;// 申请单号
    @Path(path = "subject/placerGroup/component2/observationRequest/id/item[@root='1.2.156.112713.1.1.2.1.20']", attribute = "extension")
    private String requestNoCancel;// 申请单号撤销时使用
    @Path(path = "subject/placerGroup/component2/observationRequest/code", attribute = "code")
    private String inspecNameCode;// 检验医嘱类型编码
    @Path(path = "subject/placerGroup/component2/observationRequest/code/displayName", attribute = "value")
    private String inspecName;// 检验医嘱类型名称
    @Path(path = "subject/placerGroup/component2/observationRequest/effectiveTime/any", attribute = "value", dateformat = DocConstants.DOC_DATE_FORMATTER)
    private Date createDate;// 申请时间
    @Subject(path = "subject/placerGroup/component2/observationRequest/component2", mult = true)
    private List<ReqItemInfoDaAn> items;// 检验项目明细
    /*申请单信息End*/

    @Path(path = "subject/placerGroup/componentOf1/encounter/id/item[@root='1.2.156.112698.1.1.2.1.22']", attribute = "extension")
    private String visitNumber;// 就诊次数
    @Path(path = "subject/placerGroup/componentOf1/encounter/id/item[@root='1.2.156.112698.1.1.1.4.36']", attribute = "extension")
    private String visitFlowNo;// 就诊流水号
    @Path(path = "subject/placerGroup/componentOf1/encounter/code", attribute = "code")
    private String visitCode;// 就诊类别编码
    @Path(path = "subject/placerGroup/componentOf1/encounter/code/displayName", attribute = "value")
    private String visitName;// 就诊类别名称
    @Path(path = "subject/placerGroup/componentOf1/encounter/location/serviceDeliveryLocation/serviceProviderOrganization/id/item", attribute = "extension")
    private String visitAreaCode;// 就诊院区编码
    @Path(path = "subject/placerGroup/componentOf1/encounter/location/serviceDeliveryLocation/serviceProviderOrganization/name/item/part", attribute = "value")
    private String visitAreaName;// 就诊院区名称
    @Path(path = "subject/placerGroup/componentOf1/encounter/pertinentInformation1/observationDx/code", attribute = "code")
    private String diagnosisTypeId;// 诊断类别编码
    @Path(path = "subject/placerGroup/componentOf1/encounter/pertinentInformation1/observationDx/code/displayName", attribute = "code")
    private String diagnosisType;// 诊断类别名称
    @Path(path = "subject/placerGroup/componentOf1/encounter/pertinentInformation1/observationDx/value", attribute = "code")
    private String pDiagnosisId;// 诊断编码
    @Path(path = "subject/placerGroup/componentOf1/encounter/pertinentInformation1/observationDx/value/displayName", attribute = "code")
    private String pDiagnosis;// 诊断名称
}
