/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maven.model;

/**
 *
 * @author javiergarciamontero
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Conection {

    private static final String DB = "register1";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DB + "?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=Europe/Madrid";
    private static final String USER = "root"; // o tu usuario
    private static final String PASSWORD = ""; // pon tu contraseña

    public static Connection getConnection() throws SQLException {
        Connection connect;
        try {
            connect = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión establecida con éxito.");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return connect;
    }
   
    public static void closeConnection(Connection connection) throws SQLException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión cerrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
