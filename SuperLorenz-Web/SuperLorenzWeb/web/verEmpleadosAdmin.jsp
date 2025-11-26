<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="co.edu.konradlorenz.model.Empleado" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ver Empleados</title>
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
            <div class="content-header">
                <h2>Lista de Empleados</h2>
            </div>

            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>ID Empleado</th>
                            <th>Cargo</th>
                            <th>Dependencia</th>
                            <th>Tipo de Contrato</th>
                            <th>EPS</th>
                            <th>ARL</th>
                            <th>Contacto de Emergencia</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% 
                            List<Empleado> empleados = (List<Empleado>) request.getAttribute("empleados");
                            if (empleados != null && !empleados.isEmpty()) {
                                for (Empleado empleado : empleados) {
                        %>
                            <tr>
                                <td><%= empleado.getEmpleadoID() %></td>
                                <td><%= empleado.getCargo() %></td>
                                <td><%= empleado.getDependencia() %></td>
                                <td><%= empleado.getTipoContrato() %></td>
                                <td><%= empleado.getEps() %></td>
                                <td><%= empleado.getArl() %></td>
                                <td><%= empleado.getNombreEmergencia() + " - " + empleado.getCelularEmergencia() %></td>
                            </tr>
                        <% 
                                }
                            } else {
                        %>
                            <tr>
                                <td colspan="7" style="text-align: center;">No hay empleados registrados</td>
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

