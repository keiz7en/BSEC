package com.bsec.oop.sadman.Company;

import java.io.Serializable;
import java.time.LocalDate;

public class IPODetails implements Serializable {
    private String companyName;
    private int ipoAmount;
    private final LocalDate date;
    private String ipoDetails;

    public IPODetails(LocalDate date, String companyName, int ipoAmount, String ipoDetails) {
        this.date = date;
        this.companyName = companyName;
        this.ipoAmount = ipoAmount;
        this.ipoDetails = ipoDetails;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getIpoAmount() {
        return ipoAmount;
    }

    public void setIpoAmount(int ipoAmount) {
        this.ipoAmount = ipoAmount;
    }

    public String getIpoDetails() {
        return ipoDetails;
    }

    public void setIpoDetails(String ipoDetails) {
        this.ipoDetails = ipoDetails;
    }
}