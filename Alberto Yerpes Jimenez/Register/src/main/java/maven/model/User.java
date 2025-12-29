/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maven.model;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import maven.proyecto.App;

/**
 *
 * @author Yerpes
 */
public class User {

    private static String usuarioActual;

    public int login(String user, String password) {

        Connection connection = null;   // Conexión a la Base de datos (modeloCOnectorDB)
        PreparedStatement pst;
        ResultSet rs;
        int state = -1;

        try {
            connection = Conection.getConnection();
            if (connection != null) {
                String sql = "SELECT * FROM user WHERE BINARY (username=? OR email=?) AND password=AES_ENCRYPT(?, 'key')";
                // Preparación de la consulta a la base de datos con
                pst = connection.prepareStatement(sql);
                pst.setString(1, user);     //se verifica el usuario por el username o por el correo
                pst.setString(2, user);
                pst.setString(3, password);

                rs = pst.executeQuery();
                state = rs.next() ? 1 : 0;
            } else {
                App.showAlert("Error detectado", "Hubo un error al conectarse con la base de datos", Alert.AlertType.NONE);
            }

        } catch (SQLException ex) {
            App.showAlert("Error detectado", "Hubo un error de ejecución, posibles errores: " + ex.getMessage(), Alert.AlertType.NONE);
        } finally {
            try {
                if (connection != null) {
                    Conection.closeConnection(connection);
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
        return state;
    }

    public boolean insertarUsuarios(String user, String email, String contrasenia, InputStream fotoStream) {
        Connection con = null;
        PreparedStatement ps;
        String sql = "INSERT INTO user (username, email, password, active, FOTO) VALUES (?, ?, AES_ENCRYPT(?, 'key'), 1, ?)";

        try {
            con = Conection.getConnection();
            if (con == null) {
                return false;
            }

            ps = con.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, email);
            ps.setString(3, contrasenia);
            ps.setBlob(4, fotoStream);

            ps.executeUpdate();
            return true;

        } catch (SQLException ex) {
            App.showAlert("Error SQL", "Error al guardar: " + ex.getMessage(), Alert.AlertType.ERROR);
            return false;
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar conexión: " + ex.getMessage());
            }
        }
    }

    public int createUSer() {
        // TODO
        return 0;
    }

    /**
     * @return the usuarioActual
     */
    public static String getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * @param aUsuarioActual the usuarioActual to set
     */
    public static void setUsuarioActual(String aUsuarioActual) {
        usuarioActual = aUsuarioActual;
    }

    public User() {
    }

    public Image obtenerFoto(String username) {
        Connection con = null;
        PreparedStatement ps;
        ResultSet rs;

        try {
            con = Conection.getConnection();
            String sql = "SELECT foto FROM user WHERE username = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                InputStream is = rs.getBinaryStream("foto");
                if (is != null) {
                    return new Image(is);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar foto: " + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
            }
        }
        return null; // Si no hay foto o falla
    }
}
