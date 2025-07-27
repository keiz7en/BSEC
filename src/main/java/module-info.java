module com.bsec.bsec {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.bsec.oop to javafx.fxml;
    opens com.bsec.oop.Investor to javafx.fxml;
    exports com.bsec.oop;
    exports com.bsec.oop.Investor;
}
