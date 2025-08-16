package com.bsec.oop.sadman.Company;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;

public class CompanySubmitIpoFxmlController
{
    @javafx.fxml.FXML
    private TextField companyNameTF;
    @javafx.fxml.FXML
    private DatePicker dateDP;
    @javafx.fxml.FXML
    private TextArea ipoDetailsTA;
    @javafx.fxml.FXML
    private TextField ipoAmountTF;

    @javafx.fxml.FXML
    public void initialize() {
    }

    private static final String COMPANY_BASE_PATH = "/com/bsec/summer25section2/sadman/company/";
    public void switchScene(ActionEvent event, String fxmlFile) throws IOException {
        String path = fxmlFile.startsWith("/") ? fxmlFile : COMPANY_BASE_PATH + fxmlFile;
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


    @javafx.fxml.FXML
    public void goBackButt(ActionEvent actionEvent) throws IOException {
        switchScene(actionEvent, "Main.fxml");

    }

    @javafx.fxml.FXML
    public void submitButt(ActionEvent actionEvent) throws IOException {

        ObjectOutputStream oos1;
        File f = new File("IPODetails.bin");
        if (f.exists()){
            FileOutputStream fos1 = new FileOutputStream(f ,true);
            oos1 = new Appendable(fos1);
        }
        else{
            FileOutputStream fos = new FileOutputStream(f ,true);
            oos1 = new ObjectOutputStream(fos);
        }

        IPODetails p = new IPODetails(
                dateDP.getValue(),
                companyNameTF.getText(),
                Integer.parseInt(ipoAmountTF.getText()),
                ipoDetailsTA.getText());

        oos1.writeObject(p);
        oos1.close();

        IPODetails u = null;

        FileInputStream fis1 = new FileInputStream("IPODetails.bin");
        ObjectInputStream ois1 = new ObjectInputStream(fis1);

        try{
            while (true){
                u = (IPODetails) ois1.readObject();
                Alert a = new Alert(Alert.AlertType.INFORMATION, u.toString());
                a.showAndWait();

            }
        }
        catch (EOFException e){
            //
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}