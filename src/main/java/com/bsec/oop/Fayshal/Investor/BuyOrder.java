package com.bsec.oop.Fayshal.Investor;

import com.bsec.oop.Fayshal.Investor.model.BuyOrderModel;
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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class BuyOrder {
    @FXML
    private Label marketNameLabel;
    @FXML
    private Label stockHeaderLabel;
    @FXML
    private TextField quantityField;
    @FXML
    private ComboBox<String> orderTypeComboBox;
    @FXML
    private TextField priceField;
    @FXML
    private RadioButton immediateOrCancelRadio;
    @FXML
    private RadioButton goodTillDateRadio;
    @FXML
    private DatePicker executionDatePicker;
    @FXML
    private TableView<BuyOrderModel> orderSummaryTable;
    @FXML
    private TableColumn<BuyOrderModel, String> marketNameColumn;
    @FXML
    private TableColumn<BuyOrderModel, String> stockSymbolColumn;
    @FXML
    private TableColumn<BuyOrderModel, Integer> quantityColumn;
    @FXML
    private TableColumn<BuyOrderModel, Double> priceColumn;
    @FXML
    private TableColumn<BuyOrderModel, String> orderTypeColumn;
    @FXML
    private TableColumn<BuyOrderModel, String> executionDateColumn;
    @FXML
    private TableColumn<BuyOrderModel, Double> estimatedTotalColumn;
    @FXML
    private Button checkValidateButton;
    @FXML
    private Button confirmOrderButton;
    @FXML
    private Button backToProfileButton;
    @FXML
    private Label orderStatusValueLabel;
    @FXML
    private Label orderIdValueLabel;
    @FXML
    private TextField marketNameField;

    private final DecimalFormat moneyFormat = new DecimalFormat("#,##0.00");
    private final ArrayList<BuyOrderModel> summaryRows = new ArrayList<>();
    private final ToggleGroup executionToggle = new ToggleGroup();
    private double availableBalance = 500000.0;
    private double marketPrice = 100.0;
    private String marketName = "DSE";
    private String stockName = "Sample Corp";
    private String stockSymbol = "SAMP";

    @FXML
    public void initialize() {
        marketNameLabel.setText("Market: " + marketName);
        stockHeaderLabel.setText("Stock: " + stockName + " (" + stockSymbol + ")");
        marketNameField.setText(marketName);
        orderTypeComboBox.getItems().setAll("Market Order", "Limit Order");
        orderTypeComboBox.getSelectionModel().select("Market Order");
        priceField.setDisable(true);
        immediateOrCancelRadio.setToggleGroup(executionToggle);
        goodTillDateRadio.setToggleGroup(executionToggle);
        immediateOrCancelRadio.setSelected(true);
        executionDatePicker.setDisable(true);
        orderTypeComboBox.valueProperty().addListener((o, a, b) -> priceField.setDisable("Market Order".equals(b)));
        goodTillDateRadio.selectedProperty().addListener((o, a, b) -> executionDatePicker.setDisable(!b));
        marketNameColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getMarketName()));
        stockSymbolColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStockSymbol()));
        quantityColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("price"));
        orderTypeColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getOrderType()));
        executionDateColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getExecutionDate()));
        estimatedTotalColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("estimatedTotalCost"));
        priceColumn.setCellFactory(col -> new javafx.scene.control.TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : moneyFormat.format(item));
            }
        });
        estimatedTotalColumn.setCellFactory(col -> new javafx.scene.control.TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : moneyFormat.format(item));
            }
        });
        executionDatePicker.setConverter(new StringConverter<>() {
            @Override
            public String toString(java.time.LocalDate object) {
                return object == null ? "" : object.toString();
            }

            @Override
            public java.time.LocalDate fromString(String string) {
                return string == null || string.isBlank() ? null : java.time.LocalDate.parse(string);
            }
        });
        orderSummaryTable.getItems().clear();
        orderStatusValueLabel.setText("Pending");
        orderIdValueLabel.setText("-");
    }

    @FXML
    public void onValidateOrder(ActionEvent e) {
        String selectedType = orderTypeComboBox.getValue();
        int quantity = parseInt(quantityField.getText());
        if (quantity <= 0) {
            orderStatusValueLabel.setText("Invalid Quantity");
            return;
        }
        double price = "Market Order".equals(selectedType) ? marketPrice : parseDouble(priceField.getText());
        if (price <= 0) {
            orderStatusValueLabel.setText("Invalid Price");
            return;
        }
        String executionDate = immediateOrCancelRadio.isSelected() ? "-" : (executionDatePicker.getValue() == null ? "" : executionDatePicker.getValue().toString());
        if (goodTillDateRadio.isSelected() && executionDate.isBlank()) {
            orderStatusValueLabel.setText("Select Execution Date");
            return;
        }
        double estimated = quantity * price;
        if (estimated > availableBalance) {
            orderStatusValueLabel.setText("Insufficient Balance");
        } else {
            orderStatusValueLabel.setText("Pending");
        }
        String marketUsed = marketNameField.getText() == null ? "" : marketNameField.getText().trim();
        if (marketUsed.isEmpty()) marketUsed = marketName;
        summaryRows.clear();
        summaryRows.add(new BuyOrderModel(marketUsed, stockSymbol, quantity, price, selectedType, executionDate, estimated));
        orderSummaryTable.getItems().setAll(summaryRows);
        orderIdValueLabel.setText("-");
    }

    @FXML
    public void onConfirmOrder(ActionEvent e) {
        if (orderSummaryTable.getItems().isEmpty()) {
            orderStatusValueLabel.setText("Pending");
            orderIdValueLabel.setText("-");
            return;
        }
        if ("Insufficient Balance".equalsIgnoreCase(orderStatusValueLabel.getText())) {
            return;
        }
        orderStatusValueLabel.setText("Confirmed");
        orderIdValueLabel.setText("ORD-" + System.currentTimeMillis());
    }

    @FXML
    public void goBackToStockProfile(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bsec/summer25section2/Fayshal/Investor/Menu_Investor.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private int parseInt(String text) {
        try {
            return Integer.parseInt(text == null ? "" : text.trim());
        } catch (Exception ex) {
            return -1;
        }
    }

    private double parseDouble(String text) {
        try {
            return Double.parseDouble(text == null ? "" : text.trim());
        } catch (Exception ex) {
            return -1;
        }
    }
}
