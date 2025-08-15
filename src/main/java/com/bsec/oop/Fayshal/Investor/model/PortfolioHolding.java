package com.bsec.oop.Fayshal.Investor.model;

public class PortfolioHolding {
    private final String symbol;
    private final int quantity;
    private final double buyPrice;
    private final double currentPrice;
    private final double gainLossPercent;
    private final String marketName;

    public PortfolioHolding(String symbol, int quantity, double buyPrice, double currentPrice, double gainLossPercent, String marketName) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
        this.currentPrice = currentPrice;
        this.gainLossPercent = gainLossPercent;
        this.marketName = marketName;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public double getGainLossPercent() {
        return gainLossPercent;
    }

    public String getMarketName() {
        return marketName;
    }
}
