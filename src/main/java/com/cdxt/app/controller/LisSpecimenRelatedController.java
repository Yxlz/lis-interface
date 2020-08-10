package com.cdxt.app.controller;

import app.entity.LisInspecDevInfo;
import app.manager.api.LisService;
import com.cdxt.app.entity.VLisReportInfo;
import com.cdxt.app.enums.IFStateEnum;
import com.cdxt.app.enums.ReturnCodeEnum;
import com.cdxt.app.model.request.LisRequestionXml;
import com.cdxt.app.model.request.ReSendReportParams;
import com.cdxt.app.model.response.BaseResponse;
import com.cdxt.app.service.chongqing.LisSpecimenRelatedService;
import com.cdxt.app.service.chongqing.VLisReportInfoService;
import com.cdxt.app.util.InvokeWebserviceUtil;
import com.cdxt.app.util.JsonUtils;
import com.cdxt.app.webservice.constants.JhIfConst;
import com.cdxt.app.webservice.constants.WsConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
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

    /**
     * @return: com.cdxt.app.model.response.BaseResponse
     * @author: tangxiaohui
     * @description: 根据时间段重新推送检验报告给平台
     * @Param request:
     * @date: 2020/8/10 16:29
     */
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
                LisRequestionXml xml = new LisRequestionXml();
                xml.setBarCode(reportInfo.getBarcode());
                xml.setDevCode(devInfo.getDevCode());
                xml.setHospid("1c2e5d680000be4724dc");
                xml.setInputDate(reportInfo.getInputDate());
                xml.setInspecNo(reportInfo.getInspecNo());
                Map<String, String> paramMap = new HashMap<>();
                String paramXml = lisSpecimenRelatedService.sendInspectionReport(xml);
                if (StringUtils.isBlank(paramXml)) {
                    return null;
                }
                if (hospital.equals(WsConst.HOSPITAL_ZIGONGSHI_DAANQU_RENMINYY)) {
                    paramMap.put(JhIfConst.PARAMITER_NAME_ZG, paramXml);
                    InvokeWebserviceUtil.invokeByAxis(serviceUrl, JhIfConst.NAMESPACE_URI_ZG, JhIfConst.LOCAL_PART_ZG, paramMap);
                } else if (hospital.equals(WsConst.HOSPITAL_CHONGQINGSHI_YUBEIQU_RENMINYY)) {//重庆
                    serviceUrl += new String(WsConst.ACTION_REQ_MAP.get(WsConst.ACTION_SEND_REPORT).getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
                    paramMap.put(JhIfConst.PARAMITER_NAME_CQ, paramXml);
                    InvokeWebserviceUtil.invokeByAxis(serviceUrl, JhIfConst.NAMESPACE_URI_CQ, JhIfConst.LOCAL_PART_CQ, paramMap);
                }

            }
            return new BaseResponse(IFStateEnum.SUCCESS, ReturnCodeEnum.SUCCESS, "重发报告成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse(IFStateEnum.SUCCESS, ReturnCodeEnum.FAIL, e.getMessage(), null);
        }
    }
}
