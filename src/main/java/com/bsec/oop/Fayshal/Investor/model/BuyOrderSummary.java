package com.bsec.oop.Fayshal.Investor.model;

public class BuyOrderSummary {
    private final String symbol;
    private final int quantity;
    private final double price;
    private final String orderType;
    private final double estimatedTotal;

    public BuyOrderSummary(String symbol, int quantity, double price, String orderType, double estimatedTotal) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
        this.orderType = orderType;
        this.estimatedTotal = estimatedTotal;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getOrderType() {
        return orderType;
    }

    public double getEstimatedTotal() {
        return estimatedTotal;
    }
}
