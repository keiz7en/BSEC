package com.bsec.oop.SiamShikder.BSEC_Officer;

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

public class MonitorMarketComplianceController {

    @FXML
    private TextField reportIdField;
    @FXML
    private ComboBox<String> areaCombo;
    @FXML
    private DatePicker reportDatePicker;
    @FXML
    private Button analyzeButton;
    @FXML
    private Button issueWarningButton;

    @FXML
    private TableView<ComplianceReport> reportTable;
    @FXML
    private TableColumn<ComplianceReport, String> colReportId;
    @FXML
    private TableColumn<ComplianceReport, String> colArea;
    @FXML
    private TableColumn<ComplianceReport, String> colStatus;
    @FXML
    private TableColumn<ComplianceReport, String> colFollowUp;

    private final List<ComplianceReport> reports = new ArrayList<>();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    private void initialize() {
        areaCombo.getItems().setAll("Trading", "Disclosure", "Financial");

        colReportId.setCellValueFactory(new PropertyValueFactory<>("reportId"));
        colArea.setCellValueFactory(new PropertyValueFactory<>("area"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colFollowUp.setCellValueFactory(new PropertyValueFactory<>("followUpNeeded"));

        reportTable.getSelectionModel().selectedItemProperty().addListener((obs, o, sel) -> {
            if (sel != null) {
                reportIdField.setText(sel.getReportId());
                areaCombo.setValue(sel.getArea());
                reportDatePicker.setValue(sel.getReportDate());
            }
        });

        refreshTable();
    }

    @FXML
    private void onAnalyzeCompliance(ActionEvent e) {
        String id = safe(reportIdField.getText());
        String area = areaCombo.getValue();
        LocalDate date = reportDatePicker.getValue();
        if (id.isEmpty() || area == null || date == null) {
            showError("Missing Information", "Provide Report ID, Area, and Report Date.");
            return;
        }
        ComplianceReport existing = findById(id);
        if (existing != null) {
            existing.setArea(area);
            existing.setReportDate(date);
            existing.setStatus("Analyzed");
            existing.setFollowUpNeeded("No");
            reportTable.getSelectionModel().select(existing);
        } else {
            ComplianceReport cr = new ComplianceReport(id, area, "Analyzed", "No", date);
            reports.add(cr);
            reportTable.getSelectionModel().select(cr);
        }
        refreshTable();
        showInfo("Analysis Complete", "Compliance report " + id + " analyzed.");
    }

    @FXML
    private void onIssueWarning(ActionEvent e) {
        ComplianceReport sel = reportTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            String id = safe(reportIdField.getText());
            sel = findById(id);
        }
        if (sel == null) {
            showError("No Selection", "Select a report or enter a Report ID to issue a warning.");
            return;
        }
        sel.setStatus("Warning Issued");
        sel.setFollowUpNeeded("Yes");
        refreshTable();
        showInfo("Warning Issued", "Warning issued for report " + sel.getReportId() + ".");
    }

    @FXML
    private void goBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/bsec/summer25section2/SiamShikder/BSEC_Officer/Main.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("BSEC Officer Dashboard");
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
            showError("Navigation Error", "Unable to navigate back to menu.");
        }
    }

    private ComplianceReport findById(String id) {
        for (ComplianceReport r : reports) if (Objects.equals(r.getReportId(), id)) return r;
        return null;
    }

    private void refreshTable() {
        reportTable.getItems().setAll(reports);
        reportTable.refresh();
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
