package com.bsec.oop.Abid.Broker;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class broker_dashboard
{
    private static final String BROKER_BASE_PATH = "/com/bsec/summer25section2/Abid/Broker/";


    @javafx.fxml.FXML
    public void initialize() {
    }

    public void switchScene(ActionEvent event, String fxmlFile) throws IOException {
        String path = fxmlFile.startsWith("/") ? fxmlFile : BROKER_BASE_PATH + fxmlFile;
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @javafx.fxml.FXML
    public void ShareSellRequestBut(ActionEvent actionEvent) throws IOException {
        switchScene(actionEvent, "Share_sellRequest.fxml");
    }

    @javafx.fxml.FXML
    public void BrokerRegisterBut(ActionEvent actionEvent) throws IOException {
        switchScene(actionEvent, "BrokeRegister.fxml");
    }

    @javafx.fxml.FXML
    public void AddInvestorBut(ActionEvent actionEvent) throws IOException {
        switchScene(actionEvent, "AddInvestor.fxml");
    }

    @javafx.fxml.FXML
    public void GenarateReportBut(ActionEvent actionEvent) throws IOException {
        switchScene(actionEvent, "GenarateReport.fxml");
    }

    @javafx.fxml.FXML
    public void ClientInvestmentDetailsBut(ActionEvent actionEvent) throws IOException {
        switchScene(actionEvent, "ClientInvestmentDetails.fxml");
    }

    @javafx.fxml.FXML
    public void UpdateEmailandPhoneNumberBut(ActionEvent actionEvent) throws IOException {
        switchScene(actionEvent, "UpdateEmailOrPhoneNumber.fxml");
    }

    @javafx.fxml.FXML
    public void SendTradeUpdateBut(ActionEvent actionEvent) throws IOException{
        switchScene(actionEvent, "SendTradeUpdate.fxml");
    }

    @javafx.fxml.FXML
    public void StockPurchaseRequestBut(ActionEvent actionEvent) throws IOException {
        switchScene(actionEvent, "StockPurchaseRequest.fxml");
    }

    @javafx.fxml.FXML
    public void LogOutBut(ActionEvent actionEvent) throws IOException {
        switchScene(actionEvent, "/com/bsec/summer25section2/Login.fxml");
    }
}