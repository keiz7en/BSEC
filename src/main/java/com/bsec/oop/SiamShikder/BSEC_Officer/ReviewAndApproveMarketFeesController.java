package com.bsec.oop.SiamShikder.BSEC_Officer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReviewAndApproveMarketFeesController {

    @FXML
    private TextField proposalIdField;
    @FXML
    private ComboBox<String> feeTypeCombo;
    @FXML
    private DatePicker receivedDatePicker;
    @FXML
    private RadioButton pendingRadio;
    @FXML
    private RadioButton approvedRadio;
    @FXML
    private TextArea summaryArea;

    @FXML
    private TableView<FeeProposalItem> proposalTable;
    @FXML
    private TableColumn<FeeProposalItem, String> colProposalId;
    @FXML
    private TableColumn<FeeProposalItem, String> colFeeType;
    @FXML
    private TableColumn<FeeProposalItem, String> colStatus;
    @FXML
    private TableColumn<FeeProposalItem, String> colApprovalDate;

    private final ToggleGroup statusGroup = new ToggleGroup();
    private final List<FeeProposalItem> proposals = new ArrayList<>();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    private void initialize() {
        pendingRadio.setToggleGroup(statusGroup);
        approvedRadio.setToggleGroup(statusGroup);
        pendingRadio.setSelected(true);

        feeTypeCombo.getItems().setAll("Transaction", "Listing", "Membership", "Other");

        colProposalId.setCellValueFactory(new PropertyValueFactory<>("proposalId"));
        colFeeType.setCellValueFactory(new PropertyValueFactory<>("feeType"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colApprovalDate.setCellValueFactory(cell -> {
            LocalDate d = cell.getValue().getApprovalDate();
            String display = d == null ? "" : d.format(dateFormatter);
            return new javafx.beans.property.SimpleStringProperty(display);
        });

        proposalTable.getSelectionModel().selectedItemProperty().addListener((obs, o, sel) -> {
            if (sel != null) {
                proposalIdField.setText(sel.getProposalId());
                feeTypeCombo.setValue(sel.getFeeType());
                summaryArea.setText(sel.getSummary());
                if (Objects.equals(sel.getStatus(), "Approved")) approvedRadio.setSelected(true);
                else pendingRadio.setSelected(true);
            }
        });

        refreshTable();
    }

    @FXML
    private void onAnalyzeImpact(ActionEvent e) {
        String id = safe(proposalIdField.getText());
        String type = feeTypeCombo.getValue();
        LocalDate date = receivedDatePicker.getValue();
        String status = approvedRadio.isSelected() ? "Approved" : "Pending";
        String summary = safe(summaryArea.getText());
        if (id.isEmpty() || type == null || date == null || summary.isEmpty()) {
            showError("Missing Information", "Provide Proposal ID, Fee Type, Received Date, and Summary.");
            return;
        }
        FeeProposalItem existing = findById(id);
        if (existing != null) {
            existing.setFeeType(type);
            existing.setStatus(status);
            if ("Approved".equals(status)) existing.setApprovalDate(LocalDate.now());
            existing.setSummary(summary);
            proposalTable.getSelectionModel().select(existing);
        } else {
            LocalDate approval = "Approved".equals(status) ? LocalDate.now() : null;
            FeeProposalItem item = new FeeProposalItem(id, type, status, approval, summary);
            proposals.add(item);
            proposalTable.getSelectionModel().select(item);
        }
        refreshTable();
        showInfo("Impact Analyzed", "Proposal " + id + " analyzed.");
    }

    @FXML
    private void goBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/bsec/summer25section2/SiamShikder/BSEC_Officer/Main.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("BSEC Officer Dashboard");
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
            showError("Navigation Error", "Unable to navigate back to menu.");
        }
    }

    private FeeProposalItem findById(String id) {
        for (FeeProposalItem p : proposals) if (Objects.equals(p.getProposalId(), id)) return p;
        return null;
    }

    private void refreshTable() {
        proposalTable.getItems().setAll(proposals);
        proposalTable.refresh();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static String safe(String v) {
        return v == null ? "" : v.trim();
    }
}
