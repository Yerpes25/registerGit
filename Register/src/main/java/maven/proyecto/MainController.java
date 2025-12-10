/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maven.proyecto;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

/**
 *
 * @author PON TU NOMBRE
 */
public class MainController {

    @FXML
    private MenuItem jmiQuit;
    @FXML
    private MenuItem jmiAbout;

    @FXML
    private void ActionQuit(ActionEvent event) {
        Platform.exit();
    }
    
}
