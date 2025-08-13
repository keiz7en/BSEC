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

public class SuspendCompanyListingController {

    @FXML
    private TextField companyIdField;
    @FXML
    private ComboBox<String> companyNameCombo;
    @FXML
    private DatePicker violationDatePicker;
    @FXML
    private RadioButton rbFinancialMisconduct;
    @FXML
    private RadioButton rbRegulatoryBreach;
    @FXML
    private RadioButton rbMarketManipulation;
    @FXML
    private RadioButton rbOther;
    @FXML
    private TextArea evidenceSummaryArea;
    @FXML
    private Button reviewEvidenceButton;
    @FXML
    private Button triggerSuspensionButton;

    @FXML
    private TableView<SuspensionRecord> suspensionTable;
    @FXML
    private TableColumn<SuspensionRecord, String> colCompanyId;
    @FXML
    private TableColumn<SuspensionRecord, String> colCompanyName;
    @FXML
    private TableColumn<SuspensionRecord, String> colViolationType;
    @FXML
    private TableColumn<SuspensionRecord, String> colSuspensionDate;
    @FXML
    private TableColumn<SuspensionRecord, String> colCurrentStatus;

    private final ToggleGroup violationToggleGroup = new ToggleGroup();
    private final List<SuspensionRecord> suspensionRecords = new ArrayList<>();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    private void initialize() {
        rbFinancialMisconduct.setToggleGroup(violationToggleGroup);
        rbRegulatoryBreach.setToggleGroup(violationToggleGroup);
        rbMarketManipulation.setToggleGroup(violationToggleGroup);
        rbOther.setToggleGroup(violationToggleGroup);
        rbFinancialMisconduct.setSelected(true);

        companyNameCombo.getItems().setAll(
                "Apex Industries Ltd.",
                "BlueOcean Technologies PLC",
                "Crescent Foods Co.",
                "Delta Energy Holdings",
                "Evergreen Textiles"
        );

        colCompanyId.setCellValueFactory(new PropertyValueFactory<>("companyId"));
        colCompanyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        colViolationType.setCellValueFactory(new PropertyValueFactory<>("violationType"));
        colCurrentStatus.setCellValueFactory(new PropertyValueFactory<>("currentStatus"));
        colSuspensionDate.setCellValueFactory(cell -> {
            LocalDate date = cell.getValue().getSuspensionDate();
            String display = date == null ? "" : date.format(dateFormatter);
            return new javafx.beans.property.SimpleStringProperty(display);
        });

        suspensionTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, sel) -> {
            if (sel != null) {
                companyIdField.setText(sel.getCompanyId());
                companyNameCombo.setValue(sel.getCompanyName());
                violationDatePicker.setValue(sel.getSuspensionDate());
                String v = sel.getViolationType();
                if (Objects.equals(v, "Financial Misconduct")) rbFinancialMisconduct.setSelected(true);
                else if (Objects.equals(v, "Regulatory Breach")) rbRegulatoryBreach.setSelected(true);
                else if (Objects.equals(v, "Market Manipulation")) rbMarketManipulation.setSelected(true);
                else rbOther.setSelected(true);
                evidenceSummaryArea.setText(sel.getEvidenceSummary());
            }
        });

        refreshTable();
    }

    @FXML
    private void onReviewEvidence(ActionEvent event) {
        String id = safe(companyIdField.getText());
        String name = companyNameCombo.getValue();
        LocalDate date = violationDatePicker.getValue();
        String summary = safe(evidenceSummaryArea.getText());
        if (id.isEmpty() || name == null || date == null || summary.isEmpty()) {
            showError("Missing Information", "Provide Company ID, Name, Detection Date, and Evidence Summary.");
            return;
        }
        showInfo("Evidence Review", "Evidence captured for " + name + " on " + date.format(dateFormatter) + ".");
    }

    @FXML
    private void onTriggerSuspension(ActionEvent event) {
        String id = safe(companyIdField.getText());
        String name = companyNameCombo.getValue();
        LocalDate date = violationDatePicker.getValue();
        String type = selectedViolationType();
        String summary = safe(evidenceSummaryArea.getText());
        if (id.isEmpty() || name == null || date == null || type.isEmpty()) {
            showError("Missing Information", "Provide Company ID, Name, Detection Date, and Violation Type.");
            return;
        }
        SuspensionRecord existing = findByCompanyId(id);
        if (existing != null) {
            existing.setCompanyName(name);
            existing.setViolationType(type);
            existing.setSuspensionDate(date);
            existing.setCurrentStatus("Suspended");
            existing.setEvidenceSummary(summary);
        } else {
            SuspensionRecord record = new SuspensionRecord(id, name, type, date, "Suspended", summary);
            suspensionRecords.add(record);
        }
        refreshTable();
        selectByCompanyId(id);
    }

    @FXML
    private void goBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/bsec/oop/SiamShikder/Regulator/Menu_Regulator.fxml"));
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
        suspensionTable.getItems().setAll(suspensionRecords);
        suspensionTable.refresh();
    }

    private SuspensionRecord findByCompanyId(String id) {
        for (SuspensionRecord r : suspensionRecords) {
            if (Objects.equals(r.getCompanyId(), id)) return r;
        }
        return null;
    }

    private void selectByCompanyId(String id) {
        for (SuspensionRecord r : suspensionRecords) {
            if (Objects.equals(r.getCompanyId(), id)) {
                suspensionTable.getSelectionModel().select(r);
                break;
            }
        }
    }

    private String selectedViolationType() {
        if (rbFinancialMisconduct.isSelected()) return "Financial Misconduct";
        if (rbRegulatoryBreach.isSelected()) return "Regulatory Breach";
        if (rbMarketManipulation.isSelected()) return "Market Manipulation";
        if (rbOther.isSelected()) return "Other";
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
