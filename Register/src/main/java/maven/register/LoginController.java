/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maven.register;

/**
 *
 * @author PON TU NOMBRE
 */
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import maven.model.User;

//import java.net.URL;
//import javafx.scene.image.Image;
public class LoginController {

    private User model = new User();

    @FXML
    private TextField jtfUser;
    @FXML
    private PasswordField jpfPassword;
    @FXML
    private Label jlTitle;
    @FXML
    private Label jlUser;
    @FXML
    private Label jlPassword;
    @FXML
    private Button jbLogin;
    @FXML
    private ImageView jivLogo;
    
    
    public void initialize() {
       
    }
    
    @FXML
    private void loginAction(ActionEvent event) {
        Object evt = event.getSource();
         if (evt.equals(jbLogin)) {
            login();
         }
    }

    
    @FXML
    private void loginKeyPressed(KeyEvent event) {
        System.out.println("He pulsado ENTER");
        if(event.getCode().equals(KeyCode.ENTER)) {
            login();
        }
    }
    
    @FXML
    private void registerAction(ActionEvent event) throws IOException {
        Object evt = event.getSource();
            register();
         
    }
    
    @FXML
    private void registrarKeyPressed(KeyEvent event) throws IOException{
        if(event.getCode().equals(KeyCode.ENTER)) {
            register();
        }
    }
    
    /* Método para acceso a la aplicación mediante verificación de usuario */
    private void login(){
        
        try {
            if (!jtfUser.getText().isEmpty() && !jpfPassword.getText().isEmpty()) {
                String user = jtfUser.getText();
                String pass = jpfPassword.getText();
                int state = model.login(user, pass);
                if (state == 1) {    // Si se ha hecho login se accede a la siguiente scene
                    model.setUsuarioActual(user);
                    App.setRoot("MainInventario");
                } else {
                    App.showAlert("Error detectado","Error al iniciar sesión. Datos de acceso incorrectos", Alert.AlertType.NONE);   
                }
            }
            else {
                App.showAlert("Error detectado","Error al iniciar sesión. Algún campo vacío", Alert.AlertType.NONE);                        
            }   
        } catch (IOException ex) {

        }
    }
    
    private void register() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("nuevoUsuario.fxml"));
   
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Añadir nuevo Usuario");
            stage.setScene(scene);
            stage.showAndWait();
    }
   
    
}
