package com.bsec.oop.sadman.Auditor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AuditorDashboardFxmlController
{




    @javafx.fxml.FXML
    public void initialize() {
    }

    private ActionEvent event;

    public void switchScene(ActionEvent event, String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }



    @javafx.fxml.FXML
    public void LogOutButt(ActionEvent actionEvent) throws IOException {
        switchScene(event, "Login.fxml");
    }


    @javafx.fxml.FXML
    public void SearchCompanyButt(ActionEvent actionEvent) throws IOException {
        switchScene(event, "auditor_search_compliance.fxml");
    }


    @javafx.fxml.FXML
    public void SendFeedbackButt(ActionEvent actionEvent) throws IOException {
        switchScene(event, "auditor_send_clarification.fxml");
    }

    @FXML
    public void flagReportsButt(ActionEvent actionEvent) throws IOException {
        switchScene(event, "auditor_flag_report.fxml");
    }

    @FXML
    public void AuditorPendingButt(ActionEvent actionEvent) throws IOException {
        switchScene(event, "auditor_pending_reports.fxml");
    }

    @FXML
    public void UpdateCompanyInfoButt(ActionEvent actionEvent) throws IOException {
        switchScene(event, "auditor_company_update_review.fxml");
    }

    @FXML
    public void ReviewSubmissionsButt(ActionEvent actionEvent) throws IOException {
        switchScene(event, "auditor_review_ipo.fxml");
    }

    @FXML
    public void SummaryReportButt(ActionEvent actionEvent) throws IOException {
        switchScene(event, "auditor_summary_report.fxml");
    }

    @FXML
    public void ExportIPOReportsButt(ActionEvent actionEvent) throws IOException {
        switchScene(event, "auditor_export_ipo.fxml");
    }
}