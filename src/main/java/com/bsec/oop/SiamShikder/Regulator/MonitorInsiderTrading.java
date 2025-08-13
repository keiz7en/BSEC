package com.bsec.oop.SiamShikder.Regulator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MonitorInsiderTrading {

    @FXML
    private void goBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/bsec/summer25section2/SiamShikder/Regulator/Menu_Regulator.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Regulator Menu");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}