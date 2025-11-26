package co.edu.konradlorenz.model.dao;

import co.edu.konradlorenz.model.*;
import co.edu.konradlorenz.model.connection.*;
import co.edu.konradlorenz.model.enums.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class CompraInsumosDAO {
    
    private static Conexion conexionBD = new Conexion();
    
    
    
    
    
    // Abre: agregar
    public static int agregar(CompraInsumos compraInsumos, int proveedorID, int productoID) {
        int status = 0;

        // Obtener el proveedorProductoID
        int proveedorProductoID = ProveedorProductoDAO.obtenerProveedorProductoID(proveedorID, productoID);
        
        if (proveedorProductoID == -1) {
            System.out.println("ERROR: No se encontró un proveedorProductoID válido.");
            return status; // Salir si no se encontró
        }

        // Insert CompraInsumos
        String queryCompraInsumos = "INSERT INTO CompraInsumos "
                                  + "(proveedorProductoID, cantidad, estado, "
                                  + "fechaCompra, fechaEntrega, valorTotal) "
                                  + "VALUES (?, ?, ?, ?, ?, ?);";

        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement compraInsumosStmt = connection.prepareStatement(queryCompraInsumos, PreparedStatement.RETURN_GENERATED_KEYS)) {

            compraInsumosStmt.setInt(1, proveedorProductoID);
            compraInsumosStmt.setInt(2, compraInsumos.getCantidad());
            compraInsumosStmt.setString(3, compraInsumos.getEstado().name()); // Enum conversion
            compraInsumosStmt.setDate(4, new java.sql.Date(compraInsumos.getFechaCompra().getTime())); // Convertir java.util.Date a java.sql.Date
            compraInsumosStmt.setDate(5, new java.sql.Date(compraInsumos.getFechaEntrega().getTime())); // Convertir java.util.Date a java.sql.Date
            compraInsumosStmt.setDouble(6, compraInsumos.getValorTotal());

            status = compraInsumosStmt.executeUpdate();

            System.out.println("COMPRA INSUMOS GUARDADA DE FORMA EXITOSA...");
        } catch (SQLException e) {
            System.out.println("ERROR AL REGISTRAR LA COMPRA INSUMOS...");
            e.printStackTrace();
        }

        return status;
    }
    // Cierra: agregar
    
    
    
    
    
    // Abre: obtenerCompraInsumoID
    public static int obtenerCompraInsumoID(int proveedorProductoID) {
        int compraInsumoID = -1;  // Valor predeterminado en caso de que no se encuentre

        String query = "SELECT compraInsumoID "
                     + "FROM CompraInsumos "
                     + "WHERE proveedorProductoID = ?;";

        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, proveedorProductoID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    compraInsumoID = rs.getInt("compraInsumoID");
                }
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER EL COMPRAINSUMOID...");
            e.printStackTrace();
        }

        return compraInsumoID;
    }
    // Cierra: obtenerCompraInsumoID
    
    
    
    
    
    // Abre: obtenerTodos
    public static List<CompraInsumos> obtenerTodos() {
        List<CompraInsumos> comprasInsumos = new ArrayList<>();

        String query = "SELECT * "
                     + "FROM CompraInsumos;";

        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CompraInsumos compraInsumos = new CompraInsumos();

                compraInsumos.setCompraInsumosID(rs.getInt("compraInsumosID"));
                compraInsumos.setProveedorProductoID(rs.getInt("proveedorProductoID"));
                compraInsumos.setCantidad(rs.getInt("cantidad"));

                // Manejo seguro del Enum estado
                try {
                    compraInsumos.setEstado(Estado.valueOf(rs.getString("estado")));
                } catch (IllegalArgumentException e) {
                    System.out.println("Valor inválido para estado: " + rs.getString("estado"));
                }

                compraInsumos.setFechaCompra(rs.getDate("fechaCompra"));
                compraInsumos.setFechaEntrega(rs.getDate("fechaEntrega"));
                compraInsumos.setValorTotal(rs.getDouble("valorTotal"));

                comprasInsumos.add(compraInsumos);
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER LAS COMPRAS DE INSUMOS...");
            e.printStackTrace();
        }

        return comprasInsumos;
    }
    // Cierra: obtenerTodos
    
    
    
    
    
    // Abre: modificar
    public static int modificar(CompraInsumos compraInsumos) {
        int status = 0;

        String query = "UPDATE CompraInsumos "
                     + "SET proveedorProductoID = ?, cantidad = ?, estado = ?, "
                     + "fechaCompra = ?, fechaEntrega = ?, valorTotal = ? "
                     + "WHERE compraInsumosID = ?;";

        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, compraInsumos.getProveedorProductoID());
            ps.setInt(2, compraInsumos.getCantidad());
            ps.setString(3, compraInsumos.getEstado().name()); // Enum a String
            ps.setDate(4, new java.sql.Date(compraInsumos.getFechaCompra().getTime())); // Convertir java.util.Date a java.sql.Date
            ps.setDate(5, new java.sql.Date(compraInsumos.getFechaEntrega().getTime())); // Convertir java.util.Date a java.sql.Date
            ps.setDouble(6, compraInsumos.getValorTotal());
            ps.setInt(7, compraInsumos.getCompraInsumosID());

            status = ps.executeUpdate();

            if (status > 0) {
                System.out.println("COMPRA DE INSUMOS ACTUALIZADA CON ÉXITO...");
            } else {
                System.out.println("NO SE ENCONTRÓ LA COMPRA DE INSUMOS A MODIFICAR...");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL MODIFICAR LA COMPRA DE INSUMOS...");
            e.printStackTrace();
        }

        return status;
    }
    // Cierra: modificar
    
    
    
    
    
    // Abre: eliminar
    public static int eliminar(int compraInsumosID) {
        int status = 0;

        String query = "DELETE "
                     + "FROM CompraInsumos "
                     + "WHERE compraInsumosID = ?;";

        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, compraInsumosID);

            status = ps.executeUpdate();

            if (status > 0) {
                System.out.println("COMPRA DE INSUMOS ELIMINADA CON ÉXITO...");
            } else {
                System.out.println("NO SE ENCONTRÓ LA COMPRA DE INSUMOS A ELIMINAR...");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL ELIMINAR LA COMPRA DE INSUMOS...");
            e.printStackTrace();
        }

        return status;
    }
    // Cierra: eliminar
    
}
