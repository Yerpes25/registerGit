/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maven.util;

import java.io.File;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import maven.proyecto.App;
import maven.proyecto.CamaraController;

/**
 *
 * @author Yerpes
 */
public class GestorImagen {

    public static void abrirCamara(ImageView destino, Class<?> claseContexto) {
        try {
            // Buscamos el fxml relativo a la clase que nos llama
            FXMLLoader loader = new FXMLLoader(claseContexto.getResource("camara.fxml"));
            Parent root = loader.load();

            CamaraController controller = loader.getController();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setTitle("Cámara");

            stage.setOnCloseRequest(event -> controller.cerrarCamara());

            stage.showAndWait();

            if (controller.getImagenCapturada() != null && destino != null) {
                destino.setImage(controller.getImagenCapturada());
            }

        } catch (Exception e) {
            App.showAlert("Error", "No se pudo abrir la cámara: ", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public static void configurarDragAndDropImagen(ImageView imagenDestino, Button btnImagen, Button btnBorrar) {
        // Detectar arrastre
        imagenDestino.setOnDragOver((DragEvent event) -> {
            if (event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        // Soltar archivo
        imagenDestino.setOnDragDropped((DragEvent event) -> {
            boolean exito = false;
            if (event.getDragboard().hasFiles()) {
                List<File> archivos = event.getDragboard().getFiles();
                if (!archivos.isEmpty()) {
                    File archivo = archivos.get(0);
                    try {
                        Image imagen = new Image(archivo.toURI().toString());
                        imagenDestino.setImage(imagen);

                        if (btnImagen != null) {
                            btnImagen.setDisable(true);   // Bloquear cámara
                        }
                        if (btnBorrar != null) {
                            btnBorrar.setDisable(false);  // Activar borrar
                        }
                        exito = true;
                    } catch (Exception e) {
                        App.showAlert("Error", "No se puede cargar la imagen ", Alert.AlertType.ERROR);
                    }
                }
            }
            event.setDropCompleted(exito);
            event.consume();
        });
    }
}
