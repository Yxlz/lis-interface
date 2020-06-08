package com.cdxt.inter.enums;

/**
 * @Description: 接口返回代码
 * @Author: tangxiaohui
 * @CreateDate: 2020/6/1 0001 21:04
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
public enum ReturnCodeEnum {
    SUCCESS("S", "成功"),
    FAIL("F", "失败"),
    UNAUTH("UNAUTH", "没有权限"),
    FORBIDEN("FORBIDEN", "禁止");

    ReturnCodeEnum(String code, String text){
        this.code = code;
        this.text = text;
    }
    private String code;
    private String text;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}