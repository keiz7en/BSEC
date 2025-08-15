package com.bsec.oop.Fayshal.Investor.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionLog {
    private static final TransactionLog INSTANCE = new TransactionLog();
    private final ArrayList<TransactionRecord> records = new ArrayList<>();

    private TransactionLog() {
    }

    public static TransactionLog getInstance() {
        return INSTANCE;
    }

    public synchronized void logDeposit(LocalDate date, BigDecimal amount, BigDecimal balanceAfter, String description) {
        TransactionRecord r = new TransactionRecord(
                date == null ? LocalDate.now() : date,
                "Deposit",
                description == null ? "Funds added" : description,
                BigDecimal.ZERO,
                amount,
                balanceAfter
        );
        records.add(r);
    }

    public synchronized void logBuy(LocalDate date, String symbol, int quantity, BigDecimal totalCost, BigDecimal balanceAfter) {
        String desc = (symbol == null ? "" : symbol) + (quantity > 0 ? " x" + quantity : "");
        TransactionRecord r = new TransactionRecord(
                date == null ? LocalDate.now() : date,
                "Buy",
                desc.trim(),
                totalCost,
                BigDecimal.ZERO,
                balanceAfter
        );
        records.add(r);
    }

    public synchronized void logSell(LocalDate date, String symbol, int quantity, BigDecimal totalProceeds, BigDecimal balanceAfter) {
        String desc = (symbol == null ? "" : symbol) + (quantity > 0 ? " x" + quantity : "");
        TransactionRecord r = new TransactionRecord(
                date == null ? LocalDate.now() : date,
                "Sell",
                desc.trim(),
                BigDecimal.ZERO,
                totalProceeds,
                balanceAfter
        );
        records.add(r);
    }

    public synchronized List<TransactionRecord> getAll() {
        return Collections.unmodifiableList(new ArrayList<>(records));
    }

    public synchronized List<TransactionRecord> getByDateRange(LocalDate from, LocalDate to) {
        LocalDate start = from == null ? LocalDate.MIN : from;
        LocalDate end = to == null ? LocalDate.MAX : to;
        return records.stream()
                .filter(r -> {
                    LocalDate d = r.getDate();
                    return d != null && !d.isBefore(start) && !d.isAfter(end);
                })
                .collect(Collectors.toList());
    }
}
