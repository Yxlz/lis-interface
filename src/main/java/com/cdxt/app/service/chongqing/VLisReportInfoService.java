package com.cdxt.app.service.chongqing;

import com.cdxt.app.entity.VLisReportInfo;
import com.cdxt.app.model.request.ReSendReportParams;

import java.util.List;

/**
 * @Description: 报告服务
 * @Author: tangxiaohui
 * @CreateDate: 2020/8/7 14:05
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
public interface VLisReportInfoService {
    /**
     * @return:  java.util.List<com.cdxt.app.entity.VLisReportInfo>
     * @author: tangxiaohui
     * @description: 根据参数查询报告信息
     * @Param params:
     * @date: 2020/8/7 14:14
     */
    List<VLisReportInfo> getReportInfoListByParams(ReSendReportParams params);
}
