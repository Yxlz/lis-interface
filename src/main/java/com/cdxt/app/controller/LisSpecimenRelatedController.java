package com.cdxt.app.controller;

import app.entity.LisInspecDevInfo;
import app.manager.api.LisService;
import com.cdxt.app.entity.VLisReportInfo;
import com.cdxt.app.enums.IFStateEnum;
import com.cdxt.app.enums.ReturnCodeEnum;
import com.cdxt.app.model.request.LisRequestionXml;
import com.cdxt.app.model.request.ReSendReportParams;
import com.cdxt.app.model.response.BaseResponse;
import com.cdxt.app.service.LisSpecimenRelatedService;
import com.cdxt.app.service.chongqing.VLisReportInfoService;
import com.cdxt.app.util.InvokeWebserviceUtil;
import com.cdxt.app.util.JsonUtils;
import com.cdxt.app.util.XmlUtil;
import com.cdxt.app.webservice.constants.JhIfConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: LIS标本相关控制器
 * @Author: tangxiaohui
 * @CreateDate: 2020/8/7 13:32
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/services/specimen")
public class LisSpecimenRelatedController extends BaseController {

    @Value("${Jh_ServiceUrl}")
    private String serviceUrl;//平台地址

    @Value("${Jh_Hospital}")
    private String hospital;//医院

    @Resource
    VLisReportInfoService vLisReportInfoService;

    @Resource
    LisSpecimenRelatedService lisSpecimenRelatedService;

    @Resource
    private LisService lisService;

    @RequestMapping(value = "/reSendReport")
    public BaseResponse requisition(HttpServletRequest request) {
        ReSendReportParams params;
        try {
            String bodyStr = getRequestBody(request);
            log.info("重传报告参数：" + bodyStr);
            params = JsonUtils.parseToObject(bodyStr,
                    ReSendReportParams.class);

            List<VLisReportInfo> reportInfoList = vLisReportInfoService.getReportInfoListByParams(params);

            for (VLisReportInfo reportInfo : reportInfoList) {
                LisInspecDevInfo devInfo = lisService.getLisInspecDevInfoById(reportInfo.getDevId());
                String message = "<request>\n" +
                        "\t\t\t\t<inputDate>"+reportInfo.getInputDate()+"</inputDate>\n" +
                        "\t\t\t\t<devCode>"+devInfo.getDevCode()+"</devCode>\n" +
                        "\t\t\t\t<inspecNo>"+reportInfo.getInspecNo()+"</inspecNo>\n" +
                        "\t\t\t\t<barCode>"+reportInfo.getBarcode()+"</barCode>\n" +
                        "\t\t\t\t<hospid>1c2e5d680000be4724dc</hospid>\n" +
                        "\t\t\t</request>";
                LisRequestionXml xml = XmlUtil.convertXmlToBean(LisRequestionXml.class, message);
                Map<String, String> paramMap = new HashMap<>();
                String paramXml = lisSpecimenRelatedService.sendInspectionReport(xml);
                if (StringUtils.isBlank(paramXml)) {
                    return null;
                }
                paramMap.put(JhIfConst.PARAMITER_NAME_ZG, paramXml);
                InvokeWebserviceUtil.invokeByAxis("http://192.168.252.171:57772/csp/healthshare/jhipda/JHIP.LIS.BS.LisBS.CLS", JhIfConst.NAMESPACE_URI_ZG, JhIfConst.LOCAL_PART_ZG, paramMap);
            }
            return new BaseResponse(IFStateEnum.SUCCESS, ReturnCodeEnum.SUCCESS, "重发报告成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse(IFStateEnum.SUCCESS, ReturnCodeEnum.FAIL, e.getMessage(), null);
        }
    }
}
