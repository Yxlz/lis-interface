package com.cdxt.inter.model.request.mults;

import com.cdxt.inter.annotation.Path;
import lombok.Data;

/**
 * @Description: 申请项目信息明细
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/30 0030 12:23
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Data
public class RequisitionItemInfo {
    @Path(path="observationRequest/id/item",attribute = "extension")
    private String ordersId;//医嘱ID

    @Path(path="observationRequest/cost/item",attribute = "extension")
    private String cost;//检验项目金额

    @Path(path="observationRequest/code",attribute = "code")
    private String labCode;//检验项目编码

    @Path(path="observationRequest/code/displayName",attribute = "value")
    private String labName;//检验项目名

    @Path(path="observationRequest/methodCode/item",attribute = "code")
    private String methodCode;//检验方法编码

    @Path(path="observationRequest/methodCode/item/displayName",attribute = "value")
    private String methodName;//检验方法名

    @Path(path="observationRequest/location/time/any",attribute = "value",dateformat="yyyyMMddHHmmss")
    private String execTime;//执行时间

    @Path(path="observationRequest/location/serviceDeliveryLocation/serviceProviderOrganization/id/item",attribute = "extension")
    private String execDeptId;//执行科室编码

    @Path(path="observationRequest/location/serviceDeliveryLocation/serviceProviderOrganization/name/item/part",attribute = "value")
    private String execDeptName;//执行科室名称
}
