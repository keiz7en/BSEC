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

public class OverseeWhistleblowerController {

    @FXML
    private TextField reportIdField;
    @FXML
    private ComboBox<String> categoryCombo;
    @FXML
    private DatePicker dateReceivedPicker;
    @FXML
    private RadioButton highPriorityRadio;
    @FXML
    private RadioButton mediumPriorityRadio;
    @FXML
    private RadioButton lowPriorityRadio;
    @FXML
    private TextArea summaryArea;
    @FXML
    private Button verifyButton;
    @FXML
    private Button closeAndReportButton;

    @FXML
    private TableView<WhistleblowerCase> trackerTable;
    @FXML
    private TableColumn<WhistleblowerCase, String> colReportId;
    @FXML
    private TableColumn<WhistleblowerCase, String> colCategory;
    @FXML
    private TableColumn<WhistleblowerCase, String> colAssignedTeam;
    @FXML
    private TableColumn<WhistleblowerCase, String> colStatus;
    @FXML
    private TableColumn<WhistleblowerCase, String> colOutcome;

    private final ToggleGroup urgencyGroup = new ToggleGroup();
    private final List<WhistleblowerCase> cases = new ArrayList<>();

    @FXML
    private void initialize() {
        highPriorityRadio.setToggleGroup(urgencyGroup);
        mediumPriorityRadio.setToggleGroup(urgencyGroup);
        lowPriorityRadio.setToggleGroup(urgencyGroup);
        mediumPriorityRadio.setSelected(true);

        categoryCombo.getItems().setAll(
                "Fraud",
                "Market Manipulation",
                "Insider Trading",
                "Other"
        );

        colReportId.setCellValueFactory(new PropertyValueFactory<>("reportId"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colAssignedTeam.setCellValueFactory(new PropertyValueFactory<>("assignedTeam"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colOutcome.setCellValueFactory(new PropertyValueFactory<>("outcome"));

        trackerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, sel) -> {
            if (sel != null) {
                reportIdField.setText(sel.getReportId());
                categoryCombo.setValue(sel.getCategory());
                dateReceivedPicker.setValue(sel.getDateReceived());
                if (Objects.equals(sel.getUrgency(), "High Priority")) highPriorityRadio.setSelected(true);
                else if (Objects.equals(sel.getUrgency(), "Low Priority")) lowPriorityRadio.setSelected(true);
                else mediumPriorityRadio.setSelected(true);
                summaryArea.setText(sel.getSummary());
            }
        });

        refreshTable();
    }

    @FXML
    private void onVerifyReport(ActionEvent event) {
        String id = safe(reportIdField.getText());
        String cat = categoryCombo.getValue();
        LocalDate date = dateReceivedPicker.getValue();
        String urgency = selectedUrgency();
        String summary = safe(summaryArea.getText());
        if (id.isEmpty() || cat == null || date == null || summary.isEmpty()) {
            showError("Missing Information", "Provide Report ID, Category, Date Received, and Summary.");
            return;
        }
        WhistleblowerCase existing = findById(id);
        if (existing != null) {
            existing.setCategory(cat);
            existing.setDateReceived(date);
            existing.setUrgency(urgency);
            existing.setSummary(summary);
            if (existing.getStatus() == null || existing.getStatus().isEmpty()) existing.setStatus("Pending");
            if (existing.getAssignedTeam() == null || existing.getAssignedTeam().isEmpty())
                existing.setAssignedTeam("Unassigned");
            trackerTable.getSelectionModel().select(existing);
        } else {
            WhistleblowerCase wc = new WhistleblowerCase(id, cat, date, urgency, summary, "Unassigned", "Pending", "");
            cases.add(wc);
            trackerTable.getSelectionModel().select(wc);
        }
        refreshTable();
        showInfo("Report Verified", "Report " + id + " verified and queued for investigation.");
    }

    @FXML
    private void onCloseAndGenerateReport(ActionEvent event) {
        WhistleblowerCase sel = trackerTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            String id = safe(reportIdField.getText());
            sel = findById(id);
        }
        if (sel == null) {
            showError("No Selection", "Select a case or enter a Report ID to close.");
            return;
        }
        sel.setStatus("Closed");
        sel.setOutcome("Reported");
        refreshTable();
        showInfo("Case Closed", "Case for Report " + sel.getReportId() + " closed. Outcome: Reported.");
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

    private WhistleblowerCase findById(String id) {
        for (WhistleblowerCase w : cases) {
            if (Objects.equals(w.getReportId(), id)) return w;
        }
        return null;
    }

    private void refreshTable() {
        trackerTable.getItems().setAll(cases);
        trackerTable.refresh();
    }

    private String selectedUrgency() {
        if (highPriorityRadio.isSelected()) return "High Priority";
        if (lowPriorityRadio.isSelected()) return "Low Priority";
        return "Medium Priority";
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
