package com.bsec.oop.SiamShikder.Regulator;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

public class Main
{
    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void goToLogin(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bsec/oop/Login.fxml"));
            Parent loginRoot = loader.load();
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene loginScene = new Scene(loginRoot);
            currentStage.setScene(loginScene);
            currentStage.setTitle("BSEC Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void MonitorTradeButton(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void suspendListButton(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void RegManipulateButton(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void OverseeWhistleButton(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void IPOapplicationButton(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void AuditReportsButton(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void ConductAuditButton(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void approvestockdecButton(ActionEvent actionEvent) {
    }
}