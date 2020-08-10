package com.cdxt.app.dao.chongqing;

import com.cdxt.app.entity.VLisReportInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VLisReportInfoMapper {
    /**
     * @return:  com.cdxt.app.entity.VLisReportInfo
     * @author: tangxiaohui
     * @description: 查询一份检验报告
     * @Param barcode:
     * @Param inspecNo:
     * @Param inputDate:
     * @Param devId:
     * @date: 2020/8/10 11:33
     */
    VLisReportInfo selectByParam(@Param("barcode") String barcode, @Param("inspecNo") String inspecNo, @Param("inputDate") String inputDate, @Param("devId") String devId);

    List<VLisReportInfo> selectByTime(@Param("fromDate") String fromDate, @Param("endDate") String endDate);
}