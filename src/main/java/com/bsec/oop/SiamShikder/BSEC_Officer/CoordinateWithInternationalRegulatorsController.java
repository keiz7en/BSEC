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

public class CoordinateWithInternationalRegulatorsController {

    @FXML
    private TextField agencyNameField;
    @FXML
    private ComboBox<String> meetingTypeCombo;
    @FXML
    private DatePicker meetingDatePicker;
    @FXML
    private TextArea agendaArea;
    @FXML
    private RadioButton draftRadio;
    @FXML
    private RadioButton finalizedRadio;

    @FXML
    private TableView<InternationalAgreement> agreementsTable;
    @FXML
    private TableColumn<InternationalAgreement, String> colAgencyName;
    @FXML
    private TableColumn<InternationalAgreement, String> colMeetingDate;
    @FXML
    private TableColumn<InternationalAgreement, String> colAgreementStatus;

    private final ToggleGroup statusGroup = new ToggleGroup();
    private final List<InternationalAgreement> agreements = new ArrayList<>();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    private void initialize() {
        draftRadio.setToggleGroup(statusGroup);
        finalizedRadio.setToggleGroup(statusGroup);
        draftRadio.setSelected(true);

        meetingTypeCombo.getItems().setAll("Virtual", "In-Person", "Hybrid");

        colAgencyName.setCellValueFactory(new PropertyValueFactory<>("agencyName"));
        colAgreementStatus.setCellValueFactory(new PropertyValueFactory<>("agreementStatus"));
        colMeetingDate.setCellValueFactory(cell -> {
            LocalDate d = cell.getValue().getMeetingDate();
            String display = d == null ? "" : d.format(dateFormatter);
            return new javafx.beans.property.SimpleStringProperty(display);
        });

        agreementsTable.getSelectionModel().selectedItemProperty().addListener((obs, o, sel) -> {
            if (sel != null) {
                agencyNameField.setText(sel.getAgencyName());
                meetingDatePicker.setValue(sel.getMeetingDate());
                if (Objects.equals(sel.getAgreementStatus(), "Finalized")) finalizedRadio.setSelected(true);
                else draftRadio.setSelected(true);
                agendaArea.setText(sel.getAgenda());
            }
        });

        refreshTable();
    }

    @FXML
    private void onDraftAgreement(ActionEvent e) {
        String agency = safe(agencyNameField.getText());
        String type = meetingTypeCombo.getValue();
        LocalDate date = meetingDatePicker.getValue();
        String status = finalizedRadio.isSelected() ? "Finalized" : "Draft";
        String agenda = safe(agendaArea.getText());
        if (agency.isEmpty() || type == null || date == null || agenda.isEmpty()) {
            showError("Missing Information", "Provide Agency, Meeting Type, Meeting Date, and Agenda.");
            return;
        }
        InternationalAgreement existing = findByAgency(agency);
        if (existing != null) {
            existing.setMeetingDate(date);
            existing.setAgreementStatus(status);
            existing.setAgenda(agenda);
            agreementsTable.getSelectionModel().select(existing);
        } else {
            InternationalAgreement ia = new InternationalAgreement(agency, date, status, agenda);
            agreements.add(ia);
            agreementsTable.getSelectionModel().select(ia);
        }
        refreshTable();
        showInfo("Agreement Updated", "Agreement with " + agency + " set to " + status + ".");
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

    private InternationalAgreement findByAgency(String agency) {
        for (InternationalAgreement a : agreements) if (Objects.equals(a.getAgencyName(), agency)) return a;
        return null;
    }

    private void refreshTable() {
        agreementsTable.getItems().setAll(agreements);
        agreementsTable.refresh();
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
