package com.bsec.oop.SiamShikder.Regulator;

import java.time.LocalDate;

public class SuspiciousActivity {
    private LocalDate date;
    private String stockSymbol;
    private String insiderName;
    private String violationType;
    private String status;
    private String details;

    public SuspiciousActivity() {
    }

    public SuspiciousActivity(LocalDate date, String stockSymbol, String insiderName, String violationType, String status, String details) {
        this.date = date;
        this.stockSymbol = stockSymbol;
        this.insiderName = insiderName;
        this.violationType = violationType;
        this.status = status;
        this.details = details;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public String getInsiderName() {
        return insiderName;
    }

    public void setInsiderName(String insiderName) {
        this.insiderName = insiderName;
    }

    public String getViolationType() {
        return violationType;
    }

    public void setViolationType(String violationType) {
        this.violationType = violationType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
