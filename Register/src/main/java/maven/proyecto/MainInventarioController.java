/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package maven.proyecto;

import java.io.IOException;
import maven.model.Producto;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import maven.model.Ubicacion;
import maven.model.User;
import maven.util.GestorEstilos;

/**
 * FXML Controller class
 *
 * @author Yerpes
 */
public class MainInventarioController implements Initializable {

    // Creamos las variables que necesitamos
    @FXML
    private TableView<Producto> tableModel;

    @FXML
    private TableColumn<Producto, String> codProducto;

    @FXML
    private TableColumn<Producto, String> descProducto;

    @FXML
    private TableColumn<Producto, Integer> cantProducto;

    @FXML
    private TableColumn<Producto, String> ubiProducto;

    @FXML
    private TextField miCodigo;
    @FXML
    private TextField miDescripcion;
    @FXML
    private TextField miCantidad;
    @FXML
    private ComboBox<Ubicacion> miUbicacion;
    @FXML
    private Button miDiagrama;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnModificar;

    private ObservableList<Producto> ol = observableArrayList();
    private ObservableList<Ubicacion> olUbi = observableArrayList();
    private Producto model = null;
    private User model1 = null;
    private Ubicacion modelUbi = null;
    private static Producto productoSeleccionado = null;
    private Producto modelCodigo = null;
    private ObservableList<String> olCodigo;
    private List<String> fuentes;

    @FXML
    private MenuItem mActualizar;
    @FXML
    private MenuItem mCerrarSesion;
    @FXML
    private MenuItem mSalir;
    @FXML
    private VBox anchorPane;
    @FXML
    private MenuItem mCargarDatos;
    @FXML
    private MenuItem mNopturno;
    @FXML
    private MenuItem mClaro;
    @FXML
    private MenuItem mAumentar;
    @FXML
    private MenuItem mDisminuir;
    @FXML
    private Menu menuItem1;
    @FXML
    private CheckMenuItem mSonido;
    @FXML
    private HBox VboxPanelIzquierda;
    @FXML
    private VBox VboxPanelDerecha;
    @FXML
    private Button btnNuevo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        mActualizar.setAccelerator(KeyCombination.keyCombination("Ctrl+R"));
        mCerrarSesion.setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
        mSalir.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
        mCargarDatos.setAccelerator(KeyCombination.keyCombination("Ctrl+F"));
        mNopturno.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        mClaro.setAccelerator(KeyCombination.keyCombination("Ctrl+P"));
        mAumentar.setAccelerator(KeyCombination.keyCombination("Ctrl+T"));
        mDisminuir.setAccelerator(KeyCombination.keyCombination("Ctrl+H"));
        menuItem1.setAccelerator(KeyCombination.keyCombination("Ctrl+I"));

        fuentes = Font.getFamilies();

        for (String fuente : fuentes) {
            MenuItem item = new MenuItem(fuente);
            item.setOnAction(e -> {
                GestorEstilos.setFuente(fuente, anchorPane);
            });
            menuItem1.getItems().add(item);
        }

        if (mSonido != null) {
            mSonido.setSelected(maven.model.Configuracion.sonidoActivo);
        }

//        miUbicacion.getSelectionModel().selectedItemProperty().addListener(cl);
        // Esta es la parte de añadir un icono en java, lo que hacemos es crear un objeto de tipo image llamado icono,
        // conseguimos la ruta de donde esta la imagen para el icono
        Image icono = new Image(getClass().getResourceAsStream("/maven/proyecto/asset/image/diagrama.png"));

        // Creamos una imageView para meter un icono y le damos el ancho y el alto
        ImageView imagen = new ImageView(icono);
        imagen.setFitHeight(30);
        imagen.setFitWidth(30);
        miDiagrama.setGraphic(imagen);

        // Creamos un objeto llamado model y lo inicializamos
        model1 = new User();
        model = new Producto();
        modelCodigo = new Producto();

