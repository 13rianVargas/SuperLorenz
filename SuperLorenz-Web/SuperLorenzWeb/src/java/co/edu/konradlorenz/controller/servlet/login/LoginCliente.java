package co.edu.konradlorenz.controller.servlet.login;

import co.edu.konradlorenz.model.*;
import co.edu.konradlorenz.model.dao.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class LoginCliente extends HttpServlet {

    // Manejar solicitudes comunes si es necesario
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Si accede con GET, redirigir al formulario de login
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener la acción desde el formulario
        String accion = request.getParameter("accion");

        if ("Ingresar".equalsIgnoreCase(accion)) {
            // Obtener parámetros del formulario
            String correo = request.getParameter("correo");
            String password = request.getParameter("password");

            // Validar credenciales
            Cliente cliente = ClienteDAO.iniciarSesion(correo, password);
            
            System.out.println(cliente.toString());

            if (cliente != null) {
                // Credenciales válidas: crear sesión y redirigir a cliente.jsp
                HttpSession sesion_cli = request.getSession(true);
                sesion_cli.setAttribute("nombreUsuario", cliente.getNombres());
                sesion_cli.setAttribute("cliente", cliente); // Guarda el cliente en la sesión

                // Redirigir al dashboard del cliente
                request.getRequestDispatcher("cliente.jsp").forward(request, response);
            } else {
                // Credenciales inválidas: devolver al login con mensaje de error
                request.setAttribute("error", "Credenciales inválidas. Intente de nuevo.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } else {
            // Acción no reconocida: redirigir al formulario de inicio de sesión
            request.setAttribute("error", "Acción no válida.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet para la gestión de la autenticación de clientes.";
    }
}