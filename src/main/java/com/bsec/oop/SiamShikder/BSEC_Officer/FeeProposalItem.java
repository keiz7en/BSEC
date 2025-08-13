package com.bsec.oop.SiamShikder.BSEC_Officer;

import java.time.LocalDate;

public class FeeProposalItem {
    private String proposalId;
    private String feeType;
    private String status;
    private LocalDate approvalDate;
    private String summary;

    public FeeProposalItem() {
    }

    public FeeProposalItem(String proposalId, String feeType, String status, LocalDate approvalDate, String summary) {
        this.proposalId = proposalId;
        this.feeType = feeType;
        this.status = status;
        this.approvalDate = approvalDate;
        this.summary = summary;
    }

    public String getProposalId() {
        return proposalId;
    }

    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
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
}
