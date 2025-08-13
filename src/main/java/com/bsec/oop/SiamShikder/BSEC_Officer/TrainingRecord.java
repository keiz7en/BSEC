package com.bsec.oop.SiamShikder.BSEC_Officer;

import java.time.LocalDate;

public class TrainingRecord {
    private String trainingId;
    private String type;
    private LocalDate date;
    private String status;
    private String curriculum;

    public TrainingRecord() {
    }

    public TrainingRecord(String trainingId, String type, LocalDate date, String status, String curriculum) {
        this.trainingId = trainingId;
        this.type = type;
        this.date = date;
        this.status = status;
        this.curriculum = curriculum;
    }

    public String getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(String trainingId) {
        this.trainingId = trainingId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(String curriculum) {
        this.curriculum = curriculum;
    }
}
