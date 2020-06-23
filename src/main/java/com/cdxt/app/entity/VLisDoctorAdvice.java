package com.cdxt.app.entity;

import lombok.Data;

import java.util.Date;
@Data
public class VLisDoctorAdvice {
    private String patientId;

    private String sampleIsRefuse;

    private String orderNo;

    private String inspecItemCode;

    private String inspecItemName;

    private String orderTimeStarting;

    private String orderTimeLimit;

    private String orderFrequencyCode;

    private String orderFrequencyName;

    private String priority;

    private String excuteSectionCode;

    private String excuteSectionName;

    private String barcode;

    private String sampleCode;

    private String sampleName;

    private Date timeCollect;

    private String collectorCode;

    private String collectorName;

    private String collectorPlace;

    private String attentionCode;

    private String attentionName;

}