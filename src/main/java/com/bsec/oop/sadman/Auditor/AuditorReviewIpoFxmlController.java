package com.bsec.oop.sadman.Auditor;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class AuditorReviewIpoFxmlController
{
    @javafx.fxml.FXML
    private TableView ipoStatusTV;
    @javafx.fxml.FXML
    private TableColumn ipoAmountTC;
    @javafx.fxml.FXML
    private TableColumn ipoDetailsTC;
    @javafx.fxml.FXML
    private TableColumn dateTC;
    @javafx.fxml.FXML
    private TableColumn companyNameTC;

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
    public void rejectButt(ActionEvent actionEvent) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, "The Request has been Rejected");
        a.showAndWait();

    }

    @javafx.fxml.FXML
    public void approveButt(ActionEvent actionEvent) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, "The Request has been Accepted");
        a.showAndWait();

    }
}