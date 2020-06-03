package com.cdxt.inter.model.request.mults;

import com.cdxt.inter.annotation.Path;
import lombok.Data;

/**
 * @Description: 医嘱从属信息
 * @Author: tangxiaohui
 * @CreateDate: 2020/6/3 10:48
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Data
public class InFulfillmentOf {
    @Path(path="order/id",attribute = "extension")
    private String orderNo;//医嘱号,order_sn医嘱唯一标识

    @Path(path="order/code",attribute = "code")
    private String orderItemCode;//医嘱项目编码

    @Path(path="order/code",attribute = "displayName")
    private String orderItemName;//医嘱项目名称
}
