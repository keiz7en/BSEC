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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    private TextField Id_number;

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
    protected void loginAsInvestor() {
        if (authenticateUser("Investor")) {
            navigateToUserTypePage("Investor", "Investor/Main.fxml", "BSEC - Investor Dashboard");
        }
    }

    @FXML
    protected void loginAsStockExchange() {
        if (authenticateUser("Stock Exchange")) {
            navigateToUserTypePage("Stock Exchange", "StockExchange/Main.fxml", "BSEC - Stock Exchange Dashboard");
        }
    }

    @FXML
    protected void loginAsBroker() {
        if (authenticateUser("Broker")) {
            navigateToUserTypePage("Broker", "Broker/Main.fxml", "BSEC - Broker Dashboard");
        }
    }

    @FXML
    protected void loginAsStockAnalyst() {
        if (authenticateUser("Stock Analyst")) {
            navigateToUserTypePage("Stock Analyst", "StockAnalyst/Main.fxml", "BSEC - Stock Analyst Dashboard");
        }
    }

    @FXML
    protected void loginAsCompany() {
        if (authenticateUser("Company")) {
            navigateToUserTypePage("Company", "Company/Main.fxml", "BSEC - Company Dashboard");
        }
    }

    @FXML
    protected void loginAsAuditor() {
        if (authenticateUser("Auditor")) {
            navigateToUserTypePage("Auditor", "Auditor/Main.fxml", "BSEC - Auditor Dashboard");
        }
    }

    @FXML
    protected void loginAsBSECOfficer() {
        if (authenticateUser("BSEC Officer")) {
            navigateToUserTypePage("BSEC Officer", "BSEC_Officer/Main.fxml", "BSEC - Officer Dashboard");
        }
    }

    @FXML
    protected void loginAsRegulator() {
        if (authenticateUser("Regulator")) {
            navigateToUserTypePage("Regulator", "Regulator/Main.fxml", "BSEC - Regulator Dashboard");
        }
    }

    @FXML
    protected void SubmitButton() {
        String selectedUserType = user_type.getValue();

        // Validate user type selection first
        if (selectedUserType == null) {
            showAlert("User Type Required", "Please select a user type.");
            return;
        }

        // Call the appropriate login method based on user type
        switch (selectedUserType) {
            case "Investor":
                loginAsInvestor();
                break;
            case "Stock Exchange":
                loginAsStockExchange();
                break;
            case "Broker":
                loginAsBroker();
                break;
            case "Stock Analyst":
                loginAsStockAnalyst();
                break;
            case "Company":
                loginAsCompany();
                break;
            case "Auditor":
                loginAsAuditor();
                break;
            case "BSEC Officer":
                loginAsBSECOfficer();
                break;
            case "Regulator":
                loginAsRegulator();
                break;
            default:
                showAlert("Error", "Unknown user type: " + selectedUserType);
        }
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

    private boolean authenticateUser(String expectedUserType) {
        String idText = Id_number.getText();
        String passwordText = PasswordField.getText();

        // Validate ID (must be 6 digits)
        if (idText == null || !idText.matches("\\d{6}")) {
            showAlert("Invalid ID", "ID must be exactly 6 digits.");
            return false;
        }

        // Validate password (must be 6 characters)
        if (passwordText == null || passwordText.length() != 6) {
            showAlert("Invalid Password", "Password must be exactly 6 characters (letters, digits, or combined).");
            return false;
        }

        // Authenticate user with database
        User authenticatedUser = DatabaseManager.authenticateUser(idText, passwordText, expectedUserType);

        if (authenticatedUser != null) {
            showAlert("Login Successful", "Welcome " + authenticatedUser.getFullName() + "!\n" +
                    "User ID: " + authenticatedUser.getUserId() + "\n" +
                    "User Type: " + authenticatedUser.getUserType());

            // Clear form after successful login
            Id_number.clear();
            PasswordField.clear();
            user_type.setValue(null);

            return true;
        } else {
            showAlert("Login Failed", "Invalid credentials for " + expectedUserType + ". Please check your ID and password.\n\n" +
                    "If you don't have an account, please sign up first.");
            return false;
        }
    }

    private void navigateToUserTypePage(String userType, String fxmlPath, String windowTitle) {
        try {
            // Load the user type FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent userTypeRoot = loader.load();

            // Get current stage
            Stage currentStage = (Stage) Id_number.getScene().getWindow();

            // Create new scene and set it
            Scene userTypeScene = new Scene(userTypeRoot);
            currentStage.setScene(userTypeScene);
            currentStage.setTitle(windowTitle);

        } catch (IOException e) {
            showAlert("Error", "Could not load " + userType + " dashboard: " + e.getMessage());
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
