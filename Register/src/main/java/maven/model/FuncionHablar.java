/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maven.model;

import java.io.IOException;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputControl;

/**
 *
 * @author Yerpeesss
 */
public class FuncionHablar {
    
    private static boolean vozActivada = true;
    
    private static Process procesoActual;
    private static final Object BLOQUEO = new Object();
    
    public static void setVozActivada(boolean activada) {
        vozActivada = activada;
        if (!activada) {
            detenerSilencio(); // Si desactivan la voz, callamos lo que esté sonando
        }
    }

    public static boolean isVozActivada() {
        return vozActivada;
    }
    
    public static void hablar(String texto) {
        if (!vozActivada || texto == null || texto.trim().isEmpty()) return;

        new Thread(() -> {
            // Este bloque asegura que NUNCA haya dos hilos creando voz a la vez
            synchronized (BLOQUEO) {
                // 1. Primero matamos al que esté sonando (si lo hay)
                if (procesoActual != null && procesoActual.isAlive()) {
                    procesoActual.destroyForcibly();
                    procesoActual = null;
                }

                // 2. Ahora creamos el nuevo
                try {
                    String comando = "PowerShell -Command \"Add-Type –AssemblyName System.Speech; (New-Object System.Speech.Synthesis.SpeechSynthesizer).Speak('" + texto + "');\"";
                    procesoActual = Runtime.getRuntime().exec(comando);
                    
                    // (Opcional) Esperamos un poco para asegurar que el proceso arranca antes de soltar el bloqueo
                    // Thread.sleep(50); 
                } catch (Exception e) {
                    System.err.println("Error voz: " + e.getMessage());
                }
            }
        }).start();
    }

    /*  MÉTODOS PONER VOZ */

    public static void ponerVoz(Labeled elemento) {
        if (elemento != null) {
            elemento.setOnMouseEntered(e -> hablar(elemento.getText()));
        }
    }

    public static void ponerVoz(TextInputControl campo) {
        if (campo != null) {
            campo.setOnMouseEntered(e -> {
                String txt = (campo.getText() != null && !campo.getText().isEmpty()) ? campo.getText() : campo.getPromptText();
                hablar(txt);
            });
        }
    }

    public static void ponerVoz(ComboBox<?> combo) {
        if (combo != null) {
            combo.setOnMouseEntered(e -> {
                String txt = (combo.getValue() != null) ? combo.getValue().toString() : combo.getPromptText();
                hablar(txt);
            });
        }
    }
    
    private static void detenerSilencio() {
        synchronized (BLOQUEO) {
            if (procesoActual != null && procesoActual.isAlive()) {
                procesoActual.destroyForcibly();
            }
        }
    }
    
    private static synchronized void setProcesoActual(Process p) {
        procesoActual = p;
    }
    
}
