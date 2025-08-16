package com.bsec.oop.Fayshal.StockExchange;

import java.time.LocalDate;

public class TradingHalt {
    private String haltId;
    private String reason;
    private String guideline;
    private String haltType;
    private LocalDate haltDate;
    private String status;

    public TradingHalt() {
    }

    public TradingHalt(String haltId, String reason, String guideline, String haltType, LocalDate haltDate, String status) {
        this.haltId = haltId;
        this.reason = reason;
        this.guideline = guideline;
        this.haltType = haltType;
        this.haltDate = haltDate;
        this.status = status;
    }

    public String getHaltId() {
        return haltId;
    }

    public void setHaltId(String haltId) {
        this.haltId = haltId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getGuideline() {
        return guideline;
    }

    public void setGuideline(String guideline) {
        this.guideline = guideline;
    }

    public String getHaltType() {
        return haltType;
    }

    public void setHaltType(String haltType) {
        this.haltType = haltType;
    }

    public LocalDate getHaltDate() {
        return haltDate;
    }

    public void setHaltDate(LocalDate haltDate) {
        this.haltDate = haltDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TradingHalt{" +
                "haltId='" + haltId + '\'' +
                ", reason='" + reason + '\'' +
                ", guideline='" + guideline + '\'' +
                ", haltType='" + haltType + '\'' +
                ", haltDate=" + haltDate +
                ", status='" + status + '\'' +
                '}';
    }
}