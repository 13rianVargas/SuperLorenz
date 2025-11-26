package co.edu.konradlorenz.model.dao;

import co.edu.konradlorenz.model.*;
import co.edu.konradlorenz.model.connection.*;
import co.edu.konradlorenz.model.enums.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class KardexDAO {
    
    private static Conexion conexionBD = new Conexion();
    
    
    
    
    
    // Abre: agregar
    public static int agregar(Kardex kardex, String nombreProducto) {
        int status = 0;

        int productoID = ProductoDAO.obtenerProductoID(nombreProducto);

        if (productoID == -1) {
            System.out.println("ERROR: No se encontró un producto válido.");
            return status; // Salir si no se encontró
        }

        // Insert Kardex
        String queryKardex = "INSERT INTO Kardex "
                           + "(productoID, cantidadSalida, cantidadEntrada, "
                           + "cantidadDisponible, motivoSalida, "
                           + "otroMotivoSalida, fecha) "
                           + "VALUES (?, ?, ?, ?, ?, ?, ?);";

        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement kardexStmt = connection.prepareStatement(queryKardex)) {

            kardexStmt.setInt(1, productoID);
            kardexStmt.setInt(2, kardex.getCantidadSalida());
            kardexStmt.setInt(3, kardex.getCantidadEntrada());
            kardexStmt.setInt(4, kardex.getCantidadDisponible());
            kardexStmt.setString(5, kardex.getMotivoSalida().name()); // Enum a String
            kardexStmt.setString(6, kardex.getOtroMotivoSalida()); // Puede ser null
            kardexStmt.setDate(7, new java.sql.Date(kardex.getFecha().getTime())); // Convertir java.util.Date a java.sql.Date

            status = kardexStmt.executeUpdate();

            System.out.println("KARDEX GUARDADO DE FORMA EXITOSA...");
        } catch (SQLException e) {
            System.out.println("ERROR AL REGISTRAR EL KARDEX...");
            e.printStackTrace();
        }

        return status;
    }
    // Cierra: agregar
    
    
    
    
    
    // Abre: obtenerKardexID
    public static int obtenerKardexID(int productoID, Date fecha) {
        int kardexID = -1;

        String query = "SELECT kardexID "
                     + "FROM Kardex "
                     + "WHERE productoID = ? AND fecha = ?;";

        try (Connection connection = conexionBD.crearConexion(); 
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, productoID);
            ps.setDate(2, new java.sql.Date(fecha.getTime())); // Convertir java.util.Date a java.sql.Date

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                kardexID = rs.getInt("kardexID");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER EL KARDEX ID...");
            e.printStackTrace();
        }

        return kardexID;
    }
    // Cierra: obtenerKardexID
    
    
    
    
    
    // Abre: obtenerTodos
    public static List<Kardex> obtenerTodos() {
        
        List<Kardex> kardexList = new ArrayList<>();

        String query = "SELECT * "
                     + "FROM Kardex;";

        try (Connection connection = conexionBD.crearConexion(); 
             PreparedStatement ps = connection.prepareStatement(query); 
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Kardex kardex = new Kardex();

                kardex.setKardexID(rs.getInt("kardexID"));
                kardex.setProductoID(rs.getInt("productoID"));
                kardex.setCantidadSalida(rs.getInt("cantidadSalida"));
                kardex.setCantidadEntrada(rs.getInt("cantidadEntrada"));
                kardex.setCantidadDisponible(rs.getInt("cantidadDisponible"));

                // Manejo seguro de la conversión a Enum
                try {
                    kardex.setMotivoSalida(MotivoSalida.valueOf(rs.getString("motivoSalida")));
                } catch (IllegalArgumentException e) {
                    System.out.println("Valor inválido para motivoSalida: " + rs.getString("motivoSalida"));
                }

                kardex.setOtroMotivoSalida(rs.getString("otroMotivoSalida"));
                kardex.setFecha(rs.getDate("fecha"));

                kardexList.add(kardex);
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER LOS REGISTROS DE KARDEX...");
            e.printStackTrace();
        }

        return kardexList;
    }
    // Cierra: obtenerTodos
    
    
    
    
    
    // Abre: modificar
    public static int modificar(Kardex kardex) {
        int status = 0;

        String query = "UPDATE Kardex "
                     + "SET productoID = ?, cantidadSalida = ?, "
                     + "cantidadEntrada = ?, cantidadDisponible = ?, "
                     + "motivoSalida = ?, otroMotivoSalida = ?, fecha = ? "
                     + "WHERE kardexID = ?";

        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, kardex.getProductoID());
            ps.setInt(2, kardex.getCantidadSalida());
            ps.setInt(3, kardex.getCantidadEntrada());
            ps.setInt(4, kardex.getCantidadDisponible());

            // Convertir el motivoSalida a String para almacenarlo en la base de datos
            ps.setString(5, kardex.getMotivoSalida().name());

            // Si otroMotivoSalida es null, se guarda como null en la base de datos
            ps.setString(6, kardex.getOtroMotivoSalida());

            ps.setDate(7, new java.sql.Date(kardex.getFecha().getTime()));

            ps.setInt(8, kardex.getKardexID());

            status = ps.executeUpdate();

            if (status > 0) {
                System.out.println("ACTUALIZACIÓN DE KARDEX REALIZADA CON ÉXITO...");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL MODIFICAR EL REGISTRO DE KARDEX...");
            e.printStackTrace();
        }

        return status;
    }
    // Cierra: modificar
    
    
    
    
    
    // Abre: eliminar
    public static int eliminar(int kardexID) {
        int status = 0;

        String query = "DELETE "
                     + "FROM Kardex "
                     + "WHERE kardexID = ?;";

        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, kardexID);

            status = ps.executeUpdate();

            if (status > 0) {
                System.out.println("REGISTRO DE KARDEX ELIMINADO CON ÉXITO...");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL ELIMINAR EL REGISTRO DE KARDEX...");
            e.printStackTrace();
        }

        return status;
    }
    // Cierra: eliminar
    
}
