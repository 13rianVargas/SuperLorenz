package co.edu.konradlorenz.controller.servlet;

import co.edu.konradlorenz.model.*;
import co.edu.konradlorenz.model.dao.*;
import co.edu.konradlorenz.model.enums.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import static java.lang.System.out;
import java.text.*;
import java.util.*;

@WebServlet(name = "ServletEmpleado", urlPatterns = {"/ServletEmpleado"})
public class ServletEmpleado extends HttpServlet {
    
    // Abre: processRequest
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");

        System.out.println("Action recibida: " + action);
        
        if (action == null || action.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no especificada o inválida.");
            return;
        }
        
        switch (action) {
            case "register":
                register(request, response);
                break;
            case "login":
                login(request, response);
                break;
            case "verMiCuenta":
                verMiCuenta(request, response);
                break;
            case "verProveedores":
                verProveedores(request, response);
                break;
            case "verClientes":
                verClientes(request, response);
                break;
            case "verComprasInsumos":
                verComprasInsumos(request, response);
                break;
            case "verProductos":
                verProductos(request, response);
                break;
            case "verPedidos":
                verPedidos(request, response);
                break;
            case "verKardex":
                verKardex(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
                break;
        }
        
    }
    // Cierra: processRequest
    
    
    
    
    
    //Abre: register
    private void register(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        try{
            
            Empleado nuevoEmpleado = new Empleado();
            
            
            
            //Persona:
            String numeroDocumento = request.getParameter("numeroDocumento");
            TipoDocumento tipoDocumento = TipoDocumento.valueOf(request.getParameter("tipoDocumento")); // Enum conversion
            String nombres = request.getParameter("nombres");
            String apellidos = request.getParameter("apellidos");
            String celular = request.getParameter("celular");
            String correo = request.getParameter("correo");
            String password = request.getParameter("password");
            
            nuevoEmpleado.setNumeroDocumento(numeroDocumento);
            nuevoEmpleado.setTipoDocumento(tipoDocumento);
            nuevoEmpleado.setNombres(nombres);
            nuevoEmpleado.setApellidos(apellidos);
            nuevoEmpleado.setCelular(celular);
            nuevoEmpleado.setCorreo(correo);
            nuevoEmpleado.setPassword(password);
            
            
            
            //Empleado:
            Cargo cargo = Cargo.valueOf(request.getParameter("cargo")); // Enum conversion
            Dependencia dependencia = Dependencia.valueOf(request.getParameter("dependencia")); // Enum conversion
            TipoContrato tipoContrato = TipoContrato.valueOf(request.getParameter("tipoContrato")); // Enum conversion
            String eps = request.getParameter("eps");
            String arl = request.getParameter("arl");
            String nombreEmergencia = request.getParameter("nombreEmergencia");
            String celularEmergencia = request.getParameter("celularEmergencia");

            nuevoEmpleado.setCargo(cargo);
            nuevoEmpleado.setDependencia(dependencia);
            nuevoEmpleado.setTipoContrato(tipoContrato);
            nuevoEmpleado.setEps(eps);
            nuevoEmpleado.setArl(arl);
            nuevoEmpleado.setNombreEmergencia(nombreEmergencia);
            nuevoEmpleado.setCelularEmergencia(celularEmergencia);

            int status = EmpleadoDAO.agregar(nuevoEmpleado);
        
            if (status > 0){
                response.sendRedirect("mensaje.jsp?msg=registro-exitoso");
            } else {
                response.sendRedirect("error.jsp?msg=registro-fallido");
            }
            //if
            
        } catch (Exception e) {
            System.out.println("ERROR AL REGISTRAR EL EMPLEADO...");
            e.printStackTrace();
        }
        
    }
    //Cierra: register
    
    
    
    
    
