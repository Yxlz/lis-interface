package com.cdxt.app.util;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

/**
 * @Description: 发送检验报告转换XML时需要处理数据 工具类
 * @Author: tangxiaohui
 * @CreateDate: 2020/6/5 9:35
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
public class SendReportUtil {
    /**
     * @return:  boolean
     * @author: tangxiaohui
     * @description: 判断检验结果值是数值
     * @Param value:
     * @date: 2020/6/5 9:36
     */
    public static boolean inspecValueIsNumber(String value){

        if(value==null){
            return false;
        }
        if(isNumber(value)){
            return true;
        }else{
            if(value.startsWith(">")
                    ||value.startsWith("<")
                    ||value.startsWith("≥")
                    ||value.startsWith("≤")){
                return isNumber(value.substring(1));
            }
        }
        return false;
    }

    private static boolean isNumber(String value) {
        if(value.contains("E") || value.contains("e")){
            try{
                Float.valueOf(value);
                return true;
            }catch(Exception ex){
                return false;
            }
        }else {
            return Pattern.matches("^[+-]?\\d*[.]?\\d*$", value)
                    && !Pattern.matches("^[+-]?", value);
        }
    }

    /**
     * @return:  java.lang.String
     * @author: tangxiaohui
     * @description: xml转义
     * @Param toTransferStr:  待转义字符串
     * @date: 2020/6/5 9:53
     */
    public static String transferMeaning(String toTransferStr){
        if(StringUtils.isBlank(toTransferStr)){
            return null;
        }else{
            toTransferStr.replace("<", "&lt;");
            toTransferStr.replace(">", "&gt;");
            toTransferStr.replace("&", "&amp;");
            toTransferStr.replace("’", "&apos;");
            toTransferStr.replace("\"", "&quot;");
            return toTransferStr;
        }
    }
}
