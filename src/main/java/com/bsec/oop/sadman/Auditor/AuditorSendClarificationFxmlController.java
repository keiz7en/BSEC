package com.bsec.oop.sadman.Auditor;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AuditorSendClarificationFxmlController
{
    @javafx.fxml.FXML
    private TextField companyNameTF;
    @javafx.fxml.FXML
    private Label statusLabel;
    @javafx.fxml.FXML
    private TextArea messageArea;

    @javafx.fxml.FXML
    public void initialize() {
    }

    private static final String AUDITOR_BASE_PATH = "/com/bsec/summer25section2/sadman/Auditor/";

    public void switchScene(ActionEvent event, String fxmlFile) throws IOException {
        String path = fxmlFile.startsWith("/") ? fxmlFile : AUDITOR_BASE_PATH + fxmlFile;
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


    @javafx.fxml.FXML
    public void goBackButt(ActionEvent actionEvent) throws IOException {
        switchScene(actionEvent, "auditor_dashboard.fxml");
    }

    @javafx.fxml.FXML
    public void searchButt(ActionEvent actionEvent) {

        String subject = companyNameTF.getText();
        String message = messageArea.getText();

        if (subject.isEmpty() || message.isEmpty()) {
            statusLabel.setText("Name and Clarification Message are required!");
            return;
        }

        statusLabel.setText("Clarification sent successfully!");
        System.out.println("Sent clarification:");
        System.out.println("Subject: " + subject);
        System.out.println("Message: " + message);

    }
}