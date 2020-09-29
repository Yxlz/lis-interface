package com.cdxt.app.dao.chongqing;

import com.cdxt.app.entity.LisExchangePatientItem;
import org.apache.ibatis.annotations.Param;

public interface LisExchangePatientItemMapper {
    int deleteByPrimaryKey(String id);

    int insert(LisExchangePatientItem record);

    int insertSelective(LisExchangePatientItem record);

    LisExchangePatientItem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LisExchangePatientItem record);

    int updateByPrimaryKey(LisExchangePatientItem record);

    void deleteByPatientId(@Param("PATIENT_ID") String id);

    void deleteByPatientIdAndItemCode(LisExchangePatientItem item);
}