package com.bsec.oop.Fayshal.Investor;

import com.bsec.oop.Fayshal.Investor.model.PortfolioHolding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ViewPortfolioAndPerformance {
    @FXML
    private Label marketNameLabel;
    @FXML
    private Label totalValueLabel;
    @FXML
    private Label totalGainLossLabel;
    @FXML
    private TableView<PortfolioHolding> holdingsTable;
    @FXML
    private TableColumn<PortfolioHolding, String> symbolColumn;
    @FXML
    private TableColumn<PortfolioHolding, Integer> quantityColumn;
    @FXML
    private TableColumn<PortfolioHolding, Double> buyPriceColumn;
    @FXML
    private TableColumn<PortfolioHolding, Double> currentPriceColumn;
    @FXML
    private TableColumn<PortfolioHolding, Double> gainLossPercentColumn;
    @FXML
    private TableColumn<PortfolioHolding, String> marketNameColumn;
    @FXML
    private LineChart<String, Number> portfolioLineChart;
    @FXML
    private CategoryAxis dateAxis;
    @FXML
    private NumberAxis valueAxis;
    @FXML
    private Button exportReportButton;
    @FXML
    private Button backToDashboardButton;

    private final DecimalFormat money = new DecimalFormat("#,##0.00");
    private final DecimalFormat pct = new DecimalFormat("0.00");
    private final ArrayList<PortfolioHolding> rows = new ArrayList<>();
    private String marketName = "DSE";

    @FXML
    public void initialize() {
        marketNameLabel.setText("Market: " + marketName);
        symbolColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("symbol"));
        quantityColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("quantity"));
        buyPriceColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("buyPrice"));
        currentPriceColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("currentPrice"));
        gainLossPercentColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("gainLossPercent"));
        marketNameColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("marketName"));
        buyPriceColumn.setCellFactory(col -> new javafx.scene.control.TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : money.format(item));
            }
        });
        currentPriceColumn.setCellFactory(col -> new javafx.scene.control.TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : money.format(item));
            }
        });
        gainLossPercentColumn.setCellFactory(col -> new javafx.scene.control.TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : pct.format(item) + "%");
            }
        });
        loadSampleData();
        holdingsTable.getItems().setAll(rows);
        updateTotals();
        drawChart();
        exportReportButton.setOnAction(e -> onExportReport());
        backToDashboardButton.setOnAction(e -> goBackToDashboard(e));
    }

    private void loadSampleData() {
        rows.clear();

    }

    private void updateTotals() {

    }

    private void drawChart() {
        portfolioLineChart.getData().clear();

    }

    private void onExportReport() {
        totalGainLossLabel.setText(totalGainLossLabel.getText());
    }

    @FXML
    public void goBackToDashboard(ActionEvent actionEvent) {
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
}