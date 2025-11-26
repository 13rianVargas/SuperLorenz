package co.edu.konradlorenz.controller.servlet;

import co.edu.konradlorenz.model.*;
import co.edu.konradlorenz.model.dao.*;
import co.edu.konradlorenz.model.enums.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.text.*;
import java.util.*;

@WebServlet(name = "ServletCliente", urlPatterns = {"/ServletCliente"})
public class ServletCliente extends HttpServlet {
    
    // Abre: processRequest
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action"); // Identifica la acción solicitada
        
        switch (action) {
            case "register" -> register(request, response);
            case "login" -> login(request, response);
            case "verClientes" -> verClientes(request, response);
            default -> response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
        }
        
    }
    // Cierra: processRequest
    
    
    
    
    
    //Abre: register
    private void register(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        try{
            
            Cliente nuevoCliente = new Cliente();
            
            
            
            //Persona:
            String numeroDocumento = request.getParameter("numeroDocumento");
            TipoDocumento tipoDocumento = TipoDocumento.valueOf(request.getParameter("tipoDocumento")); // Enum conversion
            String nombres = request.getParameter("nombres");
            String apellidos = request.getParameter("apellidos");
            String celular = request.getParameter("celular");
            String correo = request.getParameter("correo");
            String password = request.getParameter("password");
            
            nuevoCliente.setNumeroDocumento(numeroDocumento);
            nuevoCliente.setTipoDocumento(tipoDocumento);
            nuevoCliente.setNombres(nombres);
            nuevoCliente.setApellidos(apellidos);
            nuevoCliente.setCelular(celular);
            nuevoCliente.setCorreo(correo);
            nuevoCliente.setPassword(password);            
            
            
            
            //Cliente:
            TipoCliente tipoCliente = TipoCliente.valueOf(request.getParameter("tipoCliente")); // Enum conversion
            String direccion = request.getParameter("direccion");
                
                //Conversión de fecha de String a Date
                String fechaStr = request.getParameter("fechaNacimiento");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaNacimiento = formatter.parse(fechaStr);
                
            EstadoCivil estadoCivil = EstadoCivil.valueOf(request.getParameter("estadoCivil")); // Enum conversion
            boolean autorizacionDeDatos = Boolean.parseBoolean(request.getParameter("autorizacionDeDatos"));
            
            nuevoCliente.setTipoCliente(tipoCliente);
            nuevoCliente.setDireccion(direccion);
            nuevoCliente.setFechaNacimiento(fechaNacimiento);
            nuevoCliente.setEstadoCivil(estadoCivil);
            nuevoCliente.setAutorizacionDeDatos(autorizacionDeDatos);

            int status = ClienteDAO.agregar(nuevoCliente);
        
            if (status > 0){
                response.sendRedirect("mensaje.jsp?msg=registro-exitoso");
            } else {
                response.sendRedirect("error.jsp?msg=registro-fallido");
            }
            //if
            
        } catch (ParseException e) {
            System.out.println("ERROR AL REGISTRAR EL CLIENTE...");
            e.printStackTrace();
        }
        
    }
    //Cierra: register
    
    
    
    
    
    //Abre: login
    private void login(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        String correo = request.getParameter("correo");
        String password = request.getParameter("password");

        Cliente cliente = ClienteDAO.iniciarSesion(correo, password);

        if (cliente != null) { 
            
            // Guardar el nombre del usuario en la sesión
            request.getSession().setAttribute("nombres", cliente.getNombres());
            
            response.sendRedirect("indexCliente.jsp");
            
        } else {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("<h1>Error: Credenciales incorrectas</h1>");
                out.println("<p>Por favor, intenta nuevamente.</p>");
            }
        }
    }
    // Cierra: login
    
    
    
    
    
    // Abre: verClientes
    private void verClientes(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        List<Cliente> clientes = ClienteDAO.obtenerTodos();
        request.setAttribute("clientes", clientes);
        request.getRequestDispatcher("/botonClientes.jsp").forward(request, response);
        
    }
    // Cierra: verClientes
    
    
    
    
    
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
        return "Servlet para la gestión de clientes";
    }
    // </editor-fold>
    
}