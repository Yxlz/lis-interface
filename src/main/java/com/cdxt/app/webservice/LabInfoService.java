package com.cdxt.app.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @Description: 创建服务接口
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/21 14:23
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@WebService(name = "labInfoPortType")
//name暴露服务名称,生成客户端的类名，此属性的值包含XML Web Service的名称。在默认情况下，该值是实现XML Web Service的类的名称，wsdl:portType 的名称。缺省值为 Java 类或接口的非限定名称。（字符串
public interface LabInfoService {

    /**
     * @return: java.lang.String
     * @author: tangxiaohui
     * @description: 新增检验申请单
     * @Param applicationXml:  申请单xml
     * @date: 2020/5/21 14:46
     */
    @WebMethod(operationName = "LISLabInfoServer")
    String InspectionRequisitionRelated(@WebParam(name = "applicationXml") String applicationXml);

    /**
     * @return: java.lang.String
     * @author: tangxiaohui
     * @description: 标本审核，拒收，回传报告。。
     * @Param action:   标本相关的操作代码
     * @Param message:  消息体
     * @date: 2020/5/26 17:09
     */
    @WebMethod(operationName = "LISHL7V3Server")
    String SpecimenRelated(@WebParam(name = "action") String action, @WebParam(name = "message") String message);
}
