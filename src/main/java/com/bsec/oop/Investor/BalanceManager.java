package com.bsec.oop.Investor;

import java.math.BigDecimal;

public class BalanceManager {
    private static BalanceManager instance;
    private BigDecimal currentBalance = new BigDecimal("0.00");

    private BalanceManager() {
    }

    public static BalanceManager getInstance() {
        if (instance == null) {
            instance = new BalanceManager();
        }
        return instance;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setBalance(BigDecimal balance) {
        this.currentBalance = balance;
    }

    public void addFunds(BigDecimal amount) {
        this.currentBalance = this.currentBalance.add(amount);
    }

    public void deductFunds(BigDecimal amount) {
        this.currentBalance = this.currentBalance.subtract(amount);
    }

    public String getFormattedBalance() {
        return String.format("৳%.2f", currentBalance);
    }
}