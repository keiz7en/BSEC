package com.bsec.oop.Fayshal.Investor;

import com.bsec.oop.Fayshal.Investor.model.OrderHistoryRecord;
import com.bsec.oop.Fayshal.Investor.model.TransactionLog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import com.bsec.oop.Fayshal.Investor.model.TransactionLog;

import java.time.LocalDate;

public class StockPurchaseOrder
{
    @FXML
    private Label balanceLabel;
    @FXML
    private ComboBox<String> marketComboBox;
    @FXML
    private ComboBox<String> stockComboBox;
    @FXML
    private Label currentPriceLabel;
    @FXML
    private TextField quantityField;
    @FXML
    private ComboBox<String> orderTypeComboBox;
    @FXML
    private TextField priceLimitField;
    @FXML
    private Label totalCostLabel;
    @FXML
    private Label remainingBalanceLabel;
    @FXML
    private Button placeOrderBtn;
    @FXML
    private TextArea statusArea;

    // Order history (table below in FXML)
    @FXML
    private TableView<OrderHistoryRecord> orderHistoryTable;
    @FXML
    private TableColumn<OrderHistoryRecord, String> orderDateColumn;
    @FXML
    private TableColumn<OrderHistoryRecord, String> orderTimeColumn;
    @FXML
    private TableColumn<OrderHistoryRecord, String> orderSymbolColumn;
    @FXML
    private TableColumn<OrderHistoryRecord, String> orderTypeColumn;
    @FXML
    private TableColumn<OrderHistoryRecord, String> orderQuantityColumn;
    @FXML
    private TableColumn<OrderHistoryRecord, String> orderPriceColumn;
    @FXML
    private TableColumn<OrderHistoryRecord, String> orderAmountColumn;
    @FXML
    private TableColumn<OrderHistoryRecord, String> orderStatusColumn;
    @FXML
    private TableColumn<OrderHistoryRecord, String> orderFeesColumn;
    @FXML
    private TableColumn<OrderHistoryRecord, String> orderReferenceColumn;

    private BalanceManager balanceManager = BalanceManager.getInstance();
    private BigDecimal selectedStockPrice = BigDecimal.ZERO;
    private final java.util.ArrayList<OrderHistoryRecord> historyRecords = new java.util.ArrayList<>();

    @FXML
    public void initialize() {
        setupMarketComboBox();
        setupStockComboBox();
        setupOrderTypeComboBox();
        setupEventHandlers();
        setupOrderHistoryTable();
        balanceLabel.setText(balanceManager.getFormattedBalance());
        currentPriceLabel.setText("৳0.00");
        totalCostLabel.setText("৳0.00");
        remainingBalanceLabel.setText(balanceManager.getFormattedBalance());

        refreshBalance();
        updateStatusArea("Welcome! Please deposit funds and select a stock to start placing an order.");
    }

    private void setupMarketComboBox() {
        if (marketComboBox == null) return;
        marketComboBox.getItems().setAll(
                "DSE - Dhaka Stock Exchange (DSE)",
                "CSE - Chittagong Stock Exchange (CSE)"
        );
        marketComboBox.setValue("DSE - Dhaka Stock Exchange (DSE)");
    }

    private void setupStockComboBox() {
        stockComboBox.getItems().addAll(
                "GP - Grameenphone Ltd. (৳285.40)",
                "SQURPHARMA - Square Pharmaceuticals Ltd. (৳189.25)",
                "BEXIMCO - Beximco Pharmaceuticals Ltd. (৳125.80)",
                "WALTONHIL - Walton Hi-Tech Industries PLC (৳1,450.60)",
                "BRACBANK - BRAC Bank PLC (৳52.15)",
                "CITYBANK - City Bank PLC (৳28.90)",
                "OLYMPIC - Olympic Industries Ltd. (৳142.35)",
                "ACI - ACI Limited (৳158.70)",
                "RENATA - Renata PLC (৹1,285.25)",
                "BERGERPAINT - Berger Paints Bangladesh Ltd. (৳1,875.40)",
                "BATBC - British American Tobacco Bangladesh (৳485.90)",
                "POWERGRID - Power Grid Company of Bangladesh (৳67.30)",
                "LHBL - Lafarge Holcim Bangladesh Ltd. (৳58.45)",
                "ISLAMIBANK - Islami Bank Bangladesh PLC (৳42.80)",
                "DBBL - Dutch-Bangla Bank PLC (৳78.95)"
        );
    }

