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

public class OverseeWhistleblowerProgramController {

    @FXML
    private TextField caseIdField;
    @FXML
    private ComboBox<String> caseTypeCombo;
    @FXML
    private DatePicker reportDatePicker;
    @FXML
    private TextArea detailsArea;
    @FXML
    private RadioButton ongoingRadio;
    @FXML
    private RadioButton closedRadio;

    @FXML
    private TableView<OfficerWhistleblowerCase> casesTable;
    @FXML
    private TableColumn<OfficerWhistleblowerCase, String> colCaseId;
    @FXML
    private TableColumn<OfficerWhistleblowerCase, String> colType;
    @FXML
    private TableColumn<OfficerWhistleblowerCase, String> colStatus;
    @FXML
    private TableColumn<OfficerWhistleblowerCase, String> colClosureDate;

    private final ToggleGroup statusGroup = new ToggleGroup();
    private final List<OfficerWhistleblowerCase> cases = new ArrayList<>();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    private void initialize() {
        ongoingRadio.setToggleGroup(statusGroup);
        closedRadio.setToggleGroup(statusGroup);
        ongoingRadio.setSelected(true);

        caseTypeCombo.getItems().setAll("Fraud", "Insider Trading", "Market Manipulation", "Other");

        colCaseId.setCellValueFactory(new PropertyValueFactory<>("caseId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colClosureDate.setCellValueFactory(cell -> {
            LocalDate d = cell.getValue().getClosureDate();
            String display = d == null ? "" : d.format(dateFormatter);
            return new javafx.beans.property.SimpleStringProperty(display);
        });

        casesTable.getSelectionModel().selectedItemProperty().addListener((obs, o, sel) -> {
            if (sel != null) {
                caseIdField.setText(sel.getCaseId());
                caseTypeCombo.setValue(sel.getType());
                reportDatePicker.setValue(sel.getReportDate());
                if (Objects.equals(sel.getStatus(), "Closed")) closedRadio.setSelected(true);
                else ongoingRadio.setSelected(true);
                detailsArea.setText(sel.getDetails());
            }
        });

        refreshTable();
    }

    @FXML
    private void onAssignInvestigation(ActionEvent e) {
        String id = safe(caseIdField.getText());
        String type = caseTypeCombo.getValue();
        LocalDate date = reportDatePicker.getValue();
        String status = closedRadio.isSelected() ? "Closed" : "Ongoing";
        String details = safe(detailsArea.getText());
        if (id.isEmpty() || type == null || date == null || details.isEmpty()) {
            showError("Missing Information", "Provide Case ID, Type, Date, and Details.");
            return;
        }
        OfficerWhistleblowerCase existing = findById(id);
        if (existing != null) {
            existing.setType(type);
            existing.setReportDate(date);
            existing.setStatus(status);
            existing.setDetails(details);
            if ("Closed".equals(status)) existing.setClosureDate(LocalDate.now());
            else existing.setClosureDate(null);
            casesTable.getSelectionModel().select(existing);
        } else {
            LocalDate closure = "Closed".equals(status) ? LocalDate.now() : null;
            OfficerWhistleblowerCase c = new OfficerWhistleblowerCase(id, type, status, date, closure, details);
            cases.add(c);
            casesTable.getSelectionModel().select(c);
        }
        refreshTable();
        showInfo("Assignment Recorded", "Investigation assignment recorded for case " + id + ".");
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

    private OfficerWhistleblowerCase findById(String id) {
        for (OfficerWhistleblowerCase c : cases) if (Objects.equals(c.getCaseId(), id)) return c;
        return null;
    }

    private void refreshTable() {
        casesTable.getItems().setAll(cases);
        casesTable.refresh();
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
