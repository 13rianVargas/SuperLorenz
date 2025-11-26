package co.edu.konradlorenz.model.dao;

import co.edu.konradlorenz.model.*;
import co.edu.konradlorenz.model.connection.*;
import co.edu.konradlorenz.model.enums.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class ProductoDAO {
    
    private static Conexion conexionBD = new Conexion();
    
    
    
    
    
    // Abre: agregar
    public static int agregar(Producto producto) {
        int status = 0;

        // Consulta SQL para insertar un nuevo Producto
        String query = "INSERT INTO Producto "
                     + "(nombreProducto, descripcionProducto, categoria, marca) "
                     + "VALUES (?, ?, ?, ?)";

        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement ps = connection.prepareStatement(query)) {

            // Establecemos los parámetros de la consulta
            ps.setString(1, producto.getNombreProducto());
            ps.setString(2, producto.getDescripcionProducto());
            ps.setString(3, producto.getCategoria());
            ps.setString(4, producto.getMarca());

            // Ejecutamos la inserción
            status = ps.executeUpdate();

            if (status > 0) {
                System.out.println("PRODUCTO AGREGADO CON ÉXITO...");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL AGREGAR EL PRODUCTO...");
            e.printStackTrace();
        }

        return status;
    }
    // Cierra: agregar
    
    
    
    
    
    // Abre: obtenerProductoID
    public static int obtenerProductoID(String nombreProducto) {
        int productoID = -1; // Valor predeterminado en caso de no encontrar el producto

        String query = "SELECT productoID "
                     + "FROM Producto "
                     + "WHERE nombreProducto = ?;";

        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, nombreProducto);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    productoID = rs.getInt("productoID");
                }
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER EL PRODUCTOID...");
            e.printStackTrace();
        }

        return productoID;
    }
    // Cierra: obtenerProductoID
    
    
    
    
    
    // Abre: obtenerProductoPorID
    public static Producto obtenerProductoPorID(int productoID) throws SQLException {
        
        String query = "SELECT * "
                     + "FROM Producto p "
                     + "WHERE productoID = ?;";
        
        try (Connection con = conexionBD.crearConexion();
             PreparedStatement ps = con.prepareStatement(query)) {
                
                ps.setInt(1, productoID);
                
                try (ResultSet rs = ps.executeQuery()) {
                    
                    if (rs.next()) {
                        
                        Producto producto = mapResultSetToProducto(rs);
                        
                        // Recuperar el precio del producto
                        Precio precio = PrecioDAO.obtenerPrecioPorID(producto.getProductoID());
                        
                        if (precio != null) {
                            System.out.println("Precio asociado: " + precio.getPrecioUnitarioTotal());
                        }
                        
                        return producto;
                    }
                }
                
            return null;
        }
    }
    // Cierra: obtenerProductoPorID
    
    
    
    
    
    // Abre: mapResultSetToProducto
    private static Producto mapResultSetToProducto(ResultSet rs) throws SQLException {
        
        Producto producto = new Producto();
        
        producto.setProductoID(rs.getInt("productoID"));
        producto.setNombreProducto(rs.getString("nombreProducto"));
        producto.setDescripcionProducto(rs.getString("descripcionProducto"));
        producto.setCategoria(rs.getString("categoria"));
        producto.setMarca(rs.getString("marca"));
        
        return producto;
    }
    // Cierra: mapResultSetToProducto
    
    
    
    
    
    // Abre: obtenerTodos
    public static List<Producto> obtenerTodos() {
        
        List<Producto> productos = new ArrayList<>();
        
        String query = "SELECT * "
                     + "FROM Producto;";
        
        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                
                Producto producto = new Producto();
                
                producto.setProductoID(rs.getInt("productoID"));
                producto.setNombreProducto(rs.getString("nombreProducto"));
                producto.setDescripcionProducto(rs.getString("descripcionProducto"));
                producto.setCategoria(rs.getString("categoria"));
                producto.setMarca(rs.getString("marca"));
                producto.setImagenURL(rs.getString("imagenURL"));
                
                productos.add(producto);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return productos;
    }
    // Cierra: obtenerTodos
    
    
    
    
    
    // Abre: modificar
    public static int modificar(Producto producto) {
        int status = 0; // Variable para almacenar el estado de la actualización

        // Consulta SQL para actualizar los datos de un producto
        String query = "UPDATE Producto "
                     + "SET nombreProducto = ?, descripcionProducto = ?, "
                     + "categoria = ?, marca = ? "
                     + "WHERE productoID = ?;";

        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement ps = connection.prepareStatement(query)) {

            // Establecemos los parámetros de la consulta
            ps.setString(1, producto.getNombreProducto());
            ps.setString(2, producto.getDescripcionProducto());
            ps.setString(3, producto.getCategoria());
            ps.setString(4, producto.getMarca());
            ps.setInt(5, producto.getProductoID());

            // Ejecutamos la actualización
            status = ps.executeUpdate();

            if (status > 0) {
                System.out.println("ACTUALIZACIÓN DE PRODUCTO REALIZADA CON ÉXITO...");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL MODIFICAR EL PRODUCTO...");
            e.printStackTrace();
        }

        return status;
    }
    // Cierra: modificar
    
    
    
    
    
    // Abre: eliminar
    public static int eliminar(int productoID) {
        int status = 0; // Variable para almacenar el estado de la eliminación

        String query = "DELETE "
                     + "FROM Producto "
                     + "WHERE productoID = ?;";

        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, productoID);

            status = ps.executeUpdate();

            if (status > 0) {
                System.out.println("PRODUCTO ELIMINADO CON ÉXITO...");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL ELIMINAR EL PRODUCTO...");
            e.printStackTrace();
        }

        return status;
    }
    // Cierra: eliminar
}