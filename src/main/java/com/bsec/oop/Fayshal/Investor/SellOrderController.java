package com.bsec.oop.Fayshal.Investor;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

public class SellOrderController
{
    @FXML
    private ComboBox<String> marketComboBox;

    @javafx.fxml.FXML
    public void initialize() {
        // Populate markets if the ComboBox exists in the layout
        if (marketComboBox != null) {
            marketComboBox.getItems().setAll(
                    "DSE - Dhaka Stock Exchange (DSE)",
                    "CSE - Chittagong Stock Exchange (CSE)"
            );
            marketComboBox.setValue("DSE - Dhaka Stock Exchange (DSE)");
        }
    }

    @javafx.fxml.FXML
    public void goBackToMenu(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bsec/summer25section2/Fayshal/Investor/Menu_Investor.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Stubs to satisfy FXML action references
    @FXML
    public void onValidateOrder(ActionEvent e) {
    }

    @FXML
    public void onConfirmOrder(ActionEvent e) {
    }

    @FXML
    public void goBackToPortfolio(ActionEvent e) {
    }
}