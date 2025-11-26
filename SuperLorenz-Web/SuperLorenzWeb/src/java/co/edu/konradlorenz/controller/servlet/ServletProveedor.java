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

@WebServlet(name = "ServletProveedor", urlPatterns = {"/ServletProveedor"})
public class ServletProveedor extends HttpServlet {
    
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
            case "verComprasInsumos":
                verComprasInsumos(request, response);
                break;
            case "verProductos":
                verProductos(request, response);
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
            
            Proveedor nuevoProveedor = new Proveedor();
            
            
            
            //Persona:
            String numeroDocumento = request.getParameter("numeroDocumento");
            TipoDocumento tipoDocumento = TipoDocumento.valueOf(request.getParameter("tipoDocumento")); // Enum conversion
            String nombres = request.getParameter("nombres");
            String apellidos = request.getParameter("apellidos");
            String celular = request.getParameter("celular");
            String correo = request.getParameter("correo");
            String password = request.getParameter("password");
            
            nuevoProveedor.setNumeroDocumento(numeroDocumento);
            nuevoProveedor.setTipoDocumento(tipoDocumento);
            nuevoProveedor.setNombres(nombres);
            nuevoProveedor.setApellidos(apellidos);
            nuevoProveedor.setCelular(celular);
            nuevoProveedor.setCorreo(correo);
            nuevoProveedor.setPassword(password);
            
            
            
            //Proveedor:
            TipoProveedor tipoProveedor = TipoProveedor.valueOf(request.getParameter("tipoProveedor")); // Enum conversion
            String nombreContacto = request.getParameter("nombreContacto");
            String numeroContacto = request.getParameter("numeroContacto");
            
            nuevoProveedor.setTipoProveedor(tipoProveedor);
            nuevoProveedor.setNombreContacto(nombreContacto);
            nuevoProveedor.setNumeroContacto(numeroContacto);


            int status = ProveedorDAO.agregar(nuevoProveedor);
        
            if (status > 0){
                response.sendRedirect("mensaje.jsp?msg=registro-exitoso");
            } else {
                response.sendRedirect("error.jsp?msg=registro-fallido");
            }
            //if
            
        } catch (Exception e) {
            System.out.println("ERROR AL REGISTRAR EL PROVEEDOR...");
            e.printStackTrace();
        }
        
    }
    //Cierra: register
    
    
    
    
    
    //Abre: login
    private void login(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        String correo = request.getParameter("correo");
        String password = request.getParameter("password");

        Proveedor proveedor = ProveedorDAO.iniciarSesion(correo, password);

        if (proveedor != null) { 
            
            // Guardar el nombre del usuario en la sesión
            request.getSession().setAttribute("nombres", proveedor.getNombres());
            
            response.sendRedirect("portalProveedor.jsp");
            
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
            request.getRequestDispatcher("/verMiCuentaProveedor.jsp").forward(request, response);
        } else {
            response.sendRedirect("loginEmpleadoAdmin.jsp");
        }
        
    }
    // Cierra: verMiCuenta
    
    
    
    
    
    // Abre: verComprasInsumos
    private static void verComprasInsumos(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        List<CompraInsumos> comprasInsumos = CompraInsumosDAO.obtenerTodos();
        request.setAttribute("comprasInsumos", comprasInsumos);
        request.getRequestDispatcher("verCompraInsumosProveedor.jsp").forward(request, response);
        
    }
    // Cierra: verComprasInsumos
    
    
    
    
    
    // Abre: verProductos
    private static void verProductos(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        List<Producto> productos = ProductoDAO.obtenerTodos();
        List<Precio> precios = PrecioDAO.obtenerTodos();
        request.setAttribute("productos", productos);
        request.setAttribute("precios", precios);
        request.getRequestDispatcher("verProductosProveedor.jsp").forward(request, response);
        
    }
    // Cierra: verProductos
    
    
    
    
    
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
        return "Servlet para la gestión de proveedores";
    }
    // </editor-fold>
    
}