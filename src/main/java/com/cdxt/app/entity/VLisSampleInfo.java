package com.cdxt.app.entity;

import lombok.Data;

import java.util.Date;
@Data
public class VLisSampleInfo {
    private String patientId;

    private String sampleIsRefuse;

    private String regionId;

    private String patientCode;

    private String patientType;

    private String patientCode1;

    private Date orderTime;

    private String orderDoctorCode;

    private String orderDoctorName;

    private String orderDeptCode;

    private String orderDeptName;

    private Date timeReceiveStarting;

    private Date timeReceiveLimit;

    private String receiverJobNo;

    private String receiverName;

    private Date confirmTime;

    private String theConfirmCode;

    private String theConfirmName;

    private String requestNo;

    private String inspecNameCode;

    private String inspecName;

    private Date reqDate;

}