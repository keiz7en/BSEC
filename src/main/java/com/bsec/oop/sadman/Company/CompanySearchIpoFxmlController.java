package com.bsec.oop.sadman.Company;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class CompanySearchIpoFxmlController
{
    @javafx.fxml.FXML
    private TableView<IPODetails> ipoStatusTV;
    @javafx.fxml.FXML
    private TableColumn<IPODetails, Integer> ipoAmountTC;
    @javafx.fxml.FXML
    private TableColumn<IPODetails, String> ipoDetailsTC;
    @javafx.fxml.FXML
    private TextField companyIdTF;
    @javafx.fxml.FXML
    private TableColumn<IPODetails,String> dateTC;

    ObservableList<IPODetails> ipoList = FXCollections.observableArrayList();
    @javafx.fxml.FXML
    private TableColumn companyNameTC;

    @javafx.fxml.FXML
    public void initialize() {
        companyNameTC.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        ipoAmountTC.setCellValueFactory(new PropertyValueFactory<>("ipoAmount"));
        ipoDetailsTC.setCellValueFactory(new PropertyValueFactory<>("ipoDetails"));
        dateTC.setCellValueFactory(new PropertyValueFactory<>("date"));

        ipoStatusTV.setItems(ipoList);

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

    @javafx.fxml.FXML
    public void searchButt(ActionEvent actionEvent) {

        ipoList = GenericFileManager.readAll(IPODetails.class, "IPODetails.bin");
        //For showing filtered value
        if (companyIdTF.getText().isEmpty()){
            Alert a = new Alert(Alert.AlertType.ERROR, "Please enter company name");
            a.showAndWait();
            return;
        }

        for (IPODetails i: ipoList) {
            if (i.getCompanyName().equals(companyIdTF.getText())) {
                ipoStatusTV.getItems().add(i);
            }


    }

    }
}


