package com.bsec.oop.sadman.Company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.html.HTMLBodyElement;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class CompanyDashboardFxmlController
{

    @FXML
    private TextField companyPasswordTF;
    @FXML
    private TextField companyIdTF;

    @FXML
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




    @FXML
    public void LogoutButt(ActionEvent actionEvent) throws IOException {
        switchScene(actionEvent, "/com/bsec/summer25section2/Login.fxml");
    }

    @FXML
    public void LoginButt(ActionEvent actionEvent) {

        UserSadman foundCompany = null;
        boolean found = false;
        try {
            FileInputStream fis = new FileInputStream("UserSadman.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);

            while (true){
                UserSadman d = (UserSadman) ois.readObject();
                if ((Integer.parseInt(companyIdTF.getText()) == d.getId()) && companyPasswordTF.getText().equals(d.getPassword())){
                    switchScene(actionEvent, "/com/bsec/summer25section2/sadman/company/Main.fxml");
                }
            }
        }
        catch (IOException | ClassNotFoundException e1){
            //
        }

    }

    @FXML
    public void createNewAccountButt(ActionEvent actionEvent) throws IOException {
        switchScene(actionEvent, "/com/bsec/summer25section2/sadman/company/createUser.fxml");
    }
}