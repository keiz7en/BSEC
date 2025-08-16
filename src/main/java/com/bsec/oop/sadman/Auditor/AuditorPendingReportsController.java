package com.bsec.oop.sadman.Auditor;

import com.bsec.oop.sadman.Company.GenericFileManager;
import com.bsec.oop.sadman.Company.IPODetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class AuditorPendingReportsController
{
    @javafx.fxml.FXML
    private TableView<IPODetails> ipoStatusTV;
    @javafx.fxml.FXML
    private TableColumn<IPODetails, Integer> ipoAmountTC;
    @javafx.fxml.FXML
    private TableColumn<IPODetails, String> ipoDetailsTC;
    @javafx.fxml.FXML
    private TableColumn<IPODetails, String> dateTC;
    @javafx.fxml.FXML
    private TableColumn<IPODetails, String> companyNameTC;
    ObservableList<IPODetails> ipoList = FXCollections.observableArrayList();

    @javafx.fxml.FXML
    public void initialize() {
        companyNameTC.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        ipoAmountTC.setCellValueFactory(new PropertyValueFactory<>("ipoAmount"));
        ipoDetailsTC.setCellValueFactory(new PropertyValueFactory<>("ipoDetails"));
        dateTC.setCellValueFactory(new PropertyValueFactory<>("date"));

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
        ipoStatusTV.getItems().clear();
        ipoList = GenericFileManager.readAll(IPODetails.class, "IPODetails.bin");
        ipoStatusTV.getItems().addAll(ipoList);

    }
}