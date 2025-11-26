package co.edu.konradlorenz.model.dao;

import co.edu.konradlorenz.model.*;
import co.edu.konradlorenz.model.connection.*;
import co.edu.konradlorenz.model.enums.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class ProductoPedidoDAO {
    
    private static Conexion conexionBD = new Conexion();
    
    
    
    
    
    // Abre: agregar
    public static int agregar(int pedidoID, int productoID, int cantidad) {
        int status = 0;

        String query = "INSERT INTO ProductoPedido "
                     + "(pedidoID, productoID, cantidad) "
                     + "VALUES (?, ?, ?);";

        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, pedidoID);
            ps.setInt(2, productoID);
            ps.setInt(3, cantidad);

            status = ps.executeUpdate();

            if (status > 0) {
                System.out.println("PRODUCTO PEDIDO AGREGADO CON ÉXITO...");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL AGREGAR EL PRODUCTO AL PEDIDO...");
            e.printStackTrace();
        }

        return status;
    }
    // Cierra: agregar
    
    
    
    
    
    // Abre: obtenerProductoPedidoID
    public static int obtenerProductoPedidoID(int pedidoID, int productoID) {
        int productoPedidoID = -1;

        String query = "SELECT productoPedidoID "
                     + "FROM ProductoPedido "
                     + "WHERE pedidoID = ? AND productoID = ?";

        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, pedidoID);
            ps.setInt(2, productoID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    productoPedidoID = rs.getInt("productoPedidoID");
                }
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER EL ID DEL PRODUCTO PEDIDO...");
            e.printStackTrace();
        }

        return productoPedidoID;
    }
    // Cierra: obtenerProductoPedidoID
    
    
    
    
    
    // Abre: obtenerTodos
    public static List<ProductoPedido> obtenerTodos() {
        
        List<ProductoPedido> listaProductoPedidos = new ArrayList<>();

        String query = "SELECT * "
                     + "FROM ProductoPedido;";

        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Crear un nuevo objeto ProductoPedido con los datos obtenidos
                int pedidoID = rs.getInt("pedidoID");
                int productoID = rs.getInt("productoID");
                int cantidad = rs.getInt("cantidad");

                ProductoPedido productoPedido = new ProductoPedido();
                
                productoPedido.setPedidoID(pedidoID);
                productoPedido.setProductoID(productoID);
                productoPedido.setCantidad(cantidad);

                listaProductoPedidos.add(productoPedido);
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER LOS PRODUCTOS PEDIDOS...");
            e.printStackTrace();
        }

        return listaProductoPedidos;
    }
    // Cierra: obtenerTodos
    
    
    
    
    
    // Abre: modificar
    public static int modificar(ProductoPedido productoPedido) {
        int status = 0;

        String query = "UPDATE ProductoPedido "
                     + "SET cantidad = ? "
                     + "WHERE pedidoID = ? AND productoID = ?;";

        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, productoPedido.getCantidad());
            ps.setInt(2, productoPedido.getPedidoID());
            ps.setInt(3, productoPedido.getProductoID());

            status = ps.executeUpdate();

            if (status > 0) {
                System.out.println("PRODUCTO-PEDIDO MODIFICADO CON ÉXITO...");
            } else {
                System.out.println("NO SE PUDO MODIFICAR EL PRODUCTO-PEDIDO...");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL MODIFICAR EL PRODUCTO-PEDIDO...");
            e.printStackTrace();
        }

        return status;
    }
    // Cierra: modificar
    
    
    
    
    
    // Abre: eliminar
    public static int eliminar(int pedidoID, int productoID) {
        int status = 0;

        String query = "DELETE "
                     + "FROM ProductoPedido "
                     + "WHERE pedidoID = ? AND productoID = ?;";

        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, pedidoID);  // pedidoID
            ps.setInt(2, productoID);  // productoID

            status = ps.executeUpdate();

            if (status > 0) {
                System.out.println("PRODUCTO-PEDIDO ELIMINADO CON ÉXITO...");
            } else {
                System.out.println("NO SE PUDO ELIMINAR EL PRODUCTO-PEDIDO...");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL ELIMINAR EL PRODUCTO-PEDIDO...");
            e.printStackTrace();
        }

        return status;
    }
    // Cierra: eliminar
    
}
