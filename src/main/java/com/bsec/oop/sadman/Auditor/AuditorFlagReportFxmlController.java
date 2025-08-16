package com.bsec.oop.sadman.Auditor;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AuditorFlagReportFxmlController
{
    @javafx.fxml.FXML
    private ComboBox issueTypeComboBox;
    @javafx.fxml.FXML
    private TextField reportIdTF;
    @javafx.fxml.FXML
    private TextArea commentTA;
    @javafx.fxml.FXML
    private TextField companyIdTF;

    @javafx.fxml.FXML
    public void initialize() {
        issueTypeComboBox.getItems().addAll(
                "Data Mismatch",
                "Missing Information",
                "Suspicious Activity",
                "Other");
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
    public void reportFlagButt(ActionEvent actionEvent) throws IOException {

        Alert a = new Alert(Alert.AlertType.INFORMATION, "Company Flagged");
        a.showAndWait();
    }
}
