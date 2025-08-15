package com.bsec.oop.Fayshal.Investor.model;

public class AlertTriggerHistory {
    private String dateTime;
    private String stockSymbol;
    private String triggerCondition;
    private double currentPrice;
    private String notificationStatus;

    public AlertTriggerHistory(String dateTime, String stockSymbol, String triggerCondition, double currentPrice, String notificationStatus) {
        this.dateTime = dateTime;
        this.stockSymbol = stockSymbol;
        this.triggerCondition = triggerCondition;
        this.currentPrice = currentPrice;
        this.notificationStatus = notificationStatus;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public String getTriggerCondition() {
        return triggerCondition;
    }

    public void setTriggerCondition(String triggerCondition) {
        this.triggerCondition = triggerCondition;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }
}