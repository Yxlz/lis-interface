package com.cdxt.inter.model.request.mults;

import com.cdxt.inter.annotation.Path;
import com.cdxt.inter.constants.DocConstants;
import lombok.Data;

import java.util.Date;

/**
 * @Description: 药敏抗生素项
 * @Author: tangxiaohui
 * @CreateDate: 2020/6/3 15:56
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Data
public class InspecMicroDrugResult {
    @Path(path = "observation/code", attribute = "code")
    private String itemCode;

    @Path(path = "observation/code", attribute = "displayName")
    private String itemName;

    @Path(path = "observation/text/reference", attribute = "value")
    private String result;//-结果描述，和参考范围比较的定性结果，偏高、偏低等

    @Path(path = "observation/effectiveTime", attribute = "value", dateformat = DocConstants.DOC_DATE_FORMATTER)
    private Date reportTime;//报告时间,可为空

    @Path(path = "observation/value", attribute = "value")
    private String resultValue;//-结果值

    @Path(path = "observation/value", attribute = "unit")
    private String resultUnit;//-结果单位

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
