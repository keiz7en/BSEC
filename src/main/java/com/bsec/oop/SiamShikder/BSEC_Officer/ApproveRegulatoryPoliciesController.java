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

public class ApproveRegulatoryPoliciesController {

    @FXML
    private TextField policyIdField;
    @FXML
    private ComboBox<String> categoryCombo;
    @FXML
    private DatePicker dateReceivedPicker;
    @FXML
    private RadioButton highPriorityRadio;
    @FXML
    private RadioButton normalPriorityRadio;
    @FXML
    private TextArea summaryArea;
    @FXML
    private Button reviewButton;
    @FXML
    private Button approveButton;

    @FXML
    private TableView<PolicyItem> policyTable;
    @FXML
    private TableColumn<PolicyItem, String> colPolicyId;
    @FXML
    private TableColumn<PolicyItem, String> colCategory;
    @FXML
    private TableColumn<PolicyItem, String> colStatus;
    @FXML
    private TableColumn<PolicyItem, String> colApprovalDate;

    private final ToggleGroup urgencyGroup = new ToggleGroup();
    private final List<PolicyItem> policies = new ArrayList<>();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    private void initialize() {
        highPriorityRadio.setToggleGroup(urgencyGroup);
        normalPriorityRadio.setToggleGroup(urgencyGroup);
        normalPriorityRadio.setSelected(true);

        categoryCombo.getItems().setAll("Financial", "Trading", "Corporate Governance", "Other");

        colPolicyId.setCellValueFactory(new PropertyValueFactory<>("policyId"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colApprovalDate.setCellValueFactory(cell -> {
            LocalDate d = cell.getValue().getApprovalDate();
            String display = d == null ? "" : d.format(dateFormatter);
            return new javafx.beans.property.SimpleStringProperty(display);
        });

        policyTable.getSelectionModel().selectedItemProperty().addListener((obs, o, sel) -> {
            if (sel != null) {
                policyIdField.setText(sel.getPolicyId());
                categoryCombo.setValue(sel.getCategory());
                summaryArea.setText(sel.getSummary());
                if (Objects.equals(sel.getUrgency(), "High Priority")) highPriorityRadio.setSelected(true);
                else normalPriorityRadio.setSelected(true);
            }
        });

        refreshTable();
    }

    @FXML
    private void onReviewPolicy(ActionEvent e) {
        String id = safe(policyIdField.getText());
        String cat = categoryCombo.getValue();
        LocalDate date = dateReceivedPicker.getValue();
        String urgency = highPriorityRadio.isSelected() ? "High Priority" : "Normal Priority";
        String summary = safe(summaryArea.getText());
        if (id.isEmpty() || cat == null || date == null || summary.isEmpty()) {
            showError("Missing Information", "Provide Policy ID, Category, Date Received, and Summary.");
            return;
        }
        PolicyItem existing = findById(id);
        if (existing != null) {
            existing.setCategory(cat);
            existing.setSummary(summary);
            existing.setUrgency(urgency);
            existing.setStatus("Reviewed");
            policyTable.getSelectionModel().select(existing);
        } else {
            PolicyItem item = new PolicyItem(id, cat, "Reviewed", null, summary, urgency);
            policies.add(item);
            policyTable.getSelectionModel().select(item);
        }
        refreshTable();
        showInfo("Policy Reviewed", "Policy " + id + " marked as Reviewed.");
    }

    @FXML
    private void onApproveFinalPolicy(ActionEvent e) {
        String id = safe(policyIdField.getText());
        PolicyItem item = id.isEmpty() ? policyTable.getSelectionModel().getSelectedItem() : findById(id);
        if (item == null) {
            showError("No Selection", "Select a policy or enter a Policy ID to approve.");
            return;
        }
        item.setStatus("Approved");
        item.setApprovalDate(LocalDate.now());
        refreshTable();
        showInfo("Policy Approved", "Policy " + item.getPolicyId() + " approved.");
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

    private PolicyItem findById(String id) {
        for (PolicyItem p : policies) if (Objects.equals(p.getPolicyId(), id)) return p;
        return null;
    }

    private void refreshTable() {
        policyTable.getItems().setAll(policies);
        policyTable.refresh();
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
