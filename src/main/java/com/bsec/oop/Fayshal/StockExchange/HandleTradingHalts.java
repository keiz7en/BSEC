package com.bsec.oop.Fayshal.StockExchange;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HandleTradingHalts {

    @FXML
    private TextField reasonField;

    @FXML
    private ComboBox<String> guidelineCombo;

    @FXML
    private DatePicker haltDatePicker;

    @FXML
    private RadioButton fullHaltRadio;

    @FXML
    private RadioButton partialHaltRadio;

    @FXML
    private Button issueHaltButton;

    @FXML
    private TableView<TradingHalt> haltRecordsTable;

    @FXML
    private TableColumn<TradingHalt, String> haltIdColumn;

    @FXML
    private TableColumn<TradingHalt, String> reasonColumn;

    @FXML
    private TableColumn<TradingHalt, String> guidelineColumn;

    @FXML
    private TableColumn<TradingHalt, String> haltTypeColumn;

    @FXML
    private TableColumn<TradingHalt, LocalDate> haltDateColumn;

    @FXML
    private TableColumn<TradingHalt, String> statusColumn;

    private ToggleGroup haltTypeGroup;
    private List<TradingHalt> haltRecords;
    private int nextHaltId = 1;

    @FXML
    public void initialize() {
        setupUI();
        setupTableColumns();
        loadSampleData();
    }

    private void setupUI() {
        haltTypeGroup = new ToggleGroup();
        fullHaltRadio.setToggleGroup(haltTypeGroup);
        partialHaltRadio.setToggleGroup(haltTypeGroup);
        fullHaltRadio.setSelected(true);

        guidelineCombo.getItems().addAll(
                "Circuit Breaker Rule",
                "Exchange Policy",
                "SEC Rule 3b-16",
                "Market Volatility Rule",
                "News Pending Rule",
                "Regulatory Review",
                "Technical Issues"
        );

        haltDatePicker.setValue(LocalDate.now());

        haltRecords = new ArrayList<>();
    }

    private void setupTableColumns() {
        haltIdColumn.setCellValueFactory(new PropertyValueFactory<>("haltId"));
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));
        guidelineColumn.setCellValueFactory(new PropertyValueFactory<>("guideline"));
        haltTypeColumn.setCellValueFactory(new PropertyValueFactory<>("haltType"));
        haltDateColumn.setCellValueFactory(new PropertyValueFactory<>("haltDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadSampleData() {
        haltRecords.add(new TradingHalt("H001", "Excessive volatility detected", "Circuit Breaker Rule", "Full", LocalDate.of(2025, 1, 15), "Active"));
        haltRecords.add(new TradingHalt("H002", "Pending material news announcement", "News Pending Rule", "Partial", LocalDate.of(2025, 1, 14), "Resolved"));
        haltRecords.add(new TradingHalt("H003", "Technical system malfunction", "Exchange Policy", "Full", LocalDate.of(2025, 1, 13), "Resolved"));

        refreshTable();
    }

    @FXML
    public void issueHaltOrder(ActionEvent actionEvent) {
        if (validateInput()) {
            String reason = reasonField.getText().trim();
            String guideline = guidelineCombo.getValue();
            LocalDate haltDate = haltDatePicker.getValue();
            String haltType = getSelectedHaltType();

            String haltId = String.format("H%03d", nextHaltId++);

            TradingHalt newHalt = new TradingHalt(haltId, reason, guideline, haltType, haltDate, "Active");
            haltRecords.add(newHalt);

            refreshTable();
            clearFields();

            showAlert("Success", "Trading halt order issued successfully!\nHalt ID: " + haltId, Alert.AlertType.INFORMATION);
        }
    }

    private boolean validateInput() {
        if (reasonField.getText() == null || reasonField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Please enter a reason for the halt.", Alert.AlertType.ERROR);
            return false;
        }

        if (guidelineCombo.getValue() == null) {
            showAlert("Validation Error", "Please select a regulatory guideline.", Alert.AlertType.ERROR);
            return false;
        }

        if (haltDatePicker.getValue() == null) {
            showAlert("Validation Error", "Please select a halt date.", Alert.AlertType.ERROR);
            return false;
        }

        if (haltTypeGroup.getSelectedToggle() == null) {
            showAlert("Validation Error", "Please select a halt type.", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    private String getSelectedHaltType() {
        RadioButton selected = (RadioButton) haltTypeGroup.getSelectedToggle();
        return selected == fullHaltRadio ? "Full" : "Partial";
    }

    private void clearFields() {
        reasonField.clear();
        guidelineCombo.setValue(null);
        haltDatePicker.setValue(LocalDate.now());
        fullHaltRadio.setSelected(true);
    }

    private void refreshTable() {
        haltRecordsTable.getItems().clear();
        haltRecordsTable.getItems().addAll(haltRecords);
    }

    @FXML
    public void goBackToDashboard(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bsec/summer25section2/Fayshal/StockExchange/Main.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Stock Exchange Dashboard");
        } catch (IOException e) {
            showAlert("Error", "Could not load dashboard: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}