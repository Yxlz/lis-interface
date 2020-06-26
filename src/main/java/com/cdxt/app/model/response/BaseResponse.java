package com.cdxt.app.model.response;

import com.cdxt.app.enums.IFStateEnum;
import com.cdxt.app.enums.ReturnCodeEnum;
import lombok.Data;

/**
 * @Description: 接口返回类
 * @Author: tangxiaohui
 * @CreateDate: 2020/6/1 0001 20:24
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Data
public class BaseResponse<T> {
    private IFStateEnum state;
    private ReturnCodeEnum returnCode;
    private String message;
    private T result;

    //public BaseResponse(){}
    public BaseResponse(IFStateEnum state, ReturnCodeEnum returnCode, String message, T result) {
        this.state = state;
        this.returnCode = returnCode;
        this.message = message;
        this.result = result;
    }
}
