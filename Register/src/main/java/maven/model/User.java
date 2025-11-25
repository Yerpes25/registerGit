/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maven.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import maven.register.App;

/**
 *
 * @author CAMBIAR POR VUESTRO NOMBRE
 */
public class User {
    public int login (String user, String password){
        
        Connection connection = null;   // Conexión a la Base de datos (modeloCOnectorDB)
        PreparedStatement pst;
        ResultSet rs;
        int state = -1;
        
        try{
            connection = Conection.getConnection();
            if(connection!=null){
                String sql = "SELECT * FROM user WHERE BINARY (username=? OR email=?) AND password=AES_ENCRYPT(?, 'key')";
                // Preparación de la consulta a la base de datos con
                pst = connection.prepareStatement(sql);
                pst.setString(1, user);     //se verifica el usuario por el username o por el correo
                pst.setString(2, user);
                pst.setString(3, password);
                
                rs = pst.executeQuery();
                state = rs.next() ? 1 : 0; 
            } else{
                App.showAlert("Error detectado","Hubo un error al conectarse con la base de datos", Alert.AlertType.NONE);   
            }
            
        }catch(SQLException ex){
            App.showAlert("Error detectado","Hubo un error de ejecución, posibles errores: " + ex.getMessage(), Alert.AlertType.NONE); 
        }finally{  
            try{
                if(connection != null){
                    Conection.closeConnection(connection);           
                }            
            }catch(SQLException ex){
                System.err.println(ex.getMessage());            
            }
        }
        return state;
    }
    
    public void insertarUsuarios(String user, String email, String contrasenia){
        Connection con = null;
        PreparedStatement ps;
        String query = "String sql = \"INSERT INTO usuarios (nombre, email, password, estado) \" +\n" +
"                     \"VALUES (?, ?, AES_ENCRYPT(?, 'key'), 1)\";";
        
        try {
            con = Conection.getConnection();
            
            ps = con.prepareStatement(query);
            ps.setString(1, user);
            ps.setString(2, email);
            ps.setString(3, contrasenia);
            
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            App.showAlert("Error detectado","Hubo error al insertar" + ex.getMessage(), Alert.AlertType.NONE); 
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
            App.showAlert("Error detectado","Hubo error al insertar" + ex.getMessage(), Alert.AlertType.NONE); 
            }
        }
    }
    
    public int createUSer (){
        // TODO
        return 0;
    }
}
