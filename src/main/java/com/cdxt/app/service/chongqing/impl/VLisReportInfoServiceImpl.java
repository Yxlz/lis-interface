package com.cdxt.app.service.chongqing.impl;

import com.cdxt.app.dao.chongqing.VLisReportInfoMapper;
import com.cdxt.app.entity.VLisReportInfo;
import com.cdxt.app.model.request.ReSendReportParams;
import com.cdxt.app.service.chongqing.VLisReportInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: TODO
 * @Author: tangxiaohui
 * @CreateDate: 2020/8/7 14:05
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Service
public class VLisReportInfoServiceImpl implements VLisReportInfoService {
    @Resource
    VLisReportInfoMapper vLisReportInfoMapper;

    /**
     * @param params
     * @return: java.util.List<com.cdxt.app.entity.VLisReportInfo>
     * @author: tangxiaohui
     * @description: 根据参数查询报告信息
     * @Param params:
     * @date: 2020/8/7 14:14
     */
    @Override
    public List<VLisReportInfo> getReportInfoListByParams(ReSendReportParams params) {
        return vLisReportInfoMapper.selectByTime(params.getFromDate(),params.getEndDate());
    }
}
