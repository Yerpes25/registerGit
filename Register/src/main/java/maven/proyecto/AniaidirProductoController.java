/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package maven.proyecto;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import maven.model.Producto;
import maven.model.Ubicacion;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class AniaidirProductoController implements Initializable {

    //Creamos las variables que necesitamos
    @FXML
    private TextField nmiCodigo;
    @FXML
    private TextField nmiDesc;
    @FXML
    private TextField nmiCantidad;
    @FXML
    private ComboBox<Ubicacion> nmiUbi;
    @FXML
    private Button nmiCancelar;
    @FXML
    private Button nmiGuardar;

    private static Producto p = null;
    private ObservableList<Ubicacion> olUbi = FXCollections.observableArrayList();
    private Ubicacion modelUbi = null;
    private Producto modelCodigo = null; 
    private ObservableList<String> olCodigo = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Cargamos los datos en la tabla de la base de datos
        // Usamos el modelUbi para usar sus metodos, con un constructor vacío,
        // cargamos en lista de ubicaciones que se llama olUbi todos los datos de Ubicacion
        // y por ultimo, seleccionamos todos los items que contiene olUbi para mostrarlos.
        modelUbi = new Ubicacion();
        olUbi = modelUbi.cargarUbicacion();
        nmiUbi.setItems(olUbi);
        
        //Lo mismo que con ubicaciones pero ahora con productos
        modelCodigo = new Producto();
        olCodigo = modelCodigo.cargarCodigoProductos();
    }

    // Metodo para guardar producto en la base de datos
    public void guardarProducto() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error al añadir producto");

        // Creamos las variables que necesitamos sin espacios
        String codigo = nmiCodigo.getText().trim();
        String descripcion = nmiDesc.getText().trim();
        String cantidadStr = nmiCantidad.getText().trim();
        Ubicacion ubicacionSeleccionada = nmiUbi.getValue();

        // Validar campos vacios
        if (codigo.isEmpty() || descripcion.isEmpty() || cantidadStr.isEmpty()) {
            alert.setContentText("Ningún campo puede estar vacio.");
            alert.showAndWait();
            return;
        }

        // Validar que se ha seleccionado una ubicación
        if (ubicacionSeleccionada == null) {
            alert.setContentText("Debes seleccionar una ubicación.");
            alert.showAndWait();
            return;
        }

        // Validar que la cantidad sea un numero
        int cantidadNum;
        try {
            cantidadNum = Integer.parseInt(cantidadStr);
        } catch (NumberFormatException e) {
            alert.setContentText("La cantidad debe ser un número entero válido.");
            alert.showAndWait();
            return;
        }

        // Validar que la cantidad sea positiva
        if (cantidadNum <= 0) {
            alert.setContentText("La cantidad debe ser un número positivo mayor que cero.");
            alert.showAndWait();
            return;
        }

        // Validar formato del codigo
        String expre = "^[A-Z][0-9]{2}$";
        if (!codigo.matches(expre)) {
            alert.setContentText("El formato del código es incorrecto.");
            alert.showAndWait();
            return;
        }
        
        // Validar que el código no este duplicado
        if (olCodigo.contains(codigo)) {
            alert.setContentText("El código ya existe.");
            alert.showAndWait();
            return;
        }
        
        // Enviamos el codigo al producto para guardarlo desde los campos de texto que nosotros hemos puesto
        // ya que habremos pasado las verificaciones, controlando que se pasen todos segun su tipo
        p = new Producto();
        p.setCodigo(String.valueOf(nmiCodigo.getText()));
        p.setDescripcion(String.valueOf(nmiDesc.getText()));
        p.setCantidad(Integer.parseInt(nmiCantidad.getText()));
        p.setUbicacion(ubicacionSeleccionada.getCodigo());

        // Si guardamos bien el producto guardado mandara true a los if si no false
        boolean guardado = Producto.aniadirNuevoProducto(p);

        if (guardado) {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Producto añadido correctamente.");
            alert1.showAndWait();
            cancelarProducto(); 
        } else {
            Alert alert1 = new Alert(Alert.AlertType.ERROR, "Hubo un error al guardar en la base de datos.");
            alert1.showAndWait();
        }
        
        // Llamamos al metodo cancelar por si no se ha cerrado antes la ventana
        cancelarProducto();
    }

    // Metodo para cerrar la ventana de nuevo producto
    public void cancelarProducto() {
        // Creamos un escenario que consiga la escena que esta abierta del boton nmiCancelar 
        // y la cierra
        Stage stage = (Stage) nmiCancelar.getScene().getWindow();
        stage.close();
    }

}
