/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package maven.proyecto;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import maven.model.FuncionHablar;
import maven.model.User;
import maven.util.GestorEstilos;
import maven.util.GestorHablar;
import maven.util.GestorImagen;
import maven.util.GestorTactil;

/**
 * FXML Controller class
 *
 * @author Yerpes
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
    @FXML
    private VBox anchorPane;
    @FXML
    private ImageView imagenPerfil;
    @FXML
    private Button btnImagen;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        GestorEstilos.cargarEstilos(anchorPane);
        GestorTactil.hacerInteractable(anchorPane);
        GestorImagen.configurarDragAndDrop(imagenPerfil, btnImagen);

        if (FuncionHablar.estaVozActivada()) {
            GestorHablar.adjudicarVoces(btnCrear, btnImagen, btnCancelar,
                    tfContrasenia, tfCorreo, tfNombre);

            // Mensaje de bienvenida 
            FuncionHablar.hablar("Bienvenido al registro de usuario");
        }

    }

    @FXML
    public void crearUsuario() {
        String usuario = tfNombre.getText();
        String email = tfCorreo.getText();
        String pass = tfContrasenia.getText();

        String regexPass = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,15}$";

        String regexEmail = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

        if (usuario.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            App.showAlert("Faltan datos", "Por favor, rellena todos los campos.", Alert.AlertType.WARNING);
            return;
        }

        if (!email.matches(regexEmail)) {
            App.showAlert("Email inválido", "El formato del correo no es correcto.", Alert.AlertType.WARNING);
            return;
        }

        if (!pass.matches(regexPass)) {
            App.showAlert("Contraseña insegura",
                    "La contraseña debe tener:,8 a 15 caracteres, una letra mayuscula y un simbolo especial.",
                    Alert.AlertType.WARNING);
            return;
        }

        boolean exito = model.insertarUsuarios(usuario, email, pass);

        if (exito) {
            App.showAlert("Éxito", "Usuario creado correctamente.", Alert.AlertType.INFORMATION);
            // Cerrar la ventana al terminar
            Stage stage = (Stage) btnCrear.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void abrirCamara() {
        GestorImagen.abrirCamara(imagenPerfil, getClass());
    }

}
