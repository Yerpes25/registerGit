/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maven.model;

import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputControl;
import maven.proyecto.App;

/**
 *
 * @author Yerpeesss
 */
public class FuncionHablar {
    
    private static boolean vozActivada = false;
    
    private static Process procesoActual;
    private static final Object BLOQUEO = new Object();
    
    public static void setVozActivada(boolean activada) {
        vozActivada = activada;
    }

    public static boolean estaVozActivada() {
        return vozActivada;
    }
    
    public static void hablar(String texto) {
        if (!vozActivada || texto == null || texto.trim().isEmpty()) {
            return;
        }

        new Thread(() -> {
            synchronized (BLOQUEO) {
                if (procesoActual != null && procesoActual.isAlive()) {
                    procesoActual.destroyForcibly();
                    procesoActual = null;
                }

                try {
                    String comando = "PowerShell -Command \"Add-Type –AssemblyName System.Speech; "
                            + "(New-Object System.Speech.Synthesis.SpeechSynthesizer).Speak('" + texto + "');\"";
                    procesoActual = Runtime.getRuntime().exec(comando);
                    
                } catch (Exception e) {
                    App.showAlert("Error", "No se pudo procesar la voz", Alert.AlertType.WARNING);
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
