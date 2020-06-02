package com.cdxt.inter.model.request;

import com.cdxt.inter.annotation.Path;
import com.cdxt.inter.annotation.Subject;
import com.cdxt.inter.constants.DocConstants;
import com.cdxt.inter.model.XpathCommon;
import com.cdxt.inter.model.request.mults.DoctorAdvice;
import com.cdxt.inter.util.DateUtil;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Description: 标本接收
 * @Author: tangxiaohui
 * @CreateDate: 2020/6/1 16:53
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Data
public class SampleReceive extends XpathCommon {
    @Path(path = "controlActProcess/subject/placerGroup/subject/patient/id/item[@root='1.2.156.112698.1.1.2.1.5']", attribute = "extension")
    private String regionId;//域ID 门诊: 01 住院：02 体检：03

    @Path(path = "controlActProcess/subject/placerGroup/subject/patient/id/item[@root='1.2.156.112698.1.1.2.1.6']", attribute = "extension")
    private String patientCode;// 域ID=01门诊时为PATIENT_ID 域ID=02住院时为PATIENT_ID 域ID=03体检时为体检档案号

    @Path(path = "controlActProcess/subject/placerGroup/subject/patient/id/item[@root='1.2.156.112698.1.1.2.1.20']", attribute = "extension")
    private String patientType;//就诊类别 extension =01门诊 extension =02普通急诊 extension =03急诊留观 82 extension =04住院 extension =05普通体检 extension =06干部保险体检 extension =07转院

    @Path(path = "controlActProcess/subject/placerGroup/subject/patient/id/item[@root='1.2.156.112698.1.1.2.1.21']", attribute = "extension")
    private String patientCode1;//就诊号 就诊类别=01/02时为门诊号 就诊类别=03/04/07时为住院号 就诊类别=05/06时为体检档案号

    @Path(path = "controlActProcess/subject/placerGroup/author/time", attribute = "value", dateformat = DateUtil.DEFAUL_TIME_FORMATER)
    private Date orderTime;//开单时间

    @Path(path = "controlActProcess/subject/placerGroup/author/assignedEntity/id/item", attribute = "extension")
    private String orderDoctorCode;//开单医生编码

    @Path(path = "controlActProcess/subject/placerGroup/author/assignedEntity/assignedPerson/name/item/part", attribute = "value")
    private String orderDoctorName;//开单医生姓名

    @Path(path = "controlActProcess/subject/placerGroup/author/assignedEntity/representedOrganization/id/item", attribute = "extension")
    private String orderDeptCode;//申请科室编码

    @Path(path = "controlActProcess/subject/placerGroup/author/assignedEntity/representedOrganization/name/item/part", attribute = "value")
    private String orderDeptName;//申请科室名称

    @Path(path = "controlActProcess/subject/placerGroup/transcriber/time/low", attribute = "value", dateformat = DocConstants.DOC_DATE_FORMATTER)
    private Date timeReceiveStarting;//标本接收开始时间

    @Path(path = "controlActProcess/subject/placerGroup/transcriber/time/high", attribute = "value", dateformat = DocConstants.DOC_DATE_FORMATTER)
    private Date timeReceiveLimit;//标本接收结束时间

    @Path(path = "controlActProcess/subject/placerGroup/transcriber/assignedEntity/id/item", attribute = "extension")
    private String receiverJobNo;//标本接收者工号

    @Path(path = "controlActProcess/subject/placerGroup/transcriber/assignedEntity/assignedPerson/name/item/part", attribute = "value")
    private String receiverName;//标本接收者姓名

    @Path(path = "controlActProcess/subject/placerGroup/verifier/time", attribute = "value", dateformat = DocConstants.DOC_DATE_FORMATTER)
    private Date confirmTime;//确认时间

    @Path(path = "controlActProcess/subject/placerGroup/verifier/assignedEntity/id/item", attribute = "extension")
    private String theConfirmCode;//确认人编码

    @Path(path = "controlActProcess/subject/placerGroup/verifier/assignedEntity/assignedPerson/name/item/part", attribute = "value")
    private String theConfirmName;//确认人姓名

    @Path(path = "controlActProcess/subject/placerGroup/component2/observationRequest/id/item[@root='1.2.156.112698.1.1.2.1.20']", attribute = "extension")
    private String requestNo;//检验申请单编号 必须项已使用 HIS APPLY_ID

    @Path(path = "controlActProcess/subject/placerGroup/component2/observationRequest/code", attribute = "code")
    private String inspecNameCode;//医嘱类型必须项目已使用

    @Path(path = "controlActProcess/subject/placerGroup/component2/observationRequest/code/displayName", attribute = "value")
    private String inspecName;//医嘱类型名称

    @Path(path = "controlActProcess/subject/placerGroup/component2/observationRequest/effectiveTime/any", attribute = "value", dateformat = DocConstants.DOC_DATE_FORMATTER)
    private Date reqDate;//检验申请日期

    @Subject(path = "controlActProcess/subject/placerGroup/component2/observationRequest/component2", mult = true)
    List<DoctorAdvice> docAdvices;//医嘱
}
