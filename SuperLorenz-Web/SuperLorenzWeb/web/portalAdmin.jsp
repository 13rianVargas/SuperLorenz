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
    <title>SuperLorenz - Administrador</title>
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
        }

        .sidebar {
            width: 220px;
            background-color: #5e0063;
            padding: 20px 0;
            color: white;
            text-align: center;
            height: 100vh;
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

        .sidebar ul li:hover {
            background-color: #8c4ea8;
        }

        .content {
            flex-grow: 1;
            padding: 20px;
        }

        .welcome-message {
            background-color: #b48ebf;
            padding: 20px;
            text-align: center;
            margin-top: 20px;
            font-size: 20px;
            color: #333;
        }

        a {
            color: white;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
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
            <span><a href="index.jsp">Cerrar Sesión</a></span>
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
                <li><a href="ServletAdmin?action=verKardex">Kárdex</a></li>
            </ul>
        </div>

        <div class="content">
            <!-- Mensaje de bienvenida -->
            <div class="welcome-message">
                <h2>¡Bienvenido, ${usuario.nombres}!</h2>
                <p>Este es el panel de administración de SuperLorenz. Aquí podrás gestionar TODAS las tablas.</p>
                <p>Selecciona una opción en el menú lateral para comenzar.</p>
            </div>

        </div>
    </div>

</body>
</html>

