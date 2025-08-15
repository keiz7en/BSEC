package com.bsec.oop.Fayshal.Investor.model;

public class StockAlert {
    private String stockSymbol;
    private String alertType;
    private double thresholdValue;
    private String notificationMethod;
    private String createdAt;

    public StockAlert(String stockSymbol, String alertType, double thresholdValue, String notificationMethod, String createdAt) {
        this.stockSymbol = stockSymbol;
        this.alertType = alertType;
        this.thresholdValue = thresholdValue;
        this.notificationMethod = notificationMethod;
        this.createdAt = createdAt;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public double getThresholdValue() {
        return thresholdValue;
    }

    public void setThresholdValue(double thresholdValue) {
        this.thresholdValue = thresholdValue;
    }

    public String getNotificationMethod() {
        return notificationMethod;
    }

    public void setNotificationMethod(String notificationMethod) {
        this.notificationMethod = notificationMethod;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}