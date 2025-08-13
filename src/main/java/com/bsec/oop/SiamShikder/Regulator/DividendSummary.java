package com.bsec.oop.SiamShikder.Regulator;

public class DividendSummary {
    private String companyName;
    private String financialYear;
    private String dividendPercent;
    private String status;
    private String totalShares;
    private String notes;

    public DividendSummary() {
    }

    public DividendSummary(String companyName, String financialYear, String dividendPercent, String status, String totalShares, String notes) {
        this.companyName = companyName;
        this.financialYear = financialYear;
        this.dividendPercent = dividendPercent;
        this.status = status;
        this.totalShares = totalShares;
        this.notes = notes;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    public String getDividendPercent() {
        return dividendPercent;
    }

    public void setDividendPercent(String dividendPercent) {
        this.dividendPercent = dividendPercent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalShares() {
        return totalShares;
    }

    public void setTotalShares(String totalShares) {
        this.totalShares = totalShares;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
