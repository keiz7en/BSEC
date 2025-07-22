module com.bsec.bsec {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.bsec.bsec to javafx.fxml;
    opens com.bsec.bsec.Investor to javafx.fxml;
    exports com.bsec.bsec;
    exports com.bsec.bsec.Investor;
}