    //Abre: login
    private void login(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        String correo = request.getParameter("correo");
        String password = request.getParameter("password");

        Empleado empleado = EmpleadoDAO.iniciarSesion(correo, password);

        if (empleado != null) {
            
            // Guardar el nombre del usuario en la sesión
            request.getSession().setAttribute("nombres", empleado.getNombres());
            
            Set<String> rolesAdmin = new HashSet<>(Arrays.asList("GERENTE_GENERAL", "SUBGERENTE", "JEFE_DE_RECURSOS_HUMANOS"));
            String cargoEmpleado = empleado.getCargo().name();
            
            // Depuración
            System.out.println("Cargo del empleado recuperado: " + cargoEmpleado);
            
            // Verificar si el empleado tiene rol de admin
            if (rolesAdmin.contains(cargoEmpleado)) {
                response.sendRedirect("portalAdmin.jsp");
            } else {
                response.sendRedirect("portalEmpleado.jsp");
            }
            
        } else {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("<h1>Error: Credenciales incorrectas</h1>");
                out.println("<p>Por favor, intenta nuevamente.</p>");
            }
        }
    }
    // Cierra: login
    
    
    
    
    
    // Abre: verMiCuenta
    private static void verMiCuenta(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        Empleado empleado = (Empleado) request.getSession().getAttribute("empleado");
        
        if (empleado != null) {
            request.setAttribute("empleado", empleado);
            request.getRequestDispatcher("/verMiCuentaEmpleado.jsp").forward(request, response);
        } else {
            response.sendRedirect("loginEmpleadoAdmin.jsp");
        }
        
    }
    // Cierra: verMiCuenta
    
    
    
    
    
    // Abre: verProveedores
    private static void verProveedores(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        List<Proveedor> proveedores = ProveedorDAO.obtenerTodos();
        request.setAttribute("proveedores", proveedores);
        request.getRequestDispatcher("/verProveedoresEmpleado.jsp").forward(request, response);
        
    }
    // Cierra: verProveedores
    
    
    
    
    
    // Abre: verClientes
    private static void verClientes(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        List<Cliente> clientes = ClienteDAO.obtenerTodos();
        request.setAttribute("clientes", clientes);
        request.getRequestDispatcher("verClientesEmpleado.jsp").forward(request, response);
        
    }
    // Cierra: verClientes
    
    
    
    
    
    // Abre: verComprasInsumos
    private static void verComprasInsumos(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        List<CompraInsumos> comprasInsumos = CompraInsumosDAO.obtenerTodos();
        request.setAttribute("comprasInsumos", comprasInsumos);
        request.getRequestDispatcher("verCompraInsumosEmpleado.jsp").forward(request, response);
        
    }
    // Cierra: verComprasInsumos
    
    
    
    
    
    // Abre: verProductos
    private static void verProductos(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        List<Producto> productos = ProductoDAO.obtenerTodos();
        List<Precio> precios = PrecioDAO.obtenerTodos();
        request.setAttribute("productos", productos);
        request.setAttribute("precios", precios);
        request.getRequestDispatcher("verProductosEmpleado.jsp").forward(request, response);
        
    }
    // Cierra: verProductos
    
    
    
    
    
    // Abre: verPedidos
    private static void verPedidos(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        List<Pedido> pedidos = PedidoDAO.obtenerTodos();
        List<ProductoPedido> productoPedido = ProductoPedidoDAO.obtenerTodos();// Se puede cambiar por uno que solo traiga la cantidad.
        request.setAttribute("pedidos", pedidos);
        request.setAttribute("productoPedido", productoPedido);
        request.getRequestDispatcher("verPedidosEmpleado.jsp").forward(request, response);
        
    }
    // Cierra: verPedidos
    
    
    
    
    
    // Abre: verKardex
    private static void verKardex(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        List<Kardex> listaKardex = KardexDAO.obtenerTodos();
        request.setAttribute("listaKardex", listaKardex);
        request.getRequestDispatcher("verKardexEmpleado.jsp").forward(request, response);
        
    }
    // Cierra: verKardex
    
    
    
    
    
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet para la gestión de empleados";
    }
    // </editor-fold>
    
}