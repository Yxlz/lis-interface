package com.cdxt.app.model.request.mults;

import com.cdxt.app.annotation.Path;
import com.cdxt.app.constants.DocConstants;
import lombok.Data;

import java.util.Date;

/**
 * @Description: 细菌培养项  个人理解 细菌结果和培养结果
 * @Author: tangxiaohui
 * @CreateDate: 2020/6/3 15:53
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Data
public class InspecMicroCultureResult {
    @Path(path = "observation/code", attribute = "code")
    private String microCode;

    @Path(path = "observation/code", attribute = "displayName")
    private String microName;

    @Path(path = "observation/effectiveTime", attribute = "value", dateformat = DocConstants.DOC_DATE_FORMATTER)
    private Date reportTime;//报告时间,可为空

    @Path(path = "observation/value")
    private String result;
}
