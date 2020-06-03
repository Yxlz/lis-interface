package com.cdxt.inter.model.request.mults;

import com.cdxt.inter.annotation.Path;
import com.cdxt.inter.constants.DocConstants;
import lombok.Data;

import java.util.Date;

/**
 * @Description: 医嘱项
 * @Author: tangxiaohui
 * @CreateDate: 2020/6/1 17:43
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Data
public class DoctorAdvice {
    @Path(path="observationRequest/id/item",attribute = "extension")
    private String orderNo;//医嘱号 必须项已使用

    @Path(path="observationRequest/code",attribute = "code")
    private String inspecItemCode;//检验项目编码

    @Path(path="observationRequest/code/displayName",attribute = "value")
    private String inspecItemName;//检验项目名

    @Path(path="observationRequest/coeffectiveTimede",attribute = "validTimeLow")
    private String orderTimeStarting;//医嘱开始时间

    @Path(path="observationRequest/coeffectiveTimede",attribute = "validTimeHigh")
    private String orderTimeLimit;//医嘱预定结束时间

    @Path(path="observationRequest/coeffectiveTimede/code",attribute = "code")
    private String orderFrequencyCode;//医嘱执行频率编码

    @Path(path="observationRequest/coeffectiveTimede/code/displayName",attribute = "value")
    private String orderFrequencyName;//医嘱执行频率编名称

    @Path(path="observationRequest/priorityCode",attribute = "code")
    private String priority;//优先级别

    @Path(path="observationRequest/location/serviceDeliveryLocation/serviceProviderOrganization/id/item",attribute = "extension")
    private String excuteSectionCode;//执行科室编码

    @Path(path="observationRequest/location/serviceDeliveryLocation/serviceProviderOrganization/name/item/part",attribute = "value")
    private String excuteSectionName;//执行科室名称

    /*标本拒收的请求XML中下列节点没有*/
    @Path(path="observationRequest/specimen/specimen/id",attribute = "extension")
    private String barcode;//标本号/条码号

    @Path(path="observationRequest/specimen/specimen/specimenNatural/code",attribute = "code")
    private String sampleCode;//标本类型编码 血清/血浆/尿 标本类别代码

    @Path(path="observationRequest/specimen/specimen/specimenNatural/code/displayName",attribute = "value")
    private String sampleName;//标本类型名称

    @Path(path="observationRequest/specimen/specimen/subjectOf1/specimenProcessStep/effectiveTime/any",attribute = "value",dateformat = DocConstants.DOC_DATE_FORMATTER)
    private Date timeCollect;//采集日期

    @Path(path="observationRequest/specimen/specimen/subjectOf1/specimenProcessStep/performer/assignedEntity/id/item",attribute = "extension")
    private String collectorCode;//采集人id

    @Path(path="observationRequest/specimen/specimen/subjectOf1/specimenProcessStep/performer/assignedEntity/assignedPerson/name/item/part",attribute = "value")
    private String collectorName;//采集人姓名

    @Path(path="observationRequest/specimen/specimen/subjectOf1/specimenProcessStep/performer/assignedEntity/assignedPerson/asLocatedEntity/addr/item/part",attribute = "value")
    private String collectPlace;//采集地点：检验科，护士站，体检中心

    @Path(path="observationRequest/subjectOf6/annotation/code",attribute = "code")
    private String attentionCode;//备注类型代码

    @Path(path="observationRequest/subjectOf6/annotation/text",attribute = "value")
    private String attentionName;//标本要求 空腹之类的

}
