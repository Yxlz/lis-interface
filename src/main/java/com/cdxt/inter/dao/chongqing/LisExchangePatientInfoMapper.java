package com.cdxt.inter.dao.chongqing;

import com.cdxt.inter.entity.LisExchangePatientInfo;

public interface LisExchangePatientInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(LisExchangePatientInfo record);

    int insertSelective(LisExchangePatientInfo record);

    LisExchangePatientInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LisExchangePatientInfo record);

    int updateByPrimaryKey(LisExchangePatientInfo record);
}