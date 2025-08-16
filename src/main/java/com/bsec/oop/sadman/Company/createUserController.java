package com.bsec.oop.sadman.Company;

import com.bsec.oop.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class createUserController {
    @javafx.fxml.FXML
    private TextField companyTotalShareTF;
    @javafx.fxml.FXML
    private TextField companyNameTF;
    @javafx.fxml.FXML
    private TextField companyPasswordTF;
    @javafx.fxml.FXML
    private TextField companyIdTF;
    @javafx.fxml.FXML
    private TextField companyAvailbeShareTF;

    private static final String COMPANY_BASE_PATH = "/com/bsec/summer25section2/sadman/company/";

    public void switchScene(ActionEvent event, String fxmlFile) throws IOException {
        String path = fxmlFile.startsWith("/") ? fxmlFile : COMPANY_BASE_PATH  + fxmlFile;
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


    @javafx.fxml.FXML
    public void LogoutButt(ActionEvent actionEvent) throws IOException {

        switchScene(actionEvent, "/com/bsec/summer25section2/Login.fxml");

    }

    @javafx.fxml.FXML
    public void CreateNewAccountButt(ActionEvent actionEvent) throws IOException {
        ObjectOutputStream oos;
        File f = new File("UserSadman.bin");
        if (f.exists()){
            FileOutputStream fos = new FileOutputStream(f ,true);
            oos = new Appendable(fos);
        }
        else{
            FileOutputStream fos = new FileOutputStream(f ,true);
            oos = new ObjectOutputStream(fos);
        }

        UserSadman p = new UserSadman(
                companyNameTF.getText(),
                Integer.parseInt(companyIdTF.getText()),
                companyPasswordTF.getText(),
                Integer.parseInt(companyTotalShareTF.getText()),
                Integer.parseInt(companyAvailbeShareTF.getText()));

        oos.writeObject(p);
        oos.close();
        switchScene(actionEvent, "/com/bsec/summer25section2/sadman/company/Main.fxml");

    }
}
