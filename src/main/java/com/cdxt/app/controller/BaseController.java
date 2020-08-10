package com.cdxt.app.controller;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @Description: 控制类公用方法
 * @Author: tangxiaohui
 * @CreateDate: 2020/8/7 13:39
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
public class BaseController {
    /**
     * 获取HTTP request中body字符串内容
     * @param request
     * @return
     * @throws Exception
     */
    protected String getRequestBody(HttpServletRequest request) throws Exception{
        BufferedReader br = null;
        try {
            br = request.getReader();
            String str = "";
            StringBuffer buffer = new StringBuffer();
            while((str = br.readLine()) != null){
                buffer.append(str);
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }finally{
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
