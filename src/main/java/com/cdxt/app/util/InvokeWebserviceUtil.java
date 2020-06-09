package com.cdxt.app.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import javax.xml.namespace.QName;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 调用webservice接口工具类
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/27 17:31
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Slf4j
public class InvokeWebserviceUtil {

    /**
     * @return:  java.lang.String
     * @author: tangxiaohui
     * @description: Cxf动态调用webservice接口
     * @Param wsdlUrl: 接口地址
     * @Param namespaceURI: 命名空间
     * @Param localPart: 方法名称
     * @Param objects:  可变长参数
     * @date: 2020/5/28 9:49
     */
    public static String invokeByCxf(String wsdlUrl, String namespaceURI, String localPart, Object...objects){
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(wsdlUrl);
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
        try {
            QName opName = new QName(namespaceURI, localPart);
            // invoke("方法名",参数1,参数2,参数3....);   这里直接穿方法名，可能调不通，关键看人家接口有没得命名空间
            Object[] objarry = client.invoke(opName, objects);
            log.info((String) objects[0]);
            return (String) objarry[0];
        } catch (Exception e) {
            e.printStackTrace();
            return "Cxf动态远程调用报错";
        }
    }

    /**
     * @return:  java.lang.String
     * @author: tangxiaohui
     * @description: axis方式调用webservice接口
     * @Param wsdlUrl: 接口地址
     * @Param namespaceURI: 命名空间
     * @Param localPart: 方法名称
     * @Param paramMap: 参数map
     * @date: 2020/5/27 17:44
     */
    public static String invokeByAxis(String wsdlUrl, String namespaceURI, String localPart, Map<String,String> paramMap){
        List<Object> objects = new ArrayList<>();
        try {
            org.apache.axis.client.Service service = new org.apache.axis.client.Service();
            org.apache.axis.client.Call call = (org.apache.axis.client.Call) service.createCall();
            call.setTargetEndpointAddress(wsdlUrl);
            // 调用的方法名
            call.setOperationName(new QName(namespaceURI, localPart));
            for(String key:paramMap.keySet()){
                call.addParameter(key, org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
                objects.add(paramMap.get(key));
            }
            // 返回值类型：String
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
            // 远程调用
            String result = (String) call.invoke(objects.toArray());
            log.info(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return "Axis远程调用报错";
        }
    }

    /**
     * @return:  java.lang.String
     * @author: tangxiaohui
     * @description: http方式调用webservice接口
     * @Param wsdlUrl: 接口地址
     * @Param namespaceURI: 命名空间
     * @Param localPart: 方法名称
     * @Param paramMap:  参数map
     * @date: 2020/5/28 10:17
     */
    public static String invokeByHttp(String wsdlUrl, String namespaceURI, String localPart, Map<String,String> paramMap){
        try {
            // 1 指定WebService服务的请求地址：wsdlUrl
            // 2 创建URL：
            URL url = new URL(wsdlUrl);
            // 3 建立连接，并将连接强转为Http连接
            URLConnection conn = url.openConnection();
            HttpURLConnection con = (HttpURLConnection) conn;

            // 4，设置请求方式和请求头：
            con.setDoInput(true); // 是否有入参
            con.setDoOutput(true); // 是否有出参
            con.setRequestMethod("POST"); // 设置请求方式
            con.setRequestProperty("content-type", "text/xml;charset=UTF-8");

            // 5，手动构造请求体
            String requestBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"";
            requestBody += " xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"";
            requestBody += " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">";
            requestBody += "<soapenv:Body>";

            requestBody += "<q0:"+localPart+" xmlns:q0=\""+namespaceURI+"\">";//调用方法 和命名空间
            for(String key:paramMap.keySet()){
                requestBody += "<"+key+">" +paramMap.get(key) + "</"+key+">";//参数
            }
            requestBody += "</q0:"+localPart+">";

            requestBody += "</soapenv:Body>";
            requestBody += "</soapenv:Envelope>";

            // 6，通过流的方式将请求体发送出去：
            OutputStream out = con.getOutputStream();
            out.write(requestBody.getBytes());
            out.close();
            // 7，服务端返回正常：
            int code = con.getResponseCode();
            String result="";
            if (code == 200) {// 服务端返回正常
                InputStream is = con.getInputStream();
                byte[] b = new byte[1024];
                StringBuffer sb = new StringBuffer();
                int len = 0;
                while ((len = is.read(b)) != -1) {
                    String str = new String(b, 0, len, "UTF-8");
                    sb.append(str);
                }
                result = sb.toString();
                is.close();
            }
            con.disconnect();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "Http远程调用webservice失败";
        }
    }
}
