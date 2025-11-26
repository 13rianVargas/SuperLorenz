package co.edu.konradlorenz.model.dao;

import co.edu.konradlorenz.model.*;
import co.edu.konradlorenz.model.connection.*;
import co.edu.konradlorenz.model.enums.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;


public class ProveedorDAO {

    private static Conexion conexionBD = new Conexion();
    
    
    
    
    
    // Abre: iniciarSesion
    public static Proveedor iniciarSesion(String correo, String password) {
        Proveedor proveedor = null;

        String query = "SELECT * "
                     + "FROM Persona p "
                     + "INNER JOIN Proveedor pr "
                     + "ON p.personaID = pr.personaID "
                     + "WHERE correo = ? AND password = ?;";

        try (Connection connection = conexionBD.crearConexion();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, correo);
            ps.setString(2, password);

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    proveedor = new Proveedor();
                    proveedor.setCorreo(resultSet.getString("correo"));
                    proveedor.setPassword(resultSet.getString("contraseña"));
                }
            }

            if (proveedor != null) {
                System.out.println("PROVEEDOR LOGEADO DE FORMA EXITOSA...");
            } else {
                System.out.println("PROVEEDOR NO ENCONTRADO...");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL LOGEAR PROVEEDOR...");
            e.printStackTrace();
        }

        return proveedor;
    }
    // Cierra: iniciarSesion
    
    
    
    
    
    //Abre: agregar
    public static int agregar(Proveedor proveedor){
        int status = 0;
        
        // Insert Persona
        String queryPersona = "INSERT INTO Persona "
                            + "(numeroDocumento, tipoDocumento, nombres, apellidos, celular, correo, password) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?);";
        
        // Insert Proveedor
        String queryProveedor =  "INSERT INTO Proveedor "
                              + "(personaID, tipoProveedor, nombreContacto, numeroContacto) "
                              + "VALUES (?, ?, ?, ?);";
        
        
        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement personaStmt = connection.prepareStatement(queryPersona, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Insert en la tabla Proveedor
            personaStmt.setString(1, proveedor.getNumeroDocumento());
            personaStmt.setString(2, proveedor.getTipoDocumento().name()); // Enum a String
            personaStmt.setString(3, proveedor.getNombres());
            personaStmt.setString(4, proveedor.getApellidos());
            personaStmt.setString(5, proveedor.getCelular());
            personaStmt.setString(6, proveedor.getCorreo());
            personaStmt.setString(7, proveedor.getPassword());
            
            personaStmt.executeUpdate();

            // Obtener personaID generado
            int personaID = 0;
            try (ResultSet resultSet = personaStmt.getGeneratedKeys()) {
                if (resultSet.next()) {
                    personaID = resultSet.getInt(1);
                }
            }

            // Insert en la tabla Proveedor
            try (PreparedStatement proveedorStmt = connection.prepareStatement(queryProveedor)) {
                proveedorStmt.setInt(1, personaID);
                proveedorStmt.setString(2, proveedor.getTipoProveedor().name()); // Enum a String
                proveedorStmt.setString(3, proveedor.getNombreContacto());
                proveedorStmt.setString(4, proveedor.getNumeroContacto());

                status = proveedorStmt.executeUpdate();
            }

            System.out.println("PROVEEDOR GUARDADO DE FORMA EXITOSA...");
        } catch (SQLException e) {
            System.out.println("ERROR AL REGISTRAR EL PROVEEDOR...");
            e.printStackTrace();
        }

        return status;
    }
    // Cierra: agregar


    
    
    
    // Abre: obtenerTodos
    public static List<Proveedor> obtenerTodos() {

        List<Proveedor> proveedores = new ArrayList<>();

        String query = "SELECT * "
                     + "FROM Proveedor;";

        try (Connection connection = conexionBD.crearConexion(); 
             PreparedStatement ps = connection.prepareStatement(query); 
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Proveedor proveedor = new Proveedor();

                proveedor.setProveedorID(rs.getInt("proveedorID"));
                proveedor.setPersonaID(rs.getInt("personaID"));
                
                // Manejo seguro de la conversión a Enum
                try {
                    proveedor.setTipoProveedor(TipoProveedor.valueOf(rs.getString("tipoProveedor")));
                } catch (IllegalArgumentException e) {
                    System.out.println("Valor inválido para tipoProveedor: " + rs.getString("tipoProveedor"));
                }
            
                proveedor.setNombreContacto(rs.getString("nombreContacto"));
                proveedor.setNumeroContacto(rs.getString("numeroContacto"));

                proveedores.add(proveedor);
                
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER LOS PROVEEDORES...");
            e.printStackTrace();
        }

        return proveedores;
    }
    // Cierra: obtenerTodos

    
    
    
    
    // Abre: modificar
    public static int modificar(Proveedor proveedor) {
        int status = 0;

        // Update Persona
        String queryPersona = "UPDATE Persona "
                            + "SET tipoDocumento = ?, nombres = ?, apellidos = ?, celular = ?, correo = ?, password = ? "
                            + "WHERE personaID = ?;";

        // Update Proveedor
        String queryProveedor = "UPDATE Proveedor "
                              + "SET tipoProveedor = ?, nombreContacto = ?, numeroContacto = ? "
                              + "WHERE personaID = ?;";

        try (Connection connection = conexionBD.crearConexion()) {

            // Actualizar la tabla Persona
            try (PreparedStatement personaStmt = connection.prepareStatement(queryPersona)) {
                personaStmt.setString(1, proveedor.getTipoDocumento().name()); // Enum a String
                personaStmt.setString(2, proveedor.getNombres());
                personaStmt.setString(3, proveedor.getApellidos());
                personaStmt.setString(4, proveedor.getCelular());
                personaStmt.setString(5, proveedor.getCorreo());
                personaStmt.setString(6, proveedor.getPassword());
                personaStmt.setInt(7, proveedor.getPersonaID());

                status = personaStmt.executeUpdate();
            }

            // Si la actualización de la persona fue exitosa, actualizamos la tabla Proveedor
            if (status > 0) {
                try (PreparedStatement proveedorStmt = connection.prepareStatement(queryProveedor)) {
                    proveedorStmt.setString(1, proveedor.getTipoProveedor().name()); // Enum a String
                    proveedorStmt.setString(2, proveedor.getNombreContacto());
                    proveedorStmt.setString(3, proveedor.getNumeroContacto());
                    proveedorStmt.setInt(4, proveedor.getPersonaID());

                    status = proveedorStmt.executeUpdate();
                }
            }

            if (status > 0) {
                System.out.println("ACTUALIZACIÓN DE PROVEEDOR REALIZADA CON ÉXITO...");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL MODIFICAR EL PROVEEDOR...");
            e.printStackTrace();
        }

        return status;
    }
    // Cierra: modificar
    
    
    
    
    
    // Abre: eliminar
    public static int eliminar(int personaID) {
        int status = 0;

        // Delete Proveedor
        String queryProveedor = "DELETE "
                              + "FROM Proveedor "
                              + "WHERE personaID = ?;";
 
        // Delete Persona
        String queryPersona = "DELETE "
                            + "FROM Persona "
                            + "WHERE personaID = ?;";

        try (Connection connection = conexionBD.crearConexion()) {

            // Primero se elimina de la tabla Proveedor
            try (PreparedStatement proveedorStmt = connection.prepareStatement(queryProveedor)) {
                proveedorStmt.setInt(1, personaID);
                status = proveedorStmt.executeUpdate();
            }

            // Luego de persona
            if (status > 0) {
                try (PreparedStatement personaStmt = connection.prepareStatement(queryPersona)) {
                    personaStmt.setInt(1, personaID);
                    status = personaStmt.executeUpdate();
                }
            }

            if (status > 0) {
                System.out.println("PROVEEDOR ELIMINADO CON ÉXITO...");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL ELIMINAR EL PROVEEDOR...");
            e.printStackTrace();
        }

        return status;
    }
    // Cierra: eliminar
        
}

