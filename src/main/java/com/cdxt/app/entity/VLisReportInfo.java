package com.cdxt.app.entity;

import lombok.Data;

import java.util.Date;
@Data
public class VLisReportInfo {
    private String id;

    private String inputDate;

    private String devId;

    private String inspecNo;

    private String personInfoId;

    private String mjzNo;

    private String zyNo;

    private String tjNo;

    private String reportNo;

    private String requestNo;

    private String specimenNo;

    private String timesOfVisit;

    private String patientTypeCode;

    private String patientTypeName;

    private String patientPhone;

    private String patientIdNo;

    private String patientName;

    private String patientSexCode;

    private String patientSexStr;

    private String patientBirthday;

    private String patientAgeUnit;

    private String patientAge;

    private Date timeReport;

    private String reporterJobNo;

    private String reporterName;

    private String custodianOrgCode;

    private String custodianOrgName;

    private Date timeOfCheck;

    private String checkDoctorJobNo;

    private String checkDoctorName;

    private Date timeOfInspec;

    private String inspecDoctorJobNo;

    private String inspecDoctorName;

    private Date timeOfReq;

    private String reqDeptCode;

    private String reqDeptName;

    private String reqHospitalCode;

    private String reqHospitalName;

    private Date timeOfInspecExcute;

    private String inspecExcuteDoctorJobNo;

    private String inspecExcuteDoctorName;

    private String bedNo;

    private String patientWardNo;

    private String patientWardNoCode;

    private String patientWardName;

    private String deptCode;

    private String deptName;

    private String hospitalName;

    private String diagnosisText;

    private String diagnosisTime;

    private String diagnosisCode;

    private String diagnosisName;

    private String diagnosisOrg;

    private Date preOrderTime;

    private String preOrderPersonJobNo;

    private String preOrderPersonName;

    private Date preCollectTime;

    private String preCollectorJobNo;

    private String preCollectorName;

    private String inspecCode;

    private String inspecName;

    private String inspecTitle;

    private String barcode;

    private String sampleCode;

    private String sampleName;

    private Date sampleCollectTime;

    private String barcode1;

    private String sampleCollectorJobNo;

    private String sampleCollectorName;

    private String sampleCollectMethodCode;

    private String sampleCollectMethodName;

    private String sampleCollectPartCode;

    private String sampleCollectPartName;

    private String barcode2;

    private Date sampleReqTime;

    private Date sampleReceiveTime;

    private String barcode3;

    private String sampleReceiverJobNo;

    private String sampleReceiverName;

    private String reportNo1;
}