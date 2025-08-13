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

public class ManageRegulatoryTrainingProgramsController {

    @FXML
    private TextField trainingIdField;
    @FXML
    private ComboBox<String> trainingTypeCombo;
    @FXML
    private DatePicker trainingDatePicker;
    @FXML
    private TextArea curriculumArea;
    @FXML
    private RadioButton scheduledRadio;
    @FXML
    private RadioButton completedRadio;

    @FXML
    private TableView<TrainingRecord> trainingTable;
    @FXML
    private TableColumn<TrainingRecord, String> colTrainingId;
    @FXML
    private TableColumn<TrainingRecord, String> colType;
    @FXML
    private TableColumn<TrainingRecord, String> colDate;
    @FXML
    private TableColumn<TrainingRecord, String> colStatus;

    private final ToggleGroup statusGroup = new ToggleGroup();
    private final List<TrainingRecord> records = new ArrayList<>();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    private void initialize() {
        scheduledRadio.setToggleGroup(statusGroup);
        completedRadio.setToggleGroup(statusGroup);
        scheduledRadio.setSelected(true);

        trainingTypeCombo.getItems().setAll("Compliance", "Market Surveillance", "Legal", "Other");

        colTrainingId.setCellValueFactory(new PropertyValueFactory<>("trainingId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDate.setCellValueFactory(cell -> {
            LocalDate d = cell.getValue().getDate();
            String display = d == null ? "" : d.format(dateFormatter);
            return new javafx.beans.property.SimpleStringProperty(display);
        });

        trainingTable.getSelectionModel().selectedItemProperty().addListener((obs, o, sel) -> {
            if (sel != null) {
                trainingIdField.setText(sel.getTrainingId());
                trainingTypeCombo.setValue(sel.getType());
                trainingDatePicker.setValue(sel.getDate());
                if (Objects.equals(sel.getStatus(), "Completed")) completedRadio.setSelected(true);
                else scheduledRadio.setSelected(true);
                curriculumArea.setText(sel.getCurriculum());
            }
        });

        refreshTable();
    }

    @FXML
    private void onAssessParticipants(ActionEvent e) {
        String id = safe(trainingIdField.getText());
        String type = trainingTypeCombo.getValue();
        LocalDate date = trainingDatePicker.getValue();
        String status = completedRadio.isSelected() ? "Completed" : "Scheduled";
        String curriculum = safe(curriculumArea.getText());
        if (id.isEmpty() || type == null || date == null || curriculum.isEmpty()) {
            showError("Missing Information", "Provide Training ID, Type, Date, and Curriculum.");
            return;
        }
        TrainingRecord existing = findById(id);
        if (existing != null) {
            existing.setType(type);
            existing.setDate(date);
            existing.setStatus(status);
            existing.setCurriculum(curriculum);
            trainingTable.getSelectionModel().select(existing);
        } else {
            TrainingRecord tr = new TrainingRecord(id, type, date, status, curriculum);
            records.add(tr);
            trainingTable.getSelectionModel().select(tr);
        }
        refreshTable();
        showInfo("Assessment Recorded", "Assessment recorded for training " + id + ".");
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

    private TrainingRecord findById(String id) {
        for (TrainingRecord r : records) if (Objects.equals(r.getTrainingId(), id)) return r;
        return null;
    }

    private void refreshTable() {
        trainingTable.getItems().setAll(records);
        trainingTable.refresh();
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
