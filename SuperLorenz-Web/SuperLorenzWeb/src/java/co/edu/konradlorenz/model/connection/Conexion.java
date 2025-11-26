package co.edu.konradlorenz.model.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    // Configuración de la base de datos
    public static String usuario = "root";
    public static String clave = ""; // Contraseña por defecto
    public static String servidor = "localhost:3306";
    public static String BD = "SuperLorenzWeb";

    // Método para crear la conexión
    public Connection crearConexion() {
        Connection con = null;
        try {
            // Cargar el driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // URL para la conexión
            String URL = "jdbc:mysql://" + servidor + "/" + BD + "?useSSL=false&serverTimezone=UTC";
            
            // Establecer la conexión
            con = DriverManager.getConnection(URL, usuario, clave);
        } catch (ClassNotFoundException ex) {
            System.out.println("Error: No se encontró la clase para el driver de MySQL");
        } catch (SQLException e) {
            System.out.println("Error: No se pudo establecer conexión con la base de datos");
        }
        return con;
    }
}
