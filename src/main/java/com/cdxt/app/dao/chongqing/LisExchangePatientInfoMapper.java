package com.cdxt.app.dao.chongqing;

import com.cdxt.app.entity.LisExchangePatientInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LisExchangePatientInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(LisExchangePatientInfo record);

    int insertSelective(LisExchangePatientInfo record);

    LisExchangePatientInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LisExchangePatientInfo record);

    int updateByPrimaryKey(LisExchangePatientInfo record);

    LisExchangePatientInfo selectByPatientId(@Param("patientId") String barcode);

    Integer cancelByPatientId(@Param("patientId") String patientId);

    Integer cancelByRequestNo(@Param("requestNo") String requestNo);

    List<LisExchangePatientInfo> selectByPatientCode(@Param("patientCode") String patientCode);
}