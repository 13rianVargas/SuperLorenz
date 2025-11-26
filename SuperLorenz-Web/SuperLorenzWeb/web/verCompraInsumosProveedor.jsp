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
    <title>Compra Insumos</title>
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
            width: 960px;
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

        #content h1 {
            margin-top: 0;
            text-align: center;
        }

        .insumo-card {
            display: inline-block;
            width: 25%;
            margin: 10px;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 2px 2px 5px rgba(0,0,0,0.1);
            text-align: center;
            background-color: #fff;
            transition: transform 0.3s ease;
        }

        .insumo-card:hover {
            transform: translateY(-5px);
        }

        .insumo-card h3 {
            margin: 0;
            color: #6a1b9a;
        }

        .insumo-card p {
            color: #555;
        }

        .insumo-card .estado {
            font-weight: bold;
            color: #ff5722;
        }

        .insumo-card .btn-comprar {
            background-color: #6a1b9a;
            color: #fff;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 5px;
            display: inline-block;
            margin-top: 10px;
        }

        .insumo-card .btn-comprar:hover {
            background-color: #8e24aa;
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
                <li><a href="ServletProveedor?action=verMiCuenta">Mi Cuenta</a></li>
                <li><a href="ServletProveedor?action=verComprasInsumos">Ver Compras Insumos</a></li>
                <li><a href="ServletProveedor?action=verProductos">Ver Productos</a></li>
            </ul>
        </div>
    
    <div id="content">
        <h1>Compra Insumos</h1>
        <div class="insumos-container">
            <% 
                List<CompraInsumos> compras = (List<CompraInsumos>) request.getAttribute("compras");
                if (compras != null && !compras.isEmpty()) {
                    for (CompraInsumos compra : compras) {
            %>
                <div class="insumo-card">
                    <h3>Compra ID: <%= compra.getCompraInsumosID() %></h3>
                    <p><strong>Proveedor Producto ID:</strong> <%= compra.getProveedorProductoID() %></p>
                    <p><strong>Cantidad:</strong> <%= compra.getCantidad() %></p>
                    <p><strong>Estado:</strong> <span class="estado"><%= compra.getEstado() %></span></p>
                    <p><strong>Fecha de Compra:</strong> <%= compra.getFechaCompra() %></p>
                    <p><strong>Fecha de Entrega:</strong> <%= compra.getFechaEntrega() %></p>
                    <p><strong>Valor Total:</strong> $<%= compra.getValorTotal() %></p>
                    <a href="comprarInsumos?id=<%= compra.getCompraInsumosID() %>" class="btn-comprar">Comprar</a>
                </div>
            <% 
                    }
                } else {
            %>
                <p>No hay compras de insumos registradas.</p>
            <% 
                }
            %>
        </div>
    </div>
</body>
</html>

