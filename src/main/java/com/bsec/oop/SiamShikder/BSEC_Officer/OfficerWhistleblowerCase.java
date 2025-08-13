package com.bsec.oop.SiamShikder.BSEC_Officer;

import java.time.LocalDate;

public class OfficerWhistleblowerCase {
    private String caseId;
    private String type;
    private String status;
    private LocalDate reportDate;
    private LocalDate closureDate;
    private String details;

    public OfficerWhistleblowerCase() {
    }

    public OfficerWhistleblowerCase(String caseId, String type, String status, LocalDate reportDate, LocalDate closureDate, String details) {
        this.caseId = caseId;
        this.type = type;
        this.status = status;
        this.reportDate = reportDate;
        this.closureDate = closureDate;
        this.details = details;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public LocalDate getClosureDate() {
        return closureDate;
    }

    public void setClosureDate(LocalDate closureDate) {
        this.closureDate = closureDate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
