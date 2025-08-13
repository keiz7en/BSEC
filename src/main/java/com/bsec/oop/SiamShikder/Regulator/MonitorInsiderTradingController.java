package com.bsec.oop.SiamShikder.Regulator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MonitorInsiderTradingController {

    @FXML
    private TextField stockSymbolField;
    @FXML
    private ComboBox<String> monitoringPeriodCombo;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private RadioButton lowRadio;
    @FXML
    private RadioButton mediumRadio;
    @FXML
    private RadioButton highRadio;
    @FXML
    private TextArea patternDetailsArea;
    @FXML
    private Button matchProfilesButton;
    @FXML
    private Button flagViolationButton;
    @FXML
    private Button generateReportButton;

    @FXML
    private TableView<SuspiciousActivity> suspiciousTable;
    @FXML
    private TableColumn<SuspiciousActivity, String> colDate;
    @FXML
    private TableColumn<SuspiciousActivity, String> colStockSymbol;
    @FXML
    private TableColumn<SuspiciousActivity, String> colInsiderName;
    @FXML
    private TableColumn<SuspiciousActivity, String> colViolationType;
    @FXML
    private TableColumn<SuspiciousActivity, String> colStatus;

    private final ToggleGroup severityToggleGroup = new ToggleGroup();
    private final List<SuspiciousActivity> activities = new ArrayList<>();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    private void initialize() {
        lowRadio.setToggleGroup(severityToggleGroup);
        mediumRadio.setToggleGroup(severityToggleGroup);
        highRadio.setToggleGroup(severityToggleGroup);
        mediumRadio.setSelected(true);

        monitoringPeriodCombo.getItems().setAll(
                "Last 7 Days",
                "Last 30 Days",
                "Last 6 Months"
        );

        colStockSymbol.setCellValueFactory(new PropertyValueFactory<>("stockSymbol"));
        colInsiderName.setCellValueFactory(new PropertyValueFactory<>("insiderName"));
        colViolationType.setCellValueFactory(new PropertyValueFactory<>("violationType"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDate.setCellValueFactory(cell -> {
            LocalDate date = cell.getValue().getDate();
            String display = date == null ? "" : date.format(dateFormatter);
            return new javafx.beans.property.SimpleStringProperty(display);
        });

        suspiciousTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, sel) -> {
            if (sel != null) {
                stockSymbolField.setText(sel.getStockSymbol());
                patternDetailsArea.setText(sel.getDetails());
                String sev = sel.getStatus();
                if (Objects.equals(sev, "Low")) lowRadio.setSelected(true);
                else if (Objects.equals(sev, "Medium")) mediumRadio.setSelected(true);
                else if (Objects.equals(sev, "High")) highRadio.setSelected(true);
            }
        });

        refreshTable();
    }

    @FXML
    private void onMatchProfiles(ActionEvent event) {
        String symbol = safe(stockSymbolField.getText());
        String period = monitoringPeriodCombo.getValue();
        LocalDate start = startDatePicker.getValue();
        LocalDate end = endDatePicker.getValue();
        if (symbol.isEmpty() || period == null || start == null || end == null) {
            showError("Missing Information", "Provide Stock Symbol, Monitoring Period, Start and End Dates.");
            return;
        }
        showInfo("Profile Matching", "Matching potential insiders for " + symbol + " in period " + period + ".");
    }

    @FXML
    private void onFlagViolation(ActionEvent event) {
        String symbol = safe(stockSymbolField.getText());
        LocalDate start = startDatePicker.getValue();
        LocalDate end = endDatePicker.getValue();
        String severity = selectedSeverity();
        String details = safe(patternDetailsArea.getText());
        if (symbol.isEmpty() || start == null || end == null || severity.isEmpty() || details.isEmpty()) {
            showError("Missing Information", "Provide Stock Symbol, Dates, Severity, and Details.");
            return;
        }
        SuspiciousActivity activity = new SuspiciousActivity(LocalDate.now(), symbol, "Unknown", severity, "Under Review", details);
        activities.add(activity);
        refreshTable();
        suspiciousTable.getSelectionModel().select(activity);
    }

    @FXML
    private void onGenerateReport(ActionEvent event) {
        if (activities.isEmpty()) {
            showError("No Data", "No suspicious activities to report.");
            return;
        }
        showInfo("Report Generated", "Report generated for " + activities.size() + " activities.");
    }

    @FXML
    private void goBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/bsec/summer25section2/SiamShikder/Regulator/Menu_Regulator.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Regulator Menu");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showError("Navigation Error", "Unable to navigate back to the menu.");
        }
    }

    private void refreshTable() {
        suspiciousTable.getItems().setAll(activities);
        suspiciousTable.refresh();
    }

    private String selectedSeverity() {
        if (lowRadio.isSelected()) return "Low";
        if (mediumRadio.isSelected()) return "Medium";
        if (highRadio.isSelected()) return "High";
        return "";
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static String safe(String v) {
        return v == null ? "" : v.trim();
    }
}
