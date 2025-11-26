package co.edu.konradlorenz.model.dao;

import co.edu.konradlorenz.model.*;
import co.edu.konradlorenz.model.connection.*;
import co.edu.konradlorenz.model.enums.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;


public class EmpleadoDAO {
     
    private static Conexion conexionBD = new Conexion();
    
    
    
    
    
    // Abre: iniciarSesion
    public static Empleado iniciarSesion(String correo, String password) {
        Empleado empleado = null;

        String query = "SELECT * "
                     + "FROM Persona p "
                     + "INNER JOIN Empleado e "
                     + "ON p.personaID = e.personaID "
                     + "WHERE correo = ? AND password = ?;";

        try (Connection connection = conexionBD.crearConexion();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, correo);
            ps.setString(2, password);

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    empleado = new Empleado();
                    empleado.setCorreo(resultSet.getString("correo"));
                    empleado.setPassword(resultSet.getString("password"));
                    // Recuperar el cargo como String y convertirlo a enum
                    String cargoStr = resultSet.getString("cargo");
                    if (cargoStr != null) {
                        try {
                            empleado.setCargo(Cargo.valueOf(cargoStr));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Cargo no válido: " + cargoStr);
                            empleado.setCargo(null);
                        }
                    } else {
                        empleado.setCargo(null);
                    }
                }
            }

            // Mensaje de éxito
            if (empleado != null) {
                System.out.println("EMPLEADO LOGEADO DE FORMA EXITOSA...");
            } else {
                System.out.println("EMPLEADO NO ENCONTRADO...");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL LOGEAR EMPLEADO...");
            e.printStackTrace();
        }

        return empleado;
    }
    // Cierra: iniciarSesion
    
    
    
    
    
    //Abre: agregarEmpleado
    public static int agregar(Empleado empleado){
        int status = 0;
        
        // Insert Persona
        String queryPersona = "INSERT INTO Persona "
                            + "(numeroDocumento, tipoDocumento, nombres, "
                            + "apellidos, celular, correo, password) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?);";
        
        // Insert Empleado
        String queryEmpleado =  "INSERT INTO Empleado "
                             + "(personaID, cargo, dependencia, tipoContrato, "
                             + "eps, arl, nombreEmergencia, celularEmergencia) "
                             + "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        
        
        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement personaStmt = connection.prepareStatement(queryPersona, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Insert en la tabla Persona
            personaStmt.setString(1, empleado.getNumeroDocumento());
            personaStmt.setString(2, empleado.getTipoDocumento().name()); // Enum a String
            personaStmt.setString(3, empleado.getNombres());
            personaStmt.setString(4, empleado.getApellidos());
            personaStmt.setString(5, empleado.getCelular());
            personaStmt.setString(6, empleado.getCorreo());
            personaStmt.setString(7, empleado.getPassword());
            
            personaStmt.executeUpdate();

            // Obtener personaID generado
            int personaID = 0;
            try (ResultSet resultSet = personaStmt.getGeneratedKeys()) {
                if (resultSet.next()) {
                    personaID = resultSet.getInt(1);
                }
            }

            // Insert en la tabla Empleado
            try (PreparedStatement empleadoStmt = connection.prepareStatement(queryEmpleado)) {
                empleadoStmt.setInt(1, personaID);
                empleadoStmt.setString(2, empleado.getCargo().name()); // Enum a String
                empleadoStmt.setString(3, empleado.getDependencia().name()); // Enum a String
                empleadoStmt.setString(4, empleado.getTipoContrato().name()); // Enum a String
                empleadoStmt.setString(5, empleado.getEps());
                empleadoStmt.setString(6, empleado.getArl());
                empleadoStmt.setString(7, empleado.getNombreEmergencia());
                empleadoStmt.setString(8, empleado.getCelularEmergencia());

                status = empleadoStmt.executeUpdate();
            }

            System.out.println("EMPLEADO GUARDADO DE FORMA EXITOSA...");
        } catch (SQLException e) {
            System.out.println("ERROR AL REGISTRAR EL EMPLEADO...");
            e.printStackTrace();
        }

        return status;
    }
    // Cierra: agregar
    
    
    
    
    
    // Abre: obtenerEmpleadoID
    public static int obtenerEmpleadoID(String nombreEmpleado) {
        int empleadoID = -1;

        String query = "SELECT empleadoID "
                     + "FROM Empleado "
                     + "WHERE nombre = ?;";

        try (Connection connection = conexionBD.crearConexion(); 
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, nombreEmpleado); // O el criterio que desees usar

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    empleadoID = rs.getInt("empleadoID");
                }
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER EL EMPLEADO ID...");
            e.printStackTrace();
        }

        return empleadoID;
    }
    // Cierra: obtenerEmpleadoID
    
    
    
    
    
    // Abre: obtenerTodos
    public static List<Empleado> obtenerTodos() {

        List<Empleado> empleados = new ArrayList<>();

        String query = "SELECT * "
                     + "FROM Empleado;";

        try (Connection connection = conexionBD.crearConexion(); 
             PreparedStatement ps = connection.prepareStatement(query); 
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Empleado empleado = new Empleado();

                empleado.setEmpleadoID(rs.getInt("empleadoID"));
                empleado.setPersonaID(rs.getInt("personaID"));
                
                // Manejo seguro de la conversión a Enum
                try {
                    empleado.setCargo(Cargo.valueOf(rs.getString("cargo")));
                } catch (IllegalArgumentException e) {
                    System.out.println("Valor inválido para cargo: " + rs.getString("cargo"));
                }
                
                // Manejo seguro de la conversión a Enum
                try {
                    empleado.setDependencia(Dependencia.valueOf(rs.getString("dependencia")));
                } catch (IllegalArgumentException e) {
                    System.out.println("Valor inválido para dependencia: " + rs.getString("dependencia"));
                }
                
                // Manejo seguro de la conversión a Enum
                try {
                    empleado.setTipoContrato(TipoContrato.valueOf(rs.getString("tipoContrato")));
                } catch (IllegalArgumentException e) {
                    System.out.println("Valor inválido para tipoContrato: " + rs.getString("tipoContrato"));
                }
            
                empleado.setEps(rs.getString("eps"));
                empleado.setArl(rs.getString("arl"));
                empleado.setNombreEmergencia(rs.getString("nombreEmergencia"));
                empleado.setCelularEmergencia(rs.getString("celularEmergencia"));

                empleados.add(empleado);
                
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER LOS EMPLEADOS...");
            e.printStackTrace();
        }

        return empleados;
    }
    // Cierra: obtenerTodos
    
    
    
    
    
    // Abre: modificar
    public static int modificar(Empleado empleado) {
        int status = 0;

        // Update Persona
        String queryPersona = "UPDATE Persona "
                            + "SET tipoDocumento = ?, nombres = ?, "
                            + "apellidos = ?, celular = ?, correo = ?, "
                            + "password = ? "
                            + "WHERE personaID = ?;";

        // Update Empleado
        String queryEmpleado = "UPDATE Empleado "
                             + "SET cargo = ?, dependencia = ?, "
                             + "tipoContrato = ?, eps = ?, arl = ?, "
                             + "nombreEmergencia = ?, celularEmergencia = ? "
                             + "WHERE personaID = ?;";

        try (Connection connection = conexionBD.crearConexion()) {

            // Actualizar la tabla Persona
            try (PreparedStatement personaStmt = connection.prepareStatement(queryPersona)) {
                personaStmt.setString(1, empleado.getTipoDocumento().name()); // Enum a String
                personaStmt.setString(2, empleado.getNombres());
                personaStmt.setString(3, empleado.getApellidos());
                personaStmt.setString(4, empleado.getCelular());
                personaStmt.setString(5, empleado.getCorreo());
                personaStmt.setString(6, empleado.getPassword());
                personaStmt.setInt(7, empleado.getPersonaID());

                status = personaStmt.executeUpdate();
            }

            // Si la actualización de la persona fue exitosa, actualizamos la tabla Empleado
            if (status > 0) {
                try (PreparedStatement empleadoStmt = connection.prepareStatement(queryEmpleado)) {
                    empleadoStmt.setString(1, empleado.getCargo().name()); // Enum a String
                    empleadoStmt.setString(2, empleado.getDependencia().name()); // Enum a String
                    empleadoStmt.setString(3, empleado.getTipoContrato().name()); // Enum a String
                    empleadoStmt.setString(4, empleado.getEps());
                    empleadoStmt.setString(5, empleado.getArl());
                    empleadoStmt.setString(6, empleado.getNombreEmergencia());
                    empleadoStmt.setString(7, empleado.getCelularEmergencia());
                    empleadoStmt.setInt(8, empleado.getPersonaID());

                    status = empleadoStmt.executeUpdate();
                }
            }
            
            if (status > 0) {
                System.out.println("ACTUALIZACIÓN DE EMPLEADO REALIZADA CON ÉXITO...");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL MODIFICAR EL EMPLEADO...");
            e.printStackTrace();
        }

        return status;
    }
    // Cierra: modificar
    
    
    
    
    
    // Abre: eliminar
    public static int eliminar(int personaID) {
        int status = 0;

        // Delete Empleado
        String queryEmpleado = "DELETE "
                             + "FROM Empleado "
                             + "WHERE personaID = ?;";

        // Delete Persona
        String queryPersona = "DELETE "
                            + "FROM Persona "
                            + "WHERE personaID = ?;";

        try (Connection connection = conexionBD.crearConexion()) {

            // Primero se elimina de la tabla Empleado
            try (PreparedStatement empleadoStmt = connection.prepareStatement(queryEmpleado)) {
                empleadoStmt.setInt(1, personaID);
                status = empleadoStmt.executeUpdate();
            }

            // Luego de persona
            if (status > 0) {
                try (PreparedStatement personaStmt = connection.prepareStatement(queryPersona)) {
                    personaStmt.setInt(1, personaID);
                    status = personaStmt.executeUpdate();
                }
            }

            if (status > 0) {
                System.out.println("EMPLEADO ELIMINADO CON ÉXITO...");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL ELIMINAR EL EMPLEADO...");
            e.printStackTrace();
        }

        return status;
    }
    // Cierra: eliminar
    
}            