package com.cdxt.app.webservice;

import com.cdxt.app.webservice.constants.WsConst;

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
@WebService(targetNamespace= WsConst.NAMESPACE_URL,//命名空间，一般是接口名称的包名倒序
            name = "labInfoPortType")//暴露服务名称
public interface LabInfoService {

    /**
     * @return:  java.lang.String
     * @author: tangxiaohui
     * @description: 新增检验申请单
     * @Param applicationXml:  申请单xml
     * @date: 2020/5/21 14:46
     */
    @WebMethod(operationName = "InspectionRequisition")
    public String InspectionRequisitionRelated(@WebParam(name = "requisitionXml") String applicationXml);

    /**
     * @return:  java.lang.String
     * @author: tangxiaohui
     * @description: 标本审核，拒收，回传报告。。
     * @Param action:   标本相关的操作代码
     * @Param message:  消息体
     * @date: 2020/5/26 17:09
     */
    @WebMethod(operationName = "LISHL7V3Server")
    public String SpecimenRelated(@WebParam(name = "action") String action, @WebParam(name = "message") String message);
}
