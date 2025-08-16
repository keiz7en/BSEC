package com.bsec.oop.Fayshal.Investor.model;

import java.io.Serializable;

public class SellOrderEntry implements Serializable {
    private static final long serialVersionUID = 1L;
    private String stockSymbol;
    private int quantity;
    private String orderType;
    private double limitPrice;
    private String sellDate;

    public SellOrderEntry(String stockSymbol, int quantity, String orderType, double limitPrice, String sellDate) {
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.orderType = orderType;
        this.limitPrice = limitPrice;
        this.sellDate = sellDate;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public double getLimitPrice() {
        return limitPrice;
    }

    public void setLimitPrice(double limitPrice) {
        this.limitPrice = limitPrice;
    }

    public String getSellDate() {
        return sellDate;
    }

    public void setSellDate(String sellDate) {
        this.sellDate = sellDate;
    }
}