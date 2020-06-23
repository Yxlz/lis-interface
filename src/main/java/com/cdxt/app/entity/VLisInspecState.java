package com.cdxt.app.entity;

import lombok.Data;

import java.util.Date;
@Data
public class VLisInspecState {
    private String reqNo;

    private String reqDescription;

    private String barcode;

    private String sampleCode;

    private String sampleName;

    private Date operationTime;

    private String sampleStateCode;

    private String sampleStateStr;

    private String sampleRefuseReason;

    private String operatorJobNumber;

    private String operatorName;

    private String operatorDeptCode;

    private String operatorDeptName;

    private String personInfoId;

    private String idNumber;

    private String healthCareCardNo;

    private String patientName;

    private String patientSexCode;

    private String patientSexStr;

    private String patientBirthday;

}