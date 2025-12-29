/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package maven.proyecto;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.VBox;
import maven.model.FuncionHablar;
import maven.model.Producto;
import maven.util.GestorEstilos;
import maven.util.GestorHablar;
import maven.util.GestorTactil;

/**
 * FXML Controller class
 *
 * @author Yerpes
 */
public class DiagramaController implements Initializable {

    // Creamos las variables que necesitemos, suelen cargarse solas con el fxml
    @FXML
    private BarChart<String, Integer> tablaGrafico;
    @FXML
    private NumberAxis ejeY;
    @FXML
    private CategoryAxis ejeX;
    @FXML
    private ColorPicker colorPicker;

    XYChart.Series<String, Integer> series = new XYChart.Series<>();

    // Creamos un nuevo producto llamado model
    private Producto model;
    @FXML
    private VBox anchorPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Inicializamos model con el constructor y llamamos al metodo cargarDatosGrafico
        model = new Producto();

        GestorEstilos.cargarEstilos(anchorPane);
        GestorTactil.hacerInteractable(anchorPane);
        GestorHablar.adjudicarVoces(colorPicker);

        FuncionHablar.hablar("Diagrama de los productos");

        cargarDatosGrafico();
    }

    // Metodo para cargar los datos de la base de datos a la grafica 
    private void cargarDatosGrafico() {
        //Metemos en una lista todos los datos de la base de datos
        ObservableList<Producto> listaProductos = model.cargarDatos();

        // Recorre la lista y añade series añadiendo el codigo como x y la cantidad como y
        for (Producto p : listaProductos) {
            series.getData().add(new XYChart.Data<>(p.getCodigo(), p.getCantidad()));
        }
        tablaGrafico.getData().add(series);
    }

    @FXML
    public void cambiarColor(ActionEvent event) {
        String color = "#" + colorPicker.getValue().toString().substring(2, 8);

        for (XYChart.Data<String, Integer> dato : series.getData()) {
            dato.getNode().setStyle("-fx-bar-fill: " + color + ";");
        }
    }
}
