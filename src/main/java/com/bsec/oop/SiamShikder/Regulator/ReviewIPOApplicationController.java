package com.bsec.oop.SiamShikder.Regulator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.Objects;
import java.util.Optional;

public class ReviewIPOApplicationController {

    // Form controls
    @FXML
    private TextField applicationIdField;
    @FXML
    private ComboBox<String> companyNameCombo;
    @FXML
    private DatePicker submissionDatePicker;
    @FXML
    private RadioButton compliantRadio;
    @FXML
    private RadioButton nonCompliantRadio;
    @FXML
    private TextField reviewerNameField;
    @FXML
    private TextArea commentsArea;
    @FXML
    private Button approveButton;
    @FXML
    private Button rejectButton;


    @FXML
    private TableView<IPOApplication> applicationsTable;
    @FXML
    private TableColumn<IPOApplication, String> colAppId;
    @FXML
    private TableColumn<IPOApplication, String> colCompanyName;
    @FXML
    private TableColumn<IPOApplication, String> colSubmissionDate;
    @FXML
    private TableColumn<IPOApplication, String> colComplianceStatus;
    @FXML
    private TableColumn<IPOApplication, String> colReviewDecision;

    private final ObservableList<IPOApplication> applicationData = FXCollections.observableArrayList();
    private final ToggleGroup complianceToggleGroup = new ToggleGroup();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    private void initialize() {
        compliantRadio.setToggleGroup(complianceToggleGroup);
        nonCompliantRadio.setToggleGroup(complianceToggleGroup);
        compliantRadio.setSelected(true);

        companyNameCombo.setItems(FXCollections.observableArrayList(
                "Apex Industries Ltd.",
                "BlueOcean Technologies PLC",
                "Crescent Foods Co.",
                "Delta Energy Holdings",
                "Evergreen Textiles"
        ));

        colAppId.setCellValueFactory(new PropertyValueFactory<>("applicationId"));
        colCompanyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        colComplianceStatus.setCellValueFactory(new PropertyValueFactory<>("complianceStatus"));
        colReviewDecision.setCellValueFactory(new PropertyValueFactory<>("reviewDecision"));


        colSubmissionDate.setCellValueFactory(cell -> {
            LocalDate date = cell.getValue().getSubmissionDate();
            String display = date == null ? "" : date.format(dateFormatter);
            return new javafx.beans.property.SimpleStringProperty(display);
        });

        applicationsTable.setItems(applicationData);


        applicationsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                applicationIdField.setText(newSel.getApplicationId());
                companyNameCombo.setValue(newSel.getCompanyName());
                submissionDatePicker.setValue(newSel.getSubmissionDate());
                if (Objects.equals(newSel.getComplianceStatus(), "Compliant")) {
                    compliantRadio.setSelected(true);
                } else {
                    nonCompliantRadio.setSelected(true);
                }
                reviewerNameField.setText(newSel.getReviewerName());
                commentsArea.setText(newSel.getComments());
            }
        });
    }

    @FXML
    private void onApprove(ActionEvent event) {
        upsertFromForm("Approved");
    }

    @FXML
    private void onReject(ActionEvent event) {
        upsertFromForm("Rejected");
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

    private void upsertFromForm(String decision) {
        String appId = safeTrim(applicationIdField.getText());
        String company = companyNameCombo.getValue();
        LocalDate submitDate = submissionDatePicker.getValue();
        String reviewer = safeTrim(reviewerNameField.getText());
        String notes = Optional.ofNullable(commentsArea.getText()).orElse("");
        String compliance = compliantRadio.isSelected() ? "Compliant" : "Non-Compliant";

        if (appId.isEmpty() || company == null || submitDate == null || reviewer.isEmpty()) {
            showError(
                    "Missing Information",
                    "Please provide Application ID, Company Name, Submission Date, and Reviewer Name."
            );
            return;
        }

        IPOApplication selected = applicationsTable.getSelectionModel().getSelectedItem();

        if (selected != null && appId.equals(selected.getApplicationId())) {

            selected.setCompanyName(company);
            selected.setSubmissionDate(submitDate);
            selected.setComplianceStatus(compliance);
            selected.setReviewDecision(decision);
            selected.setReviewerName(reviewer);
            selected.setComments(notes);
            applicationsTable.refresh();
        } else {
            // Try to find existing by ID; if found, update; else create
            IPOApplication existing = applicationData.stream()
                    .filter(item -> appId.equals(item.getApplicationId()))
                    .findFirst()
                    .orElse(null);
            if (existing != null) {
                existing.setCompanyName(company);
                existing.setSubmissionDate(submitDate);
                existing.setComplianceStatus(compliance);
                existing.setReviewDecision(decision);
                existing.setReviewerName(reviewer);
                existing.setComments(notes);
                applicationsTable.getSelectionModel().select(existing);
                applicationsTable.refresh();
            } else {
                IPOApplication created = new IPOApplication(
                        appId,
                        company,
                        submitDate,
                        compliance,
                        decision,
                        reviewer,
                        notes
                );
                applicationData.add(created);
                applicationsTable.getSelectionModel().select(created);
            }
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static String safeTrim(String value) {
        return value == null ? "" : value.trim();
    }
}