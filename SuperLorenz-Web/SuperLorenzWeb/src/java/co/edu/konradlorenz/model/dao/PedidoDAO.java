package co.edu.konradlorenz.model.dao;

import co.edu.konradlorenz.model.*;
import co.edu.konradlorenz.model.connection.*;
import co.edu.konradlorenz.model.enums.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class PedidoDAO {
    
    private static Conexion conexionBD = new Conexion(); 
    
    
    
    
    
    // Abre: agregar
    public static int agregar(Pedido pedido, String nombreEmpleado, String nombreCliente) {
        int status = 0;

        // Obtener el empleadoID y clienteID antes de la inserción
        int empleadoID = EmpleadoDAO.obtenerEmpleadoID(nombreEmpleado);
        int clienteID = ClienteDAO.obtenerClienteID(nombreCliente);

        if (empleadoID == -1 || clienteID == -1) {
            System.out.println("ERROR: No se encontraron los IDs de empleado o cliente.");
            return status; // Salir si no se encontraron los IDs
        }

        String query = "INSERT INTO Pedido "
                     + "(empleadoID, clienteID, medioPago, fechaPago, "
                     + "fechaEnvio, fechaEntrega, valorTotal) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?);";

        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, empleadoID);
            ps.setInt(2, clienteID);
            ps.setString(3, pedido.getMedioPago().name()); // Convertir Enum a String
            ps.setDate(4, new java.sql.Date(pedido.getFechaPago().getTime())); // Convertir java.util.Date a java.sql.Date
            ps.setDate(5, new java.sql.Date(pedido.getFechaEnvio().getTime())); // Convertir java.util.Date a java.sql.Date
            ps.setDate(6, new java.sql.Date(pedido.getFechaEntrega().getTime())); // Convertir java.util.Date a java.sql.Date
            ps.setDouble(7, pedido.getValorTotal());

            status = ps.executeUpdate();

            if (status > 0) {
                System.out.println("PEDIDO REGISTRADO CON ÉXITO...");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL REGISTRAR EL PEDIDO...");
            e.printStackTrace();
        }

        return status;
    }
    // Cierra: agregar
    
    
    
    
    
    // Abre: obtenerPedidoID
    public static int obtenerPedidoID(int clienteID, int empleadoID) {
        int pedidoID = -1;

        String query = "SELECT pedidoID "
                     + "FROM Pedido "
                     + "WHERE clienteID = ? AND empleadoID = ?;";

        try (Connection connection = conexionBD.crearConexion(); 
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, clienteID);  // Buscar por clienteID
            ps.setInt(2, empleadoID); // Buscar por empleadoID

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pedidoID = rs.getInt("pedidoID");
                }
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER EL PEDIDO ID...");
            e.printStackTrace();
        }

        return pedidoID;
    }
    // Cierra: obtenerPedidoID
    
    
    
    
    
    // Abre: obtenerTodos
    public static List<Pedido> obtenerTodos() {
        
        List<Pedido> pedidos = new ArrayList<>();
        
        String query = "SELECT * "
                     + "FROM Pedido;";

        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Pedido pedido = new Pedido();

                pedido.setPedidoID(rs.getInt("pedidoID"));
                pedido.setEmpleadoID(rs.getInt("empleadoID"));
                pedido.setClienteID(rs.getInt("clienteID"));

                // Convertir el valor del Enum medioPago
                try {
                    pedido.setMedioPago(MedioPago.valueOf(rs.getString("medioPago")));  // Convertir a Enum
                } catch (IllegalArgumentException e) {
                    System.out.println("Valor inválido para medioPago: " + rs.getString("medioPago"));
                }

                pedido.setFechaPago(rs.getDate("fechaPago"));
                pedido.setFechaEnvio(rs.getDate("fechaEnvio"));
                pedido.setFechaEntrega(rs.getDate("fechaEntrega"));
                pedido.setValorTotal(rs.getDouble("valorTotal"));

                pedidos.add(pedido);
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER LOS PEDIDOS...");
            e.printStackTrace();
        }

        return pedidos;
    }
    // Cierra: obtenerTodos
    
    
    
    
    
    // Abre: modificar
    public static int modificar(Pedido pedido) {
        int status = 0;

        String query = "UPDATE Pedido "
                     + "SET empleadoID = ?, clienteID = ?, medioPago = ?, "
                     + "fechaPago = ?, fechaEnvio = ?, fechaEntrega = ?, "
                     + "valorTotal = ? "
                     + "WHERE pedidoID = ?;";

        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, pedido.getEmpleadoID());
            ps.setInt(2, pedido.getClienteID());
            ps.setString(3, pedido.getMedioPago().name());  // Convertir el Enum a String
            ps.setDate(4, new java.sql.Date(pedido.getFechaPago().getTime())); // Convertir java.util.Date a java.sql.Date
            ps.setDate(5, new java.sql.Date(pedido.getFechaEnvio().getTime())); // Convertir java.util.Date a java.sql.Date
            ps.setDate(6, new java.sql.Date(pedido.getFechaEntrega().getTime())); // Convertir java.util.Date a java.sql.Date
            ps.setDouble(7, pedido.getValorTotal());
            ps.setInt(8, pedido.getPedidoID());

            status = ps.executeUpdate();

            if (status > 0) {
                System.out.println("ACTUALIZACIÓN DE PEDIDO REALIZADA CON ÉXITO...");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL MODIFICAR EL PEDIDO...");
            e.printStackTrace();
        }

        return status;
    }
    // Cierra: modificar
    
    
    
    
    
    // Abre: eliminar
    public static int eliminar(int pedidoID) {
        int status = 0;

        String query = "DELETE "
                     + "FROM Pedido "
                     + "WHERE pedidoID = ?;";

        try (Connection connection = conexionBD.crearConexion();
             PreparedStatement ps = connection.prepareStatement(query)) {

            // Asignar el pedidoID al PreparedStatement
            ps.setInt(1, pedidoID);

            // Ejecutar la eliminación
            status = ps.executeUpdate();

            if (status > 0) {
                System.out.println("PEDIDO ELIMINADO CON ÉXITO...");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL ELIMINAR EL PEDIDO...");
            e.printStackTrace();
        }

        return status;
    }
    // Cierra: eliminar
    
}
