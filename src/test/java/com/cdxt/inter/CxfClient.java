package com.cdxt.inter;

import com.cdxt.inter.webservice.LabInfoService;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import javax.xml.namespace.QName;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: CxfClient
 * @Description: 调用webservice的几种方式
 * @Author tangxiaohui
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @DateTime 2020/5/26 15:44
 */
public class CxfClient {
    public static void main(String[] args) {
        //cl3();
        String wsdlUrl = "http://localhost:8080/ws/labInfo?wsdl";
        String namespaceURI = "http://webservice.inter.cdxt.com/";
        //String localPart = "InspectionRequisition";
        String localPart = "Specimen";
        Map<String,String> paramMap = new HashMap<>();

        paramMap.put("action","UpdateApplicationState");
        paramMap.put("message", "<request>\n" +
                "<inputDate>2020-03-16</inputDate>\n" +
                "<devCode>Phoenix_100</devCode>\n" +
                "<inspecNo>200316001</inspecNo>\n" +
                "<barCode>200316717682</barCode>\n" +
                "<hospid>RSS20191106000000001</hospid>\n" +
                "</request>");
        invokeByAxis(wsdlUrl,namespaceURI,localPart,paramMap);
//        invokeByCxf(wsdlUrl,namespaceURI,localPart,"sendReport","<request>\n" +
//                "\t\t\t<inputDate>2020-03-16</inputDate>\n" +
//                "\t\t\t<devCode>Phoenix_100</devCode>\n" +
//                "\t\t\t<inspecNo>200316001</inspecNo>\n" +
//                "\t\t\t<barCode>200316717682</barCode>\n" +
//                "\t\t\t<hospid>RSS20191106000000001</hospid>\n" +
//                "\t\t\t</request>");
        //invokeByHttp(wsdlUrl,namespaceURI,localPart,paramMap);
    }
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

