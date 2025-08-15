package com.bsec.oop.Fayshal.Investor.model;

public class OrderHistoryRecord {
    private String date;          // yyyy-MM-dd
    private String time;          // HH:mm:ss
    private String symbol;        // e.g., GP
    private String type;          // Buy/Sell
    private String quantity;      // keep as string for easy binding
    private String price;         // formatted currency string
    private String totalAmount;   // formatted currency string
    private String status;        // Executed/Pending
    private String fees;          // formatted currency string
    private String referenceId;   // e.g., ORD123

    public OrderHistoryRecord() {
    }

    public OrderHistoryRecord(String date, String time, String symbol, String type,
                              String quantity, String price, String totalAmount,
                              String status, String fees, String referenceId) {
        this.date = date;
        this.time = time;
        this.symbol = symbol;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.totalAmount = totalAmount;
        this.status = status;
        this.fees = fees;
        this.referenceId = referenceId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }
}
