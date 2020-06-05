package com.cdxt.inter.dao.chongqing;

import com.cdxt.inter.entity.VLisReportInfo;
import org.apache.ibatis.annotations.Param;

public interface VLisReportInfoMapper {
    VLisReportInfo selectByBarcode(@Param("barcode") String barcode);
}