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
    <title>Ver Pedidos</title>
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

        .content-header {
            display: flex;
            gap: 10px;
            margin-bottom: 20px;
        }

        .content-header input[type="button"] {
            background-color: #a168a3;
            color: white;
            padding: 10px 20px;
            border: none;
            cursor: pointer;
        }

        .content-header input[type="text"] {
            padding: 10px;
            width: 200px;
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

        a {
            color: white;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 8px;
            text-align: left;
            border: 1px solid #ddd;
        }

        th {
            background-color: #a168a3;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
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
            <div class="content-header">
                <h2>Lista de Pedidos</h2>
            </div>

            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>ID Pedido</th>
                            <th>Cliente ID</th>
                            <th>Empleado ID</th>
                            <th>Medio de Pago</th>
                            <th>Fecha de Pago</th>
                            <th>Fecha de Envío</th>
                            <th>Fecha de Entrega</th>
                            <th>Valor Total</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% 
                            List<Pedido> pedidos = (List<Pedido>) request.getAttribute("pedidos");
                            if (pedidos != null && !pedidos.isEmpty()) {
                                for (Pedido pedido : pedidos) {
                        %>
                            <tr>
                                <td><%= pedido.getPedidoID() %></td>
                                <td><%= pedido.getClienteID() %></td>
                                <td><%= pedido.getEmpleadoID() %></td>
                                <td><%= pedido.getMedioPago() %></td>
                                <td><%= pedido.getFechaPago() %></td>
                                <td><%= pedido.getFechaEnvio() %></td>
                                <td><%= pedido.getFechaEntrega() %></td>
                                <td><%= pedido.getValorTotal() %></td>
                            </tr>
                        <% 
                                }
                            } else {
                        %>
                            <tr>
                                <td colspan="8" style="text-align: center;">No hay pedidos registrados</td>
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



