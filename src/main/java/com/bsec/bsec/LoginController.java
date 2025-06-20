package com.bsec.bsec;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class LoginController {
    @FXML
    private Label welcomeText;

    @FXML
    private PasswordField Id_number;

    @FXML
    private ChoiceBox<String> user_type;

    @FXML
    private PasswordField PasswordField;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void SubmitButton() {
        // Handle login submit
    }

    @FXML
    protected void SignUpButton() {
        // Handle sign up navigation
    }
}
