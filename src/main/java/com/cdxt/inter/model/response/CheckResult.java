package com.cdxt.inter.model.response;

import lombok.Data;

/**
 * @Description: 校验及返回结果结果
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/30 0030 14:13
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Data
public class CheckResult {
    private boolean flag;

    private String resultCode;

    private String resultMsg;

    public CheckResult(String resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public CheckResult(boolean flag, String resultCode, String resultMsg) {
        this.flag = flag;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }
}