    private void setupOrderTypeComboBox() {
        orderTypeComboBox.getItems().addAll(
                "Market Order",
                "Limit Order",
                "Stop Order"
        );
        orderTypeComboBox.setValue("Market Order");
    }

    private void setupEventHandlers() {
        quantityField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                quantityField.setText(newVal.replaceAll("[^\\d]", ""));
            }
            calculateTotal();
        });

        priceLimitField.textProperty().addListener((obs, oldVal, newVal) -> {
            calculateTotal();
        });
    }

    private void setupOrderHistoryTable() {
        if (orderHistoryTable == null) return; 
        orderDateColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("date"));
        orderTimeColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("time"));
        orderSymbolColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("symbol"));
        orderTypeColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("type"));
        orderQuantityColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("quantity"));
        orderPriceColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("price"));
        orderAmountColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("totalAmount"));
        orderStatusColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("status"));
        orderFeesColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("fees"));
        orderReferenceColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("referenceId"));
        orderHistoryTable.getItems().clear();
    }

    @FXML
    public void refreshBalance() {
        // In real implementation, this would fetch from database
        balanceLabel.setText(balanceManager.getFormattedBalance());
        calculateTotal();
    }

    @FXML
    public void onStockSelected() {
        String selectedStock = stockComboBox.getValue();
        if (selectedStock != null) {

            String priceStr = selectedStock.substring(selectedStock.indexOf('৳') + 1, selectedStock.indexOf(')'));
            selectedStockPrice = new BigDecimal(priceStr.replace(",", ""));
            currentPriceLabel.setText(String.format("৳%.2f", selectedStockPrice));

            updateStatusArea("Stock selected: " + selectedStock.substring(0, selectedStock.indexOf(' ')));
            calculateTotal();
        }
    }

    @FXML
    public void calculateTotal() {
        try {
            String quantityStr = quantityField.getText().trim();
            if (quantityStr.isEmpty() || selectedStockPrice.equals(BigDecimal.ZERO)) {
                totalCostLabel.setText("৳0.00");
                remainingBalanceLabel.setText(balanceManager.getFormattedBalance());
                placeOrderBtn.setDisable(true);
                return;
            }

            int quantity = Integer.parseInt(quantityStr);
            BigDecimal price = selectedStockPrice;

            // Check if it's a limit order
            String orderType = orderTypeComboBox.getValue();
            if ("Limit Order".equals(orderType) && !priceLimitField.getText().trim().isEmpty()) {
                try {
                    price = new BigDecimal(priceLimitField.getText().trim());
                } catch (NumberFormatException e) {
                    updateStatusArea("Invalid price limit format. Using market price.");
                }
            }

            BigDecimal totalCost = price.multiply(new BigDecimal(quantity)).setScale(2, RoundingMode.HALF_UP);
            BigDecimal remainingBalance = balanceManager.getCurrentBalance().subtract(totalCost).setScale(2, RoundingMode.HALF_UP);

            totalCostLabel.setText(String.format("৳%.2f", totalCost));
            remainingBalanceLabel.setText(String.format("৳%.2f", remainingBalance));

            // Enable/disable place order button based on balance
            if (remainingBalance.compareTo(BigDecimal.ZERO) >= 0 && quantity > 0) {
                placeOrderBtn.setDisable(false);
                updateStatusArea("Order calculated successfully. Sufficient balance available.");
            } else {
                placeOrderBtn.setDisable(true);
                updateStatusArea("Insufficient balance for this order. Please reduce quantity or add funds.");
            }

        } catch (NumberFormatException e) {
            totalCostLabel.setText("৳0.00");
            remainingBalanceLabel.setText(balanceManager.getFormattedBalance());
            placeOrderBtn.setDisable(true);
            updateStatusArea("Please enter a valid quantity.");
        }
    }

    @FXML
    public void placeOrder() {
        if (!validateOrder()) {
            return;
        }


        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Order");
        confirmationAlert.setHeaderText("Order Confirmation");
        confirmationAlert.setContentText(buildOrderSummary());

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            processOrder();
        }
    }

    private boolean validateOrder() {
        if (stockComboBox.getValue() == null) {
            showAlert("Validation Error", "Please select a stock.");
            return false;
        }

        if (quantityField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Please enter quantity.");
            return false;
        }

        return true;
    }

    private String buildOrderSummary() {
        String stock = stockComboBox.getValue();
        String stockSymbol = stock.substring(0, stock.indexOf(' '));
        String quantity = quantityField.getText();
        String orderType = orderTypeComboBox.getValue();
        String totalCost = totalCostLabel.getText();

        return String.format(
                "Stock: %s\n" +
                        "Quantity: %s shares\n" +
                        "Order Type: %s\n" +
                        "Total Cost: %s\n\n" +
                        "Do you want to place this order?",
                stockSymbol, quantity, orderType, totalCost
        );
    }

    private void processOrder() {
        try {
            Thread.sleep(1000);
            BigDecimal orderCost = new BigDecimal(totalCostLabel.getText().substring(1));
            balanceManager.deductFunds(orderCost);
            String stock = stockComboBox.getValue();
            String stockSymbol = stock == null ? "" : stock.substring(0, stock.indexOf(' '));
            int quantity = Integer.parseInt(quantityField.getText().trim());

            // Log to shared statement as a Buy transaction (debit)
            TransactionLog.getInstance().logBuy(LocalDate.now(),
                    stockSymbol,
                    quantity,
                    orderCost,
                    balanceManager.getCurrentBalance());

            // Append to order history table (ArrayList + setAll; no ObservableArrayList usage)
            BigDecimal unitPrice = selectedStockPrice;
            if ("Limit Order".equals(orderTypeComboBox.getValue()) && !priceLimitField.getText().trim().isEmpty()) {
                try {
                    unitPrice = new BigDecimal(priceLimitField.getText().trim());
                } catch (Exception ignored) {
                }
            }

            String nowDate = java.time.LocalDate.now().toString();
            String nowTime = java.time.LocalTime.now().toString().substring(0, 8);
            String ref = "ORD" + System.currentTimeMillis();
            OrderHistoryRecord row = new OrderHistoryRecord(
                    nowDate,
                    nowTime,
                    stockSymbol,
                    "Buy",
                    String.valueOf(quantity),
                    String.format("৳%.2f", unitPrice),
                    totalCostLabel.getText(),
                    "Executed",
                    String.format("৳%.2f", BigDecimal.ZERO),
                    ref
            );
            historyRecords.add(row);
            if (orderHistoryTable != null) {
                orderHistoryTable.getItems().setAll(historyRecords);
            }

            // Show success message
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Order Placed");
            successAlert.setHeaderText("Success!");
            successAlert.setContentText(
                    "Your order has been placed successfully!\n\n" +
                            "Order ID: #" + System.currentTimeMillis() + "\n" +
                            "Status: Executed\n" +
                            "Updated Balance: ৳" + String.format("%.2f", balanceManager.getCurrentBalance())
            );
            successAlert.showAndWait();
            clearForm();
            refreshBalance();
            updateStatusArea("Order placed successfully! You can place another order.");

        } catch (InterruptedException e) {
            showAlert("Error", "Order processing was interrupted. Please try again.");
        }
    }

    @FXML
    public void clearForm() {
        stockComboBox.setValue(null);
        quantityField.clear();
        priceLimitField.clear();
        orderTypeComboBox.setValue("Market Order");
        currentPriceLabel.setText("৳0.00");
        totalCostLabel.setText("৳0.00");
        remainingBalanceLabel.setText(balanceManager.getFormattedBalance());
        selectedStockPrice = BigDecimal.ZERO;
        placeOrderBtn.setDisable(true);
        updateStatusArea("Form cleared. Ready for new order.");
    }

    private void updateStatusArea(String message) {
        statusArea.setText(java.time.LocalTime.now().toString().substring(0, 8) + " - " + message);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}