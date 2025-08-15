package com.bsec.oop.Fayshal.Investor.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Simple, human-readable model for a single transaction row in the account statement.
 * Uses plain getters so JavaFX TableView can bind via PropertyValueFactory without JavaFX-specific properties.
 */
public class TransactionRecord {
    private LocalDate date;
    private String transactionType;
    private String description;
    private BigDecimal debitAmount;   // money out
    private BigDecimal creditAmount;  // money in
    private BigDecimal balanceAfter;  // resulting balance after this transaction

    public TransactionRecord() {
    }

    public TransactionRecord(
            LocalDate date,
            String transactionType,
            String description,
            BigDecimal debitAmount,
            BigDecimal creditAmount,
            BigDecimal balanceAfter
    ) {
        this.date = date;
        this.transactionType = transactionType;
        this.description = description;
        this.debitAmount = debitAmount;
        this.creditAmount = creditAmount;
        this.balanceAfter = balanceAfter;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(BigDecimal debitAmount) {
        this.debitAmount = debitAmount;
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }
}
