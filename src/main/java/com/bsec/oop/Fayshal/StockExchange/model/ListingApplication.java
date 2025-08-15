package com.bsec.oop.Fayshal.StockExchange.model;

import java.io.Serializable;

public class ListingApplication implements Serializable {
    private static final long serialVersionUID = 1L;
    private String applicationId;
    private String companyName;
    private String submissionDate;
    private String status;
    private String decisionDate;

    public ListingApplication(String applicationId, String companyName, String submissionDate, String status, String decisionDate) {
        this.applicationId = applicationId;
        this.companyName = companyName;
        this.submissionDate = submissionDate;
        this.status = status;
        this.decisionDate = decisionDate;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDecisionDate() {
        return decisionDate;
    }

    public void setDecisionDate(String decisionDate) {
        this.decisionDate = decisionDate;
    }
}