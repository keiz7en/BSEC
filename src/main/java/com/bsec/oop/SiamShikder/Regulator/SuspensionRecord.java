package com.bsec.oop.SiamShikder.Regulator;

import java.time.LocalDate;

public class SuspensionRecord {
    private String companyId;
    private String companyName;
    private String violationType;
    private LocalDate suspensionDate;
    private String currentStatus;
    private String evidenceSummary;

    public SuspensionRecord() {
    }

    public SuspensionRecord(String companyId, String companyName, String violationType, LocalDate suspensionDate, String currentStatus, String evidenceSummary) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.violationType = violationType;
        this.suspensionDate = suspensionDate;
        this.currentStatus = currentStatus;
        this.evidenceSummary = evidenceSummary;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getViolationType() {
        return violationType;
    }

    public void setViolationType(String violationType) {
        this.violationType = violationType;
    }

    public LocalDate getSuspensionDate() {
        return suspensionDate;
    }

    public void setSuspensionDate(LocalDate suspensionDate) {
        this.suspensionDate = suspensionDate;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getEvidenceSummary() {
        return evidenceSummary;
    }

    public void setEvidenceSummary(String evidenceSummary) {
        this.evidenceSummary = evidenceSummary;
    }
}
