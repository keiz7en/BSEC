module com.bsec.bsec {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.bsec.oop to javafx.fxml;
    opens com.bsec.oop.Fayshal.Investor to javafx.fxml;
    opens com.bsec.oop.Fayshal.StockExchange to javafx.fxml;
    opens com.bsec.oop.SiamShikder.BSEC_Officer to javafx.fxml;
    opens com.bsec.oop.SiamShikder.Regulator to javafx.fxml;
    exports com.bsec.oop;
    exports com.bsec.oop.Fayshal.Investor;
    exports com.bsec.oop.Fayshal.StockExchange;
    exports com.bsec.oop.SiamShikder.BSEC_Officer;
    exports com.bsec.oop.SiamShikder.Regulator;
}
