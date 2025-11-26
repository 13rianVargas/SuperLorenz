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
    <title>Ver Proveedores</title>
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
            padding: 0; /* Elimina el padding entre el contenido y el menú */
        }

        .sidebar {
            width: 220px;
            background-color: #5e0063;
            padding: 20px 0;
            color: white;
            text-align: center;
            height: 100vh; /* La barra lateral ocupa toda la altura */
            display: flex;
            flex-direction: column;
            justify-content: space-between;
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

        .table-container {
            background-color: #b48ebf;
            padding: 10px;
        }

        .table-row {
            background-color: #d8aad7;
            height: 40px;
            margin-bottom: 10px;
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
                <li><a href="ServletAdmin?action=verMiCuenta">Mi Cuenta</a></li>
                <li><a href="ServletAdmin?action=verEmpleados">Ver Empleados</a></li>
                <li><a href="ServletAdmin?action=verProveedores">Ver Proveedores</a></li>
                <li><a href="ServletAdmin?action=verClientes">Ver Clientes</a></li>
                <li><a href="ServletAdmin?action=verComprasInsumos">Ver Compras Insumos</a></li>
                <li><a href="ServletAdmin?action=verProductos">Ver Productos</a></li>
                <li><a href="ServletAdmin?action=verPedidos">Ver Pedidos</a></li>
                <li><a href="ServletAdmin?action=verKardex">Ver Kárdex</a></li>
            </ul>
        </div>

        <div class="content">
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>ID Proveedor</th>
                            <th>Tipo Proveedor</th>
                            <th>Nombre Contacto</th>
                            <th>Número Contacto</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% 
                            List<Proveedor> proveedores = (List<Proveedor>) request.getAttribute("proveedores");
                            if (proveedores != null && !proveedores.isEmpty()) {
                                for (Proveedor proveedor : proveedores) {
                        %>
                                    <tr>
                                        <td><%= proveedor.getProveedorID() %></td>
                                        <td><%= proveedor.getTipoProveedor() %></td>
                                        <td><%= proveedor.getNombreContacto() %></td>
                                        <td><%= proveedor.getNumeroContacto() %></td>
                                    </tr>
                        <% 
                                }
                            } else {
                        %>
                                <tr>
                                    <td colspan="4" style="text-align: center;">No hay proveedores registrados</td>
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
