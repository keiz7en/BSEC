package com.bsec.oop.Fayshal.StockExchange.model;

import java.io.Serializable;

public class MarketDataRow implements Serializable {
    private static final long serialVersionUID = 1L;
    private String tickerSymbol;
    private String companyName;
    private double openingPrice;
    private double closingPrice;
    private long volume;
    private double marketCap;

    public MarketDataRow(String tickerSymbol, String companyName, double openingPrice, double closingPrice, long volume, double marketCap) {
        this.tickerSymbol = tickerSymbol;
        this.companyName = companyName;
        this.openingPrice = openingPrice;
        this.closingPrice = closingPrice;
        this.volume = volume;
        this.marketCap = marketCap;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(double openingPrice) {
        this.openingPrice = openingPrice;
    }

    public double getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(double closingPrice) {
        this.closingPrice = closingPrice;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(double marketCap) {
        this.marketCap = marketCap;
    }
}