package com.bsec.oop.Fayshal.Investor;

import com.bsec.oop.Fayshal.Investor.model.StockData;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SearchAndViewStocks {
    @FXML
    private TextField stockNameField;
    @FXML
    private ComboBox<String> sectorCombo;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField priceRangeField;
    @FXML
    private Button submitSearchButton;
    @FXML
    private TableView<StockData> stockTable;
    @FXML
    private TableColumn<StockData, String> symbolColumn;
    @FXML
    private TableColumn<StockData, String> nameColumn;
    @FXML
    private TableColumn<StockData, String> priceColumn;
    @FXML
    private TableColumn<StockData, String> changePercentColumn;
    @FXML
    private TableColumn<StockData, String> volumeColumn;
    @FXML
    private Button backButton;

    private final ArrayList<StockData> stockDataList = new ArrayList<>();
    private final File storageFile = new File("stock_search_data.bin");

    @FXML
    public void initialize() {
        sectorCombo.getItems().setAll("Banking", "Pharmaceuticals", "Telecom", "Food", "Textile", "Engineering");
        symbolColumn.setCellValueFactory(new PropertyValueFactory<>("stockSymbol"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("stockName"));
        priceColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.format("%.2f", cell.getValue().getCurrentPrice())));
        changePercentColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.format("%.2f%%", cell.getValue().getChangePercent())));
        volumeColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getVolume())));
        loadFromFile();
        refreshTable(stockDataList);
    }

    @FXML
    public void handleSubmitSearch(ActionEvent actionEvent) {
        String stockName = stockNameField.getText() == null ? "" : stockNameField.getText().trim();
        String sector = sectorCombo.getValue();
        String priceRange = priceRangeField.getText() == null ? "" : priceRangeField.getText().trim();
        if (stockName.isEmpty() && sector == null && priceRange.isEmpty()) {
            stockDataList.clear();
            stockDataList.add(new StockData("DSE", "Dhaka Stock Exchange", 45.75, 2.5, 1250000));
            stockDataList.add(new StockData("GPH", "Grameenphone", 325.80, -1.2, 850000));
            stockDataList.add(new StockData("SQUR", "Square Pharma", 180.40, 3.8, 950000));
            stockDataList.add(new StockData("BRAC", "BRAC Bank", 52.30, 0.9, 750000));
            stockDataList.add(new StockData("BEXI", "Bangladesh Export Import", 28.65, -0.5, 600000));
        } else {
            stockDataList.clear();
            if (!stockName.isEmpty()) {
                stockDataList.add(new StockData(stockName.toUpperCase(), stockName + " Company", 125.50, 1.8, 500000));
            }
            if (sector != null) {
                stockDataList.add(new StockData("SEC1", sector + " Corp", 89.75, 2.1, 300000));
            }
        }
        saveToFile();
        refreshTable(stockDataList);
        stockNameField.clear();
        sectorCombo.getSelectionModel().clearSelection();
        priceRangeField.clear();
    }

    @FXML
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

    private void refreshTable(List<StockData> data) {
        stockTable.setItems(FXCollections.observableList(data));
        stockTable.refresh();
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storageFile))) {
            oos.writeObject(stockDataList);
        } catch (IOException ignored) {
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        if (!storageFile.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storageFile))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList) {
                stockDataList.clear();
                stockDataList.addAll((ArrayList<StockData>) obj);
            }
        } catch (Exception ignored) {
        }
    }
}