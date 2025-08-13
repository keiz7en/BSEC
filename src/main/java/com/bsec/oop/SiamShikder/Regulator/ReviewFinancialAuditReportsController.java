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

public class ReviewFinancialAuditReportsController {

    @FXML
    private TextField companyNameField;
    @FXML
    private ComboBox<String> financialYearCombo;
    @FXML
    private DatePicker reportDatePicker;
    @FXML
    private RadioButton annualAuditRadio;
    @FXML
    private RadioButton quarterlyReviewRadio;
    @FXML
    private RadioButton specialInvestigationRadio;
    @FXML
    private Button loadReportButton;
    @FXML
    private TextArea discrepancyDetailsArea;
    @FXML
    private Button highlightButton;
    @FXML
    private Button issueNoticeButton;
    @FXML
    private Button submitFinalOpinionButton;

    @FXML
    private TableView<AuditSummaryItem> auditSummaryTable;
    @FXML
    private TableColumn<AuditSummaryItem, String> colSectionName;
    @FXML
    private TableColumn<AuditSummaryItem, String> colFindings;
    @FXML
    private TableColumn<AuditSummaryItem, String> colSeverity;
    @FXML
    private TableColumn<AuditSummaryItem, String> colStatus;

    private final ToggleGroup reportTypeToggleGroup = new ToggleGroup();
    private final List<AuditSummaryItem> auditSummaries = new ArrayList<>();

    @FXML
    private void initialize() {
        annualAuditRadio.setToggleGroup(reportTypeToggleGroup);
        quarterlyReviewRadio.setToggleGroup(reportTypeToggleGroup);
        specialInvestigationRadio.setToggleGroup(reportTypeToggleGroup);
        annualAuditRadio.setSelected(true);

        financialYearCombo.getItems().setAll("2022", "2023", "2024");

        colSectionName.setCellValueFactory(new PropertyValueFactory<>("sectionName"));
        colFindings.setCellValueFactory(new PropertyValueFactory<>("findings"));
        colSeverity.setCellValueFactory(new PropertyValueFactory<>("severity"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        auditSummaryTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, sel) -> {
            if (sel != null) {
                discrepancyDetailsArea.setText(sel.getFindings());
            }
        });

        refreshTable();
    }

    @FXML
    private void onLoadReport(ActionEvent event) {
        String company = safe(companyNameField.getText());
        String year = financialYearCombo.getValue();
        LocalDate date = reportDatePicker.getValue();
        String type = selectedReportType();
        if (company.isEmpty() || year == null || date == null || type.isEmpty()) {
            showError("Missing Information", "Provide Company Name, Financial Year, Submission Date, and Report Type.");
            return;
        }
        if (auditSummaries.isEmpty()) {
            auditSummaries.add(new AuditSummaryItem("Revenue Recognition", "Policy deviations observed", "Medium", "Pending"));
            auditSummaries.add(new AuditSummaryItem("Inventory Valuation", "FIFO vs weighted average inconsistency", "High", "Pending"));
            auditSummaries.add(new AuditSummaryItem("Related Party Transactions", "Disclosure adequate", "Low", "Resolved"));
            refreshTable();
        }
        showInfo("Report Loaded", "Loaded audit report for " + company + " (" + year + ")");
    }

    @FXML
    private void onHighlightDiscrepancies(ActionEvent event) {
        String details = safe(discrepancyDetailsArea.getText());
        if (details.isEmpty()) {
            showError("No Details", "Enter discrepancy details to highlight.");
            return;
        }
        auditSummaries.add(new AuditSummaryItem("Custom Note", details, "Medium", "Pending"));
        refreshTable();
        auditSummaryTable.getSelectionModel().selectLast();
    }

    @FXML
    private void onIssueComplianceNotice(ActionEvent event) {
        if (auditSummaries.isEmpty()) {
            showError("No Data", "No findings to issue a compliance notice.");
            return;
        }
        showInfo("Notice Issued", "Compliance notice drafted for current findings.");
    }

    @FXML
    private void onSubmitFinalOpinion(ActionEvent event) {
        if (auditSummaries.isEmpty()) {
            showError("No Data", "No findings to submit a final opinion.");
            return;
        }
        showInfo("Opinion Submitted", "Final opinion submitted.");
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
        auditSummaryTable.getItems().setAll(auditSummaries);
        auditSummaryTable.refresh();
    }

    private String selectedReportType() {
        if (annualAuditRadio.isSelected()) return "Annual Audit";
        if (quarterlyReviewRadio.isSelected()) return "Quarterly Review";
        if (specialInvestigationRadio.isSelected()) return "Special Investigation";
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
