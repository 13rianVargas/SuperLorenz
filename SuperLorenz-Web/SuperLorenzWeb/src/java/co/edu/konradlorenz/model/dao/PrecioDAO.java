package co.edu.konradlorenz.model.dao;

import co.edu.konradlorenz.model.*;
import co.edu.konradlorenz.model.connection.*;
import co.edu.konradlorenz.model.enums.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class PrecioDAO {
    
    private static Conexion conexionBD = new Conexion();
    
    
    
    
    
    // Abre: agregar
    public static int agregar(String nombreProducto, double valorUnitario, double iva, double precioUnitarioTotal, Date fecha) {
        int status = 0;

        // Obtenemos el productoID a partir del nombre del producto
        int productoID = ProductoDAO.obtenerProductoID(nombreProducto);

        if (productoID != -1) {
            String query = "INSERT INTO Precio "
                         + "(productoID, valorUnitario, iva, "
                         + "precioUnitarioTotal, fecha) "
                         + "VALUES (?, ?, ?, ?, ?);";

            try (Connection connection = conexionBD.crearConexion();
                 PreparedStatement ps = connection.prepareStatement(query)) {

                ps.setInt(1, productoID);
                ps.setDouble(2, valorUnitario);
                ps.setDouble(3, iva);
                ps.setDouble(4, precioUnitarioTotal);
                ps.setDate(5, new java.sql.Date(fecha.getTime())); // Convertir java.util.Date a java.sql.Date

                status = ps.executeUpdate();

                if (status > 0) {
                    System.out.println("PRECIO AGREGADO CON ÉXITO...");
                }

            } catch (SQLException e) {
                System.out.println("ERROR AL AGREGAR EL PRECIO...");
                e.printStackTrace();
            }
        } else {
            System.out.println("PRODUCTO NO ENCONTRADO...");
        }

        return status;
    }
    // Cierra: agregar
    
    
    
    
    
    // Abre: obtenerPrecioID
    public static int obtenerPrecioID(int productoID) {
        int precioID = -1;  // Valor predeterminado en caso de que no se encuentre

        String query = "SELECT precioID "
                     + "FROM Precio "
                     + "WHERE productoID = ? "
                     + "ORDER BY fecha DESC LIMIT 1;";

        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, productoID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    precioID = rs.getInt("precioID");
                }
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER EL PRECIOID...");
            e.printStackTrace();
        }

        return precioID;
    }
    // Cierra: obtenerPrecioID
    
    
    
    
    
    
    
    // Abre: obtenerPrecioPorID
    public static Precio obtenerPrecioPorID(int precioID) throws SQLException {
        
        String query = "SELECT * "
                     + "FROM Precio pr "
                     + "WHERE precioID = ?;";
        
        try (Connection con = conexionBD.crearConexion();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setInt(1, precioID);
            
            try (ResultSet rs = ps.executeQuery()) {
                
                if (rs.next()) {
                    return mapResultSetToPrecio(rs);
                }
                
            }
            
            return null;
        }
    }
    // Cierra: obtenerPrecioPorID
    
    
    
    
    
    // Abre: obtenerPreciosPorProductoID
    public static List<Precio> obtenerPreciosPorProductoID(int productoID) throws SQLException {
        
        List<Precio> precios = new ArrayList<>();
        
        String query = "SELECT * "
                     + "FROM Precio pr "
                     + "WHERE productoID = ?;";
        
        try (Connection con = conexionBD.crearConexion(); 
             PreparedStatement ps = con.prepareStatement(query);) {
            
            ps.setInt(1, productoID);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    precios.add(mapResultSetToPrecio(rs));
                }

            }

            return precios;
        }
    }
    // Cierra: obtenerPreciosPorProductoID
    
    
    
    
    
    // Abre: mapResultSetToPrecio
    private static Precio mapResultSetToPrecio(ResultSet rs) throws SQLException {
        
        Precio precio = new Precio();
        
        precio.setPrecioID(rs.getInt("precioID"));
        precio.setProductoID(rs.getInt("productoID"));
        precio.setValorUnitario(rs.getDouble("valorUnitario"));
        precio.setIva(rs.getDouble("iva"));
        precio.setPrecioUnitarioTotal(rs.getDouble("precioUnitarioTotal"));
        precio.setFecha(rs.getDate("fecha"));
        
        return precio;
    }
    // Cierra: mapResultSetToPrecio
    
    
    
    
    
    // Abre: obtenerTodos
    public static List<Precio> obtenerTodos() {
        List<Precio> precios = new ArrayList<>();

        String query = "SELECT * FROM "
                     + "Precio "
                     + "ORDER BY fecha DESC;";  // Selecciona todos los registros, ordenados por fecha

        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Precio precio = new Precio();

                precio.setPrecioID(rs.getInt("precioID"));
                precio.setProductoID(rs.getInt("productoID"));
                precio.setValorUnitario(rs.getDouble("valorUnitario"));
                precio.setIva(rs.getDouble("iva"));
                precio.setPrecioUnitarioTotal(rs.getDouble("precioUnitarioTotal"));
                precio.setFecha(rs.getDate("fecha"));

                precios.add(precio);
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER LOS PRECIOS...");
            e.printStackTrace();
        }

        return precios;
    }
    // Cierra: obtenerTodos
    
    
    
    
    
    // Abre: modificar
    public static int modificar(Precio precio) {
        int status = 0;

        String query = "UPDATE Precio "
                     + "SET productoID = ?, valorUnitario = ?, iva = ?, "
                     + "precioUnitarioTotal = ?, fecha = ? "
                     + "WHERE precioID = ?;";

        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, precio.getProductoID());
            ps.setDouble(2, precio.getValorUnitario());
            ps.setDouble(3, precio.getIva());
            ps.setDouble(4, precio.getPrecioUnitarioTotal());
            ps.setDate(5, new java.sql.Date(precio.getFecha().getTime()));  // Convertimos java.util.Date a java.sql.Date
            ps.setInt(6, precio.getPrecioID());

            status = ps.executeUpdate();

            if (status > 0) {
                System.out.println("ACTUALIZACIÓN DE PRECIO REALIZADA CON ÉXITO...");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL MODIFICAR EL PRECIO...");
            e.printStackTrace();
        }

        return status;
    }
    // Cierra: modificar
    
    
    
    
    
    // Abre: eliminar
    public static int eliminar(int precioID) {
        int status = 0;

        // Consulta SQL para eliminar el registro de Precio
        String query = "DELETE "
                     + "FROM Precio "
                     + "WHERE precioID = ?;";

        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement ps = connection.prepareStatement(query)) {

            // Establecemos el parámetro del precioID
            ps.setInt(1, precioID);

            // Ejecutamos la eliminación
            status = ps.executeUpdate();

            if (status > 0) {
                System.out.println("PRECIO ELIMINADO CON ÉXITO...");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL ELIMINAR EL PRECIO...");
            e.printStackTrace();
        }

        return status;
    }
    // Cierra: eliminar
    
}