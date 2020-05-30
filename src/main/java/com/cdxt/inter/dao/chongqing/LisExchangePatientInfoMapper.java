package com.cdxt.inter.dao.chongqing;

import com.cdxt.inter.entity.LisExchangePatientInfo;
import org.apache.ibatis.annotations.Param;

public interface LisExchangePatientInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(LisExchangePatientInfo record);

    int insertSelective(LisExchangePatientInfo record);

    LisExchangePatientInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LisExchangePatientInfo record);

    int updateByPrimaryKey(LisExchangePatientInfo record);

    LisExchangePatientInfo selectByPatientId(@Param("patientId") String barcode);

    Integer cancelByPatientId(@Param("patientId") String patientId);
}