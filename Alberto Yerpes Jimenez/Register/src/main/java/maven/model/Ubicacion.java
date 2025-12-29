/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maven.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Usuario
 */
public class Ubicacion {
    private String codigo;
    private String nombre;
    private ObservableList<Ubicacion> ol = FXCollections.observableArrayList();
    

    public Ubicacion(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public Ubicacion() {
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    public ObservableList<Ubicacion> cargarUbicacion() {
        ol.clear();
        Connection con = null;
        try {
             con = Conection.getConnection();
            String query = "SELECT * FROM ubicacion";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Ubicacion u = new Ubicacion(
                        rs.getString("codigo"),
                        rs.getString("nombre"));
                ol.add(u);
            }
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        finally{
            if(con != null){
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Ubicacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return ol;
    }

    @Override
    public String toString() {
        String todo = codigo + ", " + nombre;
        
        return todo; 
    }

    
    
    
}
