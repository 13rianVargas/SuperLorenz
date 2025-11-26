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
    <title>Mi Cuenta</title>
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
            padding: 40px 20px 20px 20px;
            display: flex;
            justify-content: center;
            align-items: flex-start;
        }
        .info-container {
            background-color: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            width: 60%;
            text-align: center;
        }
        .info-container h1 {
            margin-bottom: 20px;
            font-size: 24px;
            color: #333;
        }
        .info-container table {
            width: 100%;
            border-collapse: collapse;
        }
        .info-container th, .info-container td {
            padding: 10px;
            border: 1px solid #d8aad7;
            text-align: left;
        }
        .info-container th {
            background-color: #f3e5f5;
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
            <div class="info-container">
                <h1>Información de la Cuenta</h1>
                <table>
                    <tr>
                        <th>Nombres</th>
                        <td>${usuario.nombres}</td>
                    </tr>
                    <tr>
                        <th>Apellidos</th>
                        <td>${usuario.apellidos}</td>
                    </tr>
                    <tr>
                        <th>Correo</th>
                        <td>${usuario.correo}</td>
                    </tr>
                    <tr>
                        <th>Celular</th>
                        <td>${usuario.celular}</td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
