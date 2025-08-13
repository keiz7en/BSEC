package com.bsec.oop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/bsec/summer25section2/Login.fxml"));
        fxmlLoader.setClassLoader(HelloApplication.class.getClassLoader());
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("BSEC Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
