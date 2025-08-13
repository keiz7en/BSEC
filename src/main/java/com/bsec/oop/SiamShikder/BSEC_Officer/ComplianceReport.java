package com.bsec.oop.SiamShikder.BSEC_Officer;

import java.time.LocalDate;

public class ComplianceReport {
    private String reportId;
    private String area;
    private String status;
    private String followUpNeeded;
    private LocalDate reportDate;

    public ComplianceReport() {
    }

    public ComplianceReport(String reportId, String area, String status, String followUpNeeded, LocalDate reportDate) {
        this.reportId = reportId;
        this.area = area;
        this.status = status;
        this.followUpNeeded = followUpNeeded;
        this.reportDate = reportDate;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFollowUpNeeded() {
        return followUpNeeded;
    }

    public void setFollowUpNeeded(String followUpNeeded) {
        this.followUpNeeded = followUpNeeded;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }
}
