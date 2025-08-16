package com.bsec.oop.sadman.Company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main
{



    @javafx.fxml.FXML
    public void initialize() {
    }


    private static final String COMPANY_BASE_PATH = "/com/bsec/summer25section2/sadman/company/";
    
    public void switchScene(ActionEvent event, String fxmlFile) throws IOException {
        String path = fxmlFile.startsWith("/") ? fxmlFile : COMPANY_BASE_PATH  + fxmlFile;
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @javafx.fxml.FXML
    public void goToLogin(ActionEvent actionEvent) throws IOException {
        switchScene(actionEvent, "/com/bsec/summer25section2/Login.fxml");

    }

    @FXML
    public void ViewSubmittedReportsButt(ActionEvent actionEvent) throws IOException {
        switchScene(actionEvent, "company_search_ipo.fxml");
    }

    @javafx.fxml.FXML
    public void UpdateCompanyProfileButt(ActionEvent actionEvent) throws IOException {
        switchScene(actionEvent, "company_update_profile.fxml");
    }

    @javafx.fxml.FXML
    public void SendQuerytoAuditorButt(ActionEvent actionEvent) throws IOException {
        switchScene(actionEvent, "company_message_auditor.fxml");
    }

    @javafx.fxml.FXML
    public void UploadComplianceDocumentsButt(ActionEvent actionEvent) throws IOException {
        switchScene(actionEvent, "company_upload_report.fxml");
    }

    @javafx.fxml.FXML
    public void ViewAuditStatusButt(ActionEvent actionEvent) throws IOException {
        switchScene(actionEvent, "company_view_ipo_status.fxml");
    }

    @javafx.fxml.FXML
    public void SubmitfinancialreportButt(ActionEvent actionEvent) throws IOException {
        switchScene(actionEvent, "company_submit_ipo.fxml");
    }

    @javafx.fxml.FXML
    public void DownloadComplianceGuidelinesButt(ActionEvent actionEvent) throws IOException {
        switchScene(actionEvent, "company_download_certificate.fxml");
    }

}