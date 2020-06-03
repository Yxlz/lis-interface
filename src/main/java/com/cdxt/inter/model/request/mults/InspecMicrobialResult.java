package com.cdxt.inter.model.request.mults;

import com.cdxt.inter.annotation.Path;
import com.cdxt.inter.constants.DocConstants;
import lombok.Data;

import java.util.Date;

/**
 * @Description: 微生物结果项   个人理解为涂片结果
 * @Author: tangxiaohui
 * @CreateDate: 2020/6/3 15:50
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Data
public class InspecMicrobialResult {
    @Path(path = "observation/code", attribute = "code")
    private String smearCode;

    @Path(path = "observation/code", attribute = "displayName")
    private String smearName;

    @Path(path = "observation/effectiveTime", attribute = "value", dateformat = DocConstants.DOC_DATE_FORMATTER)
    private Date reportTime;//报告时间,可为空

    @Path(path = "observation/value")
    private String result;
}
