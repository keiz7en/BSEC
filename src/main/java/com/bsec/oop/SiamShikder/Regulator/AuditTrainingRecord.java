package com.bsec.oop.SiamShikder.Regulator;

import java.time.LocalDate;

public class AuditTrainingRecord {
    private String curriculumTitle;
    private String auditType;
    private LocalDate date;
    private String mode;
    private String status;
    private String participants;
    private String overview;

    public AuditTrainingRecord() {
    }

    public AuditTrainingRecord(String curriculumTitle, String auditType, LocalDate date, String mode, String status, String participants, String overview) {
        this.curriculumTitle = curriculumTitle;
        this.auditType = auditType;
        this.date = date;
        this.mode = mode;
        this.status = status;
        this.participants = participants;
        this.overview = overview;
    }

    public String getCurriculumTitle() {
        return curriculumTitle;
    }

    public void setCurriculumTitle(String curriculumTitle) {
        this.curriculumTitle = curriculumTitle;
    }

    public String getAuditType() {
        return auditType;
    }

    public void setAuditType(String auditType) {
        this.auditType = auditType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
