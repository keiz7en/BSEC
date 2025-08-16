package com.bsec.oop.sadman.Auditor;

import java.io.Serializable;

public class UpdateInfo implements Serializable {
    private String password;
    private String totalShare;
    private String companyName;
    private String availabeShare;

    public UpdateInfo(String companyId, String availabeShare, String companyName, String availabeShareName) {
        this.password = companyId;
        this.totalShare = availabeShare;
        this.companyName = companyName;
        this.availabeShare = availabeShareName;
    }

    public String getCompanyId() {
        return password;
    }

    public void setCompanyId(String companyId) {
        this.password = companyId;
    }

    public String getAvailabeShare() {
        return totalShare;
    }

    public void setAvailabeShare(String availabeShare) {
        this.totalShare = availabeShare;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAvailabeShareName() {
        return availabeShare;
    }

    public void setAvailabeShareName(String availabeShareName) {
        this.availabeShare = availabeShareName;
    }

    @Override
    public String toString() {
        return "UpdateInfo{" +
                "companyId='" + password + '\'' +
                ", availabeShare='" + totalShare + '\'' +
                ", companyName='" + companyName + '\'' +
                ", availabeShareName='" + availabeShare + '\'' +
                '}';
    }
}
