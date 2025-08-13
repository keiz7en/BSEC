package com.bsec.oop.SiamShikder.BSEC_Officer;

import java.time.LocalDate;

public class InvestigationCase {
    private String caseId;
    private String caseType;
    private String status;
    private LocalDate submissionDate;
    private String details;

    public InvestigationCase() {
    }

    public InvestigationCase(String caseId, String caseType, String status, LocalDate submissionDate, String details) {
        this.caseId = caseId;
        this.caseType = caseType;
        this.status = status;
        this.submissionDate = submissionDate;
        this.details = details;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
