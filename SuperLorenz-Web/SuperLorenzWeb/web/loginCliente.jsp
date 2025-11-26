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
        <title>Inicio de Sesión - SuperLorenz</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background: linear-gradient(135deg, #6a11cb, #2575fc);
                color: white;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }

            h1 {
                text-align: center;
                margin-bottom: 20px;
                font-size: 2rem;
                text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.3);
            }

            form {
                background: rgba(255, 255, 255, 0.1);
                border-radius: 15px;
                box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
                padding: 70px;
                max-width: 400px;
                width: 100%;
            }

            label {
                display: block;
                font-weight: bold;
                margin-bottom: 5px;
            }

            input[type="email"], input[type="password"] {
                width: 100%;
                padding: 10px;
                margin-bottom: 15px;
                border: none;
                border-radius: 5px;
                background: rgba(255, 255, 255, 0.2);
                color: white;
                font-size: 1rem;
                outline: none;
                box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.2);
            }

            input::placeholder {
                color: rgba(255, 255, 255, 0.7);
            }

            button {
                width: 100%;
                padding: 10px;
                background: #822fa1;
                color: white;
                font-size: 1rem;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                transition: background 0.3s ease;
            }

            button:hover {
                background: #9b50b6;
            }

            button:active {
                background: #6c2284;
            }

            .extra-options {
                text-align: center;
                margin-top: 10px;
            }

            .extra-options a {
                color: #e0c3fc;
                text-decoration: none;
                transition: color 0.3s ease;
            }

            .extra-options a:hover {
                color: white;
            }
        </style>
    </head>
    <body>
        <form id="loginForm" name="loginForm" action="ServletCliente" method="POST">

            <h1>Iniciar Sesión</h1>

            <!-- Campo oculto para enviar la acción -->
            <input type="hidden" name="action" value="login">

            <!-- Campo de correo -->
            <label for="email">Correo Electrónico:</label>
            <input type="email" id="correo" name="correo" placeholder="Ingrese su correo electrónico" required>

            <!-- Campo de contraseña -->
            <label for="password">Contraseña:</label>
            <input type="password" id="password" name="password" placeholder="Ingrese su contraseña" required>

            <!-- Botón de envío -->
            <button type="submit">Ingresar</button>

            <!-- Opciones extra -->
            <div class="extra-options">
                <p>¿Olvidaste tu contraseña? <a href="#">Recuperarla</a></p>
                <p>¿No tienes cuenta? <a href="#">Regístrate</a></p>
            </div>

        </form>
    </body>
</html>