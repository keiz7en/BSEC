package com.bsec.bsec;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    private PasswordField Id_number;

    @FXML
    private ChoiceBox<String> user_type;

    @FXML
    private PasswordField PasswordField;

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
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void SubmitButton() {
        String idText = Id_number.getText();
        String passwordText = PasswordField.getText();
        String selectedUserType = user_type.getValue();

        // Validate ID (must be 6 digits)
        if (idText == null || !idText.matches("\\d{6}")) {
            showAlert("Invalid ID", "ID must be exactly 6 digits.");
            return;
        }

        // Validate password (must be 6 characters)
        if (passwordText == null || passwordText.length() != 6) {
            showAlert("Invalid Password", "Password must be exactly 6 characters (letters, digits, or combined).");
            return;
        }

        // Validate user type selection
        if (selectedUserType == null) {
            showAlert("User Type Required", "Please select a user type.");
            return;
        }

        // If validation passes, proceed with login
        showAlert("Login Successful", "Welcome " + selectedUserType + "!\nID: " + idText);
    }

    @FXML
    protected void SignUpButton() {
        try {
            // Load the SignUp FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
            Parent signUpRoot = loader.load();

            // Get current stage
            Stage currentStage = (Stage) Id_number.getScene().getWindow();

            // Create new scene and set it
            Scene signUpScene = new Scene(signUpRoot);
            currentStage.setScene(signUpScene);
            currentStage.setTitle("BSEC Sign Up");

        } catch (IOException e) {
            showAlert("Error", "Could not load Sign Up page: " + e.getMessage());
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
