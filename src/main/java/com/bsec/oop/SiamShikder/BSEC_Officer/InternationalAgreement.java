package com.bsec.oop.SiamShikder.BSEC_Officer;

import java.time.LocalDate;

public class InternationalAgreement {
    private String agencyName;
    private LocalDate meetingDate;
    private String agreementStatus;
    private String agenda;

    public InternationalAgreement() {
    }

    public InternationalAgreement(String agencyName, LocalDate meetingDate, String agreementStatus, String agenda) {
        this.agencyName = agencyName;
        this.meetingDate = meetingDate;
        this.agreementStatus = agreementStatus;
        this.agenda = agenda;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public LocalDate getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(LocalDate meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getAgreementStatus() {
        return agreementStatus;
    }

    public void setAgreementStatus(String agreementStatus) {
        this.agreementStatus = agreementStatus;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }
}
