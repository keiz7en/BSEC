module com.bsec.bsec {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.bsec.bsec to javafx.fxml;
    exports com.bsec.bsec;
}