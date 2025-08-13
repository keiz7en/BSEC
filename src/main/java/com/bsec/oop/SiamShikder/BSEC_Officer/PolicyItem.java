package com.bsec.oop.SiamShikder.BSEC_Officer;

import java.time.LocalDate;

public class PolicyItem {
    private String policyId;
    private String category;
    private String status;
    private LocalDate approvalDate;
    private String summary;
    private String urgency;

    public PolicyItem() {
    }

    public PolicyItem(String policyId, String category, String status, LocalDate approvalDate, String summary, String urgency) {
        this.policyId = policyId;
        this.category = category;
        this.status = status;
        this.approvalDate = approvalDate;
        this.summary = summary;
        this.urgency = urgency;
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }
}
