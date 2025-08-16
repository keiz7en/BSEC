package com.bsec.oop.Fayshal.StockExchange;

import com.bsec.oop.Fayshal.StockExchange.model.MarketDataRow;
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
import javafx.beans.property.SimpleStringProperty;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PublishMarketIndices {
    @FXML
    private TextField marketNameField;
    @FXML
    private ComboBox<String> indexTypeCombo;
    @FXML
    private DatePicker dataCollectionDatePicker;
    @FXML
    private DatePicker enterTimePicker;
    @FXML
    private TextField tickerField;
    @FXML
    private TextField companyField;
    @FXML
    private TextField openField;
    @FXML
    private TextField closeField;
    @FXML
    private TableView<MarketDataRow> dailyDataTable;
    @FXML
    private TableColumn<MarketDataRow, String> colTicker;
    @FXML
    private TableColumn<MarketDataRow, String> colCompany;
    @FXML
    private TableColumn<MarketDataRow, String> colOpen;
    @FXML
    private TableColumn<MarketDataRow, String> colClose;
    @FXML
    private TableColumn<MarketDataRow, String> colVolume;
    @FXML
    private TableColumn<MarketDataRow, String> colMarketCap;

    private final ArrayList<MarketDataRow> tableRows = new ArrayList<>();
    private final File storageFile = new File("market_data_output.bin");

    @FXML
    public void initialize() {
        indexTypeCombo.getItems().setAll("General", "Sector", "Composite");
        colTicker.setCellValueFactory(new PropertyValueFactory<>("tickerSymbol"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        colOpen.setCellValueFactory(cell -> new SimpleStringProperty(String.format("%.2f", cell.getValue().getOpeningPrice())));
        colClose.setCellValueFactory(cell -> new SimpleStringProperty(String.format("%.2f", cell.getValue().getClosingPrice())));
        colVolume.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getVolume())));
        colMarketCap.setCellValueFactory(cell -> new SimpleStringProperty(String.format("%.2f", cell.getValue().getMarketCap())));
        loadFromFile();
        refreshTable(tableRows);
    }

    @FXML
    public void handleSubmitData(ActionEvent actionEvent) {
        if (marketNameField.getText() == null || marketNameField.getText().isBlank()) return;
        if (indexTypeCombo.getValue() == null || dataCollectionDatePicker.getValue() == null) return;
        String t = tickerField.getText() == null ? "" : tickerField.getText().trim();
        String c = companyField.getText() == null ? "" : companyField.getText().trim();
        String oText = openField.getText() == null ? "" : openField.getText().trim();
        String clText = closeField.getText() == null ? "" : closeField.getText().trim();
        if (t.isEmpty() || c.isEmpty() || oText.isEmpty() || clText.isEmpty()) return;
        double o;
        double cl;
        try {
            o = Double.parseDouble(oText);
            cl = Double.parseDouble(clText);
        } catch (Exception ex) {
            return;
        }
        MarketDataRow row = new MarketDataRow(t, c, o, cl, 0L, 0d);
        tableRows.add(row);
        saveToFile();
        refreshTable(tableRows);
        tickerField.clear();
        companyField.clear();
        openField.clear();
        closeField.clear();
    }

    @FXML
    public void handleCollectMarketData(ActionEvent actionEvent) {
        saveToFile();
        refreshTable(tableRows);
    }

    @FXML
    public void goBackToMenu(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bsec/summer25section2/Fayshal/StockExchange/Main.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Stock Exchange Dashboard");
        } catch (IOException e) {
        }
    }

    private void refreshTable(List<MarketDataRow> rows) {
        dailyDataTable.setItems(FXCollections.observableList(rows));
        dailyDataTable.refresh();
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storageFile))) {
            oos.writeObject(tableRows);
        } catch (IOException ignored) {
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        if (!storageFile.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storageFile))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList) {
                tableRows.clear();
                tableRows.addAll((ArrayList<MarketDataRow>) obj);
            }
        } catch (Exception ignored) {
        }
    }
}