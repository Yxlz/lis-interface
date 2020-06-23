package com.cdxt.app.dao.chongqing;

import com.cdxt.app.entity.VLisSampleInfo;
import org.apache.ibatis.annotations.Param;

public interface VLisSampleInfoMapper {
    /**
     * @return:  com.cdxt.app.entity.VLisSampleInfo
     * @author: tangxiaohui
     * @description: 查询
     * @Param barcode: 条码
     * @Param type:    1 没拒收的标本，0 拒收的标本
     * @date: 2020/6/23 11:27
     */
    VLisSampleInfo selectByBarcodeAndType(@Param("barcode") String barcode, @Param("sampleIsRefuse")String type);
}