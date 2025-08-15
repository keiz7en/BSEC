package com.bsec.oop.Fayshal.Investor;

import com.bsec.oop.Fayshal.Investor.model.AlertTriggerHistory;
import com.bsec.oop.Fayshal.Investor.model.StockAlert;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class NotificationStocks {
    @FXML
    private TextField stockSymbolField;
    @FXML
    private ComboBox<String> alertTypeCombo;
    @FXML
    private TextField thresholdField;
    @FXML
    private ComboBox<String> notificationMethodCombo;
    @FXML
    private Button saveAlertButton;
    @FXML
    private TextArea monitoringTextArea;
    @FXML
    private TableView<AlertTriggerHistory> historyTable;
    @FXML
    private TableColumn<AlertTriggerHistory, String> dateTimeColumn;
    @FXML
    private TableColumn<AlertTriggerHistory, String> symbolColumn;
    @FXML
    private TableColumn<AlertTriggerHistory, String> conditionColumn;
    @FXML
    private TableColumn<AlertTriggerHistory, String> currentPriceColumn;
    @FXML
    private TableColumn<AlertTriggerHistory, String> statusColumn;
    @FXML
    private Button sendTestButton;
    @FXML
    private Button backButton;
    @FXML
    private DatePicker alertDatePicker;

    private final ArrayList<StockAlert> alertPreferences = new ArrayList<>();
    private final ArrayList<AlertTriggerHistory> historyRecords = new ArrayList<>();

    @FXML
    public void initialize() {
        alertTypeCombo.getItems().setAll("Price Above", "Price Below", "% Change", "Volume Spike");
        notificationMethodCombo.getItems().setAll("Email", "SMS", "Push Notification");
        dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        symbolColumn.setCellValueFactory(new PropertyValueFactory<>("stockSymbol"));
        conditionColumn.setCellValueFactory(new PropertyValueFactory<>("triggerCondition"));
        currentPriceColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.format(Locale.US, "%.2f", cell.getValue().getCurrentPrice())));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("notificationStatus"));
        refreshHistoryTable();
    }

    @FXML
    public void handleSavePreferences(ActionEvent actionEvent) {
        String symbol = stockSymbolField.getText() == null ? "" : stockSymbolField.getText().trim().toUpperCase(Locale.ROOT);
        String alertType = alertTypeCombo.getValue();
        String thresholdText = thresholdField.getText() == null ? "" : thresholdField.getText().trim();
        String method = notificationMethodCombo.getValue();
        if (symbol.isEmpty()) return;
        if (alertType == null || alertType.isEmpty()) return;
        if (method == null || method.isEmpty()) return;
        double threshold;
        try {
            threshold = Double.parseDouble(thresholdText);
        } catch (Exception ex) {
            return;
        }
        String createdAt;
        if (alertDatePicker != null && alertDatePicker.getValue() != null) {
            createdAt = alertDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " 00:00:00";
        } else {
            createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        StockAlert alert = new StockAlert(symbol, alertType, threshold, method, createdAt);
        alertPreferences.add(alert);
        appendMonitoring("Saved: " + symbol + " | " + alertType + " | " + threshold + " | " + method);
        stockSymbolField.clear();
        alertTypeCombo.getSelectionModel().clearSelection();
        thresholdField.clear();
        notificationMethodCombo.getSelectionModel().clearSelection();
        if (alertDatePicker != null) alertDatePicker.setValue(null);
    }

    @FXML
    public void handleSendTestNotification(ActionEvent actionEvent) {
        String symbol = stockSymbolField.getText() == null || stockSymbolField.getText().isBlank() ? "TEST" : stockSymbolField.getText().trim().toUpperCase(Locale.ROOT);
        String alertType = alertTypeCombo.getValue() == null ? "Test Trigger" : alertTypeCombo.getValue();
        String thresholdText = thresholdField.getText() == null || thresholdField.getText().isBlank() ? "0" : thresholdField.getText().trim();
        double currentPrice;
        try {
            double t = Double.parseDouble(thresholdText);
            currentPrice = t + Math.max(1d, t * 0.01d);
        } catch (Exception ex) {
            currentPrice = 1.0d;
        }
        String condition = alertType + " @ " + thresholdText;
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        AlertTriggerHistory row = new AlertTriggerHistory(ts, symbol, condition, currentPrice, "Sent");
        historyRecords.add(row);
        refreshHistoryTable();
        appendMonitoring("Test notification sent for " + symbol + " | " + condition);
    }

    @FXML
    public void goBackToMenu(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bsec/summer25section2/Fayshal/Investor/Menu_Investor.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refreshHistoryTable() {
        historyTable.setItems(FXCollections.observableList(historyRecords));
        historyTable.refresh();
    }

    private void appendMonitoring(String text) {
        if (monitoringTextArea.getText() == null || monitoringTextArea.getText().isEmpty()) {
            monitoringTextArea.setText(text);
        } else {
            monitoringTextArea.appendText("\n" + text);
        }
    }
}