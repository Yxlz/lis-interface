package com.cdxt.app.model.request;

import lombok.Data;

/**
 * @Description: 重发报告接口参数
 * @Author: tangxiaohui
 * @CreateDate: 2020/8/7 13:43
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Data
public class ReSendReportParams {

    private String fromDate;

    private String endDate;
}
