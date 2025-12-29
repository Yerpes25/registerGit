/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maven.util;

import java.net.URL;
import javafx.scene.Parent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import maven.model.Configuracion;

/**
 *
 * @author Yerpeesss
 */
public class GestorEstilos {
    
    private static void refrescarVista(Parent nodo) {
        if (nodo == null) return;

        String estilo = "";
        estilo += "-fx-font-family: '" + Configuracion.tipoFuente + "'; ";
        estilo += "-fx-font-size: " + Configuracion.tamanoLetra + "px;";
        
        nodo.setStyle(estilo);

        nodo.getStyleClass().remove("oscuro");
        if (Configuracion.esModoOscuro) {
            if (!nodo.getStyleClass().contains("oscuro")) {
                nodo.getStyleClass().add("oscuro");
            }
        }
    }

    public static void cargarEstilos(Parent nodo) {
        refrescarVista(nodo);
    }

    public static void setModoOscuro(boolean activo, Parent nodo) {
        Configuracion.esModoOscuro = activo;
        refrescarVista(nodo);
    }

    public static void aumentarFuente(Parent nodo) {
        if (Configuracion.tamanoLetra < 24) {
            Configuracion.tamanoLetra += 1;
            refrescarVista(nodo);
        }
    }

    public static void disminuirFuente(Parent nodo) {
        if (Configuracion.tamanoLetra > 10) {
            Configuracion.tamanoLetra -= 1;
            refrescarVista(nodo);
        }
    }

    public static void setFuente(String fuente, Parent nodo) {
        Configuracion.tipoFuente = fuente;
        refrescarVista(nodo);
    }
    
    public static void reproducir(String tipo) {
        if (!Configuracion.sonidoActivo) {
            return;
        }

        try {
            String archivo = "";
            
            // 2. Seleccionamos el archivo
            if (tipo.equals("exito")) {
                archivo = "exito.mp3"; 
            } else if (tipo.equals("error")) {
                archivo = "error.mp3";
            }

            URL recurso = GestorEstilos.class.getResource("/maven/proyecto/asset/sound/" + archivo);

            if (recurso != null) {
                Media media = new Media(recurso.toExternalForm());
                MediaPlayer player = new MediaPlayer(media);
                
                player.play();
                
            } else {
                System.out.println("No se encontró el archivo de audio: " + archivo);
            }

        } catch (Exception e) {
            System.out.println("Error en Gestor Estilos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}