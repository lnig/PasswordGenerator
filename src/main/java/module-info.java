module org.lnig.passwordgenerator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens org.lnig.passwordgenerator to javafx.fxml;
    exports org.lnig.passwordgenerator;
}