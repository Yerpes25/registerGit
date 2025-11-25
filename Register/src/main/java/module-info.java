module maven.register {
    requires javafx.controls;
    requires javafx.fxml; 
    requires java.sql;
    requires java.base;

    opens maven.register to javafx.fxml;
    opens maven.model to javafx.base;
    exports maven.register;
}
