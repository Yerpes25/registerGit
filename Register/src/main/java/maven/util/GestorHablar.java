/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maven.util;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import maven.model.FuncionHablar;
import maven.model.Ubicacion;

/**
 *
 * @author Yerpes
 */
public class GestorHablar {

    public static void adjudicarVoces(Node... elementos) {
        for (Node nodo : elementos) {
            // Si es un Botón, Label, Checkbox...
            if (nodo instanceof Labeled) {
                FuncionHablar.ponerVoz((Labeled) nodo);
            } // Si es un TextField, TextArea, PasswordField...
            else if (nodo instanceof TextInputControl) {
                FuncionHablar.ponerVoz((TextInputControl) nodo);
            } // Si es un ComboBox
            else if (nodo instanceof ComboBox) {
                FuncionHablar.ponerVoz((ComboBox) nodo);
            }
        }
    }

    public static void ponerVozComboBox(ComboBox<Ubicacion> combo) {

        combo.setCellFactory(new Callback<ListView<Ubicacion>, ListCell<Ubicacion>>() {
            @Override
            public ListCell<Ubicacion> call(ListView<Ubicacion> param) {
                return new ListCell<Ubicacion>() {
                    @Override
                    protected void updateItem(Ubicacion item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                            setOnMouseEntered(null);
                        } else {
                            setText(item.toString());

                            setOnMouseEntered(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    // Comprobamos si la voz esta activa antes de hablar
                                    if (FuncionHablar.estaVozActivada()) {
                                        FuncionHablar.hablar(item.toString());
                                    }
                                }
                            });
                        }
                    }
                };
            }
        });
    }
}
