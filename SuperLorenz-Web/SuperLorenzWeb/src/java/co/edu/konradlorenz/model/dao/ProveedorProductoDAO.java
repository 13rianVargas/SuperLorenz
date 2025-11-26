package co.edu.konradlorenz.model.dao;

import co.edu.konradlorenz.model.*;
import co.edu.konradlorenz.model.connection.*;
import co.edu.konradlorenz.model.enums.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class ProveedorProductoDAO {
    
    private static Conexion conexionBD = new Conexion();
    
    
    
    
    
    // Abre: agregar
    public static int agregar(ProveedorProducto proveedorProducto) {
        int status = 0;

        String query = "INSERT INTO ProveedorProducto "
                     + "(productoID, proveedorID, valorMinimo, valorMaximo) "
                     + "VALUES (?, ?, ?, ?)";

        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, proveedorProducto.getProductoID());
            ps.setInt(2, proveedorProducto.getProveedorID());
            ps.setDouble(3, proveedorProducto.getValorMinimo());
            ps.setDouble(4, proveedorProducto.getValorMaximo());

            status = ps.executeUpdate();

            if (status > 0) {
                System.out.println("PROVEEDOR-PRODUCTO GUARDADO CON ÉXITO...");
            } else {
                System.out.println("NO SE PUDO GUARDAR EL PROVEEDOR-PRODUCTO...");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL REGISTRAR EL PROVEEDOR-PRODUCTO...");
            e.printStackTrace();
        }

        return status;
    }
    // Cierra: agregar
    
    
    
    
    
    // Abre: obtenerProveedorProductoID
    public static int obtenerProveedorProductoID(int proveedorID, int productoID) {
        int proveedorProductoID = -1; // Valor predeterminado si no se encuentra

        String query = "SELECT proveedorProductoID "
                     + "FROM ProveedorProducto "
                     + "WHERE proveedorID = ? AND productoID = ?;";

        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, proveedorID);
            ps.setInt(2, productoID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    proveedorProductoID = rs.getInt("proveedorProductoID");
                }
            }
        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER proveedorProductoID...");
            e.printStackTrace();
        }

        return proveedorProductoID;
    }
    // Cierra: obtenerProveedorProductoID
    
    
    
    
    
    // Abre: obtenerTodos
    public static List<ProveedorProducto> obtenerTodos() {

        List<ProveedorProducto> proveedorProductos = new ArrayList<>();

        String query = "SELECT * "
                     + "FROM ProveedorProducto";

        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ProveedorProducto proveedorProducto = new ProveedorProducto();

                proveedorProducto.setProveedorProductoID(rs.getInt("proveedorProductoID"));
                proveedorProducto.setProductoID(rs.getInt("productoID"));
                proveedorProducto.setProveedorID(rs.getInt("proveedorID"));
                proveedorProducto.setValorMinimo(rs.getDouble("valorMinimo"));
                proveedorProducto.setValorMaximo(rs.getDouble("valorMaximo"));

                proveedorProductos.add(proveedorProducto);
            }

            System.out.println("LISTA DE PROVEEDOR-PRODUCTOS OBTENIDA CON ÉXITO...");

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER LOS PROVEEDOR-PRODUCTOS...");
            e.printStackTrace();
        }

        return proveedorProductos;
    }
    // Cierra: obtenerTodos
    
    
    
    
    
    // Abre: modificar
    public static int modificar(ProveedorProducto proveedorProducto) {
        int status = 0;

        String query = "UPDATE ProveedorProducto "
                     + "SET productoID = ?, proveedorID = ?, valorMinimo = ?, valorMaximo = ? "
                     + "WHERE proveedorProductoID = ?";

        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, proveedorProducto.getProductoID());
            ps.setInt(2, proveedorProducto.getProveedorID());
            ps.setDouble(3, proveedorProducto.getValorMinimo());
            ps.setDouble(4, proveedorProducto.getValorMaximo());
            ps.setInt(5, proveedorProducto.getProveedorProductoID());

            status = ps.executeUpdate();

            if (status > 0) {
                System.out.println("PROVEEDOR-PRODUCTO MODIFICADO CON ÉXITO...");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL MODIFICAR EL PROVEEDOR-PRODUCTO...");
            e.printStackTrace();
        }

        return status;
    }
    // Cierra: modificar
    
    
    
    
    
    // Abre: eliminar
    public static int eliminar(int proveedorProductoID) {
        int status = 0;

        String query = "DELETE "
                     + "FROM ProveedorProducto "
                     + "WHERE proveedorProductoID = ?";

        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, proveedorProductoID);

            status = ps.executeUpdate();

            if (status > 0) {
                System.out.println("PROVEEDOR-PRODUCTO ELIMINADO CON ÉXITO...");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL ELIMINAR EL PROVEEDOR-PRODUCTO...");
            e.printStackTrace();
        }

        return status;
    }
    // Cierra: eliminar
    
}
