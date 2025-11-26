<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="co.edu.konradlorenz.model.*" %>
<%@ page import="co.edu.konradlorenz.model.dao.*" %>
<%@ page import="co.edu.konradlorenz.model.enums.*" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ver Clientes</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
            color: black;
        }

        body {
            background-color: #ddd;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }

        .header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            background-color: #a168a3;
            padding: 15px 20px;
            color: white;
        }

        .header .logo {
            display: flex;
            align-items: center;
        }

        .header .logo img {
            width: 60px;
            height: 60px;
            margin-right: 15px;
        }

        .header .user-info {
            display: flex;
            gap: 30px;
        }

        .container {
            display: flex;
            flex-grow: 1;
        }

        .sidebar {
            width: 220px; /* Ajuste del ancho */
            background-color: #5e0063;
            padding: 20px 0;
            color: white;
            text-align: center;
            height: 100vh; /* Ajuste de altura */
            display: flex;
            flex-direction: column;
            justify-content: space-between; /* Distribución de elementos */
        }

        .sidebar ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .sidebar ul li {
            padding: 30px 0;
            font-size: 18px;
            cursor: pointer;
        }

        .sidebar ul li a {
            color: white;
            text-decoration: none;
            display: block;
        }

        .sidebar ul li:hover {
            background-color: #8c4ea8;
        }

        .content {
            flex-grow: 1;
            padding: 20px;
        }

        .table-container {
            background-color: #b48ebf;
            padding: 10px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        table, th, td {
            border: 1px solid black;
        }

        th, td {
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #a168a3;
            color: white;
        }
    </style>
</head>
<body>
    <div class="header">
        <div class="logo">
            <img src="imagenes/LogoKonrad.png" alt="SuperLorenz Logo">
            <h1>SuperLorenz</h1>
        </div>
        <div class="user-info">
            <span>${usuario.nombres}</span>
            <span><a href="index.jsp" style="color: white;">Cerrar Sesión</a></span>
        </div>
    </div>

    <div class="container">
        <div class="sidebar">
            <ul>
                <li><a href="ServletEmpleado?action=verMiCuenta">Mi Cuenta</a></li>
                <li><a href="ServletEmpleado?action=verProveedores">Ver Proveedores</a></li>
                <li><a href="ServletEmpleado?action=verClientes">Ver Clientes</a></li>
                <li><a href="ServletEmpleado?action=verComprasInsumos">Ver Compras Insumos</a></li>
                <li><a href="ServletEmpleado?action=verProductos">Ver Productos</a></li>
                <li><a href="ServletEmpleado?action=verPedidos">Ver Pedidos</a></li>
                <li><a href="ServletEmpleado?action=verKardex">Ver Kárdex</a></li>
            </ul>
        </div>

        <div class="content">
            <div class="table-container">
                <h1>Lista de Clientes</h1>
                <table>
                    <thead>
                        <tr>
                            <th>Documento</th>
                            <th>Nombre</th>
                            <th>Apellidos</th>
                            <th>Tipo de Cliente</th>
                            <th>Correo</th>
                            <th>Dirección</th>
                            <th>Fecha de Nacimiento</th>
                            <th>Autorización de Datos</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% 
                            List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientes");
                            if (clientes != null && !clientes.isEmpty()) {
                                for (Cliente cliente : clientes) {
                        %>
                                    <tr>
                                        <td><%= cliente.getNumeroDocumento() %></td>
                                        <td><%= cliente.getNombres() %></td>
                                        <td><%= cliente.getApellidos() %></td>
                                        <td><%= cliente.getTipoCliente() %></td>
                                        <td><%= cliente.getCorreo() %></td>
                                        <td><%= cliente.getDireccion() %></td>
                                        <td><%= cliente.getFechaNacimiento() %></td>
                                        <td><%= cliente.isAutorizacionDeDatos() %></td>
                                    </tr>
                        <% 
                                }
                            } else {
                        %>
                                <tr>
                                    <td colspan="8" style="text-align: center;">No hay clientes registrados</td>
                                </tr>
                        <% 
                            }
                        %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
