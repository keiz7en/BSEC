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

public class ConductRegulatoryInvestigationsController {

    @FXML
    private TextField caseIdField;
    @FXML
    private ComboBox<String> caseTypeCombo;
    @FXML
    private DatePicker complaintDatePicker;
    @FXML
    private RadioButton ongoingRadio;
    @FXML
    private RadioButton closedRadio;
    @FXML
    private TextArea detailsArea;

    @FXML
    private TableView<InvestigationCase> caseTable;
    @FXML
    private TableColumn<InvestigationCase, String> colCaseId;
    @FXML
    private TableColumn<InvestigationCase, String> colType;
    @FXML
    private TableColumn<InvestigationCase, String> colStatus;
    @FXML
    private TableColumn<InvestigationCase, String> colSubmissionDate;

    private final ToggleGroup statusGroup = new ToggleGroup();
    private final List<InvestigationCase> cases = new ArrayList<>();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    private void initialize() {
        ongoingRadio.setToggleGroup(statusGroup);
        closedRadio.setToggleGroup(statusGroup);
        ongoingRadio.setSelected(true);

        caseTypeCombo.getItems().setAll("Fraud", "Market Manipulation", "Insider Trading");

        colCaseId.setCellValueFactory(new PropertyValueFactory<>("caseId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("caseType"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colSubmissionDate.setCellValueFactory(cell -> {
            LocalDate d = cell.getValue().getSubmissionDate();
            String display = d == null ? "" : d.format(dateFormatter);
            return new javafx.beans.property.SimpleStringProperty(display);
        });

        caseTable.getSelectionModel().selectedItemProperty().addListener((obs, o, sel) -> {
            if (sel != null) {
                caseIdField.setText(sel.getCaseId());
                caseTypeCombo.setValue(sel.getCaseType());
                complaintDatePicker.setValue(sel.getSubmissionDate());
                if (Objects.equals(sel.getStatus(), "Closed")) closedRadio.setSelected(true);
                else ongoingRadio.setSelected(true);
                detailsArea.setText(sel.getDetails());
            }
        });

        refreshTable();
    }

    @FXML
    private void onGatherEvidence(ActionEvent e) {
        String id = safe(caseIdField.getText());
        String type = caseTypeCombo.getValue();
        LocalDate date = complaintDatePicker.getValue();
        String status = closedRadio.isSelected() ? "Closed" : "Ongoing";
        String details = safe(detailsArea.getText());
        if (id.isEmpty() || type == null || date == null || details.isEmpty()) {
            showError("Missing Information", "Provide Case ID, Case Type, Complaint Date, and Details.");
            return;
        }
        InvestigationCase existing = findById(id);
        if (existing != null) {
            existing.setCaseType(type);
            existing.setSubmissionDate(date);
            existing.setStatus(status);
            existing.setDetails(details);
            caseTable.getSelectionModel().select(existing);
        } else {
            InvestigationCase ic = new InvestigationCase(id, type, status, date, details);
            cases.add(ic);
            caseTable.getSelectionModel().select(ic);
        }
        refreshTable();
        showInfo("Evidence Gathered", "Evidence recorded for case " + id + ".");
    }

    @FXML
    private void onSubmitReport(ActionEvent e) {
        InvestigationCase sel = caseTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            String id = safe(caseIdField.getText());
            sel = findById(id);
        }
        if (sel == null) {
            showError("No Selection", "Select a case or enter a Case ID to submit report.");
            return;
        }
        sel.setStatus("Closed");
        refreshTable();
        showInfo("Report Submitted", "Report submitted for case " + sel.getCaseId() + ".");
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

    private InvestigationCase findById(String id) {
        for (InvestigationCase c : cases) if (Objects.equals(c.getCaseId(), id)) return c;
        return null;
    }

    private void refreshTable() {
        caseTable.getItems().setAll(cases);
        caseTable.refresh();
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
