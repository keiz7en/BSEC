package com.bsec.oop.Fayshal.Investor.model;

import java.io.Serializable;

public class StockData implements Serializable {
    private static final long serialVersionUID = 1L;
    private String stockSymbol;
    private String stockName;
    private double currentPrice;
    private double changePercent;
    private long volume;

    public StockData(String stockSymbol, String stockName, double currentPrice, double changePercent, long volume) {
        this.stockSymbol = stockSymbol;
        this.stockName = stockName;
        this.currentPrice = currentPrice;
        this.changePercent = changePercent;
        this.volume = volume;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(double changePercent) {
        this.changePercent = changePercent;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }
}