package com.bsec.oop.Fayshal.Investor.model;

import java.io.Serializable;

public class BuyOrderEntry implements Serializable {
    private static final long serialVersionUID = 1L;
    private String stockSymbol;
    private int quantity;
    private String orderType;
    private double price;
    private String orderDate;

    public BuyOrderEntry(String stockSymbol, int quantity, String orderType, double price, String orderDate) {
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.orderType = orderType;
        this.price = price;
        this.orderDate = orderDate;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}