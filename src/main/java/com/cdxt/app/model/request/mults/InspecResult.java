package com.cdxt.app.model.request.mults;

import com.cdxt.app.annotation.Path;
import com.cdxt.app.constants.DocConstants;
import lombok.Data;

import java.util.Date;

/**
 * @Description: 检验报告XML中 检验结果项
 * @Author: tangxiaohui
 * @CreateDate: 2020/6/3 15:35
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Data
public class InspecResult {
    @Path(path = "observation/code", attribute = "code")
    private String itemCode;

    @Path(path = "observation/code", attribute = "displayName")
    private String itemName;

    @Path(path = "observation/text/reference", attribute = "value")
    private String result;//-结果描述，和参考范围比较的定性结果，偏高、偏低等

    @Path(path = "observation/effectiveTime", attribute = "value", dateformat = DocConstants.DOC_DATE_FORMATTER)
    private Date reportTime;//报告时间,可为空

    @Path(path = "observation/value", attribute = "type")
    private String resultType;//结果值，如果是定量结果，类型为PQ，定性结果，类型为ST

    @Path(path = "observation/value", attribute = "value")
    private String resultValue;//-结果值

    @Path(path = "observation/value", attribute = "unit")
    private String resultUnit;//-结果单位

    @Path(path = "observation/entryRelationship/act/id", attribute = "extension")
    private String orderNo;//医嘱号,order_sn医嘱唯一标识

    @Path(path = "observation/entryRelationship/act/code", attribute = "displayName")
    private String orderItem;//医嘱项目，order_code和order_name，如果LIS中不存医嘱项目，则传入HIS收费码charge_code

    @Path(path = "observation/referenceRange/observationRange/text")
    private String referenceRange;//参考范围描述，可为空，如果是定性描述，可以使用text

    @Path(path = "observation/referenceRange/observationRange/value/low", attribute = "value")
    private String lowValue;

    @Path(path = "observation/referenceRange/observationRange/value/low", attribute = "unit")
    private String lowValueUnit;

    @Path(path = "observation/referenceRange/observationRange/value/high", attribute = "value")
    private String highValue;

    @Path(path = "observation/referenceRange/observationRange/value/high", attribute = "unit")
    private String highValueUnit;
}
