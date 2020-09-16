package com.cdxt.app.model.request.mults;

import com.cdxt.app.annotation.Path;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName: ReqItemInfoDaAn
 * @Description: 自贡大安区人民医院体检检验申请项目信息明细
 * @Author tangxiaohui
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @DateTime 2020/9/16 10:39
 */
@Data
public class ReqItemInfoDaAn {
    @Path(path="observationRequest/id/item",attribute = "extension")
    private String orderId;//医嘱ID

    @Path(path="observationRequest/code",attribute = "code")
    private String reqItemNo;//检验项目编码

    @Path(path="observationRequest/code/displayName",attribute = "value")
    private String reqItemName;//检验项目名

    @Path(path="observationRequest/methodCode/item",attribute = "code")
    private String methodeCode;//检验方法编码

    @Path(path="observationRequest/methodCode/item/displayName",attribute = "value")
    private String methodName;//检验方法名

    @Path(path="observationRequest/location/serviceDeliveryLocation/serviceProviderOrganization/id/item",attribute = "extension")
    private String executeSectionId;//执行科室编码

    @Path(path="observationRequest/location/serviceDeliveryLocation/serviceProviderOrganization/name/item/part",attribute = "value")
    private String executeSection;//执行科室名称

    @Path(path="observationRequest/subjectOf1/valuedItem/unitPriceAmt/numerator",attribute = "value")
    private String cost;//检验项目金额


    @Path(path="observationRequest/specimen/specimen/id",attribute = "extension")
    private String pid;//标本号/条码号
    @Path(path="observationRequest/specimen/specimen/specimenNatural/code",attribute = "code")
    private String sampleCode;//标本类型编码 血清/血浆/尿 标本类别代码
    @Path(path="observationRequest/specimen/specimen/specimenNatural/code/displayName",attribute = "value")
    private String sampleName;//标本类型名称
    @Path(path="observationRequest/specimen/specimen/subjectOf1/specimenProcessStep/subject/specimenInContainer/container/code",attribute = "code")
    private String containerCode;//项目容器类型
    @Path(path="observationRequest/specimen/specimen/subjectOf1/specimenProcessStep/subject/specimenInContainer/container/desc",attribute = "value")
    private String container;//项目容器
}
