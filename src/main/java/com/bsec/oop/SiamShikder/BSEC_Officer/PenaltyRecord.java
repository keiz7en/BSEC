package com.bsec.oop.SiamShikder.BSEC_Officer;

public class PenaltyRecord {
    private String caseId;
    private String violationType;
    private String penaltyAmount;
    private String status;
    private String findings;

    public PenaltyRecord() {
    }

    public PenaltyRecord(String caseId, String violationType, String penaltyAmount, String status, String findings) {
        this.caseId = caseId;
        this.violationType = violationType;
        this.penaltyAmount = penaltyAmount;
        this.status = status;
        this.findings = findings;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getViolationType() {
        return violationType;
    }

    public void setViolationType(String violationType) {
        this.violationType = violationType;
    }

    public String getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(String penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFindings() {
        return findings;
    }

    public void setFindings(String findings) {
        this.findings = findings;
    }
}
