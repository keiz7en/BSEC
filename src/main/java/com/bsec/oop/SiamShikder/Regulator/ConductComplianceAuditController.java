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

public class ConductComplianceAuditController {

    @FXML
    private TextField curriculumTitleField;
    @FXML
    private ComboBox<String> auditTypeCombo;
    @FXML
    private DatePicker sessionDatePicker;
    @FXML
    private RadioButton onlineRadio;
    @FXML
    private RadioButton onSiteRadio;
    @FXML
    private TextField participantsField;
    @FXML
    private TextArea overviewArea;
    @FXML
    private Button scheduleButton;
    @FXML
    private Button issueCertificatesButton;

    @FXML
    private TableView<AuditTrainingRecord> recordsTable;
    @FXML
    private TableColumn<AuditTrainingRecord, String> colTitle;
    @FXML
    private TableColumn<AuditTrainingRecord, String> colAuditType;
    @FXML
    private TableColumn<AuditTrainingRecord, String> colDate;
    @FXML
    private TableColumn<AuditTrainingRecord, String> colMode;
    @FXML
    private TableColumn<AuditTrainingRecord, String> colStatus;

    private final ToggleGroup modeToggleGroup = new ToggleGroup();
    private final List<AuditTrainingRecord> records = new ArrayList<>();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    private void initialize() {
        onlineRadio.setToggleGroup(modeToggleGroup);
        onSiteRadio.setToggleGroup(modeToggleGroup);
        onlineRadio.setSelected(true);

        auditTypeCombo.getItems().setAll(
                "Internal",
                "External",
                "Regulatory",
                "Risk-Based"
        );

        colTitle.setCellValueFactory(new PropertyValueFactory<>("curriculumTitle"));
        colAuditType.setCellValueFactory(new PropertyValueFactory<>("auditType"));
        colMode.setCellValueFactory(new PropertyValueFactory<>("mode"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDate.setCellValueFactory(cell -> {
            LocalDate d = cell.getValue().getDate();
            String display = d == null ? "" : d.format(dateFormatter);
            return new javafx.beans.property.SimpleStringProperty(display);
        });

        recordsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, sel) -> {
            if (sel != null) {
                curriculumTitleField.setText(sel.getCurriculumTitle());
                auditTypeCombo.setValue(sel.getAuditType());
                sessionDatePicker.setValue(sel.getDate());
                if (Objects.equals(sel.getMode(), "Online")) onlineRadio.setSelected(true);
                else onSiteRadio.setSelected(true);
                participantsField.setText(sel.getParticipants());
                overviewArea.setText(sel.getOverview());
            }
        });

        refreshTable();
    }

    @FXML
    private void onScheduleSession(ActionEvent event) {
        String title = safe(curriculumTitleField.getText());
        String auditType = auditTypeCombo.getValue();
        LocalDate date = sessionDatePicker.getValue();
        String mode = onlineRadio.isSelected() ? "Online" : "On-Site";
        String participants = safe(participantsField.getText());
        String overview = safe(overviewArea.getText());
        if (title.isEmpty() || auditType == null || date == null || participants.isEmpty()) {
            showError("Missing Information", "Provide Title, Audit Type, Session Date, and Number of Participants.");
            return;
        }
        if (!isInteger(participants)) {
            showError("Invalid Value", "Number of Participants must be an integer.");
            return;
        }
        AuditTrainingRecord existing = findByTitleAndDate(title, date);
        if (existing != null) {
            existing.setAuditType(auditType);
            existing.setMode(mode);
            existing.setStatus("Planned");
            existing.setParticipants(participants);
            existing.setOverview(overview);
            recordsTable.getSelectionModel().select(existing);
        } else {
            AuditTrainingRecord rec = new AuditTrainingRecord(title, auditType, date, mode, "Planned", participants, overview);
            records.add(rec);
            recordsTable.getSelectionModel().select(rec);
        }
        refreshTable();
    }

    @FXML
    private void onIssueCertificates(ActionEvent event) {
        if (records.isEmpty()) {
            showError("No Records", "No sessions available to issue certificates.");
            return;
        }
        AuditTrainingRecord sel = recordsTable.getSelectionModel().getSelectedItem();
        if (sel != null) {
            sel.setStatus("Completed");
            refreshTable();
            showInfo("Certificates Issued", "Certificates issued for: " + sel.getCurriculumTitle());
        } else {
            showInfo("Certificates", "Select a session to issue certificates.");
        }
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

    private AuditTrainingRecord findByTitleAndDate(String title, LocalDate date) {
        for (AuditTrainingRecord r : records) {
            if (Objects.equals(r.getCurriculumTitle(), title) && Objects.equals(r.getDate(), date)) return r;
        }
        return null;
    }

    private void refreshTable() {
        recordsTable.getItems().setAll(records);
        recordsTable.refresh();
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

    private static boolean isInteger(String v) {
        try {
            Long.parseLong(v);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static String safe(String v) {
        return v == null ? "" : v.trim();
    }
}
