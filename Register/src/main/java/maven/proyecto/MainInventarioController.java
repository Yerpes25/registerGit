/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package maven.proyecto;

import java.io.IOException;
import java.io.InputStream;
import maven.model.Producto;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import maven.model.Conection;
import maven.model.FuncionHablar;
import maven.model.Ubicacion;
import maven.model.User;
import maven.util.GestorEstilos;
import maven.util.GestorHablar;
import maven.util.GestorTactil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

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
    @FXML
    private ImageView imgPerfilUsuario;
    @FXML
    private CheckMenuItem cbVoz;
    @FXML
    private Button btnInforme;

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
        GestorTactil.hacerInteractable(anchorPane);

        cbVoz.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                FuncionHablar.setVozActivada(newValue);

                if (newValue) {
                    FuncionHablar.hablar("Modo voz activado");
                }
            }
        });

        FuncionHablar.hablar("Gestión de inventario");

        /*Le ponemos voz a lo que queremos de main inventario*/
        GestorHablar.adjudicarVoces(btnNuevo,
                btnEliminar,
                btnModificar,
                miDiagrama,
                miCodigo,
                miDescripcion,
                miCantidad,
                miUbicacion);


        /*Metodo para decir la descripccion de los productos de la tabla*/
        tableModel.setRowFactory(new Callback<TableView<Producto>, TableRow<Producto>>() {
            @Override
            public TableRow<Producto> call(TableView<Producto> tv) {
                final TableRow<Producto> fila = new TableRow<>();

                fila.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (!fila.isEmpty() && FuncionHablar.estaVozActivada()) {
                            FuncionHablar.hablar(fila.getItem().getDescripcion());
                        }
                    }
                });

                return fila;
            }
        });

        /* Configuracion de la celda del combo box para que hable al pasar el raton*/
        GestorHablar.ponerVozComboBox(miUbicacion);

        // Cargamos foto de perfil del usuario
        cargarFotoPerfil();

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

    //Metodo para actualizar la tabla desde el menu
    @FXML
    public void menuActualizar(ActionEvent event) {
        actualizarTabla();
    }

    // Metodo para cargar los datos de la tabla desde el menú
    @FXML
    public void menuCargarDatos(ActionEvent event) {
        ol = model.cargarDatos();
    }

    // Metodo para salir de la aplicación desde el menú
    @FXML
    public void menuSalir(ActionEvent event) {
        Platform.exit();
    }

    //Metodo para mostrar la pantalla del diagrama desde el menú
    public void menuGrafico(ActionEvent event) {
        mostrarDiagrama(event);
    }

    //Metodo para cerrar sesión desde el menú
    @FXML
    private void cerrarSesion(ActionEvent event) {
        try {
            App.setRoot("login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Metodo para poner el modo oscuro a la aplicación
    @FXML
    private void modoOscuro(ActionEvent event) {
        GestorEstilos.setModoOscuro(true, anchorPane);
    }

    //Metodo para poner el modo claro a la aplicación
    @FXML
    private void modoClaro(ActionEvent event) {
        GestorEstilos.setModoOscuro(false, anchorPane);
    }

    // Metodo para aumentar la fuente del texto
    @FXML
    private void aumentarFuente(ActionEvent event) {
        GestorEstilos.aumentarFuente(anchorPane);
    }

    //Metodo para disminuir la fuente del texto
    @FXML
    private void disminuirFuente(ActionEvent event) {
        GestorEstilos.disminuirFuente(anchorPane);
    }

    //Metodo para reproducir sonido de error o exito
    @FXML
    private void reproducirSonido(ActionEvent event) {
        if (mSonido != null) {
            maven.model.Configuracion.sonidoActivo = mSonido.isSelected();
        }
    }

    //Metodo para cargar la foto del usuario
    private void cargarFotoPerfil() {
        // Obtenemos el usuario actual
        String usuario = User.getUsuarioActual();

        if (usuario != null) {
            Image foto = model1.obtenerFoto(usuario);

            if (foto != null) {
                imgPerfilUsuario.setImage(foto);

                // Recortar en círculo para que quede mejor
                double radio = imgPerfilUsuario.getFitWidth() / 2;
                Circle clip = new Circle(radio, radio, radio);
                imgPerfilUsuario.setClip(clip);
            }
        }
    }

    @FXML
private void sacarInforme(ActionEvent event) throws SQLException {
    try {
        Connection connection = Conection.getConnection();
        if (connection == null) {
            App.showAlert("Error", "Sin conexión a la BD", Alert.AlertType.ERROR);
            return;
        }

        String rutaInforme = "/maven/proyecto/asset/informes/Informe.jrxml"; 
        InputStream reporteStream = getClass().getResourceAsStream(rutaInforme);
        
        String rutaImagen = "/maven/proyecto/asset/image/Logo3.jpg"; 
        InputStream logoStream = getClass().getResourceAsStream(rutaImagen);

        if (reporteStream == null || logoStream == null) {
            System.out.println("Error: No se encuentra el reporte o la imagen.");
            App.showAlert("Error", "Faltan archivos de recursos", Alert.AlertType.ERROR);
            return;
        }

        JasperReport report = JasperCompileManager.compileReport(reporteStream);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("logoImagen", logoStream);

        JasperPrint print = JasperFillManager.fillReport(report, parametros, connection);
        JasperViewer.viewReport(print, false);

    } catch (JRException e) {
        e.printStackTrace();
        App.showAlert("Error Jasper", e.getMessage(), Alert.AlertType.ERROR);
    }
}
}
