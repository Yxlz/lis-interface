package com.cdxt.app.entity;

import java.math.BigDecimal;

public class LisExchangePatientItem {
    private String id;

    private String reqOrderNo;

    private String reqItemNo;

    private String reqItemName;

    private String reqItemCname;

    private BigDecimal reqItemFee;

    private String patientId;

    private String reqItemId;

    private String isMerge;

    private String container;

    private String executeSection;

    private String type;

    private String sampleName;

    private String itemType;

    private String hospitalId;

    private String hospitalName;

    private String reqItemNoType;

    private String reqOtherItemName;

    private String reqOtherItemNo;

    private String rqcolor;

    private String executeSectionId;

    private String orderId;

    private String methodName;

    private String methodeCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getReqOrderNo() {
        return reqOrderNo;
    }

    public void setReqOrderNo(String reqOrderNo) {
        this.reqOrderNo = reqOrderNo == null ? null : reqOrderNo.trim();
    }

    public String getReqItemNo() {
        return reqItemNo;
    }

    public void setReqItemNo(String reqItemNo) {
        this.reqItemNo = reqItemNo == null ? null : reqItemNo.trim();
    }

    public String getReqItemName() {
        return reqItemName;
    }

    public void setReqItemName(String reqItemName) {
        this.reqItemName = reqItemName == null ? null : reqItemName.trim();
    }

    public String getReqItemCname() {
        return reqItemCname;
    }

    public void setReqItemCname(String reqItemCname) {
        this.reqItemCname = reqItemCname == null ? null : reqItemCname.trim();
    }

    public BigDecimal getReqItemFee() {
        return reqItemFee;
    }

    public void setReqItemFee(BigDecimal reqItemFee) {
        this.reqItemFee = reqItemFee;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId == null ? null : patientId.trim();
    }

    public String getReqItemId() {
        return reqItemId;
    }

    public void setReqItemId(String reqItemId) {
        this.reqItemId = reqItemId == null ? null : reqItemId.trim();
    }

    public String getIsMerge() {
        return isMerge;
    }

    public void setIsMerge(String isMerge) {
        this.isMerge = isMerge == null ? null : isMerge.trim();
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container == null ? null : container.trim();
    }

    public String getExecuteSection() {
        return executeSection;
    }

    public void setExecuteSection(String executeSection) {
        this.executeSection = executeSection == null ? null : executeSection.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName == null ? null : sampleName.trim();
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType == null ? null : itemType.trim();
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId == null ? null : hospitalId.trim();
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName == null ? null : hospitalName.trim();
    }

    public String getReqItemNoType() {
        return reqItemNoType;
    }

    public void setReqItemNoType(String reqItemNoType) {
        this.reqItemNoType = reqItemNoType == null ? null : reqItemNoType.trim();
    }

    public String getReqOtherItemName() {
        return reqOtherItemName;
    }

    public void setReqOtherItemName(String reqOtherItemName) {
        this.reqOtherItemName = reqOtherItemName == null ? null : reqOtherItemName.trim();
    }

    public String getReqOtherItemNo() {
        return reqOtherItemNo;
    }

    public void setReqOtherItemNo(String reqOtherItemNo) {
        this.reqOtherItemNo = reqOtherItemNo == null ? null : reqOtherItemNo.trim();
    }

    public String getRqcolor() {
        return rqcolor;
    }

    public void setRqcolor(String rqcolor) {
        this.rqcolor = rqcolor == null ? null : rqcolor.trim();
    }

    public String getExecuteSectionId() {
        return executeSectionId;
    }

    public void setExecuteSectionId(String executeSectionId) {
        this.executeSectionId = executeSectionId == null ? null : executeSectionId.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName == null ? null : methodName.trim();
    }

    public String getMethodeCode() {
        return methodeCode;
    }

    public void setMethodeCode(String methodeCode) {
        this.methodeCode = methodeCode == null ? null : methodeCode.trim();
    }
}