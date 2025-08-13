package com.bsec.oop.SiamShikder.Regulator;

import java.time.LocalDate;

public class WhistleblowerCase {
    private String reportId;
    private String category;
    private LocalDate dateReceived;
    private String urgency;
    private String summary;
    private String assignedTeam;
    private String status;
    private String outcome;

    public WhistleblowerCase() {
    }

    public WhistleblowerCase(String reportId, String category, LocalDate dateReceived, String urgency, String summary, String assignedTeam, String status, String outcome) {
        this.reportId = reportId;
        this.category = category;
        this.dateReceived = dateReceived;
        this.urgency = urgency;
        this.summary = summary;
        this.assignedTeam = assignedTeam;
        this.status = status;
        this.outcome = outcome;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(LocalDate dateReceived) {
        this.dateReceived = dateReceived;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAssignedTeam() {
        return assignedTeam;
    }

    public void setAssignedTeam(String assignedTeam) {
        this.assignedTeam = assignedTeam;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }
}
