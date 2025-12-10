module maven.proyecto {
    requires javafx.controls;
    requires javafx.fxml; 
    requires java.sql;
    requires java.base;
    requires javafx.media;

    opens maven.proyecto to javafx.fxml;
    opens maven.model to javafx.base;
    exports maven.proyecto;
}
