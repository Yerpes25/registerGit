/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maven.util;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/**
 *
 * @author Usuario
 */
public class GestorTactil {
    private static double ratonX, ratonY;
    private static double nodoX, nodoY;
    private static double anguloInicial;
    
    public static void hacerInteractable(Node nodo) {

        // 1. AL PULSAR EL RATÓN (Guardamos coordenadas iniciales)
        nodo.setOnMousePressed((MouseEvent event) -> {
            // Guardamos dónde estaba el ratón
            ratonX = event.getSceneX();
            ratonY = event.getSceneY();
            
            // Guardamos dónde estaba la imagen (para moverla)
            nodoX = nodo.getTranslateX();
            nodoY = nodo.getTranslateY();
            
            // Guardamos el ángulo actual (para rotarla)
            anguloInicial = nodo.getRotate();
        });

        // 2. AL ARRASTRAR EL RATÓN (Calculamos el movimiento manual)
        nodo.setOnMouseDragged((MouseEvent event) -> {
            
            if (event.isPrimaryButtonDown()) {
                // --- MOVIMIENTO (Click Izquierdo) ---
                // Nueva posición = Posición guardada + (Distancia movida por el ratón)
                nodo.setTranslateX(nodoX + (event.getSceneX() - ratonX));
                nodo.setTranslateY(nodoY + (event.getSceneY() - ratonY));
                
            } else if (event.isSecondaryButtonDown()) {
                // --- ROTACIÓN (Click Derecho) ---
                // Calculamos cuánto se ha movido el ratón horizontalmente
                double deltaX = event.getSceneX() - ratonX;
                // Rotamos proporcionalmente al movimiento
                nodo.setRotate(anguloInicial + deltaX);
            }
        });

        // 3. ZOOM MANUAL (Rueda del ratón)
        nodo.setOnScroll((ScrollEvent event) -> {
            double zoomFactor = 1.05;
            double deltaY = event.getDeltaY();

            if (deltaY < 0) {
                zoomFactor = 0.95; // Alejar
            }

            // Aplicamos la escala matemática manualmente
            nodo.setScaleX(nodo.getScaleX() * zoomFactor);
            nodo.setScaleY(nodo.getScaleY() * zoomFactor);

            event.consume();
        });
    }
}