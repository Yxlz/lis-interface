package com.cdxt.inter.model;

import lombok.Data;

/**
 * @Description: 校验结果
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/30 0030 14:13
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Data
public class CheckResult {
    private boolean flag;

    private String resultMsg;

    public CheckResult(boolean flag, String resultMsg){
        this.flag=flag;
        this.resultMsg=resultMsg;
    }
}