        //Cojemos los datos de la tabla
        codProducto.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        descProducto.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        cantProducto.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        ubiProducto.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));

        // Guardamos en la lista de productos los datos de la base de datos 
        // y enviamos todos los datos de la lista a la tabla
        ol = model.cargarDatos();
        tableModel.setItems(ol);

        // Creamos arriva un objeto de ubicacion y lo inicializamos al igual que producto,
        // le metemos todas las ubicaciones a olUbi y las enviamos al campo mi Ubicacion
        modelUbi = new Ubicacion();
        olUbi = modelUbi.cargarUbicacion();
        miUbicacion.setItems(olUbi);

        // Cargamos a olCodigo los codigos de todos los productos
        olCodigo = modelCodigo.cargarCodigoProductos();

        if (mCerrarSesion != null) {
            String nombre = model1.getUsuarioActual();
            mCerrarSesion.setText("Cerrar Sesion  (" + nombre + ")");
        }

        GestorEstilos.cargarEstilos(anchorPane);
        // Actualizamos la tabla
        actualizarTabla();

    }

    // Metodo de seleccionar un producto de la tabla
    @FXML
    public void seleccionar(javafx.scene.input.MouseEvent event) {
        // Metemos en productoSeleccionado el objeto que hemos seleccionado en la tabla,
        // y mostramos los productos e iniciamos los botones para modificar
        productoSeleccionado = tableModel.getSelectionModel().getSelectedItem();
        mostrarProductos();
        btnEliminar.setDisable(false);
        btnModificar.setDisable(false);
    }

    // Metodo para mostrar los productos en los campos de texto de abajo de la tabla
    public void mostrarProductos() {
        if (productoSeleccionado != null) {
            miCodigo.setText(String.valueOf(productoSeleccionado.getCodigo()));
            miCantidad.setText(String.valueOf(productoSeleccionado.getCantidad()));
            miDescripcion.setText(String.valueOf(productoSeleccionado.getDescripcion()));
            String ubicacionProducto = productoSeleccionado.getUbicacion();
            for (Ubicacion ubicacion : miUbicacion.getItems()) {
                if (ubicacion.getCodigo().equals(ubicacionProducto)) {
                    miUbicacion.setValue(ubicacion);
                    break;
                }
            }
        }
    }

    // Metodo para modificar los objetos que seleccionamos de la tabla en la base de datos
    @FXML
    private void btnModificar(ActionEvent event) {

        // Si seleccionamos producto, no sale null y hacemos lo siguiente si no da error
        if (productoSeleccionado != null) {

            // Cogemos el dato que tenemos en el campo y lo guardamos en ubicacionActual
            Ubicacion ubicacionActual = miUbicacion.getValue();

            //Validamos que se haya cogido Ubicacion
            if (ubicacionActual == null) {
                GestorEstilos.reproducir("error");
                Alert alert = new Alert(Alert.AlertType.WARNING, "Debes seleccionar una ubicación.");
                alert.showAndWait();
                return;
            }

            // Validamos que ingresen un numero
            int cantidadCom;
            int nuevoCantidad = Integer.parseInt(miCantidad.getText());
            try {
                cantidadCom = Integer.parseInt(miCantidad.getText());
            } catch (NumberFormatException e) {
                GestorEstilos.reproducir("error");
                Alert alert = new Alert(Alert.AlertType.WARNING, "La cantidad debe ser un número válido.");
                alert.showAndWait();
                return;
            }

            //Validamos que cantidad sea positivo
            if (nuevoCantidad <= 0) {
                GestorEstilos.reproducir("error");
                Alert alert = new Alert(Alert.AlertType.WARNING, "La cantidad debe ser un número positivo mayor que cero.");
                alert.showAndWait();
                return;
            }

            //Validamos con una expresion regular en codigo 
            String expre = "^[A-Z][0-9]{2}$";
            String nuevoCodigo = miCodigo.getText().trim();
            if (!comprobarCodigo(nuevoCodigo) || !nuevoCodigo.matches(expre)) {
                GestorEstilos.reproducir("error");
                Alert alert = new Alert(Alert.AlertType.WARNING, "El código esta mal o es igual que otro");
                alert.showAndWait();
                return;
            }

            //Guardamos la ubicacion en nuevaUbicacion
            String nuevoUbicacion = ubicacionActual.getCodigo();

            //Validamos que la descripcion no este vacia
            String nuevoDescripcion = miDescripcion.getText().trim();
            if (nuevoCodigo.isEmpty() || nuevoDescripcion.isEmpty()) {
                GestorEstilos.reproducir("error");
                Alert alert = new Alert(Alert.AlertType.WARNING, "El código y la descripción no pueden estar vacíos.");
                alert.showAndWait();
                return;
            }

            //Si no ha cambiado algo entramos en el if sino en else
            if (nuevoCodigo.equals(productoSeleccionado.getCodigo())
                    && nuevoDescripcion.equals(productoSeleccionado.getDescripcion())
                    && nuevoCantidad == productoSeleccionado.getCantidad()
                    && nuevoUbicacion.equals(productoSeleccionado.getUbicacion())) {
                GestorEstilos.reproducir("error");
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Modifica algo");
                alert.showAndWait();

            } else {
                // Desactivamos el boton de modificar ya que ya hemos modificado y enviamos a productoSeleccionado los nuevos datos
                // Que se han modificado
                btnModificar.setDisable(false);
                productoSeleccionado.setCodigo(nuevoCodigo);
                productoSeleccionado.setDescripcion(nuevoDescripcion);
                productoSeleccionado.setCantidad(nuevoCantidad);
                productoSeleccionado.setUbicacion(ubicacionActual.getCodigo());

                // Si se guarda correctamente se guarda en actualizado un true
                boolean actualizado = productoSeleccionado.actualizarProducto();

                if (actualizado) {
                    actualizarTabla();
                    GestorEstilos.reproducir("exito");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Producto actualizado correctamente");
                    alert.showAndWait();
                } else {
                    GestorEstilos.reproducir("error");
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error al actualizar producto");
                    alert.showAndWait();
                }
            }

        } else {
            GestorEstilos.reproducir("error");
            Alert alert = new Alert(Alert.AlertType.WARNING, "No has modificado nada");
            alert.showAndWait();
        }

    }

    // Metodo para actualizar como al principio la tabla y los botones
    private void actualizarTabla() {
        ol = model.cargarDatos();
        tableModel.setItems(ol);
        tableModel.refresh();

        btnEliminar.setDisable(true);
        btnModificar.setDisable(true);
        vaciarCampos();
    }

    // Metodo para eliminar un producto
    @FXML
    public void btnEliminar(ActionEvent event) {

        if (productoSeleccionado != null) {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Eliminar");
            alerta.setHeaderText("¿Estas seguro de que quieres eliminarlo?");

            if (alerta.showAndWait().get() == ButtonType.OK) {
                boolean eliminado = productoSeleccionado.eliminarProducto();

                if (eliminado) {
                    actualizarTabla();
                    GestorEstilos.reproducir("exito");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Producto eliminado correctamente");
                    alert.showAndWait();
                } else {
                    GestorEstilos.reproducir("error");
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error al eliminar producto");
                    alert.showAndWait();
                }
            } else {
                GestorEstilos.reproducir("exito");
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Producto cancelado correctamente");
                alert.showAndWait();
                actualizarTabla();
            }
        } else {
            GestorEstilos.reproducir("error");
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecciona un producto antes de modificar");
            alert.showAndWait();
        }
    }

    // Metodo para abrir la escena de añadir nuevo
    @FXML
    private void aniadirNuevo() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AniaidirProducto.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Añadir nuevo producto");
            actualizarTabla();
            stage.setScene(scene);
            stage.showAndWait();

            actualizarTabla();
        } catch (IOException ex) {
            GestorEstilos.reproducir("error");
            Logger.getLogger(MainInventarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Abrir la escena de el diagrama
    @FXML
    public void mostrarDiagrama(ActionEvent event) {
        try {
            actualizarTabla();
            FXMLLoader fl = new FXMLLoader(getClass().getResource("diagrama.fxml"));
            Parent root;

            root = fl.load();

            Scene sc = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Inventario");
            stage.setScene(sc);
            stage.showAndWait();
        } catch (IOException ex) {
            GestorEstilos.reproducir("error");
            Logger.getLogger(MainInventarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // vaciamos todos los campos de texto
    public void vaciarCampos() {
        miCantidad.clear();
        miCodigo.clear();
        miDescripcion.clear();
        miUbicacion.setValue(null);

        btnModificar.setDisable(true);
        btnEliminar.setDisable(true);
        productoSeleccionado = null;

    }

    // Metodo para comprobar el codigo del producto si es igual o no al antiguo
    private boolean comprobarCodigo(String nuevoCodigo) {
        String codigoAntiguo = productoSeleccionado.getCodigo();

        if (nuevoCodigo.equals(codigoAntiguo)) {
            return true;
        }

        if (olCodigo.contains(nuevoCodigo)) {
            return false;
        }

        return true;
    }

    @FXML
    public void menuActualizar(ActionEvent event) {
        actualizarTabla();
    }

    @FXML
    public void menuCargarDatos(ActionEvent event) {
        ol = model.cargarDatos();
    }

    @FXML
    public void menuSalir(ActionEvent event) {
        Platform.exit();
    }

    public void menuGrafico(ActionEvent event) {
        mostrarDiagrama(event);
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {
        try {
            App.setRoot("login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void modoOscuro(ActionEvent event) {
        GestorEstilos.setModoOscuro(true, anchorPane);
    }

    @FXML
    private void modoClaro(ActionEvent event) {
        GestorEstilos.setModoOscuro(false, anchorPane);
    }

    @FXML
    private void aumentarFuente(ActionEvent event) {
        GestorEstilos.aumentarFuente(anchorPane);
    }

    @FXML
    private void disminuirFuente(ActionEvent event) {
        GestorEstilos.disminuirFuente(anchorPane);
    }

    @FXML
    private void reproducirSonido(ActionEvent event) {
        if (mSonido != null) {
            maven.model.Configuracion.sonidoActivo = mSonido.isSelected();
        }
    }
}
