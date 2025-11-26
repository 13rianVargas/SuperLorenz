package co.edu.konradlorenz.model.dao;

import co.edu.konradlorenz.model.*;
import co.edu.konradlorenz.model.connection.*;
import co.edu.konradlorenz.model.enums.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class ClienteDAO {
    
    private static Conexion conexionBD = new Conexion();
    
    
    
    
    
    // Abre: iniciarSesion
    public static Cliente iniciarSesion(String correo, String password) {
        Cliente cliente = null;

        String query = "SELECT * "
                     + "FROM Persona p "
                     + "INNER JOIN Cliente c "
                     + "ON p.personaID = c.personaID "
                     + "WHERE correo = ? AND password = ?;";

        try (Connection connection = conexionBD.crearConexion();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, correo);
            ps.setString(2, password);

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    cliente = new Cliente();
                    cliente.setCorreo(resultSet.getString("correo"));
                    cliente.setPassword(resultSet.getString("contraseña"));
                }
            }

            if (cliente != null) {
                System.out.println("CLIENTE LOGEADO DE FORMA EXITOSA...");
            } else {
                System.out.println("CLIENTE NO ENCONTRADO...");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL LOGEAR CLIENTE...");
            e.printStackTrace();
        }

        return cliente;
    }
    // Cierra: iniciarSesion
    
    
    
    
    
    //Abre: agregar
    public static int agregar(Cliente cliente){
        int status = 0;
        
        // Insert Persona
        String queryPersona = "INSERT INTO Persona "
                            + "(numeroDocumento, tipoDocumento, nombres, "
                            + "apellidos, celular, correo, password) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?);";
        
        // Insert Cliente
        String queryCliente =  "INSERT INTO Cliente "
                            + "(personaID, tipoCliente, direccion, "
                            + "fechaNacimiento, estadoCivil, "
                            + "autorizacionDeDatos) "
                            + "VALUES (?, ?, ?, ?, ?, ?);";
        
        
        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement personaStmt = connection.prepareStatement(queryPersona, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Insert en la tabla Persona
            personaStmt.setString(1, cliente.getNumeroDocumento());
            personaStmt.setString(2, cliente.getTipoDocumento().name()); // Enum a String
            personaStmt.setString(3, cliente.getNombres());
            personaStmt.setString(4, cliente.getApellidos());
            personaStmt.setString(5, cliente.getCelular());
            personaStmt.setString(6, cliente.getCorreo());
            personaStmt.setString(7, cliente.getPassword());
            
            personaStmt.executeUpdate();

            // Obtener personaID generado
            int personaID = 0;
            try (ResultSet resultSet = personaStmt.getGeneratedKeys()) {
                if (resultSet.next()) {
                    personaID = resultSet.getInt(1);
                }
            }

            // Insert en la tabla Cliente
            try (PreparedStatement clienteStmt = connection.prepareStatement(queryCliente)) {
                clienteStmt.setInt(1, personaID);
                clienteStmt.setString(2, cliente.getTipoCliente().name()); // Enum a String
                clienteStmt.setString(3, cliente.getDireccion());
                clienteStmt.setDate(4, new java.sql.Date(cliente.getFechaNacimiento().getTime())); // Convertir java.util.Date a java.sql.Date
                clienteStmt.setString(5, cliente.getEstadoCivil().name()); // Enum a String
                clienteStmt.setBoolean(6, cliente.isAutorizacionDeDatos());

                status = clienteStmt.executeUpdate();
            }

            System.out.println("CLIENTE GUARDADO DE FORMA EXITOSA...");
        } catch (SQLException e) {
            System.out.println("ERROR AL REGISTRAR EL CLIENTE...");
            e.printStackTrace();
        }

        return status;
    }
    // Cierra: agregar
    
    
    
    
    
    // Abre: obtenerClienteID
    public static int obtenerClienteID(String numeroDocumento) {
        int clienteID = -1;

        String query = "SELECT clienteID "
                     + "FROM Cliente "
                     + "WHERE nombre = ?;";

        try (Connection connection = conexionBD.crearConexion(); 
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, numeroDocumento); // O el criterio que desees usar

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    clienteID = rs.getInt("clienteID");
                }
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER EL CLIENTE ID...");
            e.printStackTrace();
        }

        return clienteID;
    }
    // Cierra: obtenerClienteID
    
    
    
    
    
    // Abre: obtenerTodos
    public static List<Cliente> obtenerTodos() {

        List<Cliente> clientes = new ArrayList<>();

        String query = "SELECT * "
                     + "FROM Cliente;";

        try (Connection connection = conexionBD.crearConexion(); 
             PreparedStatement ps = connection.prepareStatement(query); 
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Cliente cliente = new Cliente();

                cliente.setClienteID(rs.getInt("clienteID"));
                cliente.setPersonaID(rs.getInt("personaID"));
                
                // Manejo seguro de la conversión a Enum
                try {
                    cliente.setTipoCliente(TipoCliente.valueOf(rs.getString("tipoCliente")));
                } catch (IllegalArgumentException e) {
                    System.out.println("Valor inválido para tipoCliente: " + rs.getString("tipoCliente"));
                }
            
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                
                // Manejo seguro de la conversión a Enum
                try {
                    cliente.setEstadoCivil(EstadoCivil.valueOf(rs.getString("estadoCivil")));
                } catch (IllegalArgumentException e) {
                    System.out.println("Valor inválido para estadoCivil: " + rs.getString("estadoCivil"));
                }
                
                cliente.setAutorizacionDeDatos(rs.getBoolean("autorizacionDeDatos"));

                clientes.add(cliente);
                
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER LOS CLIENTES...");
            e.printStackTrace();
        }

        return clientes;
    }
    // Cierra: obtenerTodos
    
    
    
    
    
    // Abre: modificar
    public static int modificar(Cliente cliente) {
        int status = 0;

        // Update Persona
        String queryPersona = "UPDATE Persona "
                            + "SET tipoDocumento = ?, nombres = ?, "
                            + "apellidos = ?, celular = ?, correo = ?, "
                            + "password = ? "
                            + "WHERE personaID = ?;";

        // Update Cliente
        String queryCliente = "UPDATE Cliente "
                            + "SET tipoCliente = ?, direccion = ?, "
                            + "fechaNacimiento = ?, estadoCivil = ?, "
                            + "autorizacionDeDatos = ? "
                            + "WHERE personaID = ?;";

        try (Connection connection = conexionBD.crearConexion()) {

            // Actualizar la tabla Persona
            try (PreparedStatement personaStmt = connection.prepareStatement(queryPersona)) {
                personaStmt.setString(1, cliente.getTipoDocumento().name()); // Enum a String
                personaStmt.setString(2, cliente.getNombres());
                personaStmt.setString(3, cliente.getApellidos());
                personaStmt.setString(4, cliente.getCelular());
                personaStmt.setString(5, cliente.getCorreo());
                personaStmt.setString(6, cliente.getPassword());
                personaStmt.setInt(7, cliente.getPersonaID());

                status = personaStmt.executeUpdate();
            }

            // Si la actualización de la persona fue exitosa, actualizamos la tabla Cliente
            if (status > 0) {
                try (PreparedStatement clienteStmt = connection.prepareStatement(queryCliente)) {
                    clienteStmt.setString(1, cliente.getTipoCliente().name()); // Enum a String
                    clienteStmt.setString(2, cliente.getDireccion());
                    clienteStmt.setDate(3, new java.sql.Date(cliente.getFechaNacimiento().getTime())); // Convertir java.util.Date a java.sql.Date
                    clienteStmt.setString(4, cliente.getEstadoCivil().name()); // Enum a String
                    clienteStmt.setBoolean(5, cliente.isAutorizacionDeDatos());
                    clienteStmt.setInt(6, cliente.getPersonaID());

                    status = clienteStmt.executeUpdate();
                }
            }

            if (status > 0) {
                System.out.println("ACTUALIZACIÓN DE CLIENTE REALIZADA CON ÉXITO...");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL MODIFICAR EL CLIENTE...");
            e.printStackTrace();
        }

        return status;
    }
    // Cierra: modificar
    
    
    
    
    
    // Abre: eliminar
    public static int eliminar(int personaID) {
        int status = 0;

        // Delete Cliente
        String queryCliente = "DELETE "
                            + "FROM Cliente "
                            + "WHERE personaID = ?;";

        // Delete Persona
        String queryPersona = "DELETE "
                            + "FROM Persona "
                            + "WHERE personaID = ?;";

        try (Connection connection = conexionBD.crearConexion()) {

            // Primero se elimina de la tabla Cliente
            try (PreparedStatement clienteStmt = connection.prepareStatement(queryCliente)) {
                clienteStmt.setInt(1, personaID);
                status = clienteStmt.executeUpdate();
            }

            // Luego de persona
            if (status > 0) {
                try (PreparedStatement personaStmt = connection.prepareStatement(queryPersona)) {
                    personaStmt.setInt(1, personaID);
                    status = personaStmt.executeUpdate();
                }
            }

            if (status > 0) {
                System.out.println("CLIENTE ELIMINADO CON ÉXITO...");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL ELIMINAR EL CLIENTE...");
            e.printStackTrace();
        }

        return status;
    }
    // Cierra: eliminar
    
}
