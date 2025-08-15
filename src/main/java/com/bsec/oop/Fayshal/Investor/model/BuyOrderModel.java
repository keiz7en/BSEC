package com.bsec.oop.Fayshal.Investor.model;

public class BuyOrderModel {
    private final String marketName;
    private final String stockSymbol;
    private final int quantity;
    private final double price;
    private final String orderType;
    private final String executionDate;
    private final double estimatedTotalCost;

    public BuyOrderModel(String marketName, String stockSymbol, int quantity, double price, String orderType, String executionDate, double estimatedTotalCost) {
        this.marketName = marketName;
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.price = price;
        this.orderType = orderType;
        this.executionDate = executionDate;
        this.estimatedTotalCost = estimatedTotalCost;
    }

    public String getMarketName() {
        return marketName;
    }

    public String getStockSymbol() {
        return stockSymbol;
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

    public String getExecutionDate() {
        return executionDate;
    }

    public double getEstimatedTotalCost() {
        return estimatedTotalCost;
    }
}
