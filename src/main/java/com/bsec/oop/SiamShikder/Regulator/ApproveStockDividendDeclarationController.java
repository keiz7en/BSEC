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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ApproveStockDividendDeclarationController {

    @FXML
    private TextField companyNameField;
    @FXML
    private ComboBox<String> financialYearCombo;
    @FXML
    private DatePicker submissionDatePicker;
    @FXML
    private TextField dividendPercentField;
    @FXML
    private TextField totalSharesField;
    @FXML
    private TextArea notesArea;
    @FXML
    private Button validateButton;
    @FXML
    private Button approveButton;
    @FXML
    private Button requestChangesButton;
    @FXML
    private Button notifyButton;

    @FXML
    private TableView<DividendSummary> dividendTable;
    @FXML
    private TableColumn<DividendSummary, String> colCompanyName;
    @FXML
    private TableColumn<DividendSummary, String> colFinancialYear;
    @FXML
    private TableColumn<DividendSummary, String> colDividendPercent;
    @FXML
    private TableColumn<DividendSummary, String> colStatus;

    private final List<DividendSummary> summaries = new ArrayList<>();

    @FXML
    private void initialize() {
        financialYearCombo.getItems().setAll("2023", "2024");
        colCompanyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        colFinancialYear.setCellValueFactory(new PropertyValueFactory<>("financialYear"));
        colDividendPercent.setCellValueFactory(new PropertyValueFactory<>("dividendPercent"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        dividendTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, sel) -> {
            if (sel != null) {
                companyNameField.setText(sel.getCompanyName());
                financialYearCombo.setValue(sel.getFinancialYear());
                dividendPercentField.setText(sel.getDividendPercent());
                notesArea.setText(sel.getNotes());
                totalSharesField.setText(sel.getTotalShares());
            }
        });
        refreshTable();
    }

    @FXML
    private void onValidate(ActionEvent event) {
        String company = safe(companyNameField.getText());
        String year = financialYearCombo.getValue();
        LocalDate date = submissionDatePicker.getValue();
        String percent = safe(dividendPercentField.getText());
        String shares = safe(totalSharesField.getText());
        if (company.isEmpty() || year == null || date == null || percent.isEmpty() || shares.isEmpty()) {
            showError("Missing Information", "Provide Company Name, Year, Submission Date, Dividend %, and Total Shares.");
            return;
        }
        if (!isNumber(percent) || !isInteger(shares)) {
            showError("Invalid Values", "Dividend % must be numeric and Total Shares must be an integer.");
            return;
        }
        showInfo("Declaration Validated", "Declaration details look valid.");
    }

    @FXML
    private void onApprove(ActionEvent event) {
        upsertSummary("Approved");
    }

    @FXML
    private void onRequestChanges(ActionEvent event) {
        upsertSummary("Changes Requested");
    }

    @FXML
    private void onNotify(ActionEvent event) {
        if (summaries.isEmpty()) {
            showError("No Data", "No declaration to notify.");
            return;
        }
        showInfo("Notified", "Company and broker have been notified.");
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

    private void upsertSummary(String status) {
        String company = safe(companyNameField.getText());
        String year = financialYearCombo.getValue();
        LocalDate date = submissionDatePicker.getValue();
        String percent = safe(dividendPercentField.getText());
        String shares = safe(totalSharesField.getText());
        String notes = safe(notesArea.getText());
        if (company.isEmpty() || year == null || date == null || percent.isEmpty() || shares.isEmpty()) {
            showError("Missing Information", "Provide Company Name, Year, Submission Date, Dividend %, and Total Shares.");
            return;
        }
        if (!isNumber(percent) || !isInteger(shares)) {
            showError("Invalid Values", "Dividend % must be numeric and Total Shares must be an integer.");
            return;
        }
        DividendSummary existing = findByCompanyAndYear(company, year);
        if (existing != null) {
            existing.setDividendPercent(percent);
            existing.setStatus(status);
            existing.setTotalShares(shares);
            existing.setNotes(notes);
            dividendTable.getSelectionModel().select(existing);
        } else {
            DividendSummary s = new DividendSummary(company, year, percent, status, shares, notes);
            summaries.add(s);
            dividendTable.getSelectionModel().select(s);
        }
        refreshTable();
    }

    private DividendSummary findByCompanyAndYear(String company, String year) {
        for (DividendSummary s : summaries) {
            if (Objects.equals(s.getCompanyName(), company) && Objects.equals(s.getFinancialYear(), year)) return s;
        }
        return null;
    }

    private void refreshTable() {
        dividendTable.getItems().setAll(summaries);
        dividendTable.refresh();
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

    private static boolean isNumber(String v) {
        try {
            Double.parseDouble(v);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isInteger(String v) {
        try {
            Long.parseLong(v);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
