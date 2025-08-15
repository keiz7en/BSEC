package com.bsec.oop.Fayshal.Investor;

import com.bsec.oop.Fayshal.Investor.model.TransactionLog;
import com.bsec.oop.Fayshal.Investor.model.TransactionRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AccountStatement {

    // UID
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private Button fetchStatementButton;

    // Table
    @FXML
    private TableView<TransactionRecord> transactionTable;
    @FXML
    private TableColumn<TransactionRecord, LocalDate> dateColumn;
    @FXML
    private TableColumn<TransactionRecord, String> typeColumn;
    @FXML
    private TableColumn<TransactionRecord, String> descriptionColumn;
    @FXML
    private TableColumn<TransactionRecord, BigDecimal> debitColumn;
    @FXML
    private TableColumn<TransactionRecord, BigDecimal> creditColumn;
    @FXML
    private TableColumn<TransactionRecord, BigDecimal> balanceColumn;

    // DP
    @FXML
    private Button generateReportButton;

    // OP
    @FXML
    private TextArea previewArea;
    @FXML
    private Button downloadPdfButton;
    @FXML
    private Button downloadExcelButton;

    // System confirmation
    @FXML
    private Label statusLabel;

    // Data
    private final BalanceManager balanceManager = BalanceManager.getInstance();

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    public void initialize() {
        setupTable();
        setupDefaults();
        // initial fetch
        onFetchStatement(null);
        previewArea.setText("Preview will appear here after you generate the report.");
    }

    private void setupTable() {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("transactionType"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        debitColumn.setCellValueFactory(new PropertyValueFactory<>("debitAmount"));
        creditColumn.setCellValueFactory(new PropertyValueFactory<>("creditAmount"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balanceAfter"));

        // Friendly formatting for dates and money
        dateColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : DATE_FMT.format(item));
            }
        });
        setupMoneyColumn(debitColumn);
        setupMoneyColumn(creditColumn);
        setupMoneyColumn(balanceColumn);
    }

    private void setupMoneyColumn(TableColumn<TransactionRecord, BigDecimal> column) {
        column.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(BigDecimal item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("৳%.2f", item.setScale(2, RoundingMode.HALF_UP)));
                }
            }
        });
    }

    private void setupDefaults() {
        endDatePicker.setValue(LocalDate.now());
        startDatePicker.setValue(LocalDate.now().minusMonths(1));
        statusLabel.setText("");
    }

    @FXML
    private void onFetchStatement(ActionEvent ignored) {
        LocalDate from = startDatePicker.getValue();
        LocalDate to = endDatePicker.getValue();
        if (from == null || to == null) {
            showInfo("Please select both start and end dates.");
            return;
        }
        if (to.isBefore(from)) {
            showInfo("End date cannot be earlier than start date.");
            return;
        }
        List<TransactionRecord> filtered = filterByRange(from, to);
        refreshTable(filtered);
        statusLabel.setText("Request Logged Successfully");
    }

    @FXML
    private void onGenerateReport(ActionEvent ignored) {
        List<TransactionRecord> current = new ArrayList<>(transactionTable.getItems());
        if (current.isEmpty()) {
            previewArea.setText("No records to show for the selected period.");
            return;
        }
        String preview = buildReadableReport(current,
                startDatePicker.getValue(), endDatePicker.getValue());
        previewArea.setText(preview);
        statusLabel.setText("Statement report generated.");
    }

    private void refreshTable(List<TransactionRecord> records) {
        // Keep internal store as ArrayList; update TableView without creating ObservableArrayList explicitly
        transactionTable.getItems().setAll(records);
    }

    private List<TransactionRecord> filterByRange(LocalDate from, LocalDate to) {
        return TransactionLog.getInstance().getByDateRange(from, to).stream()
                // show only Buy and Sell history per request
                .filter(r -> {
                    String t = r.getTransactionType();
                    return "Buy".equalsIgnoreCase(t) || "Sell".equalsIgnoreCase(t);
                })
                .collect(Collectors.toList());
    }

    private String buildReadableReport(List<TransactionRecord> rows, LocalDate from, LocalDate to) {
        BigDecimal totalDebit = rows.stream()
                .map(TransactionRecord::getDebitAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalCredit = rows.stream()
                .map(TransactionRecord::getCreditAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal closing = rows.get(rows.size() - 1).getBalanceAfter();

        StringBuilder sb = new StringBuilder();
        sb.append("Account Statement\n");
        sb.append("Period: ").append(DATE_FMT.format(from)).append(" to ").append(DATE_FMT.format(to)).append('\n');
        sb.append("Total Debit: ").append(String.format("৳%.2f", totalDebit)).append('\n');
        sb.append("Total Credit: ").append(String.format("৳%.2f", totalCredit)).append('\n');
        sb.append("Closing Balance: ").append(String.format("৳%.2f", closing)).append("\n\n");

        String header = String.format("%-12s  %-16s  %-32s  %12s  %12s  %12s",
                "Date", "Type", "Description", "Debit", "Credit", "Balance");
        sb.append(header).append('\n');
        sb.append("-".repeat(header.length())).append('\n');
        for (TransactionRecord r : rows) {
            sb.append(String.format("%-12s  %-16s  %-32s  %12s  %12s  %12s",
                    r.getDate() == null ? "" : DATE_FMT.format(r.getDate()),
                    safe(r.getTransactionType()),
                    ellipsize(safe(r.getDescription()), 32),
                    money(r.getDebitAmount()),
                    money(r.getCreditAmount()),
                    money(r.getBalanceAfter()))).append('\n');
        }
        return sb.toString();
    }

    private String ellipsize(String text, int max) {
        if (text.length() <= max) return text;
        return text.substring(0, Math.max(0, max - 3)) + "...";
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }

    private String money(BigDecimal v) {
        if (v == null) return "";
        return String.format("৳%.2f", v.setScale(2, RoundingMode.HALF_UP));
    }

    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Info");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    public void goBackToMenu(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bsec/summer25section2/Fayshal/Investor/Menu_Investor.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            showInfo("Unable to open menu: " + e.getMessage());
        }
    }
}