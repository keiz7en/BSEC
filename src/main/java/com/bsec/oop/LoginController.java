package com.bsec.oop;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;
import java.net.URL;

public class LoginController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    private ComboBox<String> userComboBox;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private Button confirmButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        userComboBox.getItems().addAll(
                "Fayshal",
                "Abid",
                "Sadman",
                "SiamShikder"
        );

        userComboBox.setOnAction(event -> updateRoleComboBox());
    }

    private void updateRoleComboBox() {
        roleComboBox.getItems().clear();
        String selectedUser = userComboBox.getValue();

        if (selectedUser != null) {
            switch (selectedUser) {
                case "Fayshal":
                    roleComboBox.getItems().addAll("Investor", "StockExchange");
                    break;
                case "Abid":
                    roleComboBox.getItems().addAll("Analyst", "Broker");
                    break;
                case "Sadman":
                    roleComboBox.getItems().addAll("Auditor", "Company");
                    break;
                case "SiamShikder":
                    roleComboBox.getItems().addAll("BSEC_Officer", "Regulator");
                    break;
            }
        }
    }

    @FXML
    protected void confirmLogin() {
        String selectedUser = userComboBox.getValue();
        String selectedRole = roleComboBox.getValue();

        if (selectedUser == null) {
            showAlert("User Required", "Please select a user.");
            return;
        }

        if (selectedRole == null) {
            showAlert("Role Required", "Please select a role.");
            return;
        }


        String fxmlPath = getFXMLPath(selectedUser, selectedRole);
        String windowTitle = "BSEC - " + selectedUser + " (" + selectedRole + ")";

        navigateToUserPage(selectedUser, selectedRole, fxmlPath, windowTitle);
    }

    private String getFXMLPath(String user, String role) {
        switch (user) {
            case "Fayshal":
                if (role.equals("Investor")) {
                    return "/com/bsec/summer25section2/Fayshal/Investor/Menu_Investor.fxml";
                } else {
                    return "/com/bsec/summer25section2/Fayshal/StockExchange/Main.fxml";
                }
            case "Abid":
                if (role.equals("Analyst")) {
                    return "/com/bsec/summer25section2/Abid/Analyst/analyst1.fxml";
                } else { // Broker
                    return "/com/bsec/summer25section2/Abid/Broker/broker_dashboard.fxml";
                }
            case "Sadman":
                if (role.equals("Auditor")) {
                    return "/com/bsec/summer25section2/sadman/Auditor/auditor_dashboard.fxml";
                } else {
                    return "/com/bsec/summer25section2/sadman/company/company_dashboard.fxml";
                }
            case "SiamShikder":
                if (role.equals("BSEC_Officer")) {
                    return "/com/bsec/summer25section2/SiamShikder/BSEC_Officer/Main.fxml";
                } else { // Regulator
                    return "/com/bsec/summer25section2/SiamShikder/Regulator/Menu_Regulator.fxml";
                }
            default:
                return "";
        }
    }

    private void navigateToUserPage(String userName, String role, String fxmlPath, String windowTitle) {
        try {
            System.out.println("Attempting to load: " + fxmlPath);
            URL resourceUrl = getClass().getResource(fxmlPath);
            System.out.println("Resource URL: " + resourceUrl);
            if (resourceUrl == null) {
                throw new IOException("FXML not found on classpath: " + fxmlPath);
            }
            FXMLLoader loader = new FXMLLoader(resourceUrl);
            Parent userRoot = loader.load();
            Stage currentStage = (Stage) userComboBox.getScene().getWindow();
            Scene userScene = new Scene(userRoot);
            currentStage.setScene(userScene);
            currentStage.setTitle(windowTitle);

            showAlert("Login Successful", "Welcome " + userName + " as " + role + "!");
            userComboBox.setValue(null);
            roleComboBox.setValue(null);

        } catch (IOException e) {
            System.err.println("Detailed error loading " + userName + " " + role + " dashboard:");
            System.err.println("FXML Path: " + fxmlPath);
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            showAlert("Error", "Could not load " + userName + " " + role + " dashboard: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
