package com.bsec.oop.Abid.Broker;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddInvestor
{
    @javafx.fxml.FXML
    private TextField nameTF;
    @javafx.fxml.FXML
    private TextField contactInfoTF;
    @javafx.fxml.FXML
    private TextField nidTF;
    @javafx.fxml.FXML
    private Label Label;

    @javafx.fxml.FXML
    public void initialize() {
    }
    private static final String BROKER_BASE_PATH = "/com/bsec/summer25section2/Abid/Broker/";

    public void switchScene(ActionEvent event, String fxmlFile) throws IOException {
        String path = fxmlFile.startsWith("/") ? fxmlFile : BROKER_BASE_PATH + fxmlFile;
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @javafx.fxml.FXML
    public void previousBut(ActionEvent actionEvent) throws IOException {
        switchScene(actionEvent, "broker_dashboard.fxml");
    }

    @javafx.fxml.FXML
    public void createNewProfileBut(ActionEvent actionEvent) {
    }
}