package com.cdxt.app.dao.chongqing;

import com.cdxt.app.entity.VLisReportInfo;
import org.apache.ibatis.annotations.Param;

public interface VLisReportInfoMapper {
    VLisReportInfo selectByParam(@Param("barcode") String barcode, @Param("inspecNo") String inspecNo, @Param("inputDate") String inputDate, @Param("devId") String devId);
}