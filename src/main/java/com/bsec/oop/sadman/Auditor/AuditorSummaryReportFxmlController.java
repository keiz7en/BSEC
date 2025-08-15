package com.bsec.oop.sadman.Auditor;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AuditorSummaryReportFxmlController
{
    @javafx.fxml.FXML
    public void initialize() {
    }

    private ActionEvent event;

    public void switchScene(ActionEvent event, String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


    @javafx.fxml.FXML
    public void goBackButt(ActionEvent actionEvent) throws IOException {
        switchScene(event, "auditor_dashboard.fxml");
    }
}