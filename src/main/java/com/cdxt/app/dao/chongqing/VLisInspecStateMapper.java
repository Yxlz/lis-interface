package com.cdxt.app.dao.chongqing;

import com.cdxt.app.entity.VLisInspecState;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VLisInspecStateMapper {

    /**
     * @return:  java.util.List<com.cdxt.app.entity.VLisInspecState>
     * @author: tangxiaohui
     * @description: 通过条码号查询标本状态视图 如果标本先拒收后接收则可能存在两条记录，需要取操作时间最近的那一条
     * @Param barCode:
     * @date: 2020/6/23 15:26
     */
    List<VLisInspecState> selectByBarcode(@Param("barcode") String barCode);
}