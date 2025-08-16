package com.bsec.oop.sadman.Company;

import com.bsec.oop.sadman.Auditor.UpdateInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;

public class CompanyUpdateProfileFxmlController
{
    @javafx.fxml.FXML
    private TextField companyTotalShareTF;
    @javafx.fxml.FXML
    private TextField companyNameTF;
    @javafx.fxml.FXML
    private TextField companyPasswordTF;
    @javafx.fxml.FXML
    private TextField companyAvailbeShareTF;

    @javafx.fxml.FXML
    public void initialize() {
    }

    private static final String COMPANY_BASE_PATH = "/com/bsec/summer25section2/sadman/company/";
    public void switchScene(ActionEvent event, String fxmlFile) throws IOException {
        String path = fxmlFile.startsWith("/") ? fxmlFile : COMPANY_BASE_PATH + fxmlFile;
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @javafx.fxml.FXML
    public void goBackButt(ActionEvent actionEvent) throws IOException {
        switchScene(actionEvent, "Main.fxml");

    }

    @FXML
    public void sendToReviewButt(ActionEvent actionEvent) throws IOException {

        Alert a = new Alert(Alert.AlertType.INFORMATION, "Sent Update Request");
        a.showAndWait();

    }
}