            System.out.println(requestBody);
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
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "Http远程调用webservice失败";
        }
    }

    public static String invokeByCxf(String wsdlUrl, String namespaceURI, String localPart, Object...objects){
        System.out.println(objects);
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(wsdlUrl);
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
        try {
            QName opName = new QName(namespaceURI, localPart);
            // invoke("方法名",参数1,参数2,参数3....);   这里直接穿方法名，可能调不通，关键看人家接口有没得命名空间
            Object[] objarry = client.invoke(opName, objects);
            System.out.println(objarry[0]);
            return (String) objarry[0];
        } catch (Exception e) {
            e.printStackTrace();
            return "Cxf动态远程调用报错";
        }
    }

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
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "远程调用报错";
        }
    }

    /**
     * 方式1.代理类工厂的方式,需要拿到对方的接口
     */
    public static void cl1() {
        try {
            // 接口地址
            String address = "http://localhost:8080/ws/labInfo?wsdl";
            // 代理工厂
            JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
            // 设置代理地址
            jaxWsProxyFactoryBean.setAddress(address);
            // 设置接口类型
            jaxWsProxyFactoryBean.setServiceClass(LabInfoService.class);
            // 创建一个代理接口实现
            LabInfoService cs = (LabInfoService) jaxWsProxyFactoryBean.create();
            // 调用代理接口的方法调用并返回结果
            String result = cs.InspectionRequisitionRelated("");
            System.out.println("返回结果:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 动态调用方式
     */
    public static void cl2() {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://localhost:8080/ws/labInfo?wsdl");
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
        Object[] objects = new Object[0];
        try {
            QName opName = new QName("http://webservice.inter.cdxt.com/", "LISLabInfoServer");
            // invoke("方法名",参数1,参数2,参数3....);   这里直接穿方法名，可能调不通，关键看人家接口有没得命名空间
            objects = client.invoke(opName, "");
            System.out.println("返回数据:" + objects[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return:  void
     * @author: tangxiaohui
     * @description: axis调用方式  需要axis的jar包
     * @date: 2020/5/26 15:47
     */
    public static void cl3() {
        try {
            String endpoint = "http://localhost:8080/ws/labInfo?wsdl";
            org.apache.axis.client.Service service = new org.apache.axis.client.Service();
            org.apache.axis.client.Call call = (org.apache.axis.client.Call) service.createCall();
            call.setTargetEndpointAddress(endpoint);
            String parametersName = "RequisitionXml"; 		// 参数名//对应的是 public String printWord(@WebParam(name = "Message") String Message);
//	            call.setOperationName("printWord");  		// 调用的方法名//当这种调用不到的时候,可以使用下面的,加入命名空间名
            call.setOperationName(new QName("http://webservice.inter.cdxt.com/", "LISLabInfoServer"));// 调用的方法名
            call.addParameter(parametersName, org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);//参数名//XSD_STRING:String类型//.IN入参
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING); 	// 返回值类型：String
            String message = "123456789";
            String result = (String) call.invoke(new Object[] { message });// 远程调用
            System.out.println("result is " + result);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    /**
     * @return:  void
     * @author: tangxiaohui
     * @description: http方式调用
     * @date: 2020/5/26 15:48
     */
    public static void cl4() {
        try {
            // 1 指定WebService服务的请求地址：
            String wsUrl = "http://localhost:8080/ws/labInfo?wsdl";
            // 2 创建URL：
            URL url = new URL(wsUrl);
            // 3 建立连接，并将连接强转为Http连接
            URLConnection conn = url.openConnection();
            HttpURLConnection con = (HttpURLConnection) conn;

            // 4，设置请求方式和请求头：
            con.setDoInput(true); // 是否有入参
            con.setDoOutput(true); // 是否有出参
            con.setRequestMethod("POST"); // 设置请求方式
            con.setRequestProperty("content-type", "text/xml;charset=UTF-8");

            // 5，手动构造请求体
            String applicationXml = "";
            String requestBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"";
            requestBody += " xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"";
            requestBody += " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">";
            requestBody += "<soapenv:Body>";

            requestBody += "<q0:LISLabInfoServer xmlns:q0=\"http://webservice.inter.cdxt.com/\">";//调用方法 和命名空间
            requestBody += "<RequisitionXml>" +applicationXml + "</RequisitionXml>";//参数
            requestBody += "</q0:LISLabInfoServer>";

            requestBody += "</soapenv:Body>";
            requestBody += "</soapenv:Envelope>";

            // 6，通过流的方式将请求体发送出去：
            OutputStream out = con.getOutputStream();
            out.write(requestBody.getBytes());
            out.close();
            // 7，服务端返回正常：
            int code = con.getResponseCode();
            if (code == 200) {// 服务端返回正常
                InputStream is = con.getInputStream();
                byte[] b = new byte[1024];
                StringBuffer sb = new StringBuffer();
                int len = 0;
                while ((len = is.read(b)) != -1) {
                    String str = new String(b, 0, len, "UTF-8");
                    sb.append(str);
                }
                System.out.println(sb.toString());
                is.close();
            }
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return:  void
     * @throws:
     * @author: Administrator
     * @description: 直接SOAP调用远程的webservice  需要jar
     * @date: 2020/5/22 0022 18:28
     */
    public static void cl5(){
//        URL url = null;
//        try {
//            url = new URL(
//                    "http://192.168.0.100:8080/ca3/services/caSynrochnized");
//        } catch (MalformedURLException mue) {
//            mue.printStackTrace();
//        }
//        // This is the main SOAP object
//        Call soapCall = new Call();
//        // Use SOAP encoding
//        soapCall.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);
//        // This is the remote object we're asking for the price
//        soapCall.setTargetObjectURI("urn:xmethods-caSynrochnized");
//        // This is the name of the method on the above object
//        soapCall.setMethodName("getUser");
//        // We need to send the ISBN number as an input parameter to the method
//        Vector soapParams = new Vector();
//
//        // name, type, value, encoding style
//        Parameter isbnParam = new Parameter("userName", String.class, user,
//                null);
//        soapParams.addElement(isbnParam);
//        soapCall.setParams(soapParams);
//        try {
//            // Invoke the remote method on the object
//            Response soapResponse = soapCall.invoke(url, "");
//            // Check to see if there is an error, return "N/A"
//            if (soapResponse.generatedFault()) {
//                Fault fault = soapResponse.getFault();
//                String f = fault.getFaultString();
//                return f;
//            } else {
//                // read result
//                Parameter soapResult = soapResponse.getReturnValue();
//                // get a string from the result
//                return soapResult.getValue().toString();
//            }
//        } catch (SOAPException se) {
//            return se.getMessage();
//        }
    }
}
