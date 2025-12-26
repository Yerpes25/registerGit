/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maven.util;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputControl;
import maven.model.FuncionHablar;

/**
 *
 * @author Usuario
 */
public class GestorHablar {
    public static void adjudicarVoces(Node... elementos) {
        for (Node nodo : elementos) {
            // Si es un Botón, Label, Checkbox...
            if (nodo instanceof Labeled) {
                FuncionHablar.ponerVoz((Labeled) nodo);
            } 
            // Si es un TextField, TextArea, PasswordField...
            else if (nodo instanceof TextInputControl) {
                FuncionHablar.ponerVoz((TextInputControl) nodo);
            } 
            // Si es un ComboBox
            else if (nodo instanceof ComboBox) {
                FuncionHablar.ponerVoz((ComboBox) nodo);
            }
        }
    }
}
