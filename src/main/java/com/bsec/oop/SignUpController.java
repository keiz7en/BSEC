package com.bsec.oop;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private TextField fullName;

    @FXML
    private TextField Id_number;

    @FXML
    private ChoiceBox<String> user_type;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirmPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Populate user types
        user_type.getItems().addAll(
                "Investor",
                "Stock Exchange",
                "Broker",
                "Stock Analyst",
                "Company",
                "Auditor",
                "BSEC Officer",
                "Regulator"
        );
    }

    @FXML
    protected void SignUpButton() {
        String nameText = fullName.getText();
        String idText = Id_number.getText();
        String passwordText = password.getText();
        String confirmPasswordText = confirmPassword.getText();
        String selectedUserType = user_type.getValue();
        
        if (nameText == null || nameText.trim().isEmpty()) {
            showAlert("Invalid Name", "Full name is required.");
            return;
        }
        
        if (idText == null || !idText.matches("\\d{6}")) {
            showAlert("Invalid ID", "ID must be exactly 6 digits.");
            return;
        }
        
        if (passwordText == null || passwordText.length() != 6) {
            showAlert("Invalid Password", "Password must be exactly 6 characters (letters, digits, or combined).");
            return;
        }
        
        if (!passwordText.equals(confirmPasswordText)) {
            showAlert("Password Mismatch", "Password and confirm password do not match.");
            return;
        }
        
        if (selectedUserType == null) {
            showAlert("User Type Required", "Please select a user type.");
            return;
        }

        // Check if user ID already exists
        if (DatabaseManager.userExists(idText)) {
            showAlert("ID Already Exists", "This ID is already registered. Please use a different ID or login with existing credentials.");
            return;
        }

        // Register user in database
        boolean registrationSuccess = DatabaseManager.registerUser(idText, nameText.trim(), passwordText, selectedUserType);

        if (registrationSuccess) {
            showAlert("Sign Up Successful", "Account created successfully!\n" +
                    "Name: " + nameText + "\n" +
                    "ID: " + idText + "\n" +
                    "User Type: " + selectedUserType + "\n\n" +
                    "You can now login with your credentials.");

            // Clear form
            fullName.clear();
            Id_number.clear();
            password.clear();
            confirmPassword.clear();
            user_type.setValue(null);

            // Go back to login page
            goToLogin();
        } else {
            showAlert("Registration Failed", "Failed to create account. Please try again.");
        }
    }

    @FXML
    protected void BackToLoginButton() {
        goToLogin();
    }

    private void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent loginRoot = loader.load();
            
            Stage currentStage = (Stage) fullName.getScene().getWindow();

            // Create new scene and set it
            Scene loginScene = new Scene(loginRoot);
            currentStage.setScene(loginScene);
            currentStage.setTitle("BSEC Login");

        } catch (IOException e) {
            showAlert("Error", "Could not load Login page: " + e.getMessage());
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
