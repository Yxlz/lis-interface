package com.cdxt.inter.webservice.impl;

import com.cdxt.inter.service.LisRequisitionRelatedService;
import com.cdxt.inter.service.LisSpecimenRelatedService;
import com.cdxt.inter.util.InvokeWebserviceUtil;
import com.cdxt.inter.util.XStreamXmlUtil;
import com.cdxt.inter.webservice.LabInfoService;
import com.cdxt.inter.webservice.constants.WsConst;
import com.cdxt.inter.webservice.params.LisRequestionXml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.jws.WebService;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: cxf webservice接口实现
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/21 14:29
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@WebService(
        targetNamespace = WsConst.NAMESPACE_URL, //wsdl命名空间 与接口中的命名空间一致,一般是接口的包名倒
        name = "labInfoPortType",                 //portType名称 客户端生成代码时 为接口名称
        serviceName = "labInfoService",           //服务name名称 与接口中指定的name一致
        portName = "labInfoPortName",             //port名称
        endpointInterface = "com.cdxt.inter.webservice.LabInfoService")//指定发布webservcie的接口类，此类也需要接入@WebService注解
public class LabInfoServiceImpl implements LabInfoService {

    @Value("${Jh_ServiceUrl}")
    private String jhServiceUrl;

    @Value("${Jh_Hospital}")
    private String hospital;

    /**
     * 嘉和接口命名空间
     */
    private static final String JH_NAMESPACE_URI = "http://goodwillcis.com";
    /**
     * 嘉和接口方法_重庆
     */
    private static final String JH_LOCAL_PART_CQ = "HIPMessageServer";
    /**
     * 嘉和接口方法_自贡大安
     */
    private static final String JH_LOCAL_PART_ZG = "ReportReturn";
    /**
     * 嘉和接口方法参数名
     */
    private static final String JH_PARAMITER_NAME = "Message";

    @Autowired
    private LisSpecimenRelatedService lisSpecimenRelatedService;
    @Autowired
    private LisRequisitionRelatedService lisRequisitionRelatedService;

    /**
     * @return: java.lang.String
     * @author: tangxiaohui
     * @description: 新增检验申请单
     * @Param applicationXml:  申请单xml
     * @date: 2020/5/21 14:46
     */
    @Override
    public String InspectionRequisitionRelated(String applicationXml) {
        return lisRequisitionRelatedService.saveOrUpdateRequisition(applicationXml);
    }

    /**
     * @return: java.lang.String
     * @author: tangxiaohui
     * @description: 标本审核，拒收，回传报告。。
     * @Param action:   标本相关的操作代码
     * @Param message:  消息体
     * @date: 2020/5/26 17:09
     */
    @Override
    public String SpecimenRelated(String action, String message) {
        LisRequestionXml xml = XStreamXmlUtil.fromXml2Bean(LisRequestionXml.class, message);
        Map<String, String> paramMap = new HashMap<>();
        String paramXml = "";
        if (hospital.equals(WsConst.HOSPITAL_CHONGQINGSHI_YUBEIQU_RENMINYY)) {//重庆
            switch (action) {
                case WsConst.ACTION_SAMPLE_RECEIVE:
                    paramXml = lisSpecimenRelatedService.sampleReceive(xml);
                    dealWsdlUrl(WsConst.ACTION_SAMPLE_RECEIVE);
                    break;
                case WsConst.ACTION_SAMPLE_REFUSE:
                    paramXml = lisSpecimenRelatedService.sampleRefuse(xml);
                    dealWsdlUrl(WsConst.ACTION_SAMPLE_REFUSE);
                    break;
                case WsConst.ACTION_SEND_REPORT:
                    paramXml = lisSpecimenRelatedService.sendInspectionReport(xml);
                    dealWsdlUrl(WsConst.ACTION_SEND_REPORT);
                    break;
                case WsConst.ACTION_UPDATE_APPLICATION_STATE:
                    paramXml = lisSpecimenRelatedService.updateInspectionState(xml);
                    dealWsdlUrl(WsConst.ACTION_UPDATE_APPLICATION_STATE);
                    break;
                default:
                    return null;
            }
            paramMap.put(JH_PARAMITER_NAME, paramXml);
            return InvokeWebserviceUtil.invokeByAxis(jhServiceUrl, JH_NAMESPACE_URI, JH_LOCAL_PART_CQ, paramMap);
        } else if (hospital.equals(WsConst.HOSPITAL_ZIGONGSHI_DAANQU_RENMINYY)) {//自贡大安
            if (action.equals(WsConst.ACTION_SEND_REPORT)) {
                paramXml = lisSpecimenRelatedService.sendInspectionReport(xml);
                paramMap.put(JH_PARAMITER_NAME, paramXml);
                return InvokeWebserviceUtil.invokeByAxis(jhServiceUrl, JH_NAMESPACE_URI, JH_LOCAL_PART_ZG, paramMap);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * @return: void
     * @author: tangxiaohui
     * @description: 重庆渝北区 接口要加 操作后缀
     * @Param action:  操作 审核，标本接拒收，发报告...
     * @date: 2020/5/28 11:44
     */
    private void dealWsdlUrl(String action) {
        try {
            jhServiceUrl += new String(WsConst.ACTION_REQ_MAP.get(action).getBytes("UTF-8"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
