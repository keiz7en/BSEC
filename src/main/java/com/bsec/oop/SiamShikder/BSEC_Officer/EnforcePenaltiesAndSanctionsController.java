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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EnforcePenaltiesAndSanctionsController {

    @FXML
    private TextField caseIdField;
    @FXML
    private ComboBox<String> violationTypeCombo;
    @FXML
    private DatePicker reviewDatePicker;
    @FXML
    private TextArea findingsArea;
    @FXML
    private Button recordPenaltyButton;
    @FXML
    private Button notifyButton;

    @FXML
    private TableView<PenaltyRecord> penaltyTable;
    @FXML
    private TableColumn<PenaltyRecord, String> colCaseId;
    @FXML
    private TableColumn<PenaltyRecord, String> colViolationType;
    @FXML
    private TableColumn<PenaltyRecord, String> colAmount;
    @FXML
    private TableColumn<PenaltyRecord, String> colStatus;

    private final List<PenaltyRecord> penalties = new ArrayList<>();

    @FXML
    private void initialize() {
        violationTypeCombo.getItems().setAll(
                "Market Manipulation", "Insider Trading", "Reporting Violation", "Other"
        );

        colCaseId.setCellValueFactory(new PropertyValueFactory<>("caseId"));
        colViolationType.setCellValueFactory(new PropertyValueFactory<>("violationType"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("penaltyAmount"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        penaltyTable.getSelectionModel().selectedItemProperty().addListener((obs, o, sel) -> {
            if (sel != null) {
                caseIdField.setText(sel.getCaseId());
                violationTypeCombo.setValue(sel.getViolationType());
                findingsArea.setText(sel.getFindings());
            }
        });

        refreshTable();
    }

    @FXML
    private void onRecordPenalty(ActionEvent e) {
        String id = safe(caseIdField.getText());
        String type = violationTypeCombo.getValue();
        String findings = safe(findingsArea.getText());
        if (id.isEmpty() || type == null || findings.isEmpty()) {
            showError("Missing Information", "Provide Case ID, Violation Type, and Findings.");
            return;
        }
        PenaltyRecord existing = findById(id);
        if (existing != null) {
            existing.setViolationType(type);
            existing.setFindings(findings);
            existing.setPenaltyAmount("Calculated");
            existing.setStatus("Recorded");
            penaltyTable.getSelectionModel().select(existing);
        } else {
            PenaltyRecord pr = new PenaltyRecord(id, type, "Calculated", "Recorded", findings);
            penalties.add(pr);
            penaltyTable.getSelectionModel().select(pr);
        }
        refreshTable();
        showInfo("Penalty Recorded", "Penalty recorded for case " + id + ".");
    }

    @FXML
    private void onNotify(ActionEvent e) {
        PenaltyRecord sel = penaltyTable.getSelectionModel().getSelectedItem();
        if (sel == null) sel = findById(safe(caseIdField.getText()));
        if (sel == null) {
            showError("No Selection", "Select a record or enter Case ID to notify.");
            return;
        }
        sel.setStatus("Notified");
        refreshTable();
        showInfo("Notification Sent", "Offending party notified for case " + sel.getCaseId() + ".");
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

    private PenaltyRecord findById(String id) {
        for (PenaltyRecord p : penalties) if (Objects.equals(p.getCaseId(), id)) return p;
        return null;
    }

    private void refreshTable() {
        penaltyTable.getItems().setAll(penalties);
        penaltyTable.refresh();
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
