package com.bsec.oop.Fayshal.StockExchange;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bsec/summer25section2/Login.fxml"));
            Parent loginRoot = loader.load();
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene loginScene = new Scene(loginRoot);
            currentStage.setScene(loginScene);
            currentStage.setTitle("BSEC Login");
        } catch (IOException e) {
            showAlert("Error", "Could not load login screen: " + e.getMessage());
        }
    }

    // Goal 1: Manage Stock Listings
    @javafx.fxml.FXML
    public void manageStockListings(ActionEvent actionEvent) {
        loadFXML("/com/bsec/summer25section2/Fayshal/StockExchange/ManageStockListings.fxml", "Manage Stock Listings", actionEvent);
    }

    // Goal 2: Publish Market Indices
    @javafx.fxml.FXML
    public void publishMarketIndices(ActionEvent actionEvent) {
        loadFXML("/com/bsec/summer25section2/Fayshal/StockExchange/PublishMarketIndices.fxml", "Publish Market Indices", actionEvent);
    }

    // Goal 3: Handle Trading Halts
    @javafx.fxml.FXML
    public void handleTradingHalts(ActionEvent actionEvent) {
        loadFXML("/com/bsec/summer25section2/Fayshal/StockExchange/HandleTradingHalts.fxml", "Handle Trading Halts", actionEvent);
    }

    // Goal 4: Monitor Real-time Trading Activity
    @javafx.fxml.FXML
    public void monitorTradingActivity(ActionEvent actionEvent) {
        loadFXML("/com/bsec/summer25section2/Fayshal/StockExchange/MonitorTradingActivity.fxml", "Monitor Trading Activity", actionEvent);
    }

    // Goal 5: Publish Annual Market Reports
    @javafx.fxml.FXML
    public void publishAnnualReports(ActionEvent actionEvent) {
        loadFXML("/com/bsec/summer25section2/Fayshal/StockExchange/PublishAnnualReports.fxml", "Publish Annual Reports", actionEvent);
    }

    // Goal 6: Manage Stock Delisting
    @javafx.fxml.FXML
    public void manageStockDelisting(ActionEvent actionEvent) {
        loadFXML("/com/bsec/summer25section2/Fayshal/StockExchange/ManageStockDelisting.fxml", "Manage Stock Delisting", actionEvent);
    }

    // Goal 7: Co-ordinate IPO Scheduling
    @javafx.fxml.FXML
    public void coordinateIPOScheduling(ActionEvent actionEvent) {
        loadFXML("/com/bsec/summer25section2/Fayshal/StockExchange/CoordinateIPOScheduling.fxml", "Co-ordinate IPO Scheduling", actionEvent);
    }

    // Goal 8: Investor Education Programs
    @javafx.fxml.FXML
    public void investorEducationPrograms(ActionEvent actionEvent) {
        loadFXML("/com/bsec/summer25section2/Fayshal/StockExchange/InvestorEducationPrograms.fxml", "Investor Education Programs", actionEvent);
    }

    // Helper method to load FXML files
    private void loadFXML(String fxmlPath, String title, ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle(title);
        } catch (IOException e) {
            showAlert("Error", "Could not load " + title + ": " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}