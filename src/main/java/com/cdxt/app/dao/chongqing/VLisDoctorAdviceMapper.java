package com.cdxt.app.dao.chongqing;

import com.cdxt.app.entity.VLisDoctorAdvice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VLisDoctorAdviceMapper {
    /**
     * @return:  com.cdxt.app.entity.VLisDoctorAdvice
     * @author: tangxiaohui
     * @description: 查询医嘱
     * @Param patientId: lis_exchange_patient_info主键
     * @Param type:  1 没拒收的标本，0 拒收的标本
     * @date: 2020/6/23 11:38
     */
    List<VLisDoctorAdvice> selectByPatientIdAndType(@Param("patientId") String patientId, @Param("sampleIsRefuse")String type);
}