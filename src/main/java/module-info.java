module com.bsec.bsec {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires jdk.xml.dom;
    requires javafx.base;



    opens com.bsec.oop to javafx.fxml;
    opens com.bsec.oop.Fayshal.Investor to javafx.fxml;
    opens com.bsec.oop.Fayshal.StockExchange to javafx.fxml;
    opens com.bsec.oop.SiamShikder.BSEC_Officer to javafx.fxml;
    opens com.bsec.oop.SiamShikder.Regulator to javafx.fxml;
    opens com.bsec.oop.sadman.Auditor to javafx.fxml;
    opens com.bsec.oop.sadman.Company to javafx.fxml;
    opens com.bsec.oop.Abid.Analyst to javafx.fxml;
    opens com.bsec.oop.Abid.Broker to javafx.fxml;
    opens com.bsec.oop.Fayshal.StockExchange.model to javafx.base, javafx.fxml;
    exports com.bsec.oop;
    exports com.bsec.oop.Fayshal.Investor;
    exports com.bsec.oop.Fayshal.StockExchange;
    exports com.bsec.oop.SiamShikder.BSEC_Officer;
    exports com.bsec.oop.SiamShikder.Regulator;
    exports com.bsec.oop.Fayshal.Investor.model;
    exports com.bsec.oop.Fayshal.StockExchange.model;
    exports com.bsec.oop.sadman.Company;
    opens com.bsec.oop.Fayshal.Investor.model to javafx.base, javafx.fxml;

}
