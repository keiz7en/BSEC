package com.bsec.oop.Fayshal.Investor;

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

public class DepositFunds
{
    @FXML
    private Label currentBalanceLabel;
    @FXML
    private TextField depositAmountField;
    @FXML
    private ComboBox<String> paymentMethodCombo;
    @FXML
    private Label bankDetailsLabel;
    @FXML
    private TextField accountNumberField;
    @FXML
    private Label depositSummaryLabel;
    @FXML
    private Label newBalanceLabel;
    @FXML
    private Button depositBtn;
    @FXML
    private TextArea statusArea;

    private final BalanceManager balanceManager = BalanceManager.getInstance();

    @FXML
    public void initialize() {
        setupPaymentMethods();
        setupEventHandlers();
        refreshBalance();
        updateStatus("Welcome! Enter amount to deposit funds into your account.");
    }

    private void setupPaymentMethods() {
        paymentMethodCombo.getItems().addAll(
                "Mobile Banking (bKash)",
                "Mobile Banking (Nagad)",
                "Mobile Banking (Rocket)",
                "Bank Transfer",
                "Credit Card",
                "Debit Card"
        );
        paymentMethodCombo.setValue("Mobile Banking (bKash)");
    }

    private void setupEventHandlers() {
        // Amount field validation
        depositAmountField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*\\.?\\d*")) {
                depositAmountField.setText(newVal.replaceAll("[^\\d.]", ""));
            }
            calculateNewBalance();
        });

        // Payment method change handler
        paymentMethodCombo.setOnAction(e -> {
            String selected = paymentMethodCombo.getValue();
            boolean needsAccount = "Bank Transfer".equals(selected);
            bankDetailsLabel.setVisible(needsAccount);
            accountNumberField.setVisible(needsAccount);
            bankDetailsLabel.setText(needsAccount ? "Account Number:" : "");
        });
    }

    @FXML
    public void refreshBalance() {
        // Get balance from shared manager
        currentBalanceLabel.setText(balanceManager.getFormattedBalance());
        calculateNewBalance();
    }

    @FXML
    public void setAmount1000() {
        depositAmountField.setText("1000");
        calculateNewBalance();
    }

    @FXML
    public void setAmount5000() {
        depositAmountField.setText("5000");
        calculateNewBalance();
    }

    @FXML
    public void setAmount10000() {
        depositAmountField.setText("10000");
        calculateNewBalance();
    }

    @FXML
    public void setAmount50000() {
        depositAmountField.setText("50000");
        calculateNewBalance();
    }

    private void calculateNewBalance() {
        try {
            String amountStr = depositAmountField.getText().trim();
            if (amountStr.isEmpty()) {
                depositSummaryLabel.setText("৳0.00");
                newBalanceLabel.setText(balanceManager.getFormattedBalance());
                depositBtn.setDisable(true);
                return;
            }

            BigDecimal depositAmount = new BigDecimal(amountStr);
            if (depositAmount.compareTo(BigDecimal.ZERO) <= 0) {
                depositBtn.setDisable(true);
                updateStatus("Please enter a valid deposit amount greater than 0.");
                return;
            }

            BigDecimal newBalance = balanceManager.getCurrentBalance().add(depositAmount).setScale(2, RoundingMode.HALF_UP);

            depositSummaryLabel.setText(String.format("৳%.2f", depositAmount));
            newBalanceLabel.setText(String.format("৳%.2f", newBalance));
            depositBtn.setDisable(false);
            updateStatus("Deposit amount calculated. Ready to process deposit.");

        } catch (NumberFormatException e) {
            depositSummaryLabel.setText("৳0.00");
            newBalanceLabel.setText(balanceManager.getFormattedBalance());
            depositBtn.setDisable(true);
            updateStatus("Please enter a valid numeric amount.");
        }
    }

    @FXML
    public void processDeposit() {
        if (!validateDeposit()) {
            return;
        }
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Deposit");
        confirmAlert.setHeaderText("Deposit Confirmation");
        confirmAlert.setContentText(buildDepositSummary());

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            executeDeposit();
        }
    }

    private boolean validateDeposit() {
        if (depositAmountField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Please enter deposit amount.");
            return false;
        }

        if (paymentMethodCombo.getValue() == null) {
            showAlert("Validation Error", "Please select payment method.");
            return false;
        }

        if ("Bank Transfer".equals(paymentMethodCombo.getValue()) &&
                accountNumberField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Please enter account number for bank transfer.");
            return false;
        }

        try {
            BigDecimal amount = new BigDecimal(depositAmountField.getText().trim());
            if (amount.compareTo(new BigDecimal("10")) < 0) {
                showAlert("Validation Error", "Minimum deposit amount is ৳10.");
                return false;
            }
            if (amount.compareTo(new BigDecimal("1000000")) > 0) {
                showAlert("Validation Error", "Maximum deposit amount is ৳10,00,000.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Invalid deposit amount format.");
            return false;
        }

        return true;
    }

    private String buildDepositSummary() {
        String amount = depositAmountField.getText();
        String method = paymentMethodCombo.getValue();
        String newBalance = newBalanceLabel.getText();

        return String.format(
                "Deposit Details:\n\n" +
                        "Amount: ৳%s\n" +
                        "Payment Method: %s\n" +
                        "New Balance: %s\n\n" +
                        "Do you want to proceed with this deposit?",
                amount, method, newBalance
        );
    }

    private void executeDeposit() {
        try {
            updateStatus("Processing deposit... Please wait.");
            Thread.sleep(2000);
            BigDecimal depositAmount = new BigDecimal(depositAmountField.getText().trim());
            balanceManager.addFunds(depositAmount);
            TransactionLog.getInstance().logDeposit(java.time.LocalDate.now(),
                    depositAmount,
                    balanceManager.getCurrentBalance(),
                    paymentMethodCombo.getValue());
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Deposit Successful");
            successAlert.setHeaderText("Funds Deposited Successfully!");
            successAlert.setContentText(
                    String.format(
                            "Deposit completed successfully!\n\n" +
                                    "Transaction ID: #DEP%d\n" +
                                    "Amount Deposited: ৳%.2f\n" +
                                    "Payment Method: %s\n" +
                                    "New Balance: ৳%.2f\n\n" +
                                    "Funds are now available for trading.",
                            System.currentTimeMillis(),
                            depositAmount,
                            paymentMethodCombo.getValue(),
                            balanceManager.getCurrentBalance()
                    )
            );
            successAlert.showAndWait();
            clearForm();
            refreshBalance();
            updateStatus("Deposit successful! You can now make stock purchases.");

        } catch (InterruptedException e) {
            updateStatus("Deposit processing was interrupted. Please try again.");
        }
    }

    @FXML
    public void clearForm() {
        depositAmountField.clear();
        paymentMethodCombo.setValue("Mobile Banking (bKash)");
        accountNumberField.clear();
        bankDetailsLabel.setVisible(false);
        accountNumberField.setVisible(false);
        depositSummaryLabel.setText("৳0.00");
        newBalanceLabel.setText(balanceManager.getFormattedBalance());
        depositBtn.setDisable(true);
        updateStatus("Form cleared. Ready for new deposit.");
    }

    private void updateStatus(String message) {
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