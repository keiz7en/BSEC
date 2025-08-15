package com.bsec.oop.Fayshal.StockExchange;

import com.bsec.oop.Fayshal.StockExchange.model.ListingApplication;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ManageStockListings {
    @FXML
    private ComboBox<String> applicationStatusCombo;
    @FXML
    private DatePicker submissionDatePicker;
    @FXML
    private TableView<ListingApplication> applicationsTable;
    @FXML
    private TableColumn<ListingApplication, String> colAppId;
    @FXML
    private TableColumn<ListingApplication, String> colCompany;
    @FXML
    private TableColumn<ListingApplication, String> colSubmission;
    @FXML
    private TableColumn<ListingApplication, String> colStatus;
    @FXML
    private TableColumn<ListingApplication, String> colDecisionDate;
    @FXML
    private Button verifyEligibilityButton;
    @FXML
    private Button submitDecisionButton;
    @FXML
    private Button backButton;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    private final ArrayList<ListingApplication> applications = new ArrayList<>();
    private final File storageFile = new File("listings.bin");

    @FXML
    public void initialize() {
        applicationStatusCombo.getItems().setAll("Pending", "Approved", "Rejected");
        colAppId.setCellValueFactory(new PropertyValueFactory<>("applicationId"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        colSubmission.setCellValueFactory(new PropertyValueFactory<>("submissionDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDecisionDate.setCellValueFactory(new PropertyValueFactory<>("decisionDate"));
        loadFromFile();
        refreshTable(applications);
    }

    @FXML
    public void handleVerifyEligibility(ActionEvent actionEvent) {
        ListingApplication row = applicationsTable.getSelectionModel().getSelectedItem();
        if (row == null) return;
        if (row.getStatus().equals("Pending")) row.setStatus("Pending");
        saveToFile();
        refreshTable(applications);
    }

    @FXML
    public void handleSubmitDecision(ActionEvent actionEvent) {
        String appId = usernameField.getText() == null ? "" : usernameField.getText().trim();
        String company = passwordField.getText() == null ? "" : passwordField.getText().trim();
        String status = applicationStatusCombo.getValue();
        LocalDate subDate = submissionDatePicker.getValue();
        if (appId.isEmpty() || company.isEmpty()) return;
        if (status == null || subDate == null) return;
        String decisionDate = dateStr(LocalDate.now());
        ListingApplication row = new ListingApplication(appId, company, dateStr(subDate), status, decisionDate);
        applications.add(row);
        saveToFile();
        refreshTable(applications);
        usernameField.clear();
        passwordField.clear();
        applicationStatusCombo.getSelectionModel().clearSelection();
        submissionDatePicker.setValue(null);
    }

    @FXML
    public void handleAddApplication(ActionEvent actionEvent) {
        String appId = usernameField.getText() == null ? "" : usernameField.getText().trim();
        String company = passwordField.getText() == null ? "" : passwordField.getText().trim();
        String status = applicationStatusCombo.getValue();
        LocalDate subDate = submissionDatePicker.getValue();
        if (appId.isEmpty() || company.isEmpty()) return;
        if (status == null || subDate == null) return;
        ListingApplication row = new ListingApplication(appId, company, dateStr(subDate), status, "");
        applications.add(row);
        saveToFile();
        refreshTable(applications);
        usernameField.clear();
        passwordField.clear();
        applicationStatusCombo.getSelectionModel().clearSelection();
        submissionDatePicker.setValue(null);
    }

    @FXML
    public void goBackToMenu(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bsec/summer25section2/Fayshal/StockExchange/Main.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Stock Exchange Dashboard");
        } catch (IOException e) {
        }
    }

    private void refreshTable(List<ListingApplication> data) {
        applicationsTable.setItems(FXCollections.observableList(data));
        applicationsTable.refresh();
    }

    private String dateStr(LocalDate d) {
        return d.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storageFile))) {
            oos.writeObject(applications);
        } catch (IOException ignored) {
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        if (!storageFile.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storageFile))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList) {
                applications.clear();
                applications.addAll((ArrayList<ListingApplication>) obj);
            }
        } catch (Exception ignored) {
        }
    }
}