/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package maven.register;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import maven.model.User;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class NuevoUsuarioController implements Initializable {

    @FXML
    private Button btnCrear;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfContrasenia;
    private User model = new User();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }


    public void comprobarTextos(){
        String usuario = tfNombre.getText();
        String email = tfNombre.getText();
        String pass = tfContrasenia.getPassword();
        String passConf = new String(txtConfirmar.getPassword());

        // Regla de la imagen traducida a Java
        String regexPass = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,15}$";

        // Validaciones
        if (usuario.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Faltan datos");
        } 
        else if (!pass.equals(passConf)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden");
        } 
        else if (!pass.matches(regexPass)) {
            JOptionPane.showMessageDialog(this, "La contraseña no es segura (8-15 chars, Mayus, Minus, Num, Simbolo)");
        } 
        else {
            // Si todo está bien, llamamos al modelo
            guardarEnBaseDeDatos(usuario, email, pass);
        }
    }

    public void crearUsuario(){
        comprobarTextos();
        model.insertarUsuarios(tfNombre.getText(), tfCorreo.getText(), tfContrasenia.getText());
    }    
}
