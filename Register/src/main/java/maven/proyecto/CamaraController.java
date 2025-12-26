/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package maven.proyecto;

import com.github.sarxos.webcam.Webcam;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yerpes
 */
public class CamaraController implements Initializable {

    @FXML
    private Button btnCapturar;
    @FXML
    private ImageView idImagen;

    private Webcam webcam;
    private boolean isRunning = true;
    private Image imagenCapturada = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        iniciarWebcam();
    }

    private void iniciarWebcam() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    // Intenta obtener la segunda cámara, si falla usa la default
                    if (Webcam.getWebcams().size() > 1) {
                        webcam = Webcam.getWebcams().get(1);
                    } else {
                        webcam = Webcam.getDefault();
                    }

                    if (webcam != null) {
                        webcam.open();
                        while (isRunning) {
                            BufferedImage bImage = webcam.getImage();
                            if (bImage != null) {
                                Image fxImage = SwingFXUtils.toFXImage(bImage, null);
                                Platform.runLater(() -> idImagen.setImage(fxImage));
                            }
                            Thread.sleep(30);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    @FXML
    private void capturarFoto(ActionEvent event) {
        if (webcam != null) {
            BufferedImage bImage = webcam.getImage();
            this.imagenCapturada = SwingFXUtils.toFXImage(bImage, null);
            cerrarCamara();
            Stage stage = (Stage) btnCapturar.getScene().getWindow();
            stage.close();
        }
    }
    
    public Image getImagenCapturada() {
        return imagenCapturada;
    }
    
    public void cerrarCamara() {
        isRunning = false;
        if (webcam != null && webcam.isOpen()) {
            webcam.close();
        }
    }

}
