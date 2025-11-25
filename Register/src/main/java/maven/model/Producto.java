/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maven.model;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import maven.register.MainInventarioController;

/**
 *
 * @author Usuario
 */
public class Producto {

    private int id;
    private String codigo;
    private String descripcion;
    private int cantidad;
    private String ubicacion;
    
    // Se usa para almacenar los productos leídos de la BD y mostrarlos en una tabla.
    private ObservableList<Producto> ol = FXCollections.observableArrayList();

    public Producto(int id, String codigo, String descripcion, int cantidad, String ubicacion) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.ubicacion = ubicacion;
    }

    public Producto() {
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the ubicacion
     */
    public String getUbicacion() {
        return ubicacion;
    }

    /**
     * @param ubicacion the ubicacion to set
     */
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    // Metodo para cargar los datos de la base de datos
    // Creamos una conexion y recogemos los datos con un while
    public ObservableList<Producto> cargarDatos() {
        ol.clear();
        try (Connection con = Conection.getConnection()) {
            String query = "SELECT * FROM producto";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Producto p = new Producto(
                        rs.getInt("id"),
                        rs.getString("codigo"),
                        rs.getString("descripcion"),
                        rs.getInt("cantidad"),
                        rs.getString("ubicacion"));
                ol.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ol;
    }
    
    
    //Metodo para actualizar los datos de la tabla, igual que el de arriva pero cambiando la consulta
    public boolean actualizarProducto() {
        try (Connection con = Conection.getConnection()) {
            String query = "UPDATE producto SET codigo = ?, descripcion = ?, cantidad = ?, ubicacion = ? WHERE id = ?";
            java.sql.PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, this.codigo);
            ps.setString(2, this.descripcion);
            ps.setInt(3, this.cantidad);
            ps.setString(4, this.ubicacion);
            ps.setInt(5, this.id);

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Metodo para eliminar un producto de la base de datos con diferente consulta
    public boolean eliminarProducto() {
        String query = "DELETE FROM producto WHERE id = ?";

        try (Connection con = Conection.getConnection()) {
            java.sql.PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, this.id);

            int filas = ps.executeUpdate();

            return filas > 0;

        } catch (SQLException ex) {
            Logger.getLogger(MainInventarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    // Metodo para añadir un nuevo producto de la base de datos con diferente consulta
    public static boolean aniadirNuevoProducto(Producto producto){
        Connection con = null;
        PreparedStatement ps;
        String query = "insert into producto (codigo, descripcion, cantidad, ubicacion) values (?,?,?,?)";
        
        try {
            con = Conection.getConnection();
            
            ps = con.prepareStatement(query);
            ps.setString(1, producto.getCodigo());
            ps.setString(2, producto.getDescripcion());
            ps.setInt(3, producto.getCantidad());
            ps.setString(4, producto.getUbicacion());
            
            ps.executeUpdate();
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    // Metodo para cargar solo el codigo de todas las filas de la base de datos
    public ObservableList<String> cargarCodigoProductos() {
        ObservableList<String> ol2 = observableArrayList();
        try (Connection con = Conection.getConnection()) {
            String query = "SELECT codigo FROM producto";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            String codigo;
            while (rs.next()) {
                codigo = rs.getString("codigo");
                ol2.add(codigo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ol2;
    }
}
