package com.cdxt.app.webservice.impl;

import com.cdxt.app.model.request.LisRequestionXml;
import com.cdxt.app.service.chongqing.LisRequisitionRelatedService;
import com.cdxt.app.service.chongqing.LisSpecimenRelatedService;
import com.cdxt.app.util.InvokeWebserviceUtil;
import com.cdxt.app.util.XStreamXmlUtil;
import com.cdxt.app.webservice.LabInfoService;
import com.cdxt.app.webservice.constants.JhIfConst;
import com.cdxt.app.webservice.constants.WsConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import javax.jws.WebService;
import java.nio.charset.StandardCharsets;
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
@Slf4j
@WebService(
        targetNamespace = WsConst.NAMESPACE_URL,  //wsdl命名空间 与接口中的命名空间一致,一般是接口的包名倒
        serviceName = "labInfoService",           //对外发布的服务名，指定 Web Service 的服务名称：wsdl:service。缺省值为 Java 类的简单名称 + Service。（字符串）
        portName = "labInfoPortName",             //wsdl:portName。缺省值为 WebService.name+Port。
        endpointInterface = "com.cdxt.app.webservice.LabInfoService")//指定发布webservcie的接口类，此类也需要接入@WebService注解
public class LabInfoServiceImpl implements LabInfoService {

    @Value("${Jh_ServiceUrl}")
    private String serviceUrl;//平台地址

    @Value("${Jh_Hospital}")
    private String hospital;//医院

    @Resource
    private LisSpecimenRelatedService lisSpecimenRelatedService;
    @Resource
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
        try {
            LisRequestionXml xml = XStreamXmlUtil.fromXml2Bean(LisRequestionXml.class, message);
            Map<String, String> paramMap = new HashMap<>();
            String paramXml;
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
                if (StringUtils.isBlank(paramXml)) {
                    return null;
                }
                paramMap.put(JhIfConst.PARAMITER_NAME_CQ, paramXml);
                return InvokeWebserviceUtil.invokeByAxis(serviceUrl, JhIfConst.NAMESPACE_URI_CQ, JhIfConst.LOCAL_PART_CQ, paramMap);
            } else if (hospital.equals(WsConst.HOSPITAL_ZIGONGSHI_DAANQU_RENMINYY)) { //自贡大安
                if (action.equals(WsConst.ACTION_SEND_REPORT)) {
                    paramXml = lisSpecimenRelatedService.sendInspectionReport(xml);
                    if (StringUtils.isBlank(paramXml)) {
                        return null;
                    }
                    paramMap.put(JhIfConst.PARAMITER_NAME_ZG, paramXml);
                    return InvokeWebserviceUtil.invokeByAxis(serviceUrl, JhIfConst.NAMESPACE_URI_ZG, JhIfConst.LOCAL_PART_ZG, paramMap);
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * @return: void
     * @author: tangxiaohui
     * @description: 重庆渝北区 接口要加 操作后缀
     * @Param action:  操作 审核，标本接拒收，发报告...
     * @date: 2020/5/28 11:44
     */
    private void dealWsdlUrl(String action) {
        serviceUrl += new String(WsConst.ACTION_REQ_MAP.get(action).getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
    }
}
