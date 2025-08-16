package com.bsec.oop.Fayshal.Investor;

import com.bsec.oop.Fayshal.Investor.model.SellOrderEntry;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SellOrderController {
    @FXML
    private TextField stockSymbolField;
    @FXML
    private TextField quantityField;
    @FXML
    private ComboBox<String> orderTypeCombo;
    @FXML
    private TextField limitPriceField;
    @FXML
    private DatePicker sellDatePicker;
    @FXML
    private Button submitSellButton;
    @FXML
    private TableView<SellOrderEntry> sellOrderTable;
    @FXML
    private TableColumn<SellOrderEntry, String> symbolColumn;
    @FXML
    private TableColumn<SellOrderEntry, String> qtyColumn;
    @FXML
    private TableColumn<SellOrderEntry, String> typeColumn;
    @FXML
    private TableColumn<SellOrderEntry, String> limitPriceColumn;
    @FXML
    private TableColumn<SellOrderEntry, String> dateColumn;

    private final ArrayList<SellOrderEntry> sellOrders = new ArrayList<>();
    private final File storageFile = new File("sell_orders.bin");

    @FXML
    public void initialize() {
        orderTypeCombo.getItems().setAll("Market", "Limit");
        symbolColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStockSymbol()));
        qtyColumn.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getQuantity())));
        typeColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getOrderType()));
        limitPriceColumn.setCellValueFactory(c -> new SimpleStringProperty(String.format("%.2f", c.getValue().getLimitPrice())));
        dateColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSellDate()));
        loadFromFile();
        refreshTable(sellOrders);
    }

    @FXML
    public void handleSubmitSellOrder(ActionEvent e) {
        String symbol = stockSymbolField.getText() == null ? "" : stockSymbolField.getText().trim().toUpperCase();
        String type = orderTypeCombo.getValue();
        String qtyText = quantityField.getText() == null ? "" : quantityField.getText().trim();
        String priceText = limitPriceField.getText() == null ? "" : limitPriceField.getText().trim();
        if (symbol.isEmpty()) return;
        if (type == null || type.isEmpty()) return;
        int qty;
        double price;
        try {
            qty = Integer.parseInt(qtyText);
            price = Double.parseDouble(priceText);
        } catch (Exception ex) {
            return;
        }
        if (qty <= 0 || price <= 0) return;
        String dateStr = sellDatePicker.getValue() == null ? "" : sellDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        SellOrderEntry entry = new SellOrderEntry(symbol, qty, type, price, dateStr);
        sellOrders.add(entry);
        saveToFile();
        refreshTable(sellOrders);
        stockSymbolField.clear();
        quantityField.clear();
        orderTypeCombo.getSelectionModel().clearSelection();
        limitPriceField.clear();
        sellDatePicker.setValue(null);
    }

    private void refreshTable(List<SellOrderEntry> rows) {
        sellOrderTable.setItems(FXCollections.observableList(rows));
        sellOrderTable.refresh();
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storageFile))) {
            oos.writeObject(sellOrders);
        } catch (IOException ignored) {
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        if (!storageFile.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storageFile))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList) {
                sellOrders.clear();
                sellOrders.addAll((ArrayList<SellOrderEntry>) obj);
            }
        } catch (Exception ignored) {
        }
    }

    @FXML
    public void goBackToMenu(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bsec/summer25section2/Fayshal/Investor/Menu_Investor.fxml"));
            Parent root = loader.load();
            javafx.stage.Stage stage = (javafx.stage.Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ignored) {
        }
    }
}