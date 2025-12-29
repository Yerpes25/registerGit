/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package maven.proyecto;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
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
    @FXML
    private Button btnBorrarImagen;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        ponerImagenDefecto();
        
        /*Inicializamos los gestores*/
        GestorEstilos.cargarEstilos(anchorPane);
        GestorTactil.hacerInteractable(anchorPane);
        
        /*Mandamos la imagen que hemos arrastrado */
        GestorImagen.configurarDragAndDropImagen(imagenPerfil, btnImagen, btnBorrarImagen);

        /*Ponemos el boton que no se pueda usar*/
        btnBorrarImagen.setDisable(true);

        /*Mandamos a funcion hablar los elementos que queremos que hablen*/
            GestorHablar.adjudicarVoces(btnCrear, btnImagen, btnCancelar,
                    tfContrasenia, tfCorreo, tfNombre, btnBorrarImagen);

            // Mensaje de bienvenida 
            FuncionHablar.hablar("Bienvenido al registro de usuario");
        
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

        InputStream flujoImagen = null;

        try {
            if (imagenPerfil.getImage() != null) {
                // Convertimos la imagen de JavaFX a BufferedImage
                BufferedImage bImage = SwingFXUtils.fromFXImage(imagenPerfil.getImage(), null);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                // Escribimos en formato PNG en el flujo
                ImageIO.write(bImage, "png", outputStream);
                // Preparamos el flujo de entrada para la BD
                flujoImagen = new ByteArrayInputStream(outputStream.toByteArray());
            }
        } catch (Exception e) {
            App.showAlert("Error", "No se pudo procesar la imagen.", Alert.AlertType.WARNING);
        }

        boolean exito = model.insertarUsuarios(usuario, email, pass, flujoImagen);

        if (exito) {
            // Cerrar la ventana al terminar
            Stage stage = (Stage) btnCrear.getScene().getWindow();
            stage.close();
        }
    }

    // Metodo par salirme de la aplicación si le doy a cancelar
    @FXML
    private void cancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    //Metodo para abrir la camara y echar foto
    @FXML
    private void abrirCamara(ActionEvent event) {
        // Guardamos la imagen que estaba antes, que tiene que ser la de por defecto
        Image imagenAnterior = imagenPerfil.getImage();
        
        //Nos metemos en el gestor para echar la foto
        GestorImagen.abrirCamara(imagenPerfil, getClass());
        
        /* Si la imagen no es la misma que la del anterior ponemos los botones 
        para que no se puedan seleccionar ninguna otra y solo borrar*/
        if (imagenPerfil.getImage() != imagenAnterior) {
            btnImagen.setDisable(true);
            btnBorrarImagen.setDisable(false);

        }
    }

    /* Metodo para borrar la imagen y poner otra*/
    @FXML
    private void borrarImagen(ActionEvent event) {
        imagenPerfil.setImage(null);
        btnImagen.setDisable(false);
        btnBorrarImagen.setDisable(true);
        ponerImagenDefecto();
    }

    /*Metodo para poner la imagen por defecto si no hay foto*/
    private void ponerImagenDefecto() {
        try {
            Image imagen = new Image(getClass().getResourceAsStream("asset/image/ImagenPerfil.png"));
            imagenPerfil.setImage(imagen);
        } catch (Exception e) {
            App.showAlert("Error", "No se puede encontrar la imagen.", Alert.AlertType.WARNING);
        }
    }
}
