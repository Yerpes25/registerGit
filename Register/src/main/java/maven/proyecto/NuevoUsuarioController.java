/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package maven.proyecto;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import maven.model.FuncionHablar;
import maven.model.User;
import maven.util.GestorEstilos;

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
        
        
        adjudicarVoces();
        // Mensaje de bienvenida 
        FuncionHablar.hablar("Bienvenido al registro de usuario");
    }
    
    private void adjudicarVoces(){
    // AÑADIR VOZ A LOS ELEMENTOS 
        FuncionHablar.ponerVoz(btnCrear);
        FuncionHablar.ponerVoz(btnCancelar);
        FuncionHablar.ponerVoz(btnImagen);
        
        // A los campos de texto
        FuncionHablar.ponerVoz(tfNombre);
        FuncionHablar.ponerVoz(tfCorreo);
        FuncionHablar.ponerVoz(tfContrasenia);
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
                    "La contraseña debe tener:,8 a 15 caracteres.",
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("camara.fxml"));
            Parent root = loader.load();

            CamaraController controller = loader.getController();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setTitle("Cámara");

            // Cierra la cámara correctamente si cierran la ventana con la X
            stage.setOnCloseRequest(event -> controller.cerrarCamara());

            stage.showAndWait(); // Espera a que se cierre la ventana de la cámara

            // Recuperar la imagen
            if (controller.getImagenCapturada() != null) {
                imagenPerfil.setImage(controller.getImagenCapturada());
            }

        } catch (Exception e) {
            App.showAlert("Error", "No se pudo abrir la cámara: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

}
