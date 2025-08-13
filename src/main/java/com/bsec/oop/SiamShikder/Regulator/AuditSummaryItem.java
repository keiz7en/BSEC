package com.bsec.oop.SiamShikder.Regulator;

public class AuditSummaryItem {
    private String sectionName;
    private String findings;
    private String severity;
    private String status;

    public AuditSummaryItem() {
    }

    public AuditSummaryItem(String sectionName, String findings, String severity, String status) {
        this.sectionName = sectionName;
        this.findings = findings;
        this.severity = severity;
        this.status = status;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getFindings() {
        return findings;
    }

    public void setFindings(String findings) {
        this.findings = findings;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